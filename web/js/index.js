$(document).ready(function() {
    //$.cookie("accountId", "14");
    var accountId = $.cookie("accountId");
    if (null == accountId) {
        console.log("cookie is null");
        loadLogin();
    } else {
        console.log("cookie not null");
        loadExpenses(accountId);
    }
});

function loadExpenses(accountId) {
    $.cookie("lastAccessed", $.now());
    var expensesListId = "#expenses-list";

    getHtml("/expenses/html/expenses.html", function(data){
        $("#main").append(data);

        //add logout function
        logout();

        //add multiple select options
        attachMultipleOptions(accountId, "#labels-add");

        //todo: resolve validate problem: check validation before submitting
        validateForm();
        //filter
        dateRange();
        attachMultipleOptions(accountId, "#labels-filter");
        datePicker();


        //getExpensesList(accountId, expensesListId);
        //default index list
        var params = getUrlParameters();
        if (params.from == undefined && params.to == undefined) {
            expenseForCurrentWeek(accountId);
        } else {
            getExpensesList(accountId, expensesListId);
        }



        // Attach a submit handler to the add expense form
        submitHandlerAddExpenseForm(accountId);

        // Attach a submit handler to display expenses by period form
        submitHandlerExpenseByPeriodForm(accountId);

        // Attach a submit handler to display expenses filtered by labels
        submitHandlerFilterExpenseForm(accountId);


        $('#currentWeek').click(function () {
            expenseForCurrentWeek(accountId);
        });
        $('#currentMonth').click(function () {
            expenseForCurrentMonth(accountId);
        });
        $('#currentYear').click(function () {
            expenseForCurrentYear(accountId);
        });
        $('#allExpenses').click(function () {
            deleteFromDate();
            deleteToDate();
            getExpensesList(accountId, expensesListId);
        });

    });
}


function getExpensesList(accountId, listId) {
    $.cookie("lastAccessed", $.now());
    var params = getUrlParameters();

    if (params.from != undefined) {
        var from = params.from;
    } else {
        var from = null;
    }

    if (params.to != undefined) {
        var to = params.to;
    } else {
        var to = null;
    }

    if (params.labels != undefined) {
        var labels = params.labels;
    } else {
        var labels = null;
    }

    var url = printf("/expenses/expenses?accountId=%s&from=%s&to=%s&labels=%s",
                    accountId, from, to, labels);
    getUrl(url, function(data){
        $(listId).html("");

        for (var i in data) {
             var entry = data[i];
             //console.log(entry);
             var labels = entry.labels;
             var labelsString = [];

             //append all labels into a string
             for (var j in labels) {
                 var e = labels[j];
                 labelsString.push(e.label);
             }
             labelsString = labelsString.join(", ");

             //display the expense list
             $(listId).append(printf(
                  '<div class="table-row">' +
                      '<div id="%s">%s</div>' +
                      '<div class="dblclick name" id="%s" aid="%s">%s</div>' +
                      '<div class="dblclick amount" id="%s" aid="%s">%s</div>' +
                      '<div class="dblclick date editdatepicker" id="%s" aid="%s">%s</div>' +
                      '<div class="dblclick labels" id="%s" aid="%s">%s&#160;</div>' +
                      '<div>' +
                        '<a class="delete" href="#" expenseId="%s" accountId="%s">delete</a>' +
                      '</div>' +
                  '</div>' ,
                  entry.id, entry.id,
                  entry.id, accountId, entry.name,
                  entry.id, accountId, entry.amount,
                  entry.id, accountId, entry.date,
                  entry.id, accountId, labelsString,
                  entry.id, accountId
             ));
        }

        getTotalAmount(listId);

        attachDeleteActions();
        attachHoverAction();
        attachEditActions();
    });
}

function getTotalAmount(listId) {
    $.cookie("lastAccessed", $.now());
    var sum = 0;
    $(".amount").each(function(index){
      sum += parseFloat($(this).text());
    });

    $(listId).append('<div class="table-row">' +
                          '<div>&#160;</div>' +
                          '<div>&#160;</div>' +
                          '<div id="totalAmount">'+sum+'</div>' +
                          '<div>&#160;</div>' +
                          '<div>&#160;</div>' +
                          '<div>&#160;</div>' +
                       '</div>');

}


