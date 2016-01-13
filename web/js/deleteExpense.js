function attachDeleteActions() {
    $(".delete").click(function() {
        var expenseId = $(this).attr("expenseId");
        var accountId = $(this).attr("accountId");
        var url = printf("/expenses/expenses?accountId=%s&expenseId=%s", accountId,expenseId);
        if (!confirm(printf("Are you sure you want to delete expense %s?", expenseId))) {
            return false;
        }

        //console.log(url);

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
                getExpensesList(accountId, '#expenses-list', null, null);
            }
        });
    });
}