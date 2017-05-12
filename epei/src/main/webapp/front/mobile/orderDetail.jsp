<!doctype html>
<%@ include file="/manage/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <jsp:include page="meta.jsp"></jsp:include>
<title>陪护订单详情－医护陪诊第一平台</title>
<!-- <link rel="icon" href="images/favicon.ico" /> -->
<link rel="stylesheet" href="/front/mobile/css/normalize-min.css" />
<link rel="stylesheet" href="/front/mobile/css/style.css" />

<style>
    .form_btn .btn{
        width: 44.4%;
    }

</style>
</head>

<body>
<div id="mask" style="position:fixed;left:0;top:0;width:100%;height:100%;z-index:99999;text-align:center;padding-top:200px;display:none;">
<img src="/front/mobile/images/loading.gif">
</div>
<div class="breadcrumb">
    <div class="path"
         onclick="window.location='/order/showList.html'"><i><img src="/front/mobile/images/leftarrow.png" alt=""></i
            >订单详情</div>
</div>

<div class="wrap">
    <div class="booking_detail">
        <div class="addr">
            <h2>${order.hospitalName}<span style="font-family:隶书;">${not empty order.originalOrderId?'续单':''}</span></h2>
            <p>科室：${order.departmentName}</p>
            <a href="javascript:void(0)" class="l"></a>
        </div>

        <div class="service_info">
        	<div class="c1 cf">
                <div class="item">
                <span class="l">服务项目：</span>
                <span class="d">${order.orderType}</span>
                </div>
                <div class="item r">
                    <span class="l">订单状态：</span>
                    <span>${order.state}</span>
                </div>
            </div>
            <div class="c1 cf">
                <div class="item">
                    <span class="l">应付金额：</span>
                    <span class="d">${not empty order.amount?order.amount:'——'}</span>
                </div>
                <div class="item r">
                    <span class="l">支付状态：</span>
                    <span>${order.payStatus==0?'待付':(order.payStatus==1?'已付':'不详')}</span>
                </div>
            </div>
        </div>
        <c:if test="${not empty order.freeItem && order.freeItem==1}">
        <div style="text-align:center;margin-top:12px">
        	<span style="color:red;font-size:12px">注：费用包含VIP免费${order.orderType}1次,已从应付金额中扣除</span>
        </div>
		</c:if>
		
        <div class="main_info">
            <div class="item"><span class="l">患者姓名：</span><span class="d">${order.patientName}</span></div>
            <div class="item"><span class="l">联系电话：</span><span class="d">${order.patientMobile}</span></div>
            <div class="item"><span class="l">预约时间：</span><span class="d"><c:if test="${order.orderType=='陪诊'}"><fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></c:if><c:if test="${order.orderType=='陪护'}"><fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd"></fmt:formatDate></c:if></span></div>
            <div class="item"><span class="l">患者生日：</span><span class="d">${order.patientBirthday}</span></div>
            <div class="item"><span class="l">患者性别：</span><span class="d">${order.patientGender==0?'女':(order.patientGender==1?'男':'')}</span></div>
            <div class="item"><span class="l">服务人员：</span><span class="d">${order.servicePerson}</span></div>
            <c:if test="${order.orderType=='陪诊'}">
            <div class="item"><span class="l">医保卡号：</span><span class="d">${order.patientMedicareCard}</span></div>
            </c:if>
            <c:if test="${order.orderType=='陪护'}">
            <div class="item"><span class="l">住院床号：</span><span class="d">${order.bed}</span></div>
            <div class="item"><span class="l">开始日期：</span><span class="d"><fmt:formatDate value="${order.startTime}" pattern="yyyy-MM-dd"></fmt:formatDate></span></div>
            <div class="item"><span class="l">结束日期：</span><span class="d"><fmt:formatDate value="${order.endTime}" pattern="yyyy-MM-dd"></fmt:formatDate></span></div>
            <div class="item"><span class="l">预约天数：</span><span class="d">${order.duration==0?'':order.duration}</span></div>
            <div class="item"><span class="l">服务单价：</span><span class="d">${order.phServicePrice}</span></div>
        	</c:if>
        	<c:if test="${order.state == '完成' && not empty order.star}">
        	<div class="item"><span class="l">用户评价：</span><span class="d"><span style="color:red">${order.star}星-</span> ${order.comment}</span></div>
        	</c:if>
		</div>	

        <form id="orderDetailForm" action="/order/weixinPay.html" method="post">
            <input type="hidden" name="_csrf" value="${requestScope["org.springframework.security.web.csrf.CsrfToken"].token}"/>
            <input name="orderNo" value="${order.orderNo}" type="hidden"/>
        </form>
        <c:choose>
            <c:when test="${order.state == '预约'}">
            	<c:if test="${order.editable==true}">
	            	<c:choose>
	            		<c:when test="${empty order.acceptance || order.acceptance==1}">
			            	<div class="form_btn">
				                <input type="button" value="修改订单" class="btn" id="editBtn">
				                <input type="button" value="取消订单" class="btn" id="cancelBtn">
			            	</div>
	            		</c:when>
		            	<c:when test="${not empty order.acceptance && order.acceptance==2}">
				            <div class="form_btn" style="color: darkred;">
			          		工作人员正在处理，请耐心等待……
			          		</div>
			            </c:when> 
			            <c:when test="${not empty order.acceptance && order.acceptance==3 && order.payStatus==0}">
				       		<div class="form_btn" style="color: darkred;">
				       			<input  style="width:90%" type="button" value="在线付款" class="btn" id="confirmBtn">
				       	 	</div>
			    		</c:when>
			    		<c:when test="${order.payStatus==1}">
		    				<div class="form_btn" style="color: darkred;">
			          		您已经付款，稍后会有工作人员与您联系……
			          		</div>
			    		</c:when>
	            	</c:choose>
        		</c:if> 
	            <c:if test="${order.editable==false}">
	           		<div class="form_btn" style="color: darkred;">
	           			预约时间:<fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd mm:HH"></fmt:formatDate>预约时间已过，不可修改、取消订单,请联系客服:4008837622
	            	</div>
        		</c:if>
            </c:when>    

            <c:when test="${order.state == '取消'}">
	            <div class="form_btn" style="color: darkred;">
          		服务已取消……
          		</div>
            </c:when> 
            
            <c:when test="${order.state == '确认'}">
	            <div class="form_btn" style="color: darkred;">
          		服务正在进行……
          		</div>
            </c:when> 
            
            <c:otherwise>
	            <c:if test="${empty order.star}">
        			<div class="form_btn" style="color: darkred;">
            			服务已结束……<a id="comment">去评价</a>
           			</div>
       			</c:if>
       			<c:if test="${not empty order.star}">
        			<div class="form_btn" style="color: darkred;">
            			服务已结束……
           			</div>
       			</c:if>
            </c:otherwise> 
        </c:choose>
    </div>
