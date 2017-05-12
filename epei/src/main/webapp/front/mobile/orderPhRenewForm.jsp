<!DOCTYPE html>
<%@ include file="/manage/common/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <jsp:include page="meta.jsp"/>
    <title>申请陪护－医护陪诊第一平台</title>
    <!-- <link rel="icon" href="images/favicon.ico" /> -->
    <link rel="stylesheet" href="/front/mobile/css/normalize-min.css"/>
    <link rel="stylesheet" href="/front/mobile/css/mui.css">
    <link rel="stylesheet" href="/front/mobile/js/jqtransformplugin/jqtransform.css"/>
    <link rel="stylesheet" href="/front/mobile/css/style.css"/>
    <link rel="stylesheet" href="/front/mobile/css/mui.picker.min.css"/>
</head>

<body>
<form action="/order/ph/reBookingSubmit.html" method="post" id="booking_form" class="booking_form">
    <input type="hidden" name="_csrf" value="${requestScope["org.springframework.security.web.csrf.CsrfToken"].token}"/>
    <input type="hidden" name="originalOrderId" value="${originalOrderId}"/>
    <input type="hidden" name="hospitalName" value="${entity.hospitalName}"/>
    <input type="hidden" name="id" value="${entity.id}"/>
    <input type="hidden" name="orderNo" value="${entity.orderNo}"/>
    <input type="hidden" name="phServicePrice" value="${entity.phServicePrice}"/>
    <div class="breadcrumb">
        <div class="path" onclick="window.history.go(-1)"><i><img src="/front/mobile/images/leftarrow.png" alt=""></i>
        	专业陪护
        </div>
    </div>

    <div class="booking_box">
        <div class="formitem page1">
        <label>患者姓名：</label>
            <input type="text" name="patient.name" class="input_text" datatype="*" nullmsg="请填写患者姓名" value="${entity.patient.name}" validType="byteLength[1,16]"/>
            <div class="Validform_checktip"></div>
            <a class="arrow" id="need_service_person"><img src="/front/mobile/images/rightarrow.png" alt=""></a>
        </div>
        <div class="formitem page1">
        <label>联系电话：</label>
            <input type="text" value="${entity.patient.mobile}" name="patient.mobile" class="input_text" style="width:56%" nullmsg="请填写联系电话" datatype="m" errorMsg="请填写正确的联系电话"/>
            <div class="Validform_checktip"></div>
        </div>      
        
        <div class="formitem page1">
        <label>患者生日：</label>
            <input type="text" value="${entity.patient.birthday}" name="patient.birthday" datatype="mytype" errorMsg="请规范日期输入" placeholder="格式：19000101"
                   class="input_text" nullmsg="请填写患者生日"/>
            <div class="Validform_checktip"></div>
        </div>
        <div class="formitem page1">
        <label>患者性别：</label>
        	<input type="radio" name="customerSex" value="1" checked> 男 &nbsp;&nbsp;&nbsp;&nbsp;
    		<input type="radio" name="customerSex" value="0"> 女
		</div>

        <div class="formitem page2" id="hospitalname">
            <label for="">选择医院：</label>
            <span class="hospitalname">
                <c:choose>
                    <c:when test="${not empty entity.hospitalName}">
                        ${entity.hospitalName}
                    </c:when>
                    <c:otherwise>
                        请选择医院
                    </c:otherwise>
                </c:choose>
            </span>
            <a class="arrow">
                <img src="/front/mobile/images/rightarrow.png" alt="">
            </a>
            <input type="hidden" name="hospital.id" value="${entity.hospital.id}" datatype="*" nullmsg="请选择预约医院"/>
            <div class="Validform_checktip"></div>
        </div>

		<div class="formitem page2" id="department">
			<label>选择科室:</label>
			<span class="department">
				<c:choose>
					<c:when test="${not empty entity.departmentId}">
						${entity.departmentName}
					</c:when>
					<c:otherwise>请选择科室</c:otherwise>
				</c:choose>
			</span>
			<a class="arrow"><img alt="" src="/front/mobile/images/rightarrow.png"></a>
			<input type="hidden" name="departmentId" value="${entity.departmentId}" datatype="*" nullmsg="请选择科室"/>
			<div class="Validform_checktip"></div>
		</div>
		
		<div class="formitem page2" id="serviceperson">
			<label>服务人员:</label>
			<span class="serviceperson">
				<c:choose>
					<c:when test="${not empty servicePersonName}">
						${servicePersonName}
					</c:when>
					<c:otherwise>由医院主管安排</c:otherwise>
				</c:choose>
			</span>
			<a class="arrow"><img alt="" src="/front/mobile/images/rightarrow.png"></a>
			<input type="hidden" name="servicePersonId" value="${entity.servicePersonId}"/>
			<div class="Validform_checktip"></div>
		</div>


		<div class="formitem page2">
			<label>床位：</label>
			<input type="text" value="${entity.bed}" name="bed"
				class="input_text" nullmsg="请填写床位信息" datatype="*"/>
			<div class="Validform_checktip"></div>
		</div>
  
        <c:if test="${sessionCustomer.customerType==1}">
			<div class="formitem page3">
				<label>下单方式：</label>
				<input type="radio" name="appointmentType" value="0" checked>电话预约 
				<input type="radio" name="appointmentType" value="1">主动预约
			</div>
		</c:if>
		<c:if test="${sessionCustomer.customerType==0}">
			<input type="hidden" name="appointmentType" value="0"/>
		</c:if>
        <div class="formitem picktime page3">
            <button id='demo1' data-options='{}' class="btn mui-btn mui-btn-block" style="color:#A9A9A9;padding-left:0px">
                <c:choose>
                    <c:when test="${not empty entity.orderTime}">
                        <fmt:formatDate value="${entity.orderTime}" pattern="yyyy-MM-dd"></fmt:formatDate>
                    </c:when>
                    <c:otherwise>预约日期： (请提前一天预约)</c:otherwise>
                </c:choose>
            </button>
            <input name="orderTime" value="<fmt:formatDate value="${entity.orderTime}"  pattern="yyyy-MM-dd"/>" type="text"
             onClick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
                   class="input_txt" id="pcdate" placeholder="预约日期： (请提前一天预约)" nullmsg="请填写预约日期"/>
            <div class="Validform_checktip"></div>
        </div>
        <div class="formitem page3">
			<label>预约时间：</label>
			<input type="radio" name="timeInOneDay" value="AM" checked>上午&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="radio" name="timeInOneDay" value="PM">下午
		</div>
		<div class="formitem page3">
			<label>预约天数：</label>
            <input type="text" value="${entity.duration}" name="duration" datatype="posint" errorMsg="请输入大于0的整数" oninput="daysChange(this)" 
                   class="input_text" nullmsg="请填写预约天数"/>
            <div class="Validform_checktip"></div>
        </div>
        <div class="formitem nobd page3">
        	<label>总费用：</label>
            <input class="input_text" name="showAmount" type="text" disabled="true" value="${entity.amount}"/>
        </div>
    </div>
    <div class="form_btn">
     	<input type="button" value="上一页" class="btn button" style="width:44%" id="previous">
     	<input type="button" value="下一页" class="btn button" style="width:44%" id="next">
     	<input type="submit" value="确认" class="btn button" style="width:44%" id="submit">
    </div>
