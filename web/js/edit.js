function attachEditActions() {
    editName();
    editAmount();
    editDate();
    editLabels();
}

function editName() {
    var $this = $('.dblclick.name');
    $this.editable('/expenses/editName', {
        event: "dblclick",
        id: "expenseId",
        name: "name",
        submitdata: {accountId: $this.attr('aid') }
    });
}

function editAmount() {
    var $this = $('.dblclick.amount')
    $this.editable('/expenses/editAmount', {
        event: "dblclick",
        id: "expenseId",
        name: "amount" ,
        submitdata: {accountId: $this.attr('aid') }
    });
}

function editDate() {
    $this = $('.dblclick.date');
    $this.editable('/expenses/editDate', {
        event: "dblclick",
        id: "expenseId",
        name: "date" ,
        submitdata: {accountId: $this.attr('aid') } ,
        callback: function() {
            alert("das");
            $( ".editdatepicker" ).datepicker({
                                        dateFormat: "dd-mm-yy" ,
                                        showOn: "focus"});
        }
    });
}

function editDatePicker() {
    $(function() {
        $( ".editdatepicker" ).datepicker({ dateFormat: "dd-mm-yy" });
    });
}

function editLabels() {
    $this = $('.dblclick.labels');
    $('.dblclick_4').editable('/expenses/editLabels', {
        event: "dblclick",
        id: "expenseId",
        name: "labels" ,
        submitdata: {accountId: $this.attr('aid') }
    });
}