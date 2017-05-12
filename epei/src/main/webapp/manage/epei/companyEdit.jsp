<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_company_editform" action="${pageContext.request.contextPath}/manage/epei/company/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="company" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th>企业名称：</th>
				<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>					
			<tr>
				<th>企业地址：</th>
				<td><input type="text" name="address" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>	
			<tr>
				<th>负责人姓名：</th>
				<td><input type="text" name="leaderName" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>
			<tr>
				<th>负责人电话：</th>
				<td><input type="text" name="leaderPhone" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>
        </table>
      </jodd:form>
    </form>
</div>