</form>

<c:choose>
	<c:when test="${sessionCustomer.customerType==1}">
		<jsp:include page="adminMobile_footer.jsp"/>
	</c:when>
	<c:otherwise>
		<jsp:include page="mobile_footer.jsp"/>
	</c:otherwise>
</c:choose>
<%--联系人选择插件--%>
<div style="display: none" id="need_service_person_selector">
    <div class="breadcrumb">
        <div class="path back_form"><i><img src="/front/mobile/images/leftarrow.png" alt=""></i>选择联系人</div>
        <a href="#" class="r">完成</a>
    </div>
	 
    <div class="sch_result">
        <h2>选择联系人</h2>
        <ul class="list">
            <c:forEach var="patient" items="${patients}">
                <li><a href="javascript:void(0);" mobile="${patient.mobile}" idCard="${patient.idCard}" birthday="${patient.birthday}" onclick="selectorCallBack('patient',this)">${patient.name}</a></li>
            </c:forEach>
        </ul>
    </div>
</div>

<%--医院选择插件--%>
<div style="display: none" id="hospital_selector">
    <div class="breadcrumb">
        <div class="path back_form"><i><img src="/front/mobile/images/leftarrow.png" alt=""></i>选择医院</div>
        <a href="#" class="r">完成</a>
    </div>
    <div class="sch_result">
        <h2>选择医院</h2>
        <ul class="list">
            <c:forEach var="hospital" items="${hospitals}">
                <li><a href="javascript:void(0);" isCooperate="${hospital.isCooperate}" phServicePrice="${hospital.phServicePrice}"  value="${hospital.id}" onclick="selectorCallBack('hospital',this)">${hospital.name}</a></li>
            </c:forEach>
        </ul>
    </div>
