<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_userFocusHospital_searchform','manage_userFocusHospital_datagrid');
});
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_userFocusHospital_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
					医院名称:<input type="text" size="15" name="search_LIKE_hospitalName" value="${param.search_LIKE_hospitalName}"  />
					管理员账号:<input type="text" size="15" name="search_LIKE_userName" value="${param.search_LIKE_userName}"  />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_userFocusHospital_searchform','manage_userFocusHospital_datagrid');">查询</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_userFocusHospital_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/epei/userFocusHospital/listJson.html" toolbar="#manage_userFocusHospital_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="userId">管理员ID</th>
			<th field="userName">管理员账号</th>
			<th field="hospitalId">医院ID</th>
			<th field="hospitalName">医院名称</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_userFocusHospital_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_userFocusHospital_action" style="display: none;">
      <a class="line-action icon-edit"
         onclick="$.acooly.framework.edit({url:'/manage/epei/userFocusHospital/edit.html',id:'{0}',entity:'userFocusHospital',width:600,height:450});" href="#" title="编辑"></a>
      <a class="line-action icon-delete" onclick="$.acooly.framework.remove('/manage/epei/userFocusHospital/deleteJson.html','{0}','manage_userFocusHospital_datagrid');" href="#" title="删除"></a>
    </div>
    
    <!-- 表格的工具栏 -->
    <div id="manage_userFocusHospital_toolbar">
      <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
         onclick="$.acooly.framework.create({url:'/manage/epei/userFocusHospital/create.html',entity:'userFocusHospital',width:600,height:450})">添加</a>
      <%--<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="$.acooly.framework.removes('/manage/epei/userFocusHospital/deleteJson.html','manage_userFocusHospital_datagrid')">批量删除</a>
      <a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" onclick="$.acooly.framework.imports({url:'/manage/epei/userFocusHospital/importView.html',uploader:'manage_userFocusHospital_import_uploader_file'});">批量导入</a>
        --%>
    </div>
  </div>

</div>