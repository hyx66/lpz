<!doctype html>
<%@ include file="/manage/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<jsp:include page="meta.jsp"/>
<title>乐陪诊-在线充值</title>
<link rel="stylesheet" href="/front/mobile/css/style.css" />
<link rel="stylesheet" href="/front/mobile/css/huiyuan.css" />
</head>
<body>
<div id="mask" style="position:fixed;left:0;top:0;width:100%;height:100%;z-index:99999;text-align:center;padding-top:200px;display:none;">
<img src="/front/mobile/images/loading.gif">
</div>
<form id="form1" action="/wxzf/pay.html" method="post">
 	<input type="hidden" name="_csrf" value="${requestScope["org.springframework.security.web.csrf.CsrfToken"].token}"/>
	<input type="hidden" id="rechargeAmount" name="rechargeAmount"/>
</form>
<div class="breadcrumb">
    <div class="path" onclick="window.history.go(-1)">
        <i><img src="/front/mobile/images/leftarrow.png" alt=""></i>会员充值</div>
</div> 
<div class="huiyuan">
      <div class="huiyuan1">会员：</div>
	  <div class="huiyuannumber">${customer.userName}</div>   
</div>
<div class="clear"></div>
<div class="jine">
    <div class="amount kuang">100元</div>
	<div class="amount kuang2">200元</div>
	<div class="amount kuang3">300元</div>
</div>
<div class="clear">
</div>
<div class="jine">
    <div class="amount kuang">500元</div>
	<div class="amount kuang2">700元</div>
	<div class="amount kuang3">1000元</div>
	<div class="clear"></div>
	<div class="chongzhi">充值金额：<span id="showRecharge">请选择</span></div>
</div>
<div class="way">
支付方式：
</div>
<div class="wechat">
   <div class="wechat-left"><img src="/front/mobile/images/wechat.png" />微信支付</div>
   <div class="wechat-right"><img src="/front/mobile/images/buttom.png" /></div>
</div>
<div id="submit" class="buttom">
    立即充值
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
<script>
$(".amount").click(function(){
	$("#rechargeAmount").val($(this).text().replace('元',''));
	$("#showRecharge").text($(this).text());
	$(".amount").css("background-color","");
	$(this).css("background-color","#BBFFFF");
});
$("#submit").click(function(){
	if($("#rechargeAmount").val()!=""){
		$("#mask").show();
		$("#form1").submit();
		$("#form1").attr("id","");
	}else{
		alert("请先选择充值金额！");
	}
});
</script>

<%--微信设置--%>
<c:if test="${!empty appId}">
<script type="text/javascript">
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
            window.location.href = "/wxzf/paySuccess.html";
        } else if (res.err_msg == "get_brand_wcpay_request:cancel") {
            window.location.href = "/wxzf/recharge.html";
        } else if (res.err_msg == "get_brand_wcpay_request:fail") {
            alert("支付失败！");
            window.location.href = "/wxzf/recharge.html";
        }
    }
);
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

<c:if test="${!empty isDisable}">
<script>
alert("${isDisable}");
</script>
</c:if>

</body>
</html>
