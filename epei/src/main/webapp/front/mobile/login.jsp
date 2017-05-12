<!doctype html>
<%@ include file="/manage/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <jsp:include page="meta.jsp"></jsp:include>
<title>登录－医护陪诊第一平台</title>
<!-- <link rel="icon" href="images/favicon.ico" /> -->
<link rel="stylesheet" href="/front/mobile/css/normalize-min.css" />
<link rel="stylesheet" href="/front/mobile/js/jqtransformplugin/jqtransform.css"/>
<link rel="stylesheet" href="/front/mobile/css/style.css" />
</head>
<body>
<div class="breadcrumb">
    <div class="path" onclick="window.history.go(-1)"><i><img
            src="/front/mobile/images/leftarrow.png" alt=""></i>登录</div>
    <a href="/front/mobile/reg.jsp?code=${param.code}" class="r" style="margin-right: 110px;">注册</a>
    <a href="/front/mobile/forgotpassword.jsp" class="r">忘记密码？</a>
</div>

<form action="/login.html" method="post" id="login" class="login">
    <c:if test="${empty sessionScope.sessionCustomer.openid}">
        <input type="hidden" value="${param.code}" name="code" />
    </c:if>
    <input type="hidden" name="_csrf" value="${requestScope["org.springframework.security.web.csrf.CsrfToken"].token}"/>
    <div class="formitem">
        <input type="text" name="mobile" placeholder="手机号：" class="input_txt" datatype="m" nullMsg="请输入手机号"
               errorMsg="请输入正确的手机号"/>
        <div class="Validform_checktip"></div>
    </div>
    <div class="formitem">
        <input type="password" name="password"placeholder="密&nbsp;&nbsp;&nbsp;&nbsp;码：" class="input_txt"
               datatype="*" nullMsg="请输入密码" />
        <div class="Validform_checktip"></div>
    </div>
    <div class="form_btn"><input type="submit" value="立即登录" class="btn"></div>
</form>

<jsp:include page="mobile_footer.jsp"></jsp:include>

</body>
<script type="text/javascript" src="/front/mobile/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/front/mobile/js/jqtransformplugin/jquery.jqtransform-min.js"></script>
<script type="text/javascript" src="/front/mobile/js/Validform_v5.3.2_min.js"></script>
<jsp:include page="popup.jsp"></jsp:include>
<script type="text/javascript">
    $("#login").Validform({
        tiptype: function (msg, o, cssctl) {
            if (!o.obj.is("form")) {
                var objtip = o.obj.parent().find(".Validform_checktip");
                cssctl(objtip, o.type);
                objtip.text(msg);
            }
        },
        showAllError: true,
        usePlugin: {
            jqtransform: {
                //会在当前表单下查找这些元素;
                selector: "select,:checkbox,:radio"
            }
        }
    });

    <!--更新code-->
    var msg = "${errorMsg}";
    var code = "${param.code}";
    var codeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx88aab741b8dc110c&redirect_uri=http%3a%2f%2fwww.023666666.com%2ffront%2fmobile%2flogin.jsp&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
    function refreshCode(){
    	alert("测试中，允许电脑登录……");/* 
    	window.location.href=codeUrl; */
    } 
    $(function(){
    	//code过期时间为5分钟
    	window.setTimeout(refreshCode,1000*60*5);
        if(msg && msg != ""){
        	popup(msg,"取消","知道了",refreshCode,refreshCode);
        }else if(code=null || code == ""){
        	refreshCode();
        }
    });
</script>
</html>
