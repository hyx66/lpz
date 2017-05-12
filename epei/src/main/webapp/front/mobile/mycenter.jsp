<!doctype html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<html>
<head>
<jsp:include page="meta.jsp"/>
<title>我的－医护陪诊第一平台</title>
<!-- <link rel="icon" href="images/favicon.ico" /> -->
<link rel="stylesheet" href="/front/mobile/css/normalize-min.css" />
<link rel="stylesheet" href="/front/mobile/css/style.css" />
</head>

<body>
<div class="breadcrumb">
    <div class="path" onclick="window.history.go(-1)"><i><img src="/front/mobile/images/leftarrow.png" alt=""></i>我
        的</div>
</div>

<ul class="mycenter_list">
    <li>
        <a href="/profile/myinfo.html">个人信息<i><img src="/front/mobile/images/rightarrow.png" alt=""></i></a>
    </li>
    <li>
        <a href="/profile/changePassword.html">修改密码<i><img src="/front/mobile/images/rightarrow.png" alt=""></i></a>
    </li>
    <!-- <li>
        <a href="/wxzf/acctCenter.html">账户中心<i><img src="/front/mobile/images/rightarrow.png"></i></a>
    </li> -->
    <li>
        <a href="/coupon/list.html">优惠券<i><img src="/front/mobile/images/rightarrow.png" alt=""></i></a>
    </li>
    <li>
        <a href="/servicedPerson/personList.html">需服务人员管理<i><img src="/front/mobile/images/rightarrow.png" alt=""></i></a>
    </li>
    <c:if test="${not empty sessionCustomer.vipId}">
	<li>
        <a href="/customerFamily/customerFamilyList.html">家庭成员管理<i><img src="/front/mobile/images/rightarrow.png" alt=""></i></a>
    </li>
    </c:if>
    <li>
        <a href="/front/mobile/faq.jsp">常见问题<i><img src="/front/mobile/images/rightarrow.png" alt=""></i></a>
    </li>
    <li>
        <a href="/front/mobile/about.jsp">关于我们<i><img src="/front/mobile/images/rightarrow.png" alt=""></i></a>
    </li>
    <li>
    	<a href="/logout.html">退出登录<i><img src="/front/mobile/images/rightarrow.png" alt=""></i></a>
    </li>
</ul>

<jsp:include page="mobile_footer.jsp"/>

</body>
</html>
