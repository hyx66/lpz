<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_company_searchform','manage_company_datagrid');
});
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_company_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
				名称:<input type="text" size="15" name="search_LIKE_name" value="${param.search_LIKE_name}"  />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_company_searchform','manage_company_datagrid');">查询</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_company_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/epei/company/listJson.html" toolbar="#manage_company_toolbar" fit="true" border="false" fitColumns="true"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
        	
        	<th field="name">名称</th>
        	<th field="address">地址</th>
        	<th field="leaderName">企业负责人</th>
        	<th field="leaderPhone">企业负责人电话</th>
        	<th field="createTime">创建时间</th>
        	<th field="updateTime">修改时间</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_company_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_company_action" style="display: none;">
      <a class="line-action icon-edit" onclick="$.acooly.framework.edit({url:'/manage/epei/company/edit.html',id:'{0}',entity:'company',width:500,height:400});" href="#" title="编辑"></a>
      <a class="line-action icon-delete" onclick="$.acooly.framework.remove('/manage/epei/company/deleteJson.html','{0}','manage_company_datagrid');" href="#" title="删除"></a>
    </div>
    
    <!-- 表格的工具栏 -->
    <div id="manage_company_toolbar">
     <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$.acooly.framework.create({url:'/manage/epei/company/create.html',entity:'company',width:500,height:400})">添加</a>
   	</div>
  </div>

</div>
