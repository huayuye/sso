<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <!--[if lt IE 9] -->
    <script type="text/javascript" src="/other/html5shiv.js"></script>
    <script type="text/javascript" src="/other/respond.min.js"></script>
    <!--[endif]-->

    <link rel="stylesheet" type="text/css" href="/css/H-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="/css/H-ui.login.css" />
    <link rel="stylesheet" type="text/css" href="/Hui-iconfont/1.0.8/iconfont.css" />
    <link rel="stylesheet" type="text/css" href="/skin/default/skin.css" id="skin" />
    <link rel="stylesheet" type="text/css" href="/css/style.css" />

    <link rel="stylesheet" type="text/css" href="/bookstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="/bookstrap/css/bootstrap-table.css" />
    <title>登录</title>
</head>
<body ><!--onload="loadTopWindow()"-->

    <div class="loginWraper">
        <div id="loginform" class="loginBox">
            <form class="form form-horizontal" action="/ssoLogin" method="post">
                <input type="hidden" name="callbackURL" value="${callbackURL}">
                <div class="row cl">
                    <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
                    <div class="formControls col-xs-8">
                        <input id="login-name" name="userNo" type="text" placeholder="账户8082" class="input-text size-L">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
                    <div class="formControls col-xs-8">
                        <input id="login-pwd" name="pwd" type="password" placeholder="密码" class="input-text size-L">
                    </div>
                </div>
                <div class="row cl">
                    <div class="formControls col-xs-8 col-xs-offset-3">
                        <input class="input-text size-L" type="text" placeholder="验证码" onblur="if(this.value==''){this.value='验证码:'}" onclick="if(this.value=='验证码:'){this.value='';}" value="验证码:" style="width:150px;">
                        <img src=""> <a id="kanbuq" href="javascript:;">看不清，换一张</a> </div>
                </div>
                <div class="row cl">
                    <div class="formControls col-xs-8 col-xs-offset-3">
                        <label for="online">
                            <input type="checkbox" name="online" id="online" value="">
                            使我保持登录状态</label>
                    </div>
                </div>
                <div class="row cl">
                    <div class="formControls col-xs-8 col-xs-offset-3">
                        <input id="login_btn" name="" type="submit" class="btn btn-success radius size-L" value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
                        <!--<input id="login_admin_btn" name="" type="reset" class="btn btn-default radius size-L" value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">-->
                    </div>
                </div>
            </form>
        </div>
    </div>
    <!--_footer 作为公共模版分离出去-->
    <script type="text/javascript" src="/jquery/1.9.1/jquery.min.js"></script>
    <script type="text/javascript" src="/layer/2.4/layer.js"></script>
    <script type="text/javascript" src="/js/H-ui.min.js"></script>
    <!--<script type="text/javascript" src="../../js/H-ui.admin.js"></script>-->
    <!--/_footer 作为公共模版分离出去-->

    <!--请在下方写此页面业务相关的脚本-->
    <script type="text/javascript" src="/My97DatePicker/4.8/WdatePicker.js"></script>
    <!-- <script type="text/javascript" src="../../datatables/1.10.0/jquery.dataTables.min.js"></script>  -->
    <script type="text/javascript" src="/laypage/1.2/laypage.js"></script>
    <script type="text/javascript" src="/bookstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/bookstrap/js/bootstrap-table.js"></script>
    <script type="text/javascript" src="/bookstrap/js/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="/js/utils/encrypt.js"></script>
    <script type="text/javascript" src="/js/login/login.js"></script>

<script type="text/javascript">

//判断当前窗口是否有顶级窗口，如果有就让当前的窗口的地址栏发生变化，
//这样就可以让登陆窗口显示在整个窗口了
    function loadTopWindow(){
        if (window.top!=null && window.top.document.URL!=document.URL){
            window.top.location= document.URL;
        }
    }

</script>

</body>