var pathName = window.document.location.pathname;//获取主机地址之后的目录
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
var localhostPaht = window.document.location.href.substring(0, pathName.indexOf(pathName));//获取主机地址，如： http://localhost:8080
var priceSum = 0.00;
var num = 0;
//创建一个=商品数量长度的数组来存放购物车商品的id
var arr = new Array($('input[name="no"]').length);
var no = new Array();
$(function () {
    $(".aStyle").css("color", "black");//给所有的a标记的字体改成黑色
    $(".aStyle").hover(function () {//鼠标悬停时效果
        $(this).css("color", "#ff3333");
        $(this).css("text-decoration", "underline");
    }, function () {//鼠标移开后效果
        $(this).css("color", "black");
        $(this).css("text-decoration", "none");
    });

    //初始化数组
    for (var i = 0; i < arr.length; i++) {
        arr[i] = 0;
    }
    // console.log("arr数组："+arr.toString());

    //点击全选多选框实现选中所有多选框
    $('input[name="checkAll"]').click(function () {
        var isChecked = $(this).attr('checked');
        if ($(this).is(':checked')) {
            $('input[name="selectCheckbox"]').prop("checked", true);//选中name为selectCheckbox的多选框
            $('input[name="checkAll"]').prop("checked", true);
            priceSum = 0.00;
            var i = 0;
            //获取所有的id数组
            $('input[name="selectCheckbox"]').each(function () {
                arr[i] = $(this).val();
                console.log("第" + i + "个是" + arr[i])
                i++;
            });
            $('input[name="tolprice"]').each(function () {
                priceSum = parseFloat($(this).val()) + priceSum;
                num++;
            })
            $(".totol").val(toDecimal2(priceSum));
            $(".goodsNum").val(num);

        } else {
            $('input[name="selectCheckbox"]').prop("checked", false);//取消选中name为selectCheckbox的多选框
            $('input[name="checkAll"]').prop("checked", false);
            //删除所有的id数组
            for (var i = 0; i < arr.length; i++) {
                arr[i] = 0;
            }
            console.log(arr.toString());
            //取消全部勾选，已选商品数量、总价恢复为0
            priceSum = 0.00;
            num = 0;
            $(".totol").val(toDecimal2(priceSum));
            $(".goodsNum").val(num);
        }
    });

    $("#submit").click(function () {
        console.log("ddd");
    });

});

//实现，被选中时，更改数量的同时，改变总价
function numSub(ele, goods_id, user_id) {
    var number = ele.next("input");
    if (number.val() > 1) {
        // 实时修改数据库里的商品数量
        $.ajax({
            type: "POST",
            url: "updateGoods_num",
            data: {
                goods_num: number.val() - 1,
                user_id: user_id,
                id: goods_id,
            },
            dataType: "json", //ajax接口（请求url）返回的数据类型
            success: function (data) { //data：返回数据（json对象）
                number.val(parseInt(number.val()) - 1);
                //找优惠价
                var preferential_price = ((ele.parent("td")).prev("td")).find("input").val();
                //更改此商品的金额
                ((ele.parent("td")).next("td")).find("input").prop("value", toDecimal2(parseFloat(preferential_price) * number.val()));
                //获取当时是被第几个被循环的
                var no = ele.parent().parent().find(".no").val() - 1;
                //判断此商品是否被勾选    ,eq,获取第几个元素
                if ($('input[name="selectCheckbox"]').eq(no).is(':checked')) {
                    //更改总计的金额
                    priceSum = (priceSum * 100 - parseFloat(preferential_price) * 100) / 100;
                    $(".totol").prop("value", toDecimal2(priceSum));
                }
            },
            error: function (data) { //当访问时候，404，500 等非200的错误状态码
                alert("啊哦~修改失败！");
            }
        });
    }
}

