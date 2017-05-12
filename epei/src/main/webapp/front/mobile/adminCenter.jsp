<!doctype html>
<%@ page contentType="text/html;charset=UTF-8"%>
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
        <a href="/profile/admininfo.html">个人信息<i><img src="/front/mobile/images/rightarrow.png" alt=""></i></a>
    </li>
    <li>
        <a href="/profile/adminChangePassword.html">修改密码<i><img src="/front/mobile/images/rightarrow.png" alt=""></i></a>
    </li>
    <li>
        <a href="/wxzf/acctCenter.html">账户中心<i><img src="/front/mobile/images/rightarrow.png"></i></a>
    </li>
    <li>
    	<a href="/logout.html">退出登录<i><img src="/front/mobile/images/rightarrow.png" alt=""></i></a>
    </li>
</ul>

<jsp:include page="adminMobile_footer.jsp"/>

</body>
</html>
