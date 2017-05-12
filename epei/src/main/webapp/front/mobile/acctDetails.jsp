<!doctype html>
<%@ include file="/manage/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<jsp:include page="meta.jsp"/>
<title>账户中心</title>
<link rel="stylesheet" href="/front/mobile/css/style.css" />
<link rel="stylesheet" href="/front/mobile/css/huiyuan.css" />
<link rel="stylesheet" href="/front/mobile/css/acctCenter.css" />
</head>
<body>
<!--顶部-->
<div class="breadcrumb">
    <div class="path" onclick="window.location.href='/wxzf/acctCenter.html'">
        <i><img src="/front/mobile/images/leftarrow.png" alt=""></i>财务明细</div>
</div>
<!--当前-->
<div class="now1">
  <div class="now1-up">
    <div class="now1-left">
	当前用户
	</div>
	<div class="now1-right">
	可用金额
	</div>
 </div>
 <div class="now1-down">
    <div class="now2-left">
	${customer.userName}
    </div>
    <div class="now2-right">
	${availableAmount}
    </div>
 </div>
</div>
<div class="clear"></div>
<!-- 选择查询时间 -->

<!--充值消费明细-->
<div class="record">
<c:forEach var="customerAcctRecords" items="${customerAcctRecordsList}">
    <div class="list">
	    <div class="list-left">
		   <div class="record-time">
		   <fmt:formatDate value="${customerAcctRecords.createTime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
		   </div>
		   <c:if test="${customerAcctRecords.dataType==1}">
		   <div class="method">
		  	 标题：&nbsp;&nbsp;<span class="second-font">${customerAcctRecords.title}</span>  
		   </div>
		   </c:if>
		   <c:if test="${customerAcctRecords.dataType==2}">
		   <div class="method">
		  	 标题：&nbsp;&nbsp;<span class="second-font">${customerAcctRecords.title}</span>  
		   </div>
		   </c:if>
		</div>
		<div class="list-right">
			<c:if test="${customerAcctRecords.dataType==1}">
			<div class="figure">
			   金额：<span class="second-font1">${customerAcctRecords.rechargeAmount}元</span>
			</div>
			</c:if>
			<c:if test="${customerAcctRecords.dataType==2}">
			<div class="figure">
			   金额：<span class="second-font1">${customerAcctRecords.spendAmount}元</span>
			</div>
			</c:if>   
			<div class="mold">
			   类型：<span class="second-font">${customerAcctRecords.dataType==1?'收入':'支出'}</span>
			</div>
		</div>
	</div>
</c:forEach>
</div>
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