<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_serviceItem_editform" action="${pageContext.request.contextPath}/manage/epei/serviceItem/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="serviceItem" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
        	<tr>
				<th>英文标识：</th>
				<td><input type="text" name="code" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>	
			<tr>
				<th>服务项目名称：</th>
				<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>	
			<tr>
				<th>描述：</th>
				<td><input type="text" name="describe" class="easyui-validatebox"/></td>
			</tr>						
        </table>
      </jodd:form>
    </form>
</div>