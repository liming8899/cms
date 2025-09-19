var parameter = {}

parameter.isBlank = function (str) {
    str = str.replace(/(^\s*)|(\s*$)/g, '');
    return (str === '' || str === undefined || str == null)
}

parameter.search = function (name) {
    // debugger
    var url='/home/parameterSearch';
    var keywords = $("#keywords").val();
    var keywords2 = $("#keywords2").val();
    if (!parameter.isBlank(keywords)&&parameter.isBlank(keywords2)) {
        swal('下拉选择搜索条件'); return;
    }
    if (parameter.isBlank(keywords)&&!parameter.isBlank(keywords2)) {
        swal('请输入要查询的内容'); return;
    }
    location.href = url+'?keywords=' + keywords+'&keywords2='+keywords2;
}

parameter.searchchange = function (name) {
    var url='/home/parameterChange';
    var keywords = $("#keywords").val();
    var keywords2 = $("#keywords2").val();
    if (!parameter.isBlank(keywords)&&parameter.isBlank(keywords2)) {
        swal('下拉选择搜索条件'); return;
    }
    if (parameter.isBlank(keywords)&&!parameter.isBlank(keywords2)) {
        swal('请输入要查询的内容'); return;
    }
    location.href = url+'?keywords=' + keywords+'&keywords2='+keywords2;
}

parameter.searchchange2 = function (url) {


    var keywords = $("#keywords").val();
    var keywords2 = $("#keywords2").val();
    $.ajax({
        url : url+'?keywords=' + keywords+'&keywords2='+keywords2+'&flush="1"',
        type : "GET",
        success : function (data) {
            $(".columns_recorde").html(data);
        },
        error:function (errorMsg) {
            console.log(errorMsg);
        }
    })

}


parameter.searchchange1 = function (url) {
    // var url='/home/parameterChange';
    var keywords = $("#keywords").val();
    var keywords2 = $("#keywords2").val();
    if (!parameter.isBlank(keywords)&&parameter.isBlank(keywords2)) {
        swal('下拉选择搜索条件'); return;
    }
    if (parameter.isBlank(keywords)&&!parameter.isBlank(keywords2)) {
        swal('请输入要查询的内容'); return;
    }
    // location.href = url+'?keywords=' + keywords+'&keywords2='+keywords2;

    $.ajax({
        url : url+'?keywords=' + keywords+'&keywords2='+keywords2+'&flush="1"',
        type : "GET",
        success : function (data) {
            $(".columns_recorde").html(data);
        },
        error:function (errorMsg) {
            console.log(errorMsg);
        }
    })
}
parameter.searchchange3 = function (url) {
    var url='/'+$("#path").val();
    var keywords = $("#keywords").val();
    var keywords2 = $("#keywords2").val();
    if (!parameter.isBlank(keywords)&&parameter.isBlank(keywords2)) {
        swal('下拉选择搜索条件'); return;
    }
    if (parameter.isBlank(keywords)&&!parameter.isBlank(keywords2)) {
        swal('请输入要查询的内容'); return;
    }
    // location.href = url+'?keywords=' + keywords+'&keywords2='+keywords2;

    $.ajax({
        url :url+'?keywords=' + keywords+'&keywords2='+keywords2,
        type : "GET",
        success : function (data) {
            $(".search_recorde").html(data);
        },
        error:function (errorMsg) {
            console.log(errorMsg);
        }
    })
}





