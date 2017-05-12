<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div id ='page'>
    <form id="manage_orderPz_editform" action="${pageContext.request.contextPath}/manage/epei/orderPz/${action=='create'?'saveJson':'updateJson'}.html?orign=FZZ" method="post">
      <jodd:form bean="orderPz" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
        	<tr>
                <th><span style="color: red">*</span>会员账号：</th>
                <td>
	                <input id="userName" type="text" onchange="checkCustomer()" name="userName" class="easyui-validatebox" data-options="required:true" validType="phone" value="${userName}"/>
	                <span id="checkResult" style="color:red"></span>
                </td>
            </tr>
            <tr>
                <th><span style="color: red">*</span>病患姓名：</th>
                <td><input type="text" name="patient.name" class="easyui-validatebox" data-options="required:true" validType="byteLength[1,32]"/></td>
            </tr>
            <tr>
				<th>患者性别：</th>
				<td>
					<select name="patient.gender" style="width:153px" >
					  <option value ="1" <c:if test="${orderPz.patient.gender==1}">selected</c:if> >男</option>
					  <option value ="0" <c:if test="${orderPz.patient.gender==0}">selected</c:if> >女</option>
					</select>
				</td>
			</tr>
			<tr>
            	<th>身份证号：</th>
            	<td><input type="text" name="patientIdCard" class="easyui-validatebox" data-options="required:true" validType="idcard"/></td>
            </tr>
            <tr>
                <th><span style="color: red">*</span>联系电话：</th>
                <td><input type="text" name="patient.mobile" class="easyui-validatebox"
                           validType="phone" required="true"/></td>
            </tr>
            <tr>
                <th><span style="color: red">*</span>患者生日：</th>
                <td>
                	<input type="text" name="patient.birthday" class="easyui-validatebox" validType="byteLength[8,8]" placeholder="格式：19930209" required="true"/>
                </td>
            </tr>
            <tr>
                <th>医保卡号：</th>
                <td><input type="text" name="patient.medicareCard" class="easyui-validatebox"
                           validType="byteLength[1,20]"/></td>
            </tr>
             <tr>
				<th>就诊医院：</th>
				<td>
				<select id="hospitalId" name="hospital.id" onchange="queryDepartment()" style="width:153px">
				</select>
				</td>
			</tr>
			<tr>
				<th>就诊科室：</th>
				<td>
				<select id="departmentName" name="departmentName" style="width:153px">
				</select>
				</td>
			</tr>	
			<tr>
                <th><span style="color: red">*</span>预约日期：</th>
                <td><input type="text" name="orderTime" class="easyui-validatebox" value="<fmt:formatDate
                 value="${orderPz.orderTime}"
                 pattern="yyyy-MM-dd HH:mm:ss"/>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" data-options="required:true" /></td>
            </tr>
            <tr id="use_vip_item" style="display:none">
				<th>使用VIP免费特权${orderPz.freeItem}：</th>
				<td>
					<select name="freeItem" style="width:153px" >
					  <option value ="0" <c:if test="${orderPz.freeItem==0}">selected</c:if>>否</option>
					  <option value ="1" <c:if test="${orderPz.freeItem==1}">selected</c:if>>是</option>
					</select>
					<span id="vip_item_count" style="color:red"></span>
				</td>
			</tr>
            <tr>
            	<th>需支付的费用：</th>
            	<td><input type="text" name="amount" class="easyui-validatebox" data-options="required:true" validType="money"/></td>
            </tr>
            <tr>
            	<th>备注：</th>
            	<td><input type="text" name="note" class="easyui-validatebox" validType="byteLength[1,32]"/></td>
            </tr>		
        </table>
      </jodd:form>
    </form>
</div>
<script>
function checkCustomer(){
	$.ajax({
		 type: "GET",
		 url: "/manage/epei/orderPz/checkCustomer.html",
		 data: {"userName":$("input[name='userName']").val()},
		 async:false,
		 dataType: "json",
		 success:function(result){
			 $("#checkResult").text("["+result.message+"]");
			 if(result.success){
				 $("#use_vip_item").show();
				 $("#vip_item_count").text("[剩余"+result.data.count+"次]");
			 }else{
				 $("#use_vip_item").hide();
				 $("#vip_item_count").text("");
			 }
		}
	});
}
</script>
<script type="text/javascript">   
$(function () {
	queryHospital();
});
function queryHospital(){
	//第一步：查询所有相关科室的医院
	$.ajax({
		 type: "GET",
		 url: "/manage/epei/orderPz/comboboxHospital.html",
		 data: {serviceType:"pz"},
		 dataType: "json",
		 success: function(result){
			//第二步：把查询出来的结果罗列到select列表中
			$("#hospitalId").html("");
		    for(var i=0;i<result.length;i++){
		    	var  myoption = "";
		    	if("${orderPz.hospital.id}"==result[i].id){
		    		myoption= '<option selected="selected" value="'+result[i].id+'")">'+result[i].name+'</option>';
		    	}else{
		    		myoption= '<option value="'+result[i].id+'")">'+result[i].name+'</option>';
		    	}
				$("#hospitalId").append(myoption);        	
		    }
		    queryDepartment();
		},
		 error : function() {   
		alert("异常！");  
		}  
	});
}

