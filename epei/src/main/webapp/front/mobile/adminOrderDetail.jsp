<!doctype html>
<%@ include file="/manage/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<jsp:include page="meta.jsp"></jsp:include>
<title>订单详情－医护陪诊第一平台</title>
<link rel="stylesheet" href="/front/mobile/css/normalize-min.css"/>
<link rel="stylesheet" href="/front/mobile/css/style.css" />
<link rel="stylesheet" href="/front/mobile/css/mui.css">
<link rel="stylesheet" href="/front/mobile/js/jqtransformplugin/jqtransform.css"/>
<link rel="stylesheet" href="/front/mobile/css/mui.picker.min.css"/>
<style>
    .form_btn .btn{
        width: 44.4%;
    }
</style>
</head>

<body>
<div id="booking_form" class="booking_form">
	<div class="breadcrumb">
	    <div class="path" onclick="window.location='/front/mobile/adminIndex.jsp'">
		<i><img src="/front/mobile/images/leftarrow.png" alt=""></i>分配任务</div>
	</div>

	<form id="saveServicePersonNameForm" action="/servicedPerson/assignTask.html" class="assignTask_form">
		<input type="hidden" name="obId" value="${order.id}"/>
		<input type="hidden" name="orderNo" value="${order.orderNo}"/>
		<div class="wrap">
		    <div class="booking_detail">
		        <div class="formitem" id="${order.state=='ORDERED'?'servicePersonName':''}">
		        	<span class="l">服务人员：</span>
		            <span class="servicePersonName">
		                <c:choose>
		                    <c:when test="${not empty person.name}">患者指定-${person.name}</c:when>
		                    <c:otherwise>请选择服务人员</c:otherwise>
		                </c:choose>
		            </span>
					 <input name="id" type="hidden" datatype="*" value="${person.id}" nullmsg="请选择服务人员" />
		            <a class="arrow"><img src="/front/mobile/images/rightarrow.png"></a>
		            <div class="Validform_checktip"></div>
		        </div>
		        <div class="service_info">
		            <div class="c1 cf">
		                <div class="item">
		                    <span class="l">服务项目：</span>
		                    <span class="d">${orderType=='PZ'?'陪诊':'陪护'}</span>
		                </div>
		                <div class="item r">
		                    <span class="l">订单状态：</span>
		                    <span class="d">${order.state=='ORDERED'?'预约':(order.state=='CANCELED'?'取消':(order.state=='CONFIRMED'?'确认':'完成'))}</span>
		                </div>
		            </div>
		            <div class="c1 cf">
		                <div class="item">
		                    <span class="l">应付金额：</span>
		                    <span class="d">${not empty order.amount?order.amount:'——'}</span>
		                </div>
		                <div class="item r">
		                    <span class="l">支付状态：</span>
		                    <span>${order.payStatus==0?'待付':(order.payStatus==1?'已付':'不详')}</span>
		                </div>
            		</div>
		        </div>
		
				<c:if test="${not empty order.freeItem && order.freeItem==1}">
			        <div style="text-align:center;margin-top:12px">
			        	<span style="color:red;font-size:12px">注：费用包含VIP免费服务1次,已从应付金额中扣除</span>
			        </div>
				</c:if>
		
			    <div class="main_info">
			        <div class="item"><span class="l">患者姓名：</span><span class="d">${order.patientName}</span></div>
			        <div class="item"><span class="l">联系电话：</span><span class="d">${order.patientMobile}</span></div>
			        <div class="item"><span class="l">患者性别：</span><span class="d">${order.patient.gender==0?'女':'男'}</span></div>
			        <div class="item"><span class="l">患者生日：</span><span class="d">${order.patient.birthday}</span></div>
			        <div class="item"><span class="l">科室：</span><span class="d">${order.departmentName}</span></div>
			        <div class="item"><span class="l">预约时间：</span><span class="d"><fmt:formatDate value="${order.orderTime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></span></div>
			        <c:if test="${orderType=='PZ'}">
		            <div class="item"><span class="l">医保卡号：</span><span class="d">${order.patient.medicareCard}</span></div>
		            </c:if>
		            <c:if test="${orderType=='PH'}">
		            <div class="item"><span class="l">住院床号：</span><span class="d">${order.bed}</span></div>
		            <div class="item"><span class="l">开始日期：</span><span class="d"><fmt:formatDate value="${order.startTime}" pattern="yyyy-MM-dd"></fmt:formatDate></span></div>
		            <div class="item"><span class="l">结束日期：</span><span class="d"><fmt:formatDate value="${order.endTime}" pattern="yyyy-MM-dd"></fmt:formatDate></span></div>
		            <div class="item"><span class="l">预约天数：</span><span class="d">${order.duration==0?'':order.duration}</span></div>
		        	</c:if>
			    </div>
		   </div>
		</div>
		<c:if test="${order.state =='ORDERED'}">	
		<div class="form_btn"><input type="submit" value="确认" class="btn"></div>
		</c:if>
		<c:if test="${order.state =='CANCELED'}">
			<div class="form_btn">订单已取消……</div>
		</c:if>
		<c:if test="${order.state =='CONFIRMED'}">
			<div class="form_btn">服务正在进行……</div>
		</c:if>
		<c:if test="${order.state =='FINISHED'}">
			<div class="form_btn">服务已经结束……</div>
		</c:if>
	</form>
