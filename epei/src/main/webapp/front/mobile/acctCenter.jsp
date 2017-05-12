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
<div class="booking_form">
    <div class="breadcrumb">
        <div class="path" onclick="window.history.go(-1)"><i><img src="/front/mobile/images/leftarrow.png" alt=""></i>
           	账户中心
        </div>
    </div>
    <div class="booking_box">
        <div style="font-family:楷体;" class="formitem" class="input_text">
        <label>会员账号：</label>
            ${customer.userName}
        </div>
        <div  style="font-family:楷体;" class="formitem">
        <label>可用金额：</label>
            ${availableAmount}元
        </div>
        <div  style="font-family:楷体;" class="formitem">
        <label>剩余积分：</label>
            ${points}
        </div>
    </div>
    <!-- <div class="form_btn"><input type="button" value="充值缴费" class="btn" id="btn1" onclick="javascript:window.location.href='/wxzf/recharge.html'"></div> -->
    <div class="form_btn"><input type="button" value="余额提现" class="btn" id="btn2" onclick="javascript:window.location.href='/wxzf/toEnchashment.html'"></div>
    <div class="form_btn"><input type="button" value="财务明细" class="btn" id="btn3" onclick="javascript:window.location.href='/wxzf/acctDetails.html'"></div>
    <div class="form_btn"><input type="button" value="积分明细" class="btn" id="btn4" onclick="javascript:window.location.href='/wxzf/pointsDetails.html'"></div>
</div>

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
</body>
</html>