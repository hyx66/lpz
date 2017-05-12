<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<script>
$(function () {
	queryHospital();
});
function queryHospital(){
	//第一步：查询所有相关科室的医院
	$.ajax({
		 type: "GET",
		 url: "/manage/epei/orderPh/comboboxHospital.html",
		 data: {serviceType:"ph"},
		 dataType: "json",
		 success: function(result){
			//第二步：把查询出来的结果罗列到select列表中
			$("#hospitalId").html("");
		    for(var i=0;i<result.length;i++){
		    	var  myoption = "";
		    	if("${orderPh.hospital.id}"==result[i].id){
		    		myoption= '<option selected="selected" value="'+result[i].id+'")">'+result[i].name+'</option>';
		    	}else{
		    		myoption= '<option value="'+result[i].id+'")">'+result[i].name+'</option>';
		    	}
				$("#hospitalId").append(myoption);        	
		    }
		    queryDepartment();
		    queryServicePrice();
		},
		 error : function() {   
		alert("异常！");  
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
		 url: "/manage/epei/orderPh/comboboxDepartment.html",
		 data: {hosid:hospitalId},
		 dataType: "json",
		 success: function(result){
			 $("#departmentName").html("");
		    for(var i=0;i<result.length;i++){
		    	var myoption ="";
		    	if("${orderPh.departmentName}"==result[i].name){
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
            return /^1\d{10}$/i.test(value);
        	},
        message: '手机号码格式不正确'
        }
});
</script>

<div>
    <form id="orderPhRenewForm">
      <jodd:form bean="orderPh" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
            <tr>
                <th><span style="color: red">*</span>病患姓名：</th>
                <td><input type="text" name="patient.name" class="easyui-validatebox" data-options="required:true"
                           validType="byteLength[1,32]" /></td>
            </tr>
            <tr>
                <th><span style="color: red">*</span>联系电话：</th>
                <td><input type="text" name="patient.mobile" class="easyui-validatebox"
                           validType="phone" required="true"/></td>
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
                <th>患者生日：</th>
                <td>
                	<input type="text" name="patient.birthday" class="easyui-validatebox" validType="byteLength[8,8]" placeholder="格式：19930209"/>
                </td>
            </tr>
            <tr>
				<th>就诊医院：</th>
				<td>
				<select id="hospitalId" name="hospital.id" onchange="whenHospitalChange()" style="width:153px">
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
            	<th>价格/天：</th>
            	<td><input type="text" name="phServicePrice" class="easyui-validatebox"/></td>
            </tr>
            <tr>
            	<th><span style="color: red">*</span>所在床位：</th>
            	<td><input type="text" name="bed" class="easyui-validatebox" required="true"/></td>
            </tr>
            <tr>
                <th><span style="color: red">*</span>预约日期：</th>
                <td><input type="text" name="orderTime" value="<fmt:formatDate value="${orderPh.orderTime}"
                 pattern="yyyy-MM-dd"/>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" data-options="required:true" class="easyui-validatebox"/></td>
            </tr>
        	<tr>
            	<th>预约时间段：</th>
            	<td>
            		<input type="radio" name="timeInOneDay" value="AM" checked>上午&nbsp;&nbsp;&nbsp;&nbsp;
    				<input type="radio" name="timeInOneDay" value="PM">下午
            	</td>
            </tr>
            <tr>
            	<th><span style="color: red">*</span>预约天数：</th>
            	<td>
            		<input type="text" name="duration" validType="zhengshu" class="easyui-validatebox" required="true"/>
            	</td>
            </tr>
            <tr>
            	<th>预约方式：</th>
            	<td>
            		<input type="radio" name="appointmentType" value="0" checked="checked"/>电话预约
            		<input type="radio" name="appointmentType" value="1"/>主动预约
            	</td>
            </tr>
			<tr id="outNo">
		        <th>生活护理申请表单号：</th>
		        <td><input type="text" name="outNo" class="easyui-validatebox" validType="byteLength[1,32]" /></td>
		    </tr>
			<tr>
		        <th>病症：</th>
		        <td><input type="text" name="disease" class="easyui-validatebox" validType="byteLength[1,480]" /></td>
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
$.extend($.fn.validatebox.defaults.rules, {     
    zhengshu : {     
         validator: function(value){     
             return /^[0-9]*[1-9][0-9]*$/i.test(value);     
         },     
         message: '请输入正整数'    
     } 
 });  
</script>