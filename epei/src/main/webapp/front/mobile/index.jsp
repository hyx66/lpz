<!doctype html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<html>
<head>
<jsp:include page="meta.jsp"/>
<title>医护陪诊第一平台</title>
<link rel="stylesheet" href="/front/mobile/css/normalize-min.css" />
<link rel="stylesheet" href="/front/mobile/css/style.css" />
<link rel="stylesheet" href="/front/mobile/css/yiliao.css" />
<script src="/front/mobile/js/TouchSlide.1.1.js"></script>
</head>
<body class="home">
<div id="slideBox" class="slideBox">
	<div class="bd">
		<ul id="advertisement">
		</ul>
	</div>
	<div class="hd">
		<ul></ul>
	</div>
</div>

<div class="wrap">
    <div class="h_title"><span>医护陪诊第一平台</span></div>
    		<div class="content">
			<div class="content-list">
				<img src="/front/mobile/images/index_personal.png"/>
				
				<div class="content-text" onclick="window.location='/profile/mycenter.html'">
					<p class="bold">个人中心 </p>
					<p class="man-top">personal center</p>
				</div>
			</div>
			<div class="content-list" onclick="window.location='/order/ph/booking.html'">
				<img src="/front/mobile/images/index_ph.png"/>
				<div class="content-text">
					<p class="bold">陪护 </p>
					<p class="man-top">chaperonage</p>
				</div>
			</div>
			<div class="content-list" onclick="gotoPz()">
				<img src="/front/mobile/images/index_pz.png"/>
				<div class="content-text">
					<p class="bold">陪诊 </p>
					<p class="man-top">diagnosis</p>
				</div>
			</div>
			<div class="content-list">
				<img src="/front/mobile/images/index_lxb.png"/>
				<div class="content-text">
					<p class="bold">乐小宝  </p>
					<p class="man-top">lexiaobao</p>
				</div>
			</div>
			<div class="content-list">
				<img src="/front/mobile/images/index_gj.png"/>
				<div class="content-text">
					<p class="bold">乐管家 </p>
					<p class="man-top">leguanjia</p>
				</div>
			</div>
			<div class="content-6 content-list" onclick="window.location='/front/mobile/faq.jsp'">
				<img src="/front/mobile/images/index_more.png"/>
				<div class="content-text">
					<p class="bold">更多 </p>
					<p class="man-top">more</p>
					
				</div>
			</div>
		</div>
    <!-- h_quiklinks end -->
<div class="clear"></div>
    <div class="h_contact cf">
        <div class="email fl">
            <i class="icon"><img src="/front/mobile/images/icon_email.png" alt=""></i>
            <div class="con">
                <p class="c1">E-mail：</p>
                <p class="c2">cqsuhui@sina.com</p>
            </div>
        </div>
        <div class="tel fr">
            <i class="icon"><img src="/front/mobile/images/icon_tel.png" alt=""></i>
            <div class="con">
                <p class="c1">服务热线：</p>
                <p class="c2">400 883 7622</p>
            </div>
        </div>
    </div>
    <!-- h_contact end -->

</div>
<!-- main content end -->
<c:choose>
	<c:when test="${sessionCustomer.customerType==1}">
		<jsp:include page="adminMobile_footer.jsp"/>
	</c:when>
	<c:otherwise>
		<jsp:include page="mobile_footer.jsp"/>
	</c:otherwise>
</c:choose>
<jsp:include page="popup.jsp"></jsp:include>
<script>
var vipId = "${sessionCustomer.vipId}";
function gotoPz(){
	if(vipId==null || vipId == ""){
    	popup("陪诊服务目前只对VIP会员开放");
    }else{
    	window.location='/order/pz/booking.html';
    }
}

$(function(){
	$.ajax({
		 type: "GET",
		 url: "/queryAdvertisement.html",
		 data: {deleted:0},
		 dataType: "json",
		 async: false,
		 success: function(result){
			 for (var i=0;i<result.length;i++){
				var li = "<li><a href='"+result[i].articleUrl+"'><img style='height:264px' src='"+result[i].imageUrl+"' /></a></li>";
				$("#advertisement").append(li);
			 }
		}
	});
	TouchSlide({
	slideCell: "#slideBox",
	titCell: ".hd ul", 
	mainCell: ".bd ul",
	effect: "leftLoop",
	autoPage: true,
	autoPlay: true 
	});
});
</script>
</body>
</html>
