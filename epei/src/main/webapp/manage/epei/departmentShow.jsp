<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th width="25%">科室:</th>
		<td>${department.name}</td>
	</tr>	
	<tr>
		<th width="25%">所属医院编号:</th>
		<td>${department.hospitalId}</td>
	</tr>
	<tr>
		<th width="25%">所属医院名称:</th>
		<td>${department.hospitalName}</td>
	</tr>
	<tr>
		<th width="25%">陪护价格:</th>
		<td>${department.phServicePrice}</td>
	</tr>								
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${department.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
</table>
</div>
