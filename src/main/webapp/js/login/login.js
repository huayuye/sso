
$(function(){
    init();
    //$('#login-pwd').bind('input propertychange', function() {
    //    $(this).val(hex_md5($(this).val()));
    //    console.log($(this).val());
    //});
    $("#login-pwd").change(function(){
        $(this).val(hex_md5($(this).val()));
        console.log($(this).val());
    });
});

var init = function(){
    $("#login_btn").click(function(){
        var loginName = $("#login-name").val();
        var loginPwd = $("#login-pwd").val();
        var error = false;
        var msg = "";
        if(loginName == null || loginName ==""){
            error = true;
            msg += "用户名,";
        }
        if(loginPwd == null || loginPwd ==""){
            error = true;
            msg += "密码,";
        }
        if(error){
            msg = msg.substring(0,msg.length-1);
            layer.msg(msg+"不能为空",{icon:0,time:1500});
            return false;
        }

        //$.ajax({
        //    url:"/login",
        //    dataType:"json",
        //    data:{
        //        "name":loginName,
        //        "pwd":loginPwd
        //    },
        //    success:function(data){
        //
        //    },
        //    error:function(){
        //
        //    }
        //});
    });
}

