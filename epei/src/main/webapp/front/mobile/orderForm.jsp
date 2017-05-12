<!DOCTYPE html>
<%@ include file="/manage/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <jsp:include page="meta.jsp"/>
    <title>陪诊订单－医护陪诊第一平台</title>
    <link rel="stylesheet" href="/front/mobile/css/normalize-min.css"/>
    <link rel="stylesheet" href="/front/mobile/css/mui.css">
    <link rel="stylesheet" href="/front/mobile/js/jqtransformplugin/jqtransform.css"/>
    <link rel="stylesheet" href="/front/mobile/css/style.css"/>
    <link rel="stylesheet" href="/front/mobile/css/mui.picker.min.css"/>
</head>

<body>
<form action="/order/${type}/bookingSubmit.html" method="post" id="booking_form" class="booking_form">
    <input type="hidden" name="_csrf" value="${requestScope["org.springframework.security.web.csrf.CsrfToken"].token}"/>
    <input type="hidden" name="id" value="${entity.id}"/>
    <input type="hidden" name="orderNo" value="${entity.orderNo}"/>
    <input type="hidden" name="orderType" value="${type}"/>
    <input type="hidden" name="patient.id" value="${entity.patient.id}"/>
    <input type="hidden" name="hospitalName" value="${entity.hospitalName}"/>
    <div class="breadcrumb">
        <div class="path" onclick="window.history.go(-1)"><i><img src="/front/mobile/images/leftarrow.png"></i>
            <c:choose>
                <c:when test="${type == 'ph'}">
                    专业陪护
                </c:when>
                <c:otherwise>
                    专业陪诊
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="booking_box">
       	<div class="formitem">
        <label>患者姓名：</label>
            <input style="border:0px" type="text" name="patient.name" class="input_text" datatype="*" nullmsg="请填写患者姓名" value="${entity.patient.name}" validType="byteLength[1,16]"/>
            <div class="Validform_checktip"></div>
            <a class="arrow" id="need_service_person"><img src="/front/mobile/images/rightarrow.png"></a>
        </div>
        <div class="formitem">
        <label>联系电话：</label>
            <input style="border:0px;width:56%" type="text" value="${entity.patient.mobile}" name="patient.mobile" class="input_text" nullmsg="请填写联系电话" datatype="m" errorMsg="请填写正确的联系电话"/>
            <div class="Validform_checktip"></div>
        </div>   

        <div class="formitem" id="hospitalname">
            <label>选择医院：</label>
            <span class="hospitalname">
                <c:choose>
                    <c:when test="${not empty entity.hospitalName}">
                        ${entity.hospitalName}
                    </c:when>
                    <c:otherwise>
                        <span style="color:#999">请选择医院</span>
                    </c:otherwise>
                </c:choose>
            </span>
            <a class="arrow">
                <img src="/front/mobile/images/rightarrow.png">
            </a>
            <input type="hidden" name="hospital.id" value="${entity.hospital.id}" datatype="*" nullmsg="请选择预约医院"/>
            <div class="Validform_checktip"></div>
        </div>
        
        <div class="formitem">
         <label>预约时间：</label>
            <input id="orderTime" readonly="readonly" style="border:0px;width:56%" type="text" name="orderTime" class="input_text" value="<fmt:formatDate value="${entity.orderTime}"  pattern="yyyy-MM-dd HH:mm"/>" datatype="*" nullmsg="请填写预约日期" placeholder="请提前一天预约"/>
            <div class="Validform_checktip"></div>
        </div>
    </div>
    
    <div class="form_btn"><input type="submit" value="发布" class="btn"></div>
</form>

<%--联系人选择插件--%>
<div style="display: none" id="need_service_person_selector">
    <div class="breadcrumb">
        <div class="path back_form"><i><img src="/front/mobile/images/leftarrow.png"></i>选择联系人</div>
        <a href="#" class="r">完成</a>
    </div>
    <div class="sch_result">
        <h2>选择联系人</h2>
        <ul class="list">
            <c:forEach var="patient" items="${patients}">
                <li><a href="javascript:void(0);" mobile="${patient.mobile}" onclick="selectorCallBack('patient',this)">${patient.name}</a></li>
            </c:forEach>
        </ul>
    </div>
