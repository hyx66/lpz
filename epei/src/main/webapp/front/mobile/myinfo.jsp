<!doctype html>
<%@ include file="/manage/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <jsp:include page="meta.jsp"></jsp:include>
<title>个人信息－医护陪诊第一平台</title>
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
        <i><img src="/front/mobile/images/leftarrow.png" alt=""></i>个人信息</div>
</div>

<form action="/profile/changePassword.html" method="post" id="changeMyInfo">
    <input type="hidden" name="id" value="${entity.id}">
    <input type="hidden" name="_csrf" value="${requestScope["org.springframework.security.web.csrf.CsrfToken"].token}"/>
    <div class="formitem">
        <label>账号：</label>
        <input type="text" readonly="readonly" name="mobile" placeholder="手 机 号：" class="input_txt" datatype="m" nullmsg="请输入手机号"
               errormsg="请输入正确的手机号" value="${entity.mobile}"/>
        <div class="Validform_checktip"></div>
    </div>
    <div class="formitem">
        <label>姓名：</label>
        <input type="text" name="name" class="input_txt" datatype="length1_8" nullmsg="请输入姓名" value="${entity.name}">
        <div class="Validform_checktip" ></div>
    </div>
    <div class="formitem">
        <label>会员类别：</label>
        <input type="text" name="customerType" readonly="readonly" class="input_txt" value="${entity.customerType==0?'普通会员':'医院管理员'}">
        <div class="Validform_checktip" ></div>
    </div>
    <div class="formitem">
        <label>身份证号：</label>
        <input type="text" name="idCard" class="input_txt" datatype="idcard" nullmsg="请输入身份证号" errormsg="请输入正确的身份证号" value="${entity.idCard}">
        <div class="Validform_checktip"></div>
    </div>
     <div class="formitem">
        <label>性别：</label>
        &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="sex" value="1" <c:if test="${entity.sex==1}">checked</c:if>/> 男 &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="radio" name="sex" value="0" <c:if test="${entity.sex!=1}">checked</c:if>/> 女
    </div>
    <div class="formitem">
        <label>生日：</label>
        <input type="text" name="birthday" class="input_txt" value="${entity.birthday}" placeholder="格式：19990101" nullmsg="请输入出生年月日" datatype="birthday" errormsg="请输入正确的出生日期"/>
        <div class="Validform_checktip" ></div>
    </div>
    <div class="formitem">
        <label>籍贯：</label>
        <input type="text" datatype="length0_30" name="nativePlace" class="input_txt" placeholder="例如：四川省成都市" errormsg="请控制在30字以内" value="${entity.nativePlace}"/>
        <div class="Validform_checktip" ></div>
    </div>
    <div class="formitem">
        <label>邮箱：</label>
        <input type="text" name="email" class="input_txt" value="${entity.email}" placeholder="请输入邮箱地址" nullmsg="请输入邮箱地址" datatype="email" errormsg="请输入正确的邮箱地址"/>
        <div class="Validform_checktip" ></div>
    </div>
    <div class="formitem">
        <label>职业：</label>
        <input type="text" errormsg="请控制在8字以内" datatype="length0_8" name="profession" class="input_txt" placeholder="例如：出租车司机、教师" value="${entity.profession}"/>
        <div class="Validform_checktip" ></div>
    </div>
     <div class="formitem">
        <label>地址：</label>
        <input type="text" datatype="length0_30" errormsg="请控制在30字以内" name="address" class="input_txt" placeholder="请填写现居住地址" value="${entity.address}"/>
        <div class="Validform_checktip" ></div>
    </div>
     <div class="formitem">
        <label>家庭成员：</label>
        <input type="text" name="family" errormsg="请控制在30字以内" datatype="length0_30" class="input_txt" placeholder="例如：父亲、母亲、女儿" value="${entity.family}"/>
        <div class="Validform_checktip" ></div>
    </div>
    <div class="formitem">
        <label>婚姻状况：</label>
         &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="maritalStatus" value="1" <c:if test="${entity.maritalStatus==1}">checked</c:if>/> 已婚 &nbsp;&nbsp;&nbsp;&nbsp;
        <input type="radio" name="maritalStatus" value="0" <c:if test="${entity.maritalStatus!=1}">checked</c:if>/> 未婚
        <div class="Validform_checktip" ></div>
    </div>
    <div class="formitem">
        <label>文化程度：</label>
        <input type="text" name="degreeOfEducation" errormsg="请控制在8字以内" datatype="length0_8" class="input_txt" placeholder="例如：小学、初中、高中" value="${entity.degreeOfEducation}"/>
        <div class="Validform_checktip" ></div>
    </div>
    <div class="formitem">
    <label>联系人：</label>
        <input type="text" name="emergencyContactPerson" errormsg="请控制在8字以内" datatype="length1_8" class="input_txt" placeholder="紧急联系人姓名" value="${entity.emergencyContactPerson}"/>
        <div class="Validform_checktip" ></div>
    </div>
    <div class="formitem">
    <label>与其关系：</label>
        <input type="text" name="emergencyContactRelationship" errormsg="请控制在8字以内" datatype="length1_8"  placeholder="与紧急联系人关系" class="input_txt" value="${entity.emergencyContactRelationship}"/>
        <div class="Validform_checktip" ></div>
    </div>
     <div class="formitem">
     <label>紧急电话：</label>
        <input type="text" name="emergencyContactNumber" datatype="m" nullmsg="请填写紧急联系人电话" placeholder="紧急联系人电话" class="input_txt" value="${entity.emergencyContactNumber}"/>
        <div class="Validform_checktip" ></div>
    </div>
     <div class="formitem">
        <label>会员消息：</label>
        &nbsp;<input type="radio" name="receiveInfo" value="短信" checked/>短信
        &nbsp;<input type="radio" name="receiveInfo" value="电话" <c:if test="${entity.receiveInfo=='电话'}">checked</c:if>>电话
        &nbsp;<input type="radio" name="receiveInfo" value="邮箱" <c:if test="${entity.receiveInfo=='邮箱'}">checked</c:if>>邮箱
        &nbsp;<input type="radio" name="receiveInfo" value="QQ" <c:if test="${entity.receiveInfo=='QQ'}">checked</c:if>>QQ
        &nbsp;<div class="Validform_checktip" ></div>
    </div>
    <div class="formitem">
        <label>预留电话：</label>
        <input type="text" name="phoneNumber" class="input_txt" datatype="phone" placeholder="座机格式：023-88888888" value="${entity.phoneNumber}"/>
        <div class="Validform_checktip" ></div>
    </div>
    <div class="form_btn"><input type="submit" value="保存" class="btn"></div>

