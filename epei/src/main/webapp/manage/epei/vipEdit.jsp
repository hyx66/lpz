<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_vip_editform" action="${pageContext.request.contextPath}/manage/epei/vip/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="vip" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th>VIP名称：</th>
				<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>					
			<tr style="height:1px;border:none;border-bottom:1px solid #87CEEB;">
				<th>VIP级别：</th>
				<td><input type="text" name="grade" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>	
			<c:forEach var="serviceItem" items="${serviceItems}">
				<tr>
					<th>${serviceItem.name}免费服务次数：</th>
					<td><input type="text" name="${serviceItem.code}count" class="easyui-validatebox" value="${serviceItem.count}"/></td>
				</tr>
			</c:forEach>
        </table>
      </jodd:form>
    </form>
</div>