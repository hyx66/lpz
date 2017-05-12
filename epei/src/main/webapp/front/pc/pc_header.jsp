<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015-11-04
  Time: 18:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="header cf">
    <div class="wrap">
        <div class="logo"><img src="/front/pc/images/logo.png" alt=""><span>医护陪诊第一平台</span></div>

        <ul class="main_nav cf">
            <li class="cur"><a href="/">首页</a></li>
            <li><a href="/getAdvertisementLxx.html">在线下单</a></li>
            <li><a href="/front/pc/about.jsp">关于</a></li>
        </ul>

        <div class="user_plugin">
            <c:choose>
                <c:when test="${not empty sessionScope['sessionCustomer']}">
                    <div class="login">
                        <a href="/profile/myinfo.html" class="username">${sessionScope['sessionCustomer'].userName}</a>
                        |<a href="/logout.html">退出</a></div>
                </c:when>
                <c:otherwise>
                    <div class="login">
                        <a href="/front/mobile/login.jsp">登录</a>|<a href="/front/mobile/reg.jsp">注册</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<!-- header end -->