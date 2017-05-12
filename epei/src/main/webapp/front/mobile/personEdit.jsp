<!doctype html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<html>
<head>
<jsp:include page="meta.jsp"></jsp:include>
<title>修改服务人员信息－医护陪诊第一平台</title>
<!-- <link rel="icon" href="images/favicon.ico" /> -->
<link rel="stylesheet" href="/front/mobile/css/normalize-min.css" />
<link rel="stylesheet" href="/front/mobile/css/mui.css">
<link rel="stylesheet" href="/front/mobile/js/jqtransformplugin/jqtransform.css" />
<link rel="stylesheet" href="/front/mobile/css/style.css" />
<link rel="stylesheet" href="/front/mobile/css/mui.picker.min.css" />
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
    <div class="path"
         onclick="window.location='/servicedPerson/personList.html'"><i><img src="/front/mobile/images/leftarrow.png"
                                                                       alt=""></i>
        <c:choose>
            <c:when test="${empty person}">
                添加服务人员信息
            </c:when>
            <c:otherwise>
                修改服务人员信息
            </c:otherwise>
        </c:choose>
    </div>
</div>

<form action="#" method="post" id="person_form" class="booking_form">
    <input type="hidden" name="_csrf" value="${requestScope["org.springframework.security.web.csrf.CsrfToken"].token}"/>
    <jodd:form bean="person" scope="request">
        <input type="hidden" name="id"/>
       <%-- <c:if test="${empty person.id}">--%>
            <div class="booking_box">
                <div class="formitem">
                    <input type="text" name="name" placeholder="请输入需服务人姓名" class="input_txt" datatype="*"
                           nullmsg="请输入需服务人姓名">
                    <div class="Validform_checktip"></div>
                </div>
                <div class="formitem">
                    <input type="text" name="mobile"  class="input_txt" placeholder="请输入需服务人电话" datatype="m"
                           nullmsg="请输入需服务人电话" errorMsg="请输入正确的电话号码">
                    <div class="Validform_checktip"></div>
                </div>
                <div class="formitem">
                    <input type="text" name="idCard" class="input_txt" placeholder="请输入需服务人身份证号" datatype="idcard"
                           nullmsg="请输入需服务人身份证号" errorMsg="请输入正确的身份证号">
                    <div class="Validform_checktip"></div>
                </div>
                <div class="formitem">
                    <input type="text" name="medicareCard" class="input_txt" placeholder="请输入需服务人医保卡号">
                </div>
            </div>
        <%--</c:if>--%>
        <%--<c:if test="${!empty person.id}">
            <div class="booking_box">
                <div class="formitem">
                    <label >需服务人姓名:</label>
                    <span>${person.name}</span>
                </div>
                <div class="formitem">
                    <label >需服务人身份证号:</label>
                    <span>${person.idCard}</span>
                </div>
                <div class="formitem">
                    <label >需服务人医保卡号:</label>
                    <input type="text" name="medicareCard"  class="input_txt" placeholder="请输入医保卡号" value="${person.medicareCard}"/>
                </div>
                <div class="formitem">
                    <label>需服务人电话:</label>
                    <input type="text" name="mobile"  class="input_txt" placeholder="请输入需服务人电话" datatype="m"
                           nullmsg="请输入需服务人电话" errorMsg="请输入正确的电话号码"/>
                    <div class="Validform_checktip"></div>
                </div>
            </div>
        </c:if>--%>
    </jodd:form>
    <div class="form_btn"><input type="submit" value="提交" class="btn" id="personSubmitBtn"></div>
</form>

<jsp:include page="mobile_footer.jsp"></jsp:include>

<script type="text/javascript" src="/front/mobile/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/front/mobile/js/jqtransformplugin/jquery.jqtransform-min.js"></script>
<script type="text/javascript" src="/front/mobile/js/Validform_v5.3.2_min.js"></script>

<jsp:include page="popup.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
    //就这一行代码！;
    $("#person_form").Validform({
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
            $.post("/servicedPerson/personSave.html",$("#person_form").serialize(),function(result){
                if(result.success){
                    window.location.href="/servicedPerson/personList.html";
                }
                else{
                    popup(result.message);
                }
            });
            return false;
        }
    });
})
</script>
</body>
</html>
