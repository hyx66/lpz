<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta charset="utf-8" />
<title>医护陪诊第一平台</title>
<link rel="stylesheet" href="css/normalize-min.css" />
<link rel="stylesheet" href="css/style.css" />
<!--[if IE 6]>
	<script src="js/belatedPNG.js"></script>
	<script>
	  DD_belatedPNG.fix('*');
	</script>
<![endif]--><!-- support png for ie6 -->
</head>

<body>
<%@ include file="/front/pc/pc_header.jsp"%>
<!-- header end -->

<div class="banner_about">
    <div class="wrap">
        <div class="words"><img src="images/about_bannerwords.png" alt="安心一点 舒适一些"></div>
    </div>
</div>
<!-- banner end -->

<div class="wrap">
    <div class="content_box cf">

        <div class="sidenav">
            <ul class="subnav">
                <li class="cur"><a href="#aboutus">关于我们</a></li>
                <li><a href="#service_concept">服务理念</a></li>
                <li><a href="#contact_us">联系我们</a></li>
            </ul>
        </div>
        <!-- sidenav end -->

        <div class="content">
            <div class="box" id="aboutus">
                <h2 class="t"><span>关于我们</span></h2>
                <div class="img cf">
                    <img src="images/about_img01.jpg" alt="">
                    <img src="http://dummyimage.com/226x136/38c1c3/fff" alt="">
                    <img src="images/about_img02.jpg" alt="">
                </div>
                <p>重庆溯晖商务信息咨询有限公司，公司主要从事医院陪诊服务，为广大患者提供医院门诊陪诊服务：提前预约挂号，陪同看病就医、检查化验、排队取药、代取报告、入院等服务我们以“诚信服务，满意放心”为服务宗旨，“一切以患者需求为中心”为服务理念，为客户提供方便、快捷、温馨的陪诊服务。</p>
            </div>
            <div class="box" id="service_concept">
                <h2 class="t"><span>服务理念</span></h2>
                <p>服务理念：对客户“一切以患者需求为中心”。</p>
            </div>
            <div class="box" id="contact_us">
                <h2 class="t"><span>联系我们</span></h2>
                <p>
                    电话:400 883 7622</br>
                    邮箱:cqsuhui@sina.com</br>
                    <%--地址:</br>--%>
                </p>
            </div>
        </div>
        <!-- content end -->

    </div>
</div>
<!-- main content end -->

<%@ include file="/front/pc/pc_footer.jsp"%>

</body>
</html>
