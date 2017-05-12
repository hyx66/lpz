<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_customerFamily_editform" action="${pageContext.request.contextPath}/manage/epei/customerFamily/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="customerFamily" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th>会员账号：</th>
				<td><input type="text" name="customerUserName" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>					
			<tr>
				<th>姓名：</th>
				<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>	
			<tr>
				<th>电话：</th>
				<td><input type="text" name="phone" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>
			<tr>
				<th>身份证号：</th>
				<td><input type="text" name="idCard" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>
			<tr>
				<th>与持卡人的关系：</th>
				<td><input type="text" name="relationship" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>
        </table>
      </jodd:form>
    </form>
</div>