</div>

<jsp:include page="adminMobile_footer.jsp"></jsp:include>
<jsp:include page="popup.jsp"></jsp:include>

<%--服务人员选择插件--%>
<div style="display: none" id="servicePerson_selector">
    <div class="breadcrumb">
        <div class="path back_form"><i><img src="/front/mobile/images/leftarrow.png" alt=""></i>选择服务人员</div>
    </div>
    <div class="sch_result">
        <h2>选择服务人员</h2>
        <ul class="list">
            <c:forEach var="servicePerson" items="${servicePerson}">
                <li><a href="javascript:void(0);" value="${servicePerson.id}"
                       onclick="selectorCallBack('servicePerson',this)">${servicePerson.name}</a></li>
            </c:forEach>
        </ul>
    </div>
</div>
<script type="text/javascript" src="/front/mobile/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/front/mobile/js/jqtransformplugin/jquery.jqtransform-min.js"></script>
<script type="text/javascript" src="/front/mobile/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/front/mobile/js/My97DatePicker/WdatePicker.js"></script>
<script src="/front/mobile/js/mui.min.js"></script>
<script src="/front/mobile/js/mui.picker.min.js"></script>
<script type="text/javascript">
function selectorCallBack(target,original){
    if("servicePerson" == target){
        $("#saveServicePersonNameForm input[name='id']").val($(original).attr("value"));
        $(".servicePersonName").html($(original).html());
    }
    colseSelector();
}

function colseSelector(){
   if($("#servicePerson_selector").css("display") == "block"){
        $("#servicePerson_selector").hide();
    }
   $("#booking_form").show();
}

$(function () {
    //显示服务人员选择页面
    $("#servicePersonName").click(function(){
        $("#servicePerson_selector").show();
        $("#booking_form").hide();
    });
    
  	//回退到表单填写页
    $(".back_form").click(function(){
        colseSelector();
    });
  	
    $("#saveServicePersonNameForm").Validform({
        tiptype: function (msg, o, cssctl) {
            if (!o.obj.is("form")) {
                var objtip = o.obj.parent().find(".Validform_checktip");
                cssctl(objtip, o.type);
                objtip.text(msg);
            }
        },
        showAllError: true,
        usePlugin: {
            jqtransform: {
                //会在当前表单下查找这些元素;
                selector: "select,:checkbox,:radio"
            }
        }
    });
})

var message = "${message}";
if(message && message !=""){
    popup(message);
}
</script>
</body>
</html>