<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th width="25%">姓名:</th>
		<td>${servicePerson.name}</td>
	</tr>					
	<tr>
		<th>手机号:</th>
		<td>${servicePerson.mobile}</td>
	</tr>					
	<tr>
		<th>身份证号:</th>
		<td>${servicePerson.idCard}</td>
	</tr>					
	<tr>
		<th>所属医院:</th>
		<td>${servicePerson.hospital}</td>
	</tr>
	<tr>
		<th>入职时间:</th>
		<td><fmt:formatDate value="${servicePerson.hireDate}" pattern="yyyy-MM-dd"/></td>
	</tr>
    <tr>
        <th>员工编号:</th>
        <td>${servicePerson.empNo}</td>
    </tr>
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${servicePerson.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
</table>
</div>
