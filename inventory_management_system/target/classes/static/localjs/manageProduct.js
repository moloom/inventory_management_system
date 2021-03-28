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

    //查询是否有正在进行的交易

    var obj = $(this);
    if (confirm("你确定要删除此考试吗？")) {
        /* $.ajax({
             type: "POST",
             url: "deletePaper",
             data: {
                 id: obj.attr("materialId")
             },
             dataType: "json",
             success: function (data) {
                 if (data.delresult == "true") { //删除成功：移除删除行
                     alert("删除成功");
                     obj.parents("tr").remove(); //删除当前的这行
                     // history.go(0); //刷新
                 } else { //删除失败
                     alert("对不起，删除试题失败");
                 }
             },
             error: function (data) {
                 alert("对不起，删除失败");
             }
         });*/
    }
});

function page_nav(pageindex) {
    console.log("page_nav请求页数：" + pageindex);
    $("input[name='pageindex']").val(pageindex);
    $("#form1").submit();
    // window.location.href = localhostPaht + "/manageProduct.html?pageindex=" + pageindex;
}