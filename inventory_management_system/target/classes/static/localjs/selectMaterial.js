$(function () {
    // 这样填充数据，点击重置按钮可以清除数据，用thymeleaf清不了数据
    var id = $("input[name='supplierId']").val();
    var name = $("input[name='namee']").val();
    console.log(id);
    console.log("name:" + name);
    $("input[name='name']").val(name);
    if (id != null && id != "")
        $("#supplier_id").val(id);
});

function page_nav(pageindex) {
    console.log("page_nav请求页数：" + pageindex);
    $("input[name='pageindex']").val(pageindex);
    $("#form1").submit();
    // window.location.href = localhostPaht + "/selectMaterial?pageindex=" + pageindex;
}