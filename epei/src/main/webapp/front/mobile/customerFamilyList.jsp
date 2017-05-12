<!doctype html>
<%@ include file="/manage/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <jsp:include page="meta.jsp"></jsp:include>
<title>需服务人员管理－医护陪诊第一平台</title>
<!-- <link rel="icon" href="images/favicon.ico" /> -->
<link rel="stylesheet" href="/front/mobile/css/normalize-min.css" />
<link rel="stylesheet" href="/front/mobile/css/style.css" />
</head>

<body>
<div class="breadcrumb">
    <div class="path"
         onclick="window.location='/profile/mycenter.html'"><i><img src="/front/mobile/images/leftarrow.png" alt=""></i>会员家庭成员列表</div>
    <a href="/customerFamily/customerFamilyEdit.html" class="r"><img src="/front/mobile/images/icon_plus.png" alt=""></a>
</div>

<div class="person_manage">
    <ul class="list">
        <c:forEach var="customerFamily" items="${customerFamilyList}">
            <li>
                <div class="topline"></div>
                <div class="item cf">
                    <div class="p50">
                        <span class="icon"><img src="/front/mobile/images/card_icon1.png" alt=""></span>${customerFamily.name}
                    </div>
                    <div class="p47">
                        <span class="icon"><img src="/front/mobile/images/card_icon2.png" alt=""></span>${customerFamily.phone}
                    </div>
                </div>
                <div
                        class="item"><span class="icon"><img src="/front/mobile/images/card_icon3.png"
                                                             alt=""></span>${customerFamily.idCard}</div>
                <div class="item nobd"><span class="icon"><img src="/front/mobile/images/card_icon4.png"
                                                               alt=""></span>${customerFamily.relationship}</div>
               <%--  <div class="btn ac cf">
                        <a href="/customerFamily/customerFamilyEdit.html?id=${customerFamily.id}" class="edit">编辑</a>
                        <a href="javascript:void(0);" class="del" onclick="delcustomerFamily(${customerFamily.id})">删除</a>
                </div> --%>
            </li>
        </c:forEach>
    </ul>
</div>
<jsp:include page="mobile_footer.jsp"></jsp:include>
</body>
<script type="text/javascript" src="/front/mobile/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
    function delcustomerFamily(id){
        if(confirm("确定删除该信息?")){
            $.post("/servicedcustomerFamily/customerFamilyDel.html",{"id":id,"_csrf":"${requestScope["org.springframework.security.web.csrf.CsrfToken"].token}"},function(result){
                if(result.success){
                    window.location.reload(true);
                }
                alert(result.message);
            });
        }
    }
</script>
</html>