function validateForm() {
    var $messages = $('#error-message-wrapper');

    var valid = $.validate({
        modules: 'date',
        errorMessagePosition : $messages,
        scrollToTopOnError      : false,     // Set this property to true if you have a long form
        onError : function() {
                    console.log("onError msg");
                    return false;
                  },
        onSuccess: function() {
                    console.log("onSuccess msg");
                    return true;
        }
    });
}


function dateRange() {
    $(function() {
        $( "#from" ).datepicker({
            dateFormat: "dd-mm-yy",
            defaultDate: "+1w",
            changeMonth: true,
            numberOfMonths: 3,
            onClose: function( selectedDate ) {
                $( "#to" ).datepicker( "option", "minDate", selectedDate );
            }
        });
        $( "#to" ).datepicker({
            dateFormat: "dd-mm-yy",
            defaultDate: "+1w",
            changeMonth: true,
            numberOfMonths: 3,
            onClose: function( selectedDate ) {
                $( "#from" ).datepicker( "option", "maxDate", selectedDate );
            }
        });
    });
}

function datePicker() {
    $(function() {
        $( "#datepicker" ).datepicker({ dateFormat: "dd-mm-yy" });
    });
}


function attachMultipleOptions(accountId, htmlLabelsId) {
    //console.log("load labels");
    getUrl("/expenses/labels?accountId=" + accountId, function(data){
        for (var i in data) {
            var entry = data[i];
            $(htmlLabelsId).append(printf(
                '<option value="%s">%s</option>',
                entry.id, entry.label
            ))
        }

        $(function() {
            $(htmlLabelsId).multiselect({
                includeSelectAllOption: false
            });
        });

    });
}


function submitHandlerAddExpenseForm(accountId) {
    $( "#addExpense" ).submit(function( event ) {
        //console.log("submit add expense");
        // Stop form from submitting normally
        event.preventDefault();

        // Get some values from elements on the page:
        var $form  = $( this );
        var name   = $form.find( "input[name='name']"   ).val();
        var amount = $form.find( "input[name='amount']" ).val();
        var date   = $form.find( "input[name='date']"   ).val();
        var labels = [];
        $form.find( "#select-labels-add >* li.active >* input[name='multiselect']" ).each(function() {
            labels.push($(this).val());
        });
        labels = labels.join(',');

        // Send the data using post
        var data = $.post( '/expenses/expenses',
                            { accountId: accountId,
                              name: name ,
                              amount: amount,
                              date: date,
                              labels: labels
                            });

        data.success(function() {
            getExpensesList(accountId, '#expenses-list');
        });


        data.error(function() {
            console.log("Error while adding the expense");
        });
    });
}

function submitHandlerExpenseByPeriodForm(accountId) {
    $( "#filter-date" ).submit(function(event) {
        //console.log("display expenses by period");
        // Stop form from submitting normally
        event.preventDefault();

        // Get form values
        var $form  = $( this );
        var from   = $form.find( "input[name='from']"   ).val();
        var to     = $form.find( "input[name='to']"     ).val();

        if (from == "") {
            deleteFromDate();
        } else {
            changeFromDate(from);
        }

        if (to == "") {
            deleteToDate();
        } else {
            changeToDate(to);
        }

        getExpensesList(accountId, '#expenses-list');

    });
}

function submitHandlerFilterExpenseForm(accountId) {
    $( "#filter-labels" ).submit(function( event ) {
        // Stop form from submitting normally
        event.preventDefault();

        // Get some values from elements on the page:
        var $form  = $( this );
        //var date   = $form.find( "input[name='date']"   ).val();
        var labels = [];
        $form.find( "#select-labels-filter >* li.active >* input[name='multiselect']" ).each(function() {
            labels.push($(this).val());
        });
        labels = labels.join(',');
        console.log("filter label ids: " + labels);

        var params = getUrlParameters();
        console.log(params);


        getExpensesList(accountId, '#expenses-list');

    });
}