</div>

<%--选择科室插件--%>
<div style="display: none" id="department_selector">
	<div class="breadcrumb">
		<div class="path back_form"><i><img alt="" src="/front/mobile/images/leftarrow.png"></i>选择科室</div>
		<a href="#" class="r">完成</a>
	</div>
	<div class="sch_result">
		<h2>科室</h2>
		<ul class="list">
			
		</ul>
	</div>
</div>

<%--服务人员选择插件--display:none表示隐藏--%>
<div style="display: none" id="servicePerson_selector">
    <div class="breadcrumb">
        <div class="path back_form"><i><img alt="" src="/front/mobile/images/leftarrow.png"></i>选择服务人员</div>
    </div>
    <div class="sch_result">
        <h2>选择服务人员</h2>
        <ul class="list">
           	 <%--这里列出所选择医院的服务人员--%>
        </ul>
    </div>
</div>

<script type="text/javascript" src="/front/mobile/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/front/mobile/js/jqtransformplugin/jquery.jqtransform-min.js"></script>
<script type="text/javascript" src="/front/mobile/js/Validform_v5.3.2_min.js"></script>
<jsp:include page="popup.jsp"></jsp:include>
<script type="text/javascript">
//翻页用到的JS
var count = 1;
flip(count);
$("#next").click(function(){
	if(count<3)count = count+1;
	flip(count);
});
$("#previous").click(function(){
	if(count>1)count = count-1;
	flip(count);
});
function flip(count){
	$(".button").hide();
	if(count==3){
		$("#previous").show();
		$("#submit").show();
	}else if(count==1){
		$("#previous").css("color","red");
		$("#previous").show();
		$("#next").show();
	}else{
		$("#previous").show();
		$("#next").show();
		$("#next").css("color","white");
		$("#previous").css("color","white");
	}
	var page = ".page"+count;
	$(".formitem").hide();
	$(page).show();
}
$("#submit").click(function(){
	$("#booking_form").submit();
	var can = true;
	var xinxi;
	 $(".Validform_checktip").each(function(){
		 if($(this).text()!='' && $(this).text()!='通过信息验证！'){
			 xinxi = $(this).text();
			 can = false;
		 }
		 });
	 if(!can){
		 alert(xinxi);
	 }
});
</script>
<script type="text/javascript"> 
function showghfs() {
	$("#ghfs").show();
}
function hideghfs() {
	$("#ghfs").hide();
}
function svli(){
	var servicepersons=${servicepersons};
    for(s=0;s<servicepersons.length;s++){
    	if($("input[name='hospital.id']").val()==servicepersons[s].hospitalId){
   			var  svli='<li><a href="javascript:void(0);" value="'+servicepersons[s].id+'" onclick="selectorCallBack(\'servicePerson\',this)">'+servicepersons[s].name+'</a></li>'
    		$("#servicePerson_selector .list").append(svli);    
    	}
    }
}
/*----------------------------------------------------列出符合规则的科室-----------------------------------*/
function myli(){
	var departments=${departments};
    for(j=0;j<departments.length;j++){
   		if($("input[name='hospital.id']").val()==departments[j].hospitalId){
   			var  myli='<li><a href="javascript:void(0);" value="'+departments[j].id+'" onclick="selectorCallBack(\'department\',this)">'+departments[j].name+'</a></li>'
    		$("#department_selector .list").append(myli);
   		}            	
    }
}
/*------------------------------------------------选择触发事件-------------------------------------------*/
function selectorCallBack(target,original){
    if("hospital" == target){
        $("input[name='hospital.id']").val($(original).attr("value"));
        $("input[name='hospitalName']").val($(original).html());
        $(".hospitalname").html($(original).html());

        //医院改变，价格也要跟着改变
        if($(original).attr("isCooperate")=="1"){
        	 $("input[name='phServicePrice']").val($(original).attr("phServicePrice"));
        }else{
        	 $("input[name='phServicePrice']").val("");
        }
        calculat();
        
        //清空已选择的服务方式
        $(".department").html("请选择科室");
        $("input[name='departmentId']").val(null);
        $("#department_selector .list").html("");
        myli();
        $(".serviceperson").html("由医院主管安排");
        $("input[name='servicePersonId']").val(null);
        $("#servicePerson_selector .list").html("");
        svli();
    }

    if("patient" == target){
        $("input[name='patient.mobile']").val($(original).attr("mobile"));
        $("input[name='patient.idCard']").val($(original).attr("idCard"));
        $("input[name='patient.name']").val($(original).html());
        $("input[name='patient.birthday']").val($(original).attr("birthday"));
    }

    if("department" == target){
    	$("input[name='departmentId']").val($(original).attr("value"));
    	$(".department").html($(original).html());
    }
    
    if("servicePerson" == target){
    	$("input[name='servicePersonId']").val($(original).attr("value"));
    	$(".serviceperson").html($(original).html());
    }
    
    colseSelector();
}
/*----------------------------------------计算费用----------------------------------------------------- */
function daysChange(obj){
	obj.value=obj.value.replace(/[^\d]/g,'');
	calculat();
}
function calculat(){
	var univalence = $("input[name='phServicePrice']").val();
	var days = $("input[name='duration']").val();
	if(univalence!="" && days!=""){
		var amount = univalence * days;
		$("input[name='showAmount']").val(amount);
	}else{
		$("input[name='showAmount']").val("费用视患者病情而定");
	}
}
/*-----------------------------------------关闭（隐藏）选择菜单---------------------------------------*/
function colseSelector(){
    if($("#need_service_person_selector").css("display") == "block"){
        $("#need_service_person_selector").hide();
    }else if($("#hospital_selector").css("display") == "block"){
        $("#hospital_selector").hide();
    }else if($("#servicePerson_selector").css("display") == "block"){
        $("#servicePerson_selector").hide();
    }else{
    	$("#department_selector").hide();
    }
    $("#booking_form").show();
}
/*---------------------------------------------------------------------------------------------------------*/
$(function () {
myli();
svli();
    var errorMsg = "${errorMsg}";
    if(errorMsg && errorMsg !=""){
        popup(errorMsg);
    }

    //显示联系人选择页面
    $("#need_service_person").click(function(){
        $("#need_service_person_selector").show();
        $("#booking_form").hide();
    });

    //显示医院选择页面
    $("#hospitalname").click(function(){
        $("#hospital_selector").show();
        $("#booking_form").hide();
    });
    
    //显示服务人员选择页面
    $("#serviceperson").click(function(){
        $("#servicePerson_selector").show();
        $("#booking_form").hide();
    });

//显示科室选择界面
$("#department").click(function(){
$("#department_selector").show();
$("#booking_form").hide();
});

    //回退到表单填写页
    $(".back_form").click(function(){
        colseSelector();
    });

    // $(".registerform").Validform();
    //就这一行代码！;
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

        datatype:{//传入自定义datatype类型【方式二】;
        	"mytype":function(gets,obj,curform,datatype){
        		var mydate = /^[12]\d{3}(0\d|1[0-2])([0-2]\d|3[01])$/;   
        	    var check = gets.match(mydate);
    			if (check==null) {
    				return false;
    	            }
    		},
    		"money":function(gets,obj,curform,datatype){
        		var mydate = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;   
        	    var check = gets.match(mydate);
    			if (check==null) {
    				return false;
    	            }
    		},
    		"posint":function(gets,obj,curform,datatype){
        		var mydate = /^[0-9]*[1-9][0-9]*$/;   
        	    var check = gets.match(mydate);
    			if (check==null) {
    				return false;
    	            }
    		},
            "idcard":function(gets,obj,curform,datatype){
                //该方法由佚名网友提供;
                var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// 加权因子;
                var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];// 身份证验证位值，10代表X;

                if (gets.length == 15) {
                    return isValidityBrithBy15IdCard(gets);
                }else if (gets.length == 18){
                    var a_idCard = gets.split("");// 得到身份证数组
                    if (isValidityBrithBy18IdCard(gets)&&isTrueValidateCodeBy18IdCard(a_idCard)) {
                        return true;
                    }
                    return false;
                }
                return false;

                function isTrueValidateCodeBy18IdCard(a_idCard) {
                    var sum = 0; // 声明加权求和变量
                    if (a_idCard[17].toLowerCase() == 'x') {
                        a_idCard[17] = 10;// 将最后位为x的验证码替换为10方便后续操作
                    }
                    for ( var i = 0; i < 17; i++) {
                        sum += Wi[i] * a_idCard[i];// 加权求和
                    }
                    valCodePosition = sum % 11;// 得到验证码所位置
                    if (a_idCard[17] == ValideCode[valCodePosition]) {
                        return true;
                    }
                    return false;
                }

                function isValidityBrithBy18IdCard(idCard18){
                    var year = idCard18.substring(6,10);
                    var month = idCard18.substring(10,12);
                    var day = idCard18.substring(12,14);
                    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));
                    // 这里用getFullYear()获取年份，避免千年虫问题
                    if(temp_date.getFullYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){
                        return false;
                    }
                    return true;
                }

                function isValidityBrithBy15IdCard(idCard15){
                    var year =  idCard15.substring(6,8);
                    var month = idCard15.substring(8,10);
                    var day = idCard15.substring(10,12);
                    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));
                    // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法
                    if(temp_date.getYear()!=parseFloat(year) || temp_date.getMonth()!=parseFloat(month)-1 || temp_date.getDate()!=parseFloat(day)){
                        return false;
                    }
                    return true;
                }

            }

        },
        callback : function(data){
            //平台、设备和操作系统
            var system = {
                win: false,
                mac: false,
                xll: false,
                ipad: false
            };
            //检测平台
            var p = navigator.platform;
            system.win = p.indexOf("Win") == 0;
            system.mac = p.indexOf("Mac") == 0;
            system.x11 = (p == "X11") || (p.indexOf("Linux") == 0);
            system.ipad = (navigator.userAgent.match(/iPad/i) != null) ? true : false;
            //跳转语句，如果是手机访问自动切换mui日历
            if (system.win || system.mac || system.xll || system.ipad) {
                var orderTime = $("input[name='orderTime']").val();
                if(!orderTime){
                    popup("请选择预约日期");
                    return false;
                }
                
                var birthday = $("input[name='patient.birthday']").val();
                if(!birthday){
                    popup("请选择患者生日");
                    return false;
                }
            } else {
            	var btns = $('#demo1');
                if(btns.html().trim()+"" == "预约日期： (请提前一天预约)"){
                    popup("请选择预约日期");
                    return false;
                }else{
                	$("input[name='orderTime']").val(btns.html().trim()+":00");
                }
                
                
            }
        }
    });
})
/*---------------------------------------------------------------------------------------------------------*/
</script>


