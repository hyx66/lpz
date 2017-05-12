<!doctype html>
<%@ include file="/manage/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<jsp:include page="meta.jsp"/>
<title>积分明细</title>
<link rel="stylesheet" href="/front/mobile/css/style.css" />
<link rel="stylesheet" href="/front/mobile/css/huiyuan.css" />
<link rel="stylesheet" href="/front/mobile/css/acctCenter.css" />
</head>
<body>
<!--顶部-->
<div class="breadcrumb">
    <div class="path" onclick="window.history.go(-1)">
        <i><img src="/front/mobile/images/leftarrow.png" alt=""></i>积分明细</div>
</div>
<!--当前-->
<div class="now1">
  <div class="now1-up">
    <div class="now1-left">
	当前用户
	</div>
	<div class="now1-right">
	当前积分
	</div>
 </div>
 <div class="now1-down">
    <div class="now2-left">
	${customer.userName}
    </div>
    <div class="now2-right">
	${points}
    </div>
 </div>
</div>
<div class="clear"></div>
<!-- 选择查询时间 -->

<!--充值消费明细-->
<div class="record">
<c:forEach var="customerPointsRecords" items="${customerPointsRecordsList}">
    <div class="list">
	    <div class="list-left">
		   <div class="record-time">
		   <fmt:formatDate value="${customerPointsRecords.createTime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
		   </div>
		   <div class="method">
		  	 操作方式&nbsp;&nbsp;<span class="second-font">${customerPointsRecords.memo}</span>  
		   </div>
		</div>
		<div class="list-right">
			<div class="figure">
			   积分：<span class="second-font1">${customerPointsRecords.points}</span>
			</div>
			<div class="mold">
			   操作类型：<span class="second-font">${customerPointsRecords.dataType==1?'收益':'支出'}</span>
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