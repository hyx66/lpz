<!doctype html>
<%@ include file="/manage/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <jsp:include page="meta.jsp"></jsp:include>
<title>找回密码－医护陪诊第一平台</title>
<!-- <link rel="icon" href="images/favicon.ico" /> -->
<link rel="stylesheet" href="/front/mobile/css/normalize-min.css" />
<link rel="stylesheet" href="/front/mobile/css/style.css" />
<link rel="stylesheet" href="/front/mobile/js/jqtransformplugin/jqtransform.css" />
</head>

<body>
<div class="breadcrumb">
    <div class="path" onclick="window.location='/front/mobile/login.jsp'"><i><img src="/front/mobile/images/leftarrow.png" alt=""></i>找回密码</div>
</div>

<form action="/resetPassword.html" method="post" id="forgot_password" class="reg">
    <input type="hidden" name="_csrf" value="${requestScope["org.springframework.security.web.csrf.CsrfToken"].token}"/>
    <div class="formitem">
        <input type="text" name="mobile" placeholder="手 机 号：" class="input_txt" datatype="m" nullmsg="请输入手机号"
               errormsg="请输入正确的手机号" value="${param['mobile']}"/>
        <div class="Validform_checktip"></div>
    </div>
    <div class="formitem">
        <input type="text" name="mobileValidCode" placeholder="验 证 码：" class="input_txt valid" datatype="*" nullmsg="请输入手机验证码">
        <div class="Validform_checktip"></div>
        <a href="javascript:void(0);" class="getvalidnum" id="getMobileValidCode"><span>获取验证码</span></a>
    </div>
    <div class="formitem">
        <input type="password" name="password" datatype="*8-18" nullmsg="请输入密码!"
               errormsg="密码必须8-18位" placeholder="密&nbsp;&nbsp;&nbsp;&nbsp;码：" class="input_txt"/>
        <div class="Validform_checktip"></div>
    </div>
    <div class="formitem">
        <input type="password" name="repassword" nullmsg="请再输入一次密码！" datatype="*" recheck="password"
               errormsg="两次输入的密码不一致!" placeholder="确认密码：" class="input_txt repeatpwd">
        <div class="Validform_checktip"></div>
    </div>
    <div class="form_btn"><input type="submit" value="提交" class="btn"></div>
</form>

<jsp:include page="mobile_footer.jsp"></jsp:include>
<script type="text/javascript" src="/front/mobile/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/front/mobile/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/front/mobile/js/passwordStrength.js"></script>
<jsp:include page="popup.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
    $("#forgot_password").Validform({
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
            $.post("/resetPassword.html",$(data).serialize(),function(result){
                if(result.success){
                    window.location="/front/mobile/login.jsp";
                }
                popup(result.message);
            });
            return false;
        }
    });

    //发送短信验证码
    $("#getMobileValidCode").click(function(){
        getMobileValidCode();
    });

})

function getMobileValidCode(){
    var mobile = $("input[name='mobile']").val();
    if(mobile){
        $.post("/mobileValidCode.html?timstap=" +new Date(),
                {"type":1,"mobile":mobile,"_csrf":"${requestScope["org.springframework.security.web.csrf.CsrfToken"].token}"},
                function(result){
                    if (result && result.success) {
                        $("#getMobileValidCode").unbind("click");
                        i = 120;
                        sendCountdown('getMobileValidCode');
                    }
                    else{
                        popup(result.message);
                    }
        });
    }
}

// 计数器
function sendCountdown(buttonId) {
    i--;
    if (i < 0) {
        $("#" + buttonId).click(function(){
            getMobileValidCode();
        });
        $("#" + buttonId).find("span").html("重新下发短信");
    } else {
        $("#" + buttonId).find("span").html("倒计时" + i + "秒");
        setTimeout("sendCountdown('" + buttonId + "')", 1000)
    }
}

var msg = "${errorMsg}";
$(function(){
    if(msg && msg != ""){
    	popup(msg);
    }
});
</script>
</body>
</html>