<script src="/front/mobile/js/mui.min.js"></script>
<script src="/front/mobile/js/mui.picker.min.js"></script>
<script>
/*---------------------------------------------------------------------------------------------------------*/
    (function ($) {
        $.init();
        var result = $('#demo1')[0];
        var btns = $('#demo1');
        
        btns.each(function (i, btn) {
            btn.addEventListener('tap', function () {
                var optionsJson = this.getAttribute('data-options') || '{}';
                /*
                 * var options = JSON.parse(optionsJson);
                 */
                var options = {type : 'date'};
                var id = this.getAttribute('id');
                /*
                 * 首次显示时实例化组件
                 * 示例为了简洁，将 options 放在了按钮的 dom 上
                 * 也可以直接通过代码声明 optinos 用于实例化 DtPicker
                 */
                var picker = new $.DtPicker(options);
                picker.show(function (rs) {
                    /*
                     * rs.value 拼合后的 value
                     * rs.text 拼合后的 text
                     * rs.y 年，可以通过 rs.y.vaue 和 rs.y.text 获取值和文本
                     * rs.m 月，用法同年
                     * rs.d 日，用法同年
                     * rs.h 时，用法同年
                     * rs.i 分（minutes 的第二个字母），用法同年
                     */
                    result.innerText = rs.text;

                    /* 
                     * 返回 false 可以阻止选择框的关闭
                     * return false;
                     */
                    /*
                     * 释放组件资源，释放后将将不能再操作组件
                     * 通常情况下，不需要示放组件，new DtPicker(options) 后，可以一直使用。
                     * 当前示例，因为内容较多，如不进行资原释放，在某些设备上会较慢。
                     * 所以每次用完便立即调用 dispose 进行释放，下次用时再创建新实例。
                     */
                    picker.dispose();
                });
            }, false);
        });

    })(mui);
/*---------------------------------------------------------------------------------------------------------*/
</script>

<script type="text/javascript" src="/front/mobile/js/My97DatePicker/WdatePicker.js"></script>
<script>
    //平台、设备和操作系统
    var system = {
        win: false,
        mac: false,
        xll: false,
        ipad: false
    };
    //检测平台
    var p = navigator.platform;
    system.win = p.indexOf("Win") == 0;
    system.mac = p.indexOf("Mac") == 0;
    system.x11 = (p == "X11") || (p.indexOf("Linux") == 0);
    system.ipad = (navigator.userAgent.match(/iPad/i) != null) ? true : false;
    //跳转语句，如果是手机访问自动切换mui日历
    if (system.win || system.mac || system.xll || system.ipad) {
		$("#pcdate").show();
        $("#pcbirthday").show();
    } else {
    	$("#pcdate").hide();
		$("#pcbirthday").hide();
    }
</script>
</body>
</html>
