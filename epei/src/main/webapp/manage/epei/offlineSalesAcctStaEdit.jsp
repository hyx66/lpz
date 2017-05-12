<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>

<div>
	<form id="manage_offlineSalesAcctSta_editform" action="${pageContext.request.contextPath}/manage/epei/offlineSalesAcctSta/${action=='create'?'saveJson':'updateJson'}.html" method="post">
		<jodd:form bean="offlineSalesAcctSta" scope="request">
			<input name="id" type="hidden"/>
			<table class="tableForm" width="100%">
				<c:if test="${action == 'create'}">
	            <tr>
	                <th><span style="color: red">*</span>账期：</th>
	                <td>
	                	<input type="text" name="acctYmd" class="easyui-validatebox" value="<fmt:formatDate
							value="${acctYmd}" pattern="yyyyMMdd"/>" 
							onFocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd'})"
							data-options="required:true"/>
	                </td>
	            </tr>
	            <tr>
	            	<th><span style="color: red">*</span>统计范围：</th>
	            	<td>
	            	<select id="countScope" name="countScope" style="width:153px" onchange="countScopeChange()">
	            	<c:if test="${userFocusHospital==null}">
	            	<option value="1">所有医院</option>
	            	</c:if>
	            	<option value="2">单个医院</option>
	            	<option value="3">单个科室</option>
	            	</select>
                    </td>
	            </tr>
	            <tr id="orderType_tr">
	            	<th><span style="color: red">*</span>统计类型：</th>
	            	<td>
	            	<select id="orderType" name="orderType" style="width:153px" onchange="whenOrderTypeChange()">
	            	<option value="2">陪护</option>
	            	<option value="1">陪诊</option>
	            	</select>
                    </td>
	            </tr>
	            <tr id="hospitalId_tr">
	            	<th><span style="color: red">*</span>统计医院：</th>
	            	<td>
	            	<select id="hospitalId" name="hospitalId" onchange="whenHospitalChange()" style="width:153px" onchange="whenHospitalChange()">
					</select>
                    </td>
	            </tr>
	            <tr id="departmentId_tr">
	            	<th><span style="color: red">*</span>统计科室：</th>
	            	<td>
	            	<select id="departmentId" name="departmentId" style="width:153px">
					</select>
                    </td>
	            </tr>
	            <tr>
	            	<th><span style="color: red">*</span>统计数目：</th>
	            	<td>
	            		<input type="text" name="dataCount" class="easyui-validatebox" required="true"/>
                    </td>
	            </tr>
	            <tr>
	            	<th><span style="color: red">*</span>入账总金额：</th>
	            	<td>
	            		<input type="text" name="offlineAmount" class="easyui-validatebox" required="true" validType="money"/>
                    </td>
	            </tr>
	            </c:if>
	            <c:if test="${action == 'edit'}">
	            	<tr>
	            		<th><span style="color: red">*</span>账期：</th>
	            		<td>${offlineSalesAcctSta.acctYmd}</td>
	            		<input name="acctYmd" type="hidden"/>
	            	</tr>
	            	<tr>
	            		<th><span style="color: red">*</span>入账总金额：</th>
		            	<td>${offlineSalesAcctSta.offlineAmount}</td>
		            	<input name="offlineAmount" type="hidden"/>
	            	</tr>
	            </c:if>
	            <tr>
	            	<th>实账总金额：</th>
	            	<td><input type="text" name="acctAmount" class="easyui-validatebox" validType="money"/></td>
	            </tr>
	            <tr>
		            <th><span style="color: red">*</span>录入备注：</th>
	            	<td><input type="text" name="createMemo" class="easyui-validatebox" validType="byteLength[1,32]" required="true"/></td>
	            </tr>
			</table>
		</jodd:form>
	</form>
</div>
<c:if test="${action=='create'}">
<script type="text/javascript">
$(function () {
	$('#hospitalId_tr').hide();
	$('#departmentId_tr').hide();
	$('#orderType_tr').hide();
	$("#freezAmount").val("");
});
$.extend($.fn.validatebox.defaults.rules, {     
    money : {     
         validator: function(value){     
             return /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/i.test(value);     
         },     
         message: '请输入正确的金额'    
     } 
 });  
 
 function countScopeChange(){
	 var cv = $('#countScope option:selected').val();
	 $('#hospitalId_tr').show();
	 $('#departmentId_tr').show();
	 $('#orderType_tr').show();
	 //科室
	 if(cv==3){
		 queryHospital();
	 //医院
	 }else if(cv==2){
		 $('#departmentId_tr').hide();
		 $('#orderType_tr').hide();
		 queryHospital();
	 }else{
		 $('#hospitalId_tr').hide();
		 $('#departmentId_tr').hide();
		 $('#orderType_tr').hide();
	 }
 }
 
 function queryHospital(){
	var dataType;
	if($('#countScope option:selected').val()==2){
		dataType = "all";
	}else{
		if($('#orderType option:selected').val()==1){
			dataType = "pz";
		}else{
			dataType = "ph";
		}
	}
	//第一步：查询所有相关科室的医院
	$.ajax({
		 type: "GET",
		 url: "/manage/epei/offlineSalesAcctSta/comboboxHospital.html",
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
			    	var myoption= '<option value="'+result[i].id+'")">'+result[i].name+'</option>';
					$("#hospitalId").append(myoption);        	
			    }
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
		 url: "/manage/epei/offlineSalesAcctSta/comboboxDepartment.html",
		 data: {hosid:hospitalId},
		 dataType: "json",
		 success: function(result){
			 $("#departmentId").html("");
		    for(var i=0;i<result.length;i++){
		    	var myoption= '<option value="'+result[i].id+'")">'+result[i].name+'</option>';
				$("#departmentId").append(myoption);        	
		    }		
		 },
		 error : function() {   
		 alert("异常！");  
		 }  
	});
}
 
function whenHospitalChange(){
	queryDepartment();
}
function whenOrderTypeChange(){
	queryHospital();
}
</script>
</c:if>
<c:if test="${userFocusHospital!=null}">
<script>
queryHospital();
$('#hospitalId_tr').show();
</script>
</c:if>
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