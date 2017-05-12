<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th width="25%">医院名:</th>
		<td>${hospital.name}</td>
	</tr>					
	<tr>
		<th>医院logo:</th>
		<td>
            <img width="300px" height="120px"
                 src="${pageContext.request.contextPath}/manage/epei/hospital/logo.html?id=${hospital.logoImageId}">
        </td>
	</tr>
    <tr>
        <th width="25%">服务类型:</th>
        <td>${hospital.serviceType == 'ALL'?"陪诊和陪护":(hospital.serviceType == 'PZ'?'陪诊':'陪护')}</td>
    </tr>
    <tr>
        <th width="25%">合作关系:</th>
        <td>${hospital.isCooperate == '1'?'非合作':'已合作'}</td>
    </tr>
    <tr>
		<th>接待地点:</th>
		<td>${hospital.receptionPosition}</td>
	</tr>	
	<tr>
		<th>陪诊负责人姓名:</th>
		<td>${hospital.pzPrincipalName}</td>
	</tr>
	<tr>
		<th>陪诊负责人电话:</th>
		<td>${hospital.pzPrincipalMobile}</td>
	</tr>
	<tr>
		<th>陪护负责人姓名:</th>
		<td>${hospital.phPrincipalName}</td>
	</tr>
	<tr>
		<th>陪护负责人电话:</th>
		<td>${hospital.phPrincipalMobile}</td>
	</tr>
	<tr>
		<th>创建时间:</th>
		<td><fmt:formatDate value="${hospital.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
</table>
</div>
