<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_customerFamily_searchform','manage_customerFamily_datagrid');
});
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_customerFamily_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
				会员账号:<input type="text" size="15" name="search_LIKE_customerUserName" value="${param.search_LIKE_customerUserName}"  />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_customerFamily_searchform','manage_customerFamily_datagrid');">查询</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_customerFamily_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/epei/customerFamily/listJson.html" toolbar="#manage_customerFamily_toolbar" fit="true" border="false" fitColumns="true"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
        	<th field="customerUserName">会员账号</th>
        	<th field="name">姓名</th>
        	<th field="phone">电话</th>
        	<th field="idCard">身份证号</th>
        	<th field="relationship">与持卡人的关系</th>
        	<th field="createTime">创建时间</th>
        	<th field="updateTime">修改时间</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_customerFamily_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_customerFamily_action" style="display: none;">
      <a class="line-action icon-edit" onclick="$.acooly.framework.edit({url:'/manage/epei/customerFamily/edit.html',id:'{0}',entity:'customerFamily',width:500,height:400});" href="#" title="编辑"></a>
      <a class="line-action icon-delete" onclick="$.acooly.framework.remove('/manage/epei/customerFamily/deleteJson.html','{0}','manage_customerFamily_datagrid');" href="#" title="删除"></a>
    </div>
    
    <!-- 表格的工具栏 -->
    <div id="manage_customerFamily_toolbar">
     <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$.acooly.framework.create({url:'/manage/epei/customerFamily/create.html',entity:'customerFamily',width:500,height:400})">添加</a>
   	</div>
  </div>

</div>
