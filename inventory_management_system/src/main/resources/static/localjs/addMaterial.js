var curWwwPath = window.document.location.href;
// 获取主机地址之后的目录，如： xxx/xxx.html
var pathName = window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
// 获取主机地址，如： http://localhost:8080
var localhostPath = curWwwPath.substring(0, pos);
$(function () {
    $("#submit").on("click", function () {

        var priceFlage = false;
        var minimum_stockFlage = false;
        var supplier_idFlag = false;
        var message = "";
        var unit_price = $("input[name='unit_price']").val();
        var minimum_stock = $("input[name='minimum_stock']").val();
        var create_by = $("input[name='create_by']").val();
        var name = $("input[name='name']").val();
        var specification = $("input[name='specification']").val();
        var color = $("input[name='color']").val();
        var position = $("input[name='position']").val();
        var unit = $("input[name='unit']").val();
        var supplier_id = $("#supplier_id").val();
        var note = $("#note").val();

        if (unit_price > 0) {
            priceFlage = true;
        } else message += "您输入的价格有误\n";
        if (minimum_stock >= 0) {
            minimum_stockFlage = true;
        } else message += "您输入的最低库存有误\n";
        if (supplier_id != 0) {
            supplier_idFlag = true;
        } else message += "请选择供应商\n";

        if (priceFlage && minimum_stockFlage && supplier_idFlag) {
            //提交
            $.ajax({
                type: "POST", //请求类型
                url: "addMaterial",
                data: {
                    name: name,
                    create_by: create_by,
                    unit_price: unit_price,
                    minimum_stock: minimum_stock,
                    specification: specification,
                    color: color,
                    position: position,
                    unit: unit,
                    supplier_id: supplier_id,
                    note: note,
                },
                dataType: "json", //ajax接口（请求url）返回的数据类型
                success: function (data) { //data：返回数据（json对象）
                    //转到成功界面
                    window.location.href = localhostPath + data;
                },
                error: function (data) { //当访问时候，404，500 等非200的错误状态码
                    alert("添加失败！");
                    history.back(0);
                }
            });
        }
    });


    $("#back").on("click", function () {
        window.location.href = localhostPath;
    });
});

