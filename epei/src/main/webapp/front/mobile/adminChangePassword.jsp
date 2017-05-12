<!doctype html>
<%@ include file="/manage/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <jsp:include page="meta.jsp"></jsp:include>
<title>修改密码－医护陪诊第一平台</title>
<!-- <link rel="icon" href="images/favicon.ico" /> -->
<link rel="stylesheet" href="/front/mobile/css/normalize-min.css" />
<link rel="stylesheet" href="/front/mobile/css/style.css" />
<link rel="stylesheet" href="/front/mobile/js/jqtransformplugin/jqtransform.css" />
<style>
    .formitem .input_txt, .picktime button {
        width: 60%;
        border: none;
        font-size: 16px;
        line-height: 31px;
        text-align: left;
        text-indent: 1em;
    }
</style>
</head>

<body>
<div class="breadcrumb">
    <div class="path" onclick="window.history.go(-1);">
        <i><img src="/front/mobile/images/leftarrow.png" alt=""></i>修改密码</div>
</div>

<form action="/profile/changePassword.html" method="post" id="changePassword">
    <input type="hidden" name="_csrf" value="${requestScope["org.springframework.security.web.csrf.CsrfToken"].token}"/>
    <div class="formitem">
        <label>手机号：</label>
            <span>
                ${entity.mobile}
            </span>
    </div>
    <div class="formitem">
        <label>姓名：</label>
            <span>
                ${entity.name}
            </span>
    </div>
    <%--
    <div class="formitem">
        <label>身份证号：</label>
            <span>
                ${entity.idCard}
            </span>
    </div> --%>
    <div class="formitem">
        <label>旧密码：</label>
        <input type="password" name="oldpassword" placeholder="旧&nbsp;&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;&nbsp;码"  datatype="*" class="input_txt" nullmsg="请输入旧密码">
        <div class="Validform_checktip"></div>
    </div>
    <div class="formitem">
        <label>新密码：</label>
        <input type="password" name="password" plugin="passwordStrength" datatype="/^[\w,\-,_]{8,18}$/"
               nullmsg="新密码!" errormsg="密码必须8-18位" placeholder="新&nbsp;&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;&nbsp;码" class="input_txt"/>
        <div class="Validform_checktip"></div>
    </div>
    <div class="formitem">
        <label>确认新密码：</label>
        <input type="password" name="repassword" nullmsg="请再输入一次密码！" datatype="*8-18" recheck="password" errormsg="两次输入的密码不一致!" placeholder="确认新密码" class="input_txt repeatpwd">
        <div class="Validform_checktip"></div>
    </div>
    <div class="form_btn"><input type="submit" value="修改密码" class="btn"></div>
</form>

<jsp:include page="adminMobile_footer.jsp"></jsp:include>
<script type="text/javascript" src="/front/mobile/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/front/mobile/js/Validform_v5.3.2_min.js"></script>
<jsp:include page="popup.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
    $("#changePassword").Validform({
        tiptype:function(msg,o,cssctl){
            if(!o.obj.is("form")){
                var objtip=o.obj.parent().find(".Validform_checktip");
                cssctl(objtip,o.type);
                objtip.text(msg);
            }
        },
        showAllError:true,
        usePlugin: {
            jqtransform: {
                //会在当前表单下查找这些元素;
                selector: "select,:checkbox,:radio"
            }
        },
        callback : function(data){
            $.post("/profile/changePassword.html",$(data).serialize(),function(result){
                if(result.success){
                    window.location="/front/mobile/login.jsp";
                }
                popup(result.message);
            });
            return false;
        }
    });
})
</script>
</body>
</html>
