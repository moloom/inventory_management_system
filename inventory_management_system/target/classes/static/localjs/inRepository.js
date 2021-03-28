var curWwwPath = window.document.location.href;
// 获取主机地址之后的目录，如： xxx/xxx.html
var pathName = window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
// 获取主机地址，如： http://localhost:8080
var localhostPath = curWwwPath.substring(0, pos);

$('input[name="quantity"]').change(function () {
    var quantity = $(this).val();
    console.log("quantity:" + quantity);
    var unit_priceprice = $(this).parent("td").next().text();
    console.log("unit_priceprice=" + unit_priceprice);
    console.log("total=" + mul(quantity, unit_priceprice));
    ($(this).parent("td").next()).next().text(mul(quantity, unit_priceprice));
    $.ajax({
        type: "POST",
        url: "updateSelectMaterialQuantity",
        data: {
            id: $(this).parent().attr("id"),
            quantity: quantity
        },
        dataType: "json",
        success: function (data) {
        },
        error: function (data) {
            alert("对不起，修改数量失败");
        }
    });
});

var materialLength = $(".s1").attr("materialLength");
$(".deleteMaterial").on("click", function () {
    var obj = $(this);
    if (confirm("你确定要移除此物料吗吗？")) {
        $.ajax({
            type: "POST",
            url: "deleteSelectMaterial",
            data: {
                id: obj.attr("materialId")
            },
            dataType: "json",
            success: function (data) {
                //删除成功：移除删除行
                obj.parents("tr").remove(); //删除当前的这行
                //修改显示物料数量
                materialLength--;
                $(".s1").text(materialLength);
            },
            error: function (data) {
                alert("对不起，删除失败");
            }
        });
    }
});

$("#submit").click(function () {
    var flag = true;
    if ($('input[name="quantity"]').length > 0) {
        $('input[name="quantity"]').each(function () {
            if ($(this).val() == 0) {
                flag = false;
            }
            console.log("$(this).val() =" + $(this).val());
        });
    } else flag = false;
    //判断是否添加了物料，设置了物料的数量
    if (materialLength != 0 && flag) {
        window.location.href = localhostPath + "/inRepositoryTwo.html";
    } else {
        alert("物料未添加或物料数量无填写!");
    }
});

function mul(num1, num2) {
    num1 *= 1000;
    num2 *= 1000;
    var sum = num1 * num2;
    sum /= 1000000;
    return sum;
}

function toDecimal2(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return false;
    }
    var f = Math.round(x * 100) / 100;
    var s = f.toString();
    var rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 2) {
        s += '0';
    }
    return s;
}