</div>

<%--医院选择插件--%>
<div style="display: none" id="hospital_selector">
    <div class="breadcrumb">
        <div class="path back_form"><i><img src="/front/mobile/images/leftarrow.png"></i>选择医院</div>
        <a href="#" class="r">完成</a>
    </div>
    <div class="sch_result">
        <h2>选择医院</h2>
        <ul class="list">
            <c:forEach var="hospital" items="${hospitals}">
                <li><a href="javascript:void(0);" value="${hospital.id}" phPricesJson='${hospital.phPricesJson}'
                       pzPricesJson='${hospital.pzPricesJson}'
                       onclick="selectorCallBack('hospital',this)">${hospital.name}</a></li>
            </c:forEach>
        </ul>
    </div>
</div>

<c:choose>
	<c:when test="${sessionCustomer.customerType==1}">
		<jsp:include page="adminMobile_footer.jsp"/>
	</c:when>
	<c:otherwise>
		<jsp:include page="mobile_footer.jsp"/>
	</c:otherwise>
</c:choose>
<jsp:include page="popup.jsp"></jsp:include>

<script type="text/javascript" src="/front/mobile/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/front/mobile/js/jqtransformplugin/jquery.jqtransform-min.js"></script>
<script type="text/javascript" src="/front/mobile/js/Validform_v5.3.2_min.js"></script>
<script src="/front/mobile/js/mui.min.js"></script>
<script src="/front/mobile/js/mui.picker.min.js"></script>
<script type="text/javascript" src="/front/mobile/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">           
function selectorCallBack(target,original){
	if("hospital" == target){
	    $("input[name='hospital.id']").val($(original).attr("value"));
	    $("input[name='hospitalName']").val($(original).html());
	    $(".hospitalname").html($(original).html());
	}
	if("patient" == target){
	    $("input[name='patient.mobile']").val($(original).attr("mobile"));
	    $("input[name='patient.name']").val($(original).html());
	}
	colseSelector();
}

function colseSelector(){
	if($("#need_service_person_selector").css("display") == "block"){
        $("#need_service_person_selector").hide();
    }else if($("#hospital_selector").css("display") == "block"){
        $("#hospital_selector").hide();
    }
    $("#booking_form").show();
}

$(function () {
    var errorMsg = "${errorMsg}";
    if(errorMsg && errorMsg !=""){
        popup(errorMsg);
    }

    //显示医院选择页面
    $("#hospitalname").click(function(){
        $("#hospital_selector").show();
        $("#booking_form").hide();
    });
    
  	//显示联系人选择页面
    $("#need_service_person").click(function(){
        $("#need_service_person_selector").show();
        $("#booking_form").hide();
    });
  
    //回退到表单填写页
    $(".back_form").click(function(){
        colseSelector();
    });

    $("#booking_form").Validform({
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
        },

        datatype:{//传入自定义datatype类型;
        	"mytype":function(gets,obj,curform,datatype){
        		var mydate = /^[12]\d{3}(0\d|1[0-2])([0-2]\d|3[01])$/;   
        	    var check = gets.match(mydate);
    			if (check==null) {
    				return false;
    	            }
    		}
        },
        
        callback : function(data){
        	var orderType = "${type}";
        	if(orderType="pz"){
        		var orderTime = $('#orderTime').val()+":00";
        		$("#orderTime").val(orderTime);
        	}
        }
        
    });
})
</script>

<script>
(function ($) {
    $.init();
    var result = $('#orderTime')[0];
    var btns = $('#orderTime');
    var orderType = "${type}";
    btns.each(function (i, btn) {
        btn.addEventListener('tap', function () {
            var options = {};
			if(orderType=='ph'){
				options = {type : 'date'};
        	}
            var picker = new $.DtPicker(options);
            picker.show(function (rs) {
                result.value = rs.text;
                picker.dispose();
            });
        }, false);
    });
})(mui);
</script>

<!-- 如果是新创建订单，则表单填写的信息默认为会员的基本信息 -->
<c:if test="${empty entity.id}">
<script>
$("input[name='patient.name']").val("${sessionCustomer.name}");
$("input[name='patient.mobile']").val("${sessionCustomer.mobile}");
</script>
</c:if>

</body>
</html>