var pathName = window.document.location.pathname;//获取主机地址之后的目录
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
var localhostPaht = window.document.location.href.substring(0, pathName.indexOf(pathName));//获取主机地址，如： http://localhost:8080

$(function () {

    //提交表单
    $("#sub").click(function () {
        var codeFlage = null;
        var passFlage = null;
        var phoneFlage = null;
        var birthdayFlage = null;
        var message = "";
        var user_code = $("input[name='uid']").val();
        var user_name = $("input[name='name']").val();
        var phone = $("input[name='telephone']").val();
        var duty_id = $("#duty_id").val();
        var password = $("input[name='password']").val();
        var sex = $("input[name='sex']:checked").val();
        var birthday = $("input[name='birthday']").val();
        if (phone != "") {
            birthdayFlage = true;
            var m, d, end;
            /*MM/dd/yyyy转换yyyy-MM-dd HH:mm:ss*/
            end = birthday.indexOf("/");
            m = birthday.substring(0, end);
            birthday = birthday.substr(end + 1, birthday.length);
            end = birthday.indexOf("/");
            d = birthday.substring(0, end);
            birthday = birthday.substr(end + 1, birthday.length);
            birthday = birthday + "-" + m + "-" + d + " 00:00:00";
        } else message = message + "请输入生日\n";

        //账号匹配表达式
        var regCode = /^[a-zA-Z0-9]{6,12}$/;
        // 密码正则匹配表达式
        var rePass = /^[a-zA-Z]{1}[0-9a-zA-Z]{5,11}$/
        // 邮箱正则匹配表达式
        //var reMail = /^[a-z0-9][\w\.\-]*@[a-z0-9\-]+(\.[a-z]{2,5}){1,2}$/i
        //手机号匹配表达式
        var rePhone = /^[1]{1}[0-9]{10}$/;
        if (user_code != "" && regCode.test(user_code)) {
            codeFlage = true;
        } else message = message + "请输入6~12位字母+数字组成的账号\n";
        if (password != "" && rePass.test(password)) {
            passFlage = true;
        } else message = message + "请输入6~12位字母+数字的密码\n";
        if (phone != "" && rePhone.test(phone)) {
            phoneFlage = true;
        } else message = message + "请输入正确的手机号\n";


        if (codeFlage && passFlage && phoneFlage && birthdayFlage) {
            //异步提交表单
            $.ajax({
                type: "POST", //请求类型
                url: "signup", //请求的url
                data: {
                    uid: user_code,
                    name: user_name,
                    telephone: phone,
                    duty_id: duty_id,
                    password: password,
                    sex: sex,
                    birthday: birthday,
                },
                dataType: "json", //ajax接口（请求url）返回的数据类型
                success: function (data) { //data：返回数据（json对象）
                    //成功则转到主界，
                    window.location.href = localhostPaht + data;
                },
                error: function () { //当访问时候，404，500 等非200的错误状态码
                    window.location.href = localhostPaht + data;
                    alert("账号重复，注册失败");
                }
            });
        } else alert(message);
    });
});