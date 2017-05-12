<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/manage/common/taglibs.jsp"%>
<html>
<head>
<jsp:include page="meta.jsp"></jsp:include>
<title>FAQ－医护陪诊第一平台</title>
<link rel="stylesheet" href="css/normalize-min.css" />
<link rel="stylesheet" href="css/style.css" />
</head>

<body>
<div class="breadcrumb">
    <div class="path" onclick="window.location='/profile/mycenter.html'"><i><img src="/front/mobile/images/leftarrow.png" alt=""></i
            >FAQ</div>
</div>

<ul class="faq_list">
    <li>
        <h2><i><img src="/front/mobile/images/icon_qa.png" alt=""></i>护理员服务内容有哪些？</h2>
        <p>
            1、协助整理病人的床单及物品，整理床头；</br>
            2、通知饮食，为患者端送（喂）饭菜、递送（喂）药、递送（喂）水、洗净碗筷等；</br>
            3、帮助患者洗脸、漱口、梳头、擦澡、更换衣裤；</br>
            4、帮助患者购买日用品，协助办理住院期间的各种手续；</br>
            5、协助了解患者输液情况，并及时向护士汇报；</br>
            6、协助患者定期修剪指甲，协助患者个人卫生清洁；</br>
            7、扶患者上厕所、大小便或放尿、倒尿、洗澡等；</br>
            8、帮助患者处置大小便、随脏，随洗，随换；</br>
            9、必要时在护士指导下给患者做鼻饲饮食、放置冰袋；</br>
            10、在护士指导下帮助患者翻身、拍背及更换体位。</br>
            11、在医护人员允许的情况下，可协助患者出病室活动，呼吸新鲜空气。</br>
            12、及时解决患者各种需求，如有必要，及时向医护人员反映。</br>
            13、负责外送化验标本、通知单及接送病人做各种检查治疗。</br>
            14、遵守护工人员的职业守则、礼仪要求、员工守则，服从管理。完成上级交办的其他工作。</br>
        </p>
    </li>
    <li>
        <h2><i><img src="/front/mobile/images/icon_qa.png" alt=""></i>什么叫陪诊服务？</h2>
        <p>
            陪诊是我们为市民提供的个性化增值服务，是由乐陪诊提供医院门诊全程的陪诊服务。
        </p>
    </li>
    <li>
        <h2><i><img src="/front/mobile/images/icon_qa.png" alt=""></i>陪诊的服务内容？</h2>
        <p>
            陪诊的服务内容是在门诊部为客户排队挂号、取药、陪同就诊、代取报告等服务。
        </p>
    </li>
    <li>
        <h2><i><img src="/front/mobile/images/icon_qa.png" alt=""></i>陪诊的服务时间？</h2>
        <p>
            门诊服务时间为：8:00——17:30   客服中心：全天24小时服务。
        </p>
    </li>
    <li>
        <h2><i><img src="/front/mobile/images/icon_qa.png" alt=""></i>陪诊服务所针对的人群？</h2>
        <p>
            所有有需要的市民。
        </p>
    </li>
    <li>
        <h2><i><img src="/front/mobile/images/icon_qa.png" alt=""></i>什么时候拨打预约电话可以享受陪诊服务？</h2>
        <p>
            就诊前一天拨打预约电话400—8837—622就可以拥有私人定制的陪诊服务。
        </p>
    </li>
    <li>
        <h2><i><img src="/front/mobile/images/icon_qa.png" alt=""></i>目前有那几家医院开展陪诊服务？</h2>
        <p>
            重庆市第三人民医院</br>
            重庆长城医院
        </p>
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
