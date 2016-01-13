function expenseForCurrentWeek(accountId) {
    var date = new Date();
    var timestamp = date.getTime();

    var monday = getMonday(date);
    var from = dateToDMYFormat(monday);
    //console.log("monday: " + from)

    //calculate Sunday
    var timestamp = monday.getTime();
    timestamp += 6 * 24 * 3600 * 1000;
    date = new Date(timestamp);
    var to = dateToDMYFormat(date);
    //console.log("sunday: " + to)

    changeFromDate(from);
    changeToDate(to);

    getExpensesList(accountId, '#expenses-list');
}

function getMonday(date) {
    var timestamp = new Date().getTime();
    var date = new Date(timestamp);
    for (i = 0; i < 7; i++) {
        if (date.getDay() == 1) {
          return date;
        } else {
            timestamp -= (24 * 3600 * 1000); //substract one day
            date = new Date(timestamp);
        }
    }
}


function expenseForCurrentMonth(accountId) {
    var date = new Date();
    var y = date.getFullYear();
    var m = date.getMonth();
    var firstDay = dateToDMYFormat(new Date(y, m, 1));
    var lastDay = dateToDMYFormat(new Date(y, m + 1, 0));

    changeFromDate(firstDay);
    changeToDate(lastDay);

    getExpensesList(accountId, '#expenses-list');
}


function expenseForCurrentYear(accountId) {
    var y = new Date().getFullYear();
    var firstDay = dateToDMYFormat(new Date(y, 0, 1));
    var lastDay = dateToDMYFormat(new Date(y, 11, 31));

    changeFromDate(firstDay);
    changeToDate(lastDay);

    getExpensesList(accountId, '#expenses-list');
}


function dateToDMYFormat(date) {
    var d = date.getDate();
    var m = date.getMonth() + 1; // the count starts from 0
    var y = date.getFullYear();
    return d + "-" + m + "-" + y;
}