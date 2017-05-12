<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<link rel="stylesheet" type="text/css"
	href="/manage/plugin/jquery-easyui/themes/basic.css">
<div>
	<form id="manage_lxyPerson_editform"
		action="${pageContext.request.contextPath}/manage/epei/lxyPerson/${action=='create'?'saveJson':'updateJson'}.html"
		method="post">
		<jodd:form bean="lxyPerson" scope="request">
			<input name="id" type="hidden" />
			<table class="tableForm" width="100%">
				<tr>
					<td><font color="#ff883a">&nbsp;&nbsp;所有带*的项目为必填项</font></td>
					<td></td>
				</tr>
				<tr>
					<th><font color="#ff3200">*&nbsp;&nbsp;</font>头像：</th>
					<td><img id="imageCover" width='80px' height='80px'
						src='${pageContext.request.contextPath}/upload/${lxyPerson.image}' />
						<input type="file" name="upImage"
						accept="image/gif,image/jpeg,image/png"
						style="width: 210px; height: 30px; border: none;"></td>
				</tr>
				<tr>
					<th><font color="#ff3200">*&nbsp;&nbsp;</font>服务人员名：</th>
					<td><input type="text" name="name" class="easyui-validatebox"
						data-options="required:true" validType="byteLength[1,32]"
						style="width: 210px; height: 30px;" /></td>
				</tr>
				<tr>
					<th><font color="#ff3200">*&nbsp;&nbsp;</font>性别：</th>
					<td>
						<input type="radio" name="gender" value="1" class="easyui-validatebox" checked="checked" />男
						<input type="radio" name="gender" value="0" class="easyui-validatebox"  <c:if test="${lxyOrderBase.gender==0}">checked="checked"</c:if> />女 
					</td>
				</tr>
				<tr>
					<th><font color="#ff3200">*&nbsp;&nbsp;</font>工号：</th>
					<td><input type="text" name="employeeNumber"
						class="easyui-validatebox" validType="byteLength[1,32]"
						style="width: 210px; height: 30px;" data-options="required:true" /></td>
				</tr>
				<tr>
					<th><font color="#ff3200">*&nbsp;&nbsp;</font>联系方式：</th>
					<td><input type="text" name="phone"
						class="easyui-validatebox" data-options="required:true"
						validType="mobile" style="width: 210px; height: 30px;" /></td>
				</tr>
				<tr>
					<th>*&nbsp;&nbsp;服务医院：</th>
					<td><select name="hospitalId" style="width: 215px; height: 30px; border: 1px solid #e6e6e6;">
							<c:forEach items="${hospitals}" var="hospital">
							<c:if test="${hospital.name=='重庆医科大学附属儿童医院'}">
							<option value="${hospital.id}" <c:if test='${lxyOrderBase.hospitalId==hospital.id}'>selected="selected"</c:if>>${hospital.name}</option>
							</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
		</jodd:form>
	</form>
</div>
<script>
	$.extend($.fn.validatebox.defaults.rules, {
		mobile : {
			validator : function(value) {
				return /^1[0-9]{10}$/i.test(value);
			},
			message : '请输入正确的手机号'
		}
	});

	$.extend($.fn.validatebox.defaults.rules, {
		idcard : {// 验证身份证
			validator : function(value) {
				return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
			},
			message : '请输入有效的身份证号'
		}
	});
</script>