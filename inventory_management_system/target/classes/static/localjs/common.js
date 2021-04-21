var pathName = window.document.location.pathname;//获取主机地址之后的目录
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
var localhostPaht = window.document.location.href.substring(0, pathName.indexOf(pathName));//获取主机地址，如： http://localhost:8080
$(function () {
    $("#signOut").append("<span></span>");
    $("#signOut span").click();
    $("#signOut").click(function () {
        $.ajax({
            type: "POST", //请求类型
            url: "signOut", //请求的url
            dataType: "json", //ajax接口（请求url）返回的数据类型
            success: function (data) { //data：返回数据（json对象）
                window.location.href = localhostPaht + projectName;
            }
        });
    });

    //给购物车获取数据
    $.ajax({
        type: "POST", //请求类型
        url: "selectCartList", //请求的url
        data: {
            user_id: $('input[name="userId"]').val(),
        },
        dataType: "json", //ajax接口（请求url）返回的数据类型
        success: function (data) { //data：返回数据（json对象）
        }
    });
});

//onclick触发的方法必须放在最外面！！！
function requestPage(id, start) {
    $.ajax({
        type: "POST", //请求类型
        url: "selectGoodsByCategoryId",
        data: {
            id: id,
            start: start,
        },
        dataType: "json", //ajax接口（请求url）返回的数据类型
        success: function (data) { //data：返回数据（json对象）
            window.location.href = localhostPaht + "indexSearch.html";
        },
        error: function (data) { //当访问时候，404，500 等非200的错误状态码
            alert("请求失败！");
        }
    });
}