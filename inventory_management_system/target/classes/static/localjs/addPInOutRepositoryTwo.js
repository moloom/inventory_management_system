$(function () {
    // if (status != null && status != "") ;

    $("#status").change(function () {
        var status = $("#status").val();
        if (status == 1) {
            $("#department").show();
            $("#takeName").show();
            $("#customer").hide();
        } else {
            $("#department").hide();
            $("#takeName").hide();
            $("#customer").show();
        }
    });

    $("#sub").click(function () {
        var status = $("#status").val();
        var repository = $("#repository_id").val();
        var time = $("#single_cal2").val();
        var takeDepartment = $("input[name='takeDepartment']").val();
        var takeName = $("input[name='takeName']").val();
        var customer_id = $("input[name='customer_id']").val();
        var flag1 = false;
        var flag3 = false;
        var flag4 = false;
        var m, d, end;
        /*MM/dd/yyyy转换yyyy-MM-dd HH:mm:ss*/
        end = time.indexOf("/");
        m = time.substring(0, end);
        time = time.substr(end + 1, time.length);
        end = time.indexOf("/");
        d = time.substring(0, end);
        time = time.substr(end + 1, time.length);
        time = time + "-" + m + "-" + d + " 00:00:00";
        console.log(status);
        console.log("customer="+customer_id)
        $("#single_cal2").val(time);
        if (status == 2 && customer_id != 0 && customer_id != '') flag1 = true;
        if (status == 1 && takeDepartment != '' && takeName != '') flag1 = true;
        if (repository != '0' && repository != 0) flag3 = true;
        if (time != '') flag4 = true;
        console.log("flag="+$("input[name='flag']").val());
        if (flag1 && flag3 && flag4) {
            //判断，出库时，出库数量是否小于实际可用数量
            if (status == 2 && $("input[name='flag']").val() == 1) {
                alert("提交失败，出库数量大于实际可用数量\n请返回修改数量!");
            } else
                $("#commit").submit();
        } else {
            alert("您有信息未填写！");
        }

    });

})

function back() {
    history.go(-1);
}