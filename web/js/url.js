//url format: #{"from":"12-12-2005","to":"12-15-2016","tags":["tesco","dulce"]}
function getUrlParameters() {
    var url = window.location.href;
    return JSON.parse(decodeURIComponent(url.split("#")[1]));
}

function updateUrl(newUrl) {
    encodedUrl = JSON.stringify(newUrl);
    encodedUrl = encodedUrl.replace(/"/g, ' ');
    encodedUrl = encodedUrl.replace(/ /g, '%22');
    window.location.href = "#" + encodedUrl;
}

function changeFromDate(newFrom) {
    var params = getUrlParameters();
    params.from = newFrom;
    updateUrl(params);
}

function deleteFromDate() {
    var params = getUrlParameters();
    delete params.from;
    updateUrl(params);
}

function changeToDate(newTo) {
    var params = getUrlParameters();
    params.to = newTo;
    updateUrl(params);
}

function deleteToDate() {
    var params = getUrlParameters();
    delete params.to;
    updateUrl(params);
}

function changeLabels(newLabels) {
    var params = getUrlParameters();
    params.labels = newLabels;
    updateUrl(params);
}

function deleteLabels() {
    var params = getUrlParameters();
    delete params.labels;
    updateUrl(params);
}
