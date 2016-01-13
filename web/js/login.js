function loadLogin() {
    getHtml("/expenses/html/login.html", function(data){
        console.log("get login html");
        $("#main").append(data);
        submitHandlerLoginForm();
    });
}

function submitHandlerLoginForm() {
    $( "#login" ).submit(function( event ) {
        console.log("submit login");
        // Stop form from submitting normally
        event.preventDefault();

        // Get some values from elements on the page:
        var $form = $( this );
        var name  = $form.find( "input[name='name']" ).val();
        var pass  = $form.find( "input[name='pass']" ).val();

        // browser data
        var browserName = navigator.userAgent;
        var lastAccessed = $.now();

        // Send the data using post
        var data = $.post( '/expenses/login',
                            { name: name,
                              pass: pass,
                              browserName: browserName,
                              lastAccessed: lastAccessed
                            });

        data.success(function() {
            console.log("Login successful!");
            var json = $.parseJSON(data.responseText);
            var accountId = json.accountId;
            setCookies(json);

            $("#loginBlock").html("");
            loadExpenses(accountId);
        });


        data.error(function() {
            alert("Error at login");
            console.log("Error at login.");
        });
    });
}

function setCookies(session) {

    if (session.sessionId != null) {
        $.cookie("sessionId", session.sessionId);
    }

    if (session.accountId != null) {
        $.cookie("accountId", session.accountId);
    }

    if (session.user != null) {
        $.cookie("user", session.user);
    }

    if (session.from ==  null) {
        console.log("null");
        $.cookie("from", "null");
    } else {
        console.log("else");
        $.cookie("from", session.from);
    }

    if (session.to == null) {
        $.cookie("to", "null");
    } else {
        $.cookie("to", session.to);
    }

    if (session.labels == null) {
        $.cookie("labels", "null");
    } else {
        $.cookie("labels", session.labels);
    }

    if (session.browser != null) {
        $.cookie("browser", session.browser);
    }

    if (session.lastAccessed != null) {
        $.cookie("lastAccessed", session.lastAccessed);
    }
}

function removeCookies(sessionId) {
    $.removeCookie("sessionId");
    $.removeCookie("accountId");
    $.removeCookie("user");
    $.removeCookie("from");
    $.removeCookie("to");
    $.removeCookie("labels");
    $.removeCookie("browser");
    $.removeCookie("lastAccessed");
}
