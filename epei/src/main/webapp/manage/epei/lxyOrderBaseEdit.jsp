<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="/manage/plugin/jquery-easyui/themes/basic.css">
<div>
	<br>
   <font color="#ff883a">&nbsp;所有带*的项目为必填项</font><br>
   
	<div  class="title_message" >
		订单编号：${lxyOrderBase.orderId} &nbsp;&nbsp;
		下单时间：<fmt:formatDate value="${lxyOrderBase.time}" pattern="yyyy-MM-dd HH:mm"/> &nbsp;&nbsp;
		微店账户：${lxyOrderBase.buyerId}
	</div><br>
    <form id="manage_lxyOrderBase_editform" action="${pageContext.request.contextPath}/manage/epei/lxyOrderBase/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="lxyOrderBase" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%" style="margin-auto">	
			<tr>
				<th><font color="#ff3200">*&nbsp;&nbsp;</font>宝宝姓名：</th>
				<td><input style="width: 210px; height: 30px;"
				type="text" name="babyName" data-options="required:true" class="easyui-validatebox"  validType="byteLength[1,32]"/></td>
			</tr>					
			<tr>
				<th><font color="#ff3200">*&nbsp;&nbsp;</font>宝宝性别:</th>
				<td>
					<input type="radio" name="babySex" value="1"  class="easyui-validatebox" checked="checked"/>男
					<input type="radio" name="babySex" value="0"  class="easyui-validatebox" <c:if test="${lxyOrderBase.babySex==0}">checked="checked"</c:if> />女
				</td>
			</tr>	
			<tr>
				<th ><font color="#ff3200">*&nbsp;&nbsp;</font>服务医院：</th>
				<td>
					  <select id="hospitalId" name="hospitalId" onchange="queryDepartment()" style="width: 215px; height: 30px; border: 1px solid #e6e6e6;" >
						<c:forEach items="${hospitals}" var="hospital">
							<c:if test="${hospital.name=='重庆医科大学附属儿童医院'}">
							<option value="${hospital.id}" <c:if test='${lxyOrderBase.hospitalId==hospital.id}'>selected="selected"</c:if>>${hospital.name}</option>
							</c:if>
						</c:forEach>
					</select>  
				</td>
			</tr>	
			<tr>
				<th ><font color="#ff3200">*&nbsp;&nbsp;</font>预约时间：</th>
				<td><input style="width: 210px; height: 30px;"
				type="text" name="orderTime" class="easyui-validatebox" value="<fmt:formatDate value="${lxyOrderBase.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" data-options="required:true" /></td>
			</tr>					
			<tr>
				<th><font >*&nbsp;&nbsp;</font>指派陪诊员：</th>
				<td>
					<select id="pzId" name="pzId" style="width: 215px; height: 30px; border: 1px solid #e6e6e6;" >
						<c:forEach items="${lxyPersons}" var="lxyPerson"><option class="${lxyPerson.phone}" value="${lxyPerson.id}" 
						<c:if test='${lxyOrderBase.pzId==lxyPerson.id}'>selected="selected"</c:if>>${lxyPerson.name}</option></c:forEach>
					</select>
				</td>
			</tr>	
			<tr>
				<th ><font >*&nbsp;&nbsp;</font>陪诊员联系方式：</th>
				<td><input style="width: 210px; height: 30px;"
				id="pzPhone" type="text" name="pzPhone" data-options="required:true" class="easyui-validatebox"  validType="phone"/></td>
			</tr>		
			<tr style="border-bottom: 1px dashed #e6e6e6 ">
				<th >备注：</th>
				<td>
					<textarea style="width: 340px; height: 70px;" name="remark"></textarea>
				</td>
			</tr>	
			<tr>
				<th ><font size="4px"><b>家长信息:</b></font></th>
				<td></td>
			</tr>	
			<tr>
				<th><font color="#ff3200">*&nbsp;&nbsp;</font>家长姓名：</th>
				<td><input style="width: 210px; height: 30px;" 
				type="text" name="patriarchName" data-options="required:true" class="easyui-validatebox"  validType="byteLength[1,32]"/></td>
			</tr>	
			<tr>
				<th><font color="#ff3200">*&nbsp;&nbsp;</font>家长联系方式：</th>
				<td><input style="width: 210px; height: 30px;" 
				type="text" name="patriarchPhone" data-options="required:true" class="easyui-validatebox"  validType="phone"/></td>
			</tr>								
			<tr>
				<th><font color="#ff3200">*&nbsp;&nbsp;</font>家长身份证：</th>
				<td><input style="width: 210px; height: 30px;" 
				type="text" name="patriarchIdCard" data-options="required:true" class="easyui-validatebox"  validType="idcard"/></td>
			</tr>			
			<tr>
				<th><font color="#ff3200">*&nbsp;&nbsp;</font>家庭地址：</th>
				<td>
					<textarea style="width: 340px; height: 100px;" name="site" data-options="required:true" class="easyui-validatebox"></textarea>
				</td>
			</tr>
        </table>
      </jodd:form>
    </form>
     <div style="margin:10px;color:gray">
		<span>订单留言：${buyerNote}</span>
	</div>
</div>
<script type="text/javascript">
queryDepartment();

$.extend($.fn.validatebox.defaults.rules, {     
	birthday : {     
         validator: function(value){     
             return /^[12]\d{3}(0\d|1[0-2])([0-2]\d|3[01])$/i.test(value);     
         },     
         message: '曰期格式:19010101'    
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

$.extend($.fn.validatebox.defaults.rules, {
    idcard: {// 验证身份证
        validator: function (value) {
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message: '请输入有效的身份证号'
	}
});

function queryDepartment(){
	var hospitalId = $("#hospitalId option:selected").val();
	$.ajax({
		 type: "GET",
		 url: "/manage/epei/lxyOrderBase/comboboxDepartment.html",
		 data: {hosid:hospitalId},
		 dataType: "json",
		 success: function(result){
			 $("#departmentId").html("");
		    for(var i=0;i<result.length;i++){
		    	var myoption ="";
		    	if("${lxyOrderBase.departmentId}"==result[i].id){
		    		myoption= '<option selected="selected" value="'+result[i].id+'")">'+result[i].name+'</option>';
		    	}else{
		    		myoption= '<option value="'+result[i].id+'")">'+result[i].name+'</option>';
		    	}
				$("#departmentId").append(myoption);        	
		    }		
		 },
		 error : function() {   
		 alert("异常！");  
		 }  
	});
};

$('#pzId').change(function(){
	changePzPhone();
})

function changePzPhone(){
	$("#pzPhone").val($('#pzId option:selected').attr("class"));
}

changePzPhone();
</script>