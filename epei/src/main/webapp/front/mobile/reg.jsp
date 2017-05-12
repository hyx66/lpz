
<!doctype html>
<%@ include file="/manage/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <jsp:include page="meta.jsp"></jsp:include>
<title>用户注册－医护陪诊第一平台</title>
<!-- <link rel="icon" href="images/favicon.ico" /> -->
<link rel="stylesheet" href="/front/mobile/css/normalize-min.css" />
<link rel="stylesheet" href="/front/mobile/css/style.css" />
<link rel="stylesheet" href="/front/mobile/js/jqtransformplugin/jqtransform.css" />
</head>

<body>
<div class="breadcrumb">
    <div class="path" onclick="window.location='/front/mobile/login.jsp'"><i><img src="/front/mobile/images/leftarrow.png" alt=""></i>用户注册</div>
</div>

<form action="/register.html" method="post" id="reg" class="reg">
    <c:if test="${empty sessionScope.sessionCustomer.openid}">
        <input type="hidden" value="${param.code}" name="code" />
    </c:if>
    <input type="hidden" name="_csrf" value="${requestScope["org.springframework.security.web.csrf.CsrfToken"].token}"/>
    <input type="hidden" name="customerType" value="0"/>
    <div class="formitem">
        <input type="text" name="mobile" placeholder="手 机 号：" class="input_txt" datatype="m" nullmsg="请输入手机号"
               errormsg="请输入正确的手机号"/>
        <div class="Validform_checktip"></div>
    </div>
    <div class="formitem">
        <input type="text" name="captcha" placeholder="验 证 码：" class="input_txt valid" datatype="*" nullmsg="请输入验证码">
        <div class="Validform_checktip"></div>
        <a href="javascript:void(0);"  class="getvalidnum"  style="text-align: left;">
            <img id="jcaptchaImage" src="${pageContext.request.contextPath}/jcaptcha.jpg" style="width:100%;height:
            100%;display:inline-block;position: absolute;"/>
        </a>
    </div>
    <div class="formitem">
        <input type="password" name="password" plugin="passwordStrength" datatype="*8-18"
               nullmsg="请输入密码!" errormsg="密码必须8-18位" placeholder="密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：" class="input_txt"/>
        <div class="Validform_checktip"></div>
    </div>
    <div class="formitem">
        <input type="password" name="repassword" nullmsg="请再输入一次密码！" datatype="*8-18" recheck="password" errormsg="两次输入的密码不一致!" placeholder="确认密码：" class="input_txt repeatpwd">
        <div class="Validform_checktip"></div>
    </div>
    <div class="formitem">
        <input type="text" name="referenceMobile" placeholder="推荐人(可为空)：" class="input_txt">
        <div class="Validform_checktip"></div>
    </div>
    <div class="form_btn"><input type="submit" value="注册" class="btn"></div>
</form>

<jsp:include page="mobile_footer.jsp"></jsp:include>
<script type="text/javascript" src="/front/mobile/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/front/mobile/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/front/mobile/js/passwordStrength.js"></script>
<jsp:include page="popup.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
    $('#jcaptchaImage').click(function(){
        $('#jcaptchaImage').attr("src","/jcaptcha.jpg?"+new Date());
    })

    var regValid = $("#reg").Validform({
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
        datatype:{//传入自定义datatype类型【方式二】;
            "idcard":function(gets,obj,curform,datatype){
                //该方法由佚名网友提供;
                var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// 加权因子;
                var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];// 身份证验证位值，10代表X;

                if (gets.length == 15) {
                    return isValidityBrithBy15IdCard(gets);
                }else if (gets.length == 18){
                    var a_idCard = gets.split("");// 得到身份证数组
                    if (isValidityBrithBy18IdCard(gets)&&isTrueValidateCodeBy18IdCard(a_idCard)) {
                        return true;
                    }
                    return false;
                }
                return false;

                function isTrueValidateCodeBy18IdCard(a_idCard) {
                    var sum = 0; // 声明加权求和变量
                    if (a_idCard[17].toLowerCase() == 'x') {
                        a_idCard[17] = 10;// 将最后位为x的验证码替换为10方便后续操作
                    }
                    for ( var i = 0; i < 17; i++) {
                        sum += Wi[i] * a_idCard[i];// 加权求和
                    }
                    valCodePosition = sum % 11;// 得到验证码所位置
                    if (a_idCard[17] == ValideCode[valCodePosition]) {
                        return true;
                    }
                    return false;
                }

                function isValidityBrithBy18IdCard(idCard18){
                    var year = idCard18.substring(6,10);
                    var month = idCard18.substring(10,12);
                    var day = idCard18.substring(12,14);
                    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));
                    // 这里用getFullYear()获取年份，避免千年虫问题
                    if(temp_date.getFullYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){
                        return false;
                    }
                    return true;
                }

                function isValidityBrithBy15IdCard(idCard15){
                    var year =  idCard15.substring(6,8);
                    var month = idCard15.substring(8,10);
                    var day = idCard15.substring(10,12);
                    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));
                    // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法
                    if(temp_date.getYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){
                        return false;
                    }
                    return true;
                }

            }

        },
        callback : function(data){
            $.post("/register.html",$(data).serialize(),function(result){
                if(result.success){
                	window.setTimeout("window.location='/front/mobile/login.jsp'",2000);
                }
                else if(result.code == "CAPTCHA"){
                    $('#jcaptchaImage').attr("src","/jcaptcha.jpg?"+new Date());
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
