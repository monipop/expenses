$(document).ready(function() {
    validateForm();

    // Attach a submit handler to the form
    $( "#addLabel" ).submit(function( event ) {
        console.log("s-a dat submit");
        // Stop form from submitting normally
        event.preventDefault();


        // Get some values from elements on the page:
        var $form  = $( this );
        var name   = $( "input[name='name']"   ).val();
        var url    = $form.attr( "action" );
        var accountId = 14;

        // Send the data using post
        var data = $.post( '/expenses/labels',
                            { accountId: accountId,
                              name: name
                            });

        data.success(function() {
            loadLabels(accountId, '#labels-list');
        });

        data.error(function() {
            console.log("Error while adding the expense");
        });

        data.complete(function() {
            //document.location.reload();
            loadLabels(14, '#labels-list');

        });
    });

    loadLabels(14, '#labels-list');

});


function loadLabels(accountId, listId) {
    getUrl("/expenses/labels?accountId=" + accountId, function(data){
        $(listId).html("");
        for (var i in data) {
            var entry = data[i];
            $(listId).append(printf(
                '<div class="table-row">' +
                    '<div>%s</div>' +
                    '<div>%s</div>'+
                    '<div><a class="delete" href="#" labelId="%s" accountId="%s">delete</a></div>'+
                '</div>'
                ,entry.id, entry.label, entry.id, accountId
            ));
        }
        attachDeleteAction();
        attachHoverAction();
    });
}


function attachDeleteAction() {
    $(".delete").click(function() {
        var accountId = $(this).attr("accountId");
        var labelId   = $(this).attr("labelId");
        var url = printf("/expenses/labels?accountId=%s&labelId=%s", accountId, labelId);
        console.log(url);
        if (!confirm(printf("Are you sure you want to delete label %s?", labelId))) {
            return false;
        }
        $.ajax({
            type: 'DELETE',
            url: url,
            //dataType: 'json',
            success: function(data) {
                console.log("Entry is deleted.");
            },
            error: function(data) {
                console.log("error when trying to delete expense.");
            },
            complete: function(data) {
                document.location.reload();
            }
        });
    });

}

function validateForm() {
    var $messages = $('#error-message-wrapper');

    var valid = $.validate({
        errorMessagePosition : $messages,
        scrollToTopOnError   : false,     // Set this property to true if you have a long form
        onError              : function() {
                                 console.log("onError msg");
                                 return false;
                               },
        onSuccess            : function() {
                                 console.log("onSuccess msg");
                                 return true;
                               }
    });
}