<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>

<div>
	<form id="manage_offlineSalesRecords_editform" action="${pageContext.request.contextPath}/manage/epei/offlineSalesRecords/${action=='create'?'saveJson':'updateJson'}.html" method="post">
		<jodd:form bean="offlineSalesRecords" scope="request">
			<input name="id" type="hidden" />
			<table class="tableForm" width="100%">
				<tr>
			        <th><span style="color: red">*</span>支付类型：</th>
			        <td>
			        	<input type="radio" name="payType" value="0" checked="checked"/>现金
			        	<input type="radio" name="payType" value="1"/>POS机
			        	<input type="radio" name="payType" value="2"/>支票
			        </td>
			    </tr>
			    <tr>
			        <th><span style="color: red">*</span>客户姓名：</th>
			        <td><input type="text" name="customerName" class="easyui-validatebox" data-options="required:true" validType="byteLength[1,32]" /></td>
			    </tr>
			    <tr>
			        <th><span style="color: red">*</span>性别：</th>
			        <td>
			        	<input type="radio" name="customerSex" value="1" checked="checked"/>男
			        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        	<input type="radio" name="customerSex" value="0"/>女
			        </td>
			    </tr>
			    <tr>
			        <th>年龄：</th>
			        <td><input type="text" name="customerAge" class="easyui-validatebox" validType="byteLength[0,3]" /></td>
			    </tr>
			    <tr>
			        <th>客户手机号：</th>
			        <td><input type="text" name="customerMobile" class="easyui-validatebox" validType="number" /></td>
			    </tr>
			    <tr>
			        <th><span style="color: red">*</span>订单类型：</th>
			        <td>
			        	<input type="radio" name="dataType" value="1" checked="checked" onClick="fillDataType();">陪护
			        	&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="radio" name="dataType" value="0" onClick="fillDataType();">陪诊
					</td>
			    </tr> 
	            <tr>
					<th>就诊医院：</th>
					<td>
					<select id="hospitalId" name="hospitalId" onchange="whenHospitalChange()" style="width:153px">
					</select>
					</td>
				</tr>
			    <tr>
					<th>就诊科室：</th>
					<td>
					<select id="departId" name="departId" style="width:153px">
					</select>
					</td>
				</tr>
				<tr >
					<th>床位：</th>
			        <td><input type="text" name="customerBed" class="easyui-validatebox"/></td>
				</tr>
				<tr>
					<th>服务人员姓名：</th>
					<td><input type="text" name="servicePersonName" class="easyui-validatebox"/></td>
				</tr>
				<tr>
					<th>服务人员电话：</th>
					<td><input type="text" validType="number" name="servicePersonMobile" class="easyui-validatebox"/></td>
				</tr>
				<tr>
                	<th><span style="color: red">*</span>服务开始时间：</th>
	                <td><input type="text" name="serviceYmd" class="easyui-validatebox" data-options="required:true" value="<fmt:formatDate
						value="${serviceYmd}" pattern="yyyy-MM-dd"/>" validType="date"/>
					</td>
	            </tr>
	            <tr>
                	<th><span style="color: red">*</span>服务结束时间：</th>
	                <td><input type="text" name="serviceEndYmd" class="easyui-validatebox" data-options="required:true" value="<fmt:formatDate
						value="${serviceEndYmd}" pattern="yyyy-MM-dd"/>" validType="date"/>
					</td>
	            </tr>
	            <tr>
					<th><span style="color: red">*</span>总费用：</th>
			        <td><input id="amount" type="text" name="amount" data-options="required:true" validType="money" class="easyui-validatebox"/></td>
				</tr>
				<tr>
			        <th><span style="color: red">*</span>收款人姓名：</th>
			        <td><input type="text" name="payeeName" class="easyui-validatebox" data-options="required:true" validType="byteLength[1,32]" /></td>
				</tr>
				<tr id="outNo">
			        <th>生活护理申请表单号：</th>
			        <td><input type="text" name="outNo" class="easyui-validatebox" validType="byteLength[1,32]" /></td>
			    </tr>
	            <tr id="ticketNo">
			        <th>小/支票号：</th>
			        <td><input type="text" name="ticketNo" class="easyui-validatebox" validType="byteLength[1,32]" /></td>
			    </tr>
			    <tr>
			        <th>病症：</th>
			        <td><input type="text" name="disease" class="easyui-validatebox" validType="byteLength[1,480]" /></td>
			    </tr>
			    <tr>
			        <th>备注：</th>
			        <td><input type="text" name="memo" class="easyui-validatebox" validType="byteLength[1,128]" /></td>
			    </tr>
			</table>
		</jodd:form>
	</form>
