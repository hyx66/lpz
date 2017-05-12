<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th width="25%">所属会员姓名:</th>
		<td>${patient.cusName}</td>
	</tr>					
	<tr>
		<th>所属会员编号:</th>
		<td>${patient.cusNo}</td>
	</tr>					
	<tr>
		<th>姓名:</th>
		<td>${patient.name}</td>
	</tr>					
	<tr>
		<th>手机号:</th>
		<td>${patient.mobile}</td>
	</tr>					
	<tr>
		<th>身份证号:</th>
		<td>${patient.idCard}</td>
	</tr>					
	<tr>
		<th>生日:</th>
		<td>${patient.birthday}</td>
	</tr>					
	<tr>
		<th>性别</th>
		<td>${patient.gender == 1?"男":"女"}</td>
	</tr>					
	<tr>
		<th>医保卡号:</th>
		<td>${patient.medicareCard}</td>
	</tr>					
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${patient.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
</table>
</div>