function queryDepartment(){
	var hospitalId = $("#hospitalId option:selected").val();
	$.ajax({
		 type: "GET",
		 url: "/manage/epei/orderPz/comboboxDepartment.html",
		 data: {hosid:hospitalId},
		 async: false,
		 dataType: "json",
		 success: function(result){
			 $("#departmentName").html("");
		    for(var i=0;i<result.length;i++){
		    	var myoption ="";
		    	if("${orderPz.departmentName}"==result[i].name){
		    		myoption= '<option selected="selected" value="'+result[i].name+'")">'+result[i].name+'</option>';
		    	}else{
		    		myoption= '<option value="'+result[i].name+'")">'+result[i].name+'</option>';
		    	}
				$("#departmentName").append(myoption);        	
		    }		
		 },
		 error : function() {   
		 alert("异常！");  
		 }  
	});
}

$.extend($.fn.validatebox.defaults.rules, {
    idcard: {// 验证身份证
        validator: function (value) {
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
        	},
        message: '身份证号码格式不正确'
        }
});

$.extend($.fn.validatebox.defaults.rules, {
    phone: {// 手机号
        validator: function (value) {
            return /^[0-9]{0,11}$/i.test(value);
        	},
        message: '手机号码格式不正确'
        }
});

$.extend($.fn.validatebox.defaults.rules, {
    money: {// 手机号
        validator: function (value) {
            return /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/i.test(value);
        	},
        message: '金额不正确'
        }
});
</script>
<script>
//通过上、下按键移动焦点
$(document).ready(function () {
	$(':input:enabled').addClass('enterIndex');
	textboxes = $('.enterIndex');
	if ($.browser.mozilla) {
		$(textboxes).bind('keypress', CheckForEnter);
	}else {
		$(textboxes).bind('keydown', CheckForEnter);
	}
});
function CheckForEnter(event) {
	if (event.keyCode == 40 && $(this).attr('type') != 'button' && $(this).attr('type') != 'submit' && $(this).attr('type') != 'textarea' && $(this).attr('type') != 'reset') {
		var i = $('.enterIndex').index($(this));
		var n = $('.enterIndex').length;
		if (i < n) {
		if ($(this).attr('type') != 'radio'){
			NextDOM($('.enterIndex'),i);
		}
		else {
			var last_radio = $('.enterIndex').index($('.enterIndex[type=radio][name=' + $(this).attr('name') + ']:last'));
			NextDOM($('.enterIndex'),last_radio);
			}
		}
		return false;
	}
	if (event.keyCode == 38 && $(this).attr('type') != 'button' && $(this).attr('type') != 'submit' && $(this).attr('type') != 'textarea' && $(this).attr('type') != 'reset') {
		var i = $('.enterIndex').index($(this));
		var n = $('.enterIndex').length;
		if (i < n) {
		if ($(this).attr('type') != 'radio'){
			PreDOM($('.enterIndex'),i);
		}
		else {
			var last_radio = $('.enterIndex').index($('.enterIndex[type=radio][name=' + $(this).attr('name') + ']:first'));
			PreDOM($('.enterIndex'),last_radio);
			}
		}
		return false;
	}
	
}
function NextDOM(myjQueryObjects,counter) {
	if (myjQueryObjects.eq(counter+1)[0].disabled) {
		NextDOM(myjQueryObjects, counter + 1);
	}
	else {
		myjQueryObjects.eq(counter + 1).trigger('focus');
	}
}
function PreDOM(myjQueryObjects,counter) {
	if (myjQueryObjects.eq(counter-1)[0].disabled) {
		PreDOM(myjQueryObjects, counter - 1);
	}
	else {
		myjQueryObjects.eq(counter - 1).trigger('focus');
	}
}
</script>
<c:if test="${action!='create'}">
<script>
$(function () {
	$("#page").hide();
	$('#acooly-framework-edit-btn').linkbutton('disable');
	$.ajax({
		 type: "get",
		 url: "/manage/epei/orderPz/queryAcceptance.html",
		 data: {"id":"${orderPz.id}"},
		 async:false,
		 dataType: "json",
		 success: function(data){
			 if(data=="NO" && !confirm("订单已由其他工作人员受理,是否继续更改？")){
		 		$('.panel-tool-close').trigger("click");
			 }else{
				$("#page").show();
				$('#acooly-framework-edit-btn').linkbutton('enable');
			 }
		 }
	});
});
$("#userName").attr("readonly",true);
checkCustomer();
</script>
</c:if>