<%@ include file="/manage/common/taglibs.jsp"%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<jsp:include page="meta.jsp"/>
<title>订单－医护陪诊第一平台</title>
<link rel="stylesheet" href="/front/mobile/css/normalize-min.css" />
<link rel="stylesheet" href="/front/mobile/css/style.css" />
</head>

<body>
<div class="breadcrumb">
    <div class="path"
         onclick="window.location='/front/mobile/index.jsp'"><i><img src="/front/mobile/images/leftarrow.png" alt=""></i>订单</div>
</div>

<c:forEach var="order" items="${orders}">
    <div class="booking" onclick="window.location='/order/detail.html?orderNo=${order.orderNo}'">
        <div class="wrap">
            <div class="t">
                <i>
                    <c:choose>
                        <c:when test="${order.orderType =='陪护'}">
                            <img src="/front/mobile/images/icon_pf.png" alt="陪护">
                        </c:when>
                        <c:otherwise>
                            <img src="/front/mobile/images/icon_heart.png" alt="陪诊">
                        </c:otherwise>
                    </c:choose>
                </i>
                    ${order.orderType}
                <a href="javascript:void(0);" class="a"><span style="font-family:隶书;font-size:16px;">${not empty order.originalOrderId?'续':''}</span>
                    ${order.state}${order.payStatus==0?'丨未支付':(order.payStatus==1?'丨已支付':'')}
                </a>
            </div>
            <ul class="booking_list">
                <li class="cf">
                    <div class="date">
                        <p class="c1">
                            <fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd"/>
                        </p>
                        <p class="c2">
                            ${order.timeInOneDay=="AM"?'上午':'下午'}
                        </p>
                    </div>
                    <div class="addr">
                        <p class="c1"> ${order.hospitalName}</p>
                        <p class="c2"> ${order.hospitalReception}</p>
                    </div>	
                    <div class="price">
                        <p class="c1">
                            <span class="yellow"> ${order.price==0?'免费':order.price}</span>
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

</body>
</html>