</div>

<c:choose>
	<c:when test="${sessionCustomer.customerType==1}">
		<jsp:include page="adminMobile_footer.jsp"/>
	</c:when>
	<c:otherwise>
		<jsp:include page="mobile_footer.jsp"/>
	</c:otherwise>
</c:choose>
<jsp:include page="popup.jsp"></jsp:include>

<script type="text/javascript" src="/front/mobile/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/front/mobile/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
$('#comment').click(function(){
	$.ajax({
        type: "get",
        dataType: "json",
        url: '/order/commmentQuery.html',
        data:{"orderId":"${order.id}"},  
        success: function (data) {
          if(data=="success"){
        	   window.location.href='/order/comment.html?orderId=${order.id}';
           }else{
        	   popup("您已经评价过了哦！");
           }
        }
    });
}); 
$(function(){
	$("#cancelBtn").click(function(){
	   if(confirm("确定取消该订单?")){
	       $.post("/order/orderCancel.html",$("#orderDetailForm").serialize(),function(result){
	            if(result.success){
	            	alert(result.message);
	                window.location = "/order/showList.html";
	            }
	           else{
	                popup(result.message);
	            }
	       });
	   }
});

$("#editBtn").click(function(){
	window.location.href = "/order/${order.orderType=='陪诊'?'pz':'ph'}/booking.html?orderNo=${order.orderNo}";
});
        
$("#confirmBtn").click(function(){
	    $("#orderDetailForm").submit();
	    $("#mask").show();
 	});
});
</script>
<c:if test="${!empty appId}">
<script type="text/javascript">
$("#mask").show();
function onBridgeReady(){
    WeixinJSBridge.invoke(
            'getBrandWCPayRequest', {
                "appId" : "${appId}", //公众号名称，由商户传入
                "timeStamp" : "${timestamp}", //时间戳
                "nonceStr" : "${nonceStr}", //随机串
                "package" : "${wxPackage}",//统一支付接口返回的prepay_id 参数值，提交格式如：prepay_id=***
                "signType" : "${signType}", //微信签名方式:sha1
                "paySign" : "${paySign}" //微信签名
     },
    function(res){
        if (res.err_msg == "get_brand_wcpay_request:ok") {
            window.location.href = "/order/showList.html";
        } else if (res.err_msg == "get_brand_wcpay_request:cancel") {
            window.location.href = "/order/showList.html";
        } else if (res.err_msg == "get_brand_wcpay_request:fail") {
            alert("支付失败！");
            window.location.href = "/order/showList.html";
        }
    }
);
    $("#mask").hide();
}
if (typeof WeixinJSBridge == "undefined"){
    if( document.addEventListener ){
        document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
    }else if (document.attachEvent){
        document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
        document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
    }
}else{
    onBridgeReady();
}
</script>
</c:if>
<c:if test="${!empty err_code_des}">
<script>
popup("${err_code_des}");
</script>
</c:if>
</body>
</html>