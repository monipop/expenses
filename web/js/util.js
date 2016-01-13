function getUrl(link, parser) {
    $.ajax({
        url: link,
        dataType: "text",
        success: function(data) {

            //data downloaded so we call parseJSON function
            //and pass downloaded data
            var json = $.parseJSON(data);
            parser(json);
        }
    });
}

function getHtml(link, parser) {
    $.ajax({
        url: link,
        dataType: "html",
        success: function(data) {
            parser(data);
        }
    });
}

function attachHoverAction() {
    $(".table-row").hover(function() {
        $(this).addClass("row-hover");
    }, function() {
        $(this).removeClass("row-hover");
    });
}

function printf() {
   var template = arguments[0];
   for (var i = 1; i < arguments.length; i++) {
      template = template.replace('%s', arguments[i]);
   }
   return template;
}