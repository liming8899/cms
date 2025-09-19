var parameter = {}

parameter.isBlank = function (str) {
    str = str.replace(/(^\s*)|(\s*$)/g, '');
    return (str === '' || str === undefined || str == null)
}

parameter.search = function (name) {
    // debugger;
    var url='/home/exchangeRateSearch';
    var keywords = $("#keywords").val();
    var keywords2 = $("#keywords2").val();
    if (keywords2 === null || keywords2 === undefined || keywords2 === "") {
        swal('查询条件不能为空'); return;
    }
    location.href = url+'?keywords=' + keywords+'&keywords2='+keywords2;
}

parameter.searchchange = function (name) {
    var url='/home/exchangeRateSearch';
    var keywords = $("#keywords").val();
    var keywords2 = $("#keywords2").val();
    if (parameter.isBlank(keywords) && parameter.isBlank(keywords2)) {
        swal('查询条件不能为空'); return;
    }
    location.href = url+'?keywords=' + keywords+'&keywords2='+keywords2;
}