function numAdd(ele, goods_id, user_id) {
    var number = ele.prev("input");
    // 实时修改数据库里的商品数量
    $.ajax({
        type: "POST", //请求类型
        url: "updateGoods_num",
        data: {
            goods_num: parseInt(number.val()) + 1,
            user_id: user_id,
            id: goods_id,
        },
        dataType: "json", //ajax接口（请求url）返回的数据类型
        success: function (data) { //data：返回数据（json对象）
            number.val(parseInt(number.val()) + 1);
            var preferential_price = ((ele.parent("td")).prev("td")).find("input").val();
            //找到所触发事件所在的那层foreach循环里的存放商品总价的input
            ((ele.parent("td")).next("td")).find("input").prop("value", toDecimal2(parseFloat(preferential_price) * number.val()));
            //获取当时是被第几个被循环的,因为初始值为1，而数组下标从0开始，所以减1
            var no = ele.parent().parent().find(".no").val() - 1;
            //判断此商品是否被勾选
            if ($('input[name="selectCheckbox"]').eq(no).is(':checked')) {
                //更改总计的金额
                priceSum = priceSum + parseFloat(preferential_price);
                $(".totol").val(toDecimal2(priceSum));
            }
        },
        error: function (data) { //当访问时候，404，500 等非200的错误状态码
            alert("啊哦~修改失败！");
        }
    });
}

//选中单个触发事件
function selectCheckbox(ele, id) {
    var i = ele.prev("input").val() - 1;
    /*找到td下的input，并循环，只取前两个就行了*/
    // alert("i="+i);
    console.log("改变前的" + arr.toString());
    var price = ele.parent().parent().find(".tolprice").val();
    if (ele.is(':checked')) {
        priceSum = priceSum + parseFloat(price);
        num++;
        $(".totol").val(toDecimal2(priceSum));
        $(".goodsNum").val(num);
        //获取当前no值，和id，给arr数组
        arr[i] = id;
        // console.log(arr.toString());
    } else {
        priceSum = (priceSum * 100 - parseFloat(price) * 100) / 100;
        num--;
        //获取当前no值，和id，给arr数组
        arr[i] = 0;
        $(".totol").val(toDecimal2(priceSum));
        $(".goodsNum").val(num);
    }
    console.log("改变后的" + arr.toString());
    //判断是否全选中，商品全选则把全选按钮也选中，否则不选中
    var mo = 0;
    for (var m = 0; m < arr.length; m++) {
        if (arr[m] != 0) {
            mo++;
        }
    }
    if (mo == arr.length) {
        $('input[name="checkAll"]').prop("checked", true);
    }
}

//删除单个购物车里的商品
function deleteCart(ele, id) {
    $.ajax({
        type: "POST", //请求类型
        url: "deleteCart",
        data: {
            id: id,
        },
        dataType: "json", //ajax接口（请求url）返回的数据类型
        success: function (data) { //data：返回数据（json对象）
            ele.parents("tr").next().remove();
            ele.parents("tr").remove(); //删除成功后，动态删除当前的这行
            //获取no的值
            var m = ele.parent("td").prevAll().children(".no").val() - 1;
            //把arr里面的当前所被删除的id也给删除了
            arr[m] = 0;
        },
        error: function (data) { //当访问时候，404，500 等非200的错误状态码
            alert("删除失败了！");
        }
    });
}

//删除多个购物车商品，获取到每个商品的对象，然后调用deleteCart删除
function deleteManyCart(ele) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] != 0) {
            deleteCart($('a[name="deleteCart"]').eq(i), arr[i]);
        }
    }
}

//购买请求
function buy(user_id) {
    for (var m = 0; m < arr.length; m++) {
        if (arr[m] != 0) {
            $.ajax({
                type: "POST", //请求类型
                url: "buy",
                data: {
                    goods_id: arr[m],
                    user_id: user_id,
                },
                dataType: "json", //ajax接口（请求url）返回的数据类型
                success: function (data) { //data：返回数据（json对象）
                    alert("购买成功~");
                    window.location.href = localhostPaht + projectName + "?user_id=" + user_id;
                },
                error: function (data) { //当访问时候，404，500 等非200的错误状态码
                    alert("购买失败了！");
                }
            });
        }
    }

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