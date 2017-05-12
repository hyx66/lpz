<!doctype html>
<%@ include file="/manage/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<jsp:include page="meta.jsp"></jsp:include>
<title>订单详情－医护陪诊第一平台</title>
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
<div class="breadcrumb">
    <div class="path"
         onclick="window.location='/front/mobile/adminIndex.jsp'"><i><img src="/front/mobile/images/leftarrow.png" alt=""></i>订单</div>
</div>

<c:forEach var="order" items="${paramPageInfo.pageResults}">
    <div class="booking">
        <div class="wrap">
            <div class="t">
            </div>
            <ul class="booking_list">
                <li class="cf">
                    <div class="date">
                        <p class="c1">
                            <fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd"/><span style="font-family:隶书;font-size:16px;">${not empty order.originalOrderId?'续':''}</span>
                        </p>
                        <p class="c1">
                     	   ${orderType=="PH"?"陪护":"陪诊"}
                        </p>
                    </div>
                    <div class="addr">
                        <p class="c1"> ${order.patientName}</p>
                        <p class="c2"> ${order.patientMobile}</p>
                    </div>
                    <div class="price" onclick="window.location='/order/adminOrderDetail.html?orderNo=${order.orderNo}&orderType=${orderType}'">
                        <p class="c1">
			                <c:choose>
			                    	<c:when test="${order.state =='ORDERED'}">
			                    	<span style="color: #EE5C42">未确认</span>
			                    	</c:when>
			                    	<c:when test="${order.state =='FINISHED'}">
			                    	<span style="color: #4dc5c5">已完成</span>
			                    	</c:when>
			                    	<c:when test="${order.state =='CONFIRMED'}">
			                    	<span style="color: #4dc5c5">已确认</span>
			                    	</c:when>
			                    	<c:otherwise>
			                    	<span style="color: #4dc5c5">已取消</span>
			                    	</c:otherwise>
			                 </c:choose>
                    	</p>
                    	<p class="c1">
                    	<span style="color: #4dc5c5">${order.payStatus==1?'已付款':(order.payStatus==0?'未付款':'无记录')}</span>
                        </p>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</c:forEach>
<div class="footer cf" style="height:30px">
    <ul>
        <li><a href="/order/${orderType}/orderList.html"><span>首页</span></a></li>
        <li><a href="/order/${orderType}/orderList.html?page=${paramPageInfo.currentPage-1==0?'1':paramPageInfo.currentPage-1}"><span>上页</span></a></li>
        <li><a href="/order/${orderType}/orderList.html?page=${paramPageInfo.currentPage+1<=paramPageInfo.totalPage?paramPageInfo.currentPage+1:paramPageInfo.currentPage}"><span>下页</span></a></li>
    </ul>
</div>
<jsp:include page="popup.jsp"></jsp:include>
</body>
</html>