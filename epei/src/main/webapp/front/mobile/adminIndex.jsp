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


<ul class="mycenter_list">
	<li>
	<c:if test="${sessionCustomer.adminType==0}">
		<a href="/order/PZ/orderList.html">陪诊订单<i><img src="/front/mobile/images/rightarrow.png" alt=""></i></a>
	</c:if>
	<c:if test="${sessionCustomer.adminType==1}">
		<a href="/order/PH/orderList.html">陪护订单<i><img src="/front/mobile/images/rightarrow.png" alt=""></i></a>
    </c:if>
    	<a href="/order/showList.html">个人订单<i><img src="/front/mobile/images/rightarrow.png" alt=""></i></a>
	</li>
</ul>

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
