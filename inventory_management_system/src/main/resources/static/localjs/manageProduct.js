var curWwwPath = window.document.location.href;
// 获取主机地址之后的目录，如： xxx/xxx.html
var pathName = window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
// 获取主机地址，如： http://localhost:8080
var localhostPath = curWwwPath.substring(0, pos);

$(function () {
    // 这样填充数据，点击重置按钮可以清除数据，用thymeleaf清不了数据
    var ctId = $("input[name='clothingTypesId']").val();
    var csId = $("input[name='clothingSex']").val();
    var name = $("input[name='namee']").val();
    $("input[name='name']").val(name);
    if (ctId != "") $("#clothing_types_id").val(ctId);
    if (csId != "") $("#clothing_sex").val(csId);
});

$(".deleteProduct").on("click", function () {
    var obj = $(this);
    if (confirm("你确定要删除此商品吗？")) {
        $.ajax({
            type: "POST",
            url: "deleteProduct",
            data: {
                id: obj.children().attr("productid")
            },
            dataType: "json",
            success: function (data) {
                obj.parents("tr").remove(); //删除当前的这行
            },
            error: function (data) {
                alert("对不起，删除失败");
            }
        });
    }
});

function page_nav(pageindex) {
    console.log("page_nav请求页数：" + pageindex);
    $("input[name='pageindex']").val(pageindex);
    $("#form1").submit();
    // window.location.href = localhostPaht + "/manageProduct.html?pageindex=" + pageindex;
}