</div>
<script type="text/javascript">
$(function () {
	if($('input:radio[name="payType"]:checked').val()==0){
		$("#ticketNo").hide();
	}else{
		$("#ticketNo").show();
	}
	queryHospital();
});
$('input:radio[name="dataType"]').change(function(){
	queryHospital();
});
$('input:radio[name="payType"]').change(function(){
	if($('input:radio[name="payType"]:checked').val()==0){
		$("#ticketNo").hide();
	}else{
		$("#ticketNo").show();
	}
});
function queryHospital(){
	var dataType;
	if($('input[name="dataType"]:checked').val()==0){
		dataType = "pz";
	}else{
		dataType = "ph";
	}
	//第一步：查询所有相关科室的医院
	$.ajax({
		 type: "GET",
		 url: "/manage/epei/offlineSalesRecords/comboboxHospital.html",
		 data: {serviceType:dataType},
		 dataType: "json",
		 success: function(result){
			//第二步：把查询出来的结果罗列到select列表中
			$("#hospitalId").html("");
			if("${userFocusHospital}"!=""){
				for(var i=0;i<result.length;i++){
					var  myoption = "";
					if("${userFocusHospital.hospitalId}"==result[i].id){
			    		myoption= '<option selected="selected" value="'+result[i].id+'")">'+result[i].name+'</option>';
			    		$("#hospitalId").append(myoption); 
			    	}
				}	
			}else{
				for(var i=0;i<result.length;i++){
			    	var  myoption = "";
			    	if("${offlineSalesRecords.hospitalId}"==result[i].id){
			    		myoption= '<option selected="selected" value="'+result[i].id+'")">'+result[i].name+'</option>';
			    	}else{
			    		myoption= '<option value="'+result[i].id+'")">'+result[i].name+'</option>';
			    	}
					$("#hospitalId").append(myoption);        	
			    }
			}
		    queryDepartment();
		},
		 error : function() {   
		alert("异常！！");  
		}  
	});
}

function whenHospitalChange(){
	queryDepartment();
}

function queryDepartment(){
	var hospitalId = $("#hospitalId option:selected").val();
	$.ajax({
		 type: "GET",
		 url: "/manage/epei/offlineSalesRecords/comboboxDepartment.html",
		 data: {hosid:hospitalId},
		 dataType: "json",
		 success: function(result){
			 $("#departId").html("");
		    for(var i=0;i<result.length;i++){
		    	var myoption ="";
		    	if("${offlineSalesRecords.departId}"==result[i].id){
		    		myoption= '<option selected="selected" value="'+result[i].id+'")">'+result[i].name+'</option>';
		    	}else{
		    		myoption= '<option value="'+result[i].id+'")">'+result[i].name+'</option>';
		    	}
				$("#departId").append(myoption);        	
		    }		
		 },
		 error : function() {   
		 alert("异常！");  
		 }  
	});
}

$.extend($.fn.validatebox.defaults.rules, {   
    money : {     
         validator: function(value){     
             return /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/i.test(value);     
         },     
         message: '请输入正确的金额'    
     }     
 });  
$.extend($.fn.validatebox.defaults.rules, {   
    phone : {     
         validator: function(value){     
             return /^1\d{10}$/i.test(value);     
         },     
         message: '请输入正确的手机号'    
     }     
 });
$.extend($.fn.validatebox.defaults.rules, {   
    date : {     
         validator: function(value){     
             return /^((?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)|([0-9]{2}(0[48]|[2468][048]|[13579][26])|(0[48]|[2468][048]|[13579][26])00)-02-29)$/i.test(value);     
         },     
         message: '日期格式：2016-01-01'    
     }     
 });   
$.extend($.fn.validatebox.defaults.rules, {   
    number : {     
         validator: function(value){     
             return /^[0-9]{0,11}$/i.test(value);     
         },     
         message: '电话号码只能由数字组成,且不能多于11位数字'    
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