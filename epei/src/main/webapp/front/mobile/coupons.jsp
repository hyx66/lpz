<%@ include file="/manage/common/taglibs.jsp"%>
<!doctype html>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<jsp:include page="meta.jsp"></jsp:include>
<title>优惠券－医护陪诊第一平台</title>
<!-- <link rel="icon" href="images/favicon.ico" /> -->
<link rel="stylesheet" href="/front/mobile/css/normalize-min.css" />
<link rel="stylesheet" href="/front/mobile/css/style.css" />
</head>

<body>
<div class="breadcrumb">
    <div class="path" onclick="window.location='/profile/mycenter.html'"><i><img src="/front/mobile/images/leftarrow.png" alt=""></i>优惠券</div>
</div>

<div class="tickets">
    <ul class="list">
        <c:forEach var="coupon" items="${coupons}">
            <li>
                <a href="#" class="cf">
                    <div class="l">
                        <div class="n">抵用券</div>
                        <div class="c">Voucher</div>
                    </div>
                    <div class="r">
                        <div class="n">${coupon.couponType}</div>
                        <div class="d">
                            使用期限
                            <span>
                                <c:choose>
                                    <c:when test="${empty coupon.expiryDate}">
                                        永久
                                    </c:when>
                                    <c:otherwise>
                                        ${coupon.expiryDate}
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </div>
                    </div>
                </a>
            </li>
        </c:forEach>
    </ul>
</div>

<jsp:include page="mobile_footer.jsp"></jsp:include>

</body>
</html>
