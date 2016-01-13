function logout() {
    $( "#logout" ).click(function( event ) {
        console.log("submit logout");
        // Stop form from submitting normally
        event.preventDefault();

        // Get some values from elements on the page:
        var sessionId  = $.cookie("sessionId");

        // Send the data using post
        var data = $.post( '/expenses/logout',
                            { sessionId: sessionId
                            });

        data.success(function() {
            var json = $.parseJSON(data.responseText);
            var response = json.logout;
            if (response == "success") {
                removeCookies(sessionId);
                $("#expensesBlock").html("");
                loadLogin();
            } else {
                alert("Error trying to logout.");
            }
        });

        data.error(function() {
            console.log("Error at logout.");
        });
    });
}