</form>

<jsp:include page="mobile_footer.jsp"></jsp:include>
<script type="text/javascript" src="/front/mobile/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/front/mobile/js/Validform_v5.3.2_min.js"></script>
<jsp:include page="popup.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
    $("#changeMyInfo").Validform({
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
        	"phone":function(gets,obj,curform,datatype){
        		var mydate1 = /^1[0-9]{10}$/;
        		var mydate2 = /^(\(\d{3,4}\)|\d{3,4}-)?\d{7,8}$/;
        	    var check1 = gets.match(mydate1);
        	    var check2 = gets.match(mydate2);
    			if (check1=null&&check2==null) {
    				return false;
    	            }
    		},
    		"length1_8":function(gets,obj,curform,datatype){
        		var mydate = /^.{1,8}$/;   
        	    var check = gets.match(mydate);
    			if (check==null) {
    				return false;
    	            }
    		},
    		"length0_8":function(gets,obj,curform,datatype){
        		var mydate = /^.{0,8}$/;   
        	    var check = gets.match(mydate);
    			if (check==null) {
    				return false;
    	            }
    		},
    		"length0_30":function(gets,obj,curform,datatype){
        		var mydate = /^.{0,30}$/;   
        	    var check = gets.match(mydate);
    			if (check==null) {
    				return false;
    	            }
    		},
        	"birthday":function(gets,obj,curform,datatype){
        		var mydate = /^[12]\d{3}(0\d|1[0-2])([0-2]\d|3[01])$/;   
        	    var check = gets.match(mydate);
    			if (check==null) {
    				return false;
    	            }
    		},
    		"email":function(gets,obj,curform,datatype){
        		var mydate = /^[a-zA-Z0-9_+.-]+\@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/;  
        	    var check = gets.match(mydate);
    			if (check==null) {
    				return false;
    	            }
    		},
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
            $.post("/profile/modifyMyInfo.html",$(data).serialize(),function(result){
                popup(result.message);
            });
            return false;
        }
    });
})
</script>
</body>
</html>
