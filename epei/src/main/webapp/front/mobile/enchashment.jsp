<!DOCTYPE html>
<%@ include file="/manage/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <jsp:include page="meta.jsp"/>
    <title>陪诊订单－医护陪诊第一平台</title>
    <!-- <link rel="icon" href="images/favicon.ico" /> -->
    <link rel="stylesheet" href="/front/mobile/css/normalize-min.css"/>
    <link rel="stylesheet" href="/front/mobile/css/mui.css">
    <link rel="stylesheet" href="/front/mobile/js/jqtransformplugin/jqtransform.css"/>
    <link rel="stylesheet" href="/front/mobile/css/style.css"/>
    <link rel="stylesheet" href="/front/mobile/css/mui.picker.min.css"/>
</head>

<body>
<form action="/wxzf/doEnchashment.html" method="post" class="booking_form">
    <input type="hidden" name="_csrf" value="${requestScope["org.springframework.security.web.csrf.CsrfToken"].token}"/>
    <div class="breadcrumb">
        <div class="path" onclick="window.history.go(-1)"><i><img src="/front/mobile/images/leftarrow.png" alt=""></i>
           	 余额提现
        </div>
    </div>
    <div class="booking_box">
        <div class="formitem" class="input_text">
        <label>会员账号：</label>
            <input type="text" class="input_text" value="${customer.userName}" disabled="disabled" style="border:0px"/>
        </div>
        <div class="formitem">
        <label>可用金额：</label>
            <input type="text" id="availableAmount" class="input_text" value="${availableAmount}" disabled="disabled" style="border:0px"/>
        </div>
        <div class="formitem">
        <label>提现金额：</label>
            <input id="amount" name="amount" type="text" class="input_text"  oninput="check(this)"/>
            <div class="Validform_checktip"></div>
        </div>
        <div class="formitem" name="">
        <label>申请理由：</label>
            <input name="explain" type="text" class="input_text"/>
        </div>
    </div>
    <div class="form_btn"><input type="submit" value="申请提现" class="btn" id="submit"></div>
</form>
<div class="form_btn" style="padding-bottom:30px;color:red">注意：提现申请通过后企业将付款到您当前登录的微信账号，请确认您当前的登录账号！</div>
<c:forEach var="enchashment" items="${enchashmentApplyList}">
    <div class="booking">
        <div class="wrap">
            <div class="t">
            </div>
            <ul class="booking_list">
                <li class="cf">
                    <div class="date">
                    <p class="c1">
                    申请时间
                    </p>
                        <p class="c1">
                            <fmt:formatDate value="${enchashment.createTime}" pattern="yyyy-MM-dd"/><span style="font-family:隶书;font-size:16px;"></span>
                        </p>
                    </div>
                    <div class="addr">
                        <p class="c1">提现金额：${enchashment.amount}</p>
                        <p class="c1">NO.${fn:substring(enchashment.applyNo, 0, 6)}……</p>
                    </div>
                    <div class="price">
                    	<p class="c1">
                    	<span style="color: #4dc5c5">${enchashment.status==1?'申请中':'已提现'}</span>
                        </p>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</c:forEach>

<c:choose>
	<c:when test="${sessionCustomer.customerType==1}">
		<jsp:include page="adminMobile_footer.jsp"/>
	</c:when>
	<c:otherwise>
		<jsp:include page="mobile_footer.jsp"/>
	</c:otherwise>
</c:choose>

<script type="text/javascript" src="/front/mobile/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/front/mobile/js/jqtransformplugin/jquery.jqtransform-min.js"></script>
<script type="text/javascript" src="/front/mobile/js/Validform_v5.3.2_min.js"></script>
<jsp:include page="popup.jsp"></jsp:include>
<script>
var amount = null;
var max = $("#availableAmount").val();
function check(obj){
	var reg;
	if(obj.value.indexOf(".")!=-1){
		reg = /^(([1-9]\d{0,3})|0)(\.\d{0,2})?$/;
	}else{
		reg = /^(([1-9]\d{0,3})|0|)(\.\d{0,2})?$/;
	}
	if(obj.value.match(reg)!=null){
		amount = obj.value;
	}else{
		obj.value = amount;
	}
}

$("#submit").click(function(){
	if($("#amount").val()>max || $("#amount").val()<1){
		alert("请输入大于1且不小于可用金额的数字！");
		return false;
	}
 });
 
var errorMsg = "${message}";
if(errorMsg && errorMsg !=""){
    popup(errorMsg);
}
</script>
</body>
</html>