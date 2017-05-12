<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_lxyPerson_searchform','manage_lxyPerson_datagrid');
});

function showLogo(value){
    return "<img width='80px' height='20px' src='${pageContext.request.contextPath}/manage/epei/lxyPerson/logo.html?id="+value+"'/>";
}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
	<div data-options="region:'north',border:false" style="padding: 5px; overflow: hidden;"
		align="left">
		<form id="manage_lxyPerson_searchform" onsubmit="return false">
			<table class="tableForm">
				<tr>
					<td align="left">
						姓名:
						<input size="13" name="search_LIKE_name" value="${param.search_LIKE_name}"  />
						电话:
						<input size="13" name="search_LIKE_phone" value="${param.search_LIKE_phone}"  />
						<a href="javascript:void(0);" class="easyui-linkbutton"
							data-options="iconCls:'icon-search',plain:true"
							onclick="$.acooly.framework.search('manage_lxyPerson_searchform','manage_lxyPerson_datagrid');">查询</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_lxyPerson_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/epei/lxyPerson/listJson.html" toolbar="#manage_lxyPerson_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="name">人员名</th>
			<th field="phone">联系方式</th>
			<th field="gender" data-options="formatter:function(value){return value==0?'女':(value==1?'男':'')}">性别</th>
			<th field="hospitalName">服务医院</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_lxyPerson_action',value,row)}">操作</th>
        </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_lxyPerson_action" style="display: none;">
      <a class="line-action icon-edit" onclick="$.acooly.framework.edit({url:'/manage/epei/lxyPerson/edit.html',id:'{0}',entity:'lxyPerson',width:600,height:450});" href="#" title="编辑"></a>
      <a class="line-action icon-delete" onclick="$.acooly.framework.remove('/manage/epei/lxyPerson/deleteJson.html','{0}','manage_lxyPerson_datagrid');" href="#" title="删除"></a>
    </div>
  	</div>

<!-- 表格的工具栏 -->
	<div id="manage_lxyPerson_toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
			onclick="$.acooly.framework.create({url:'/manage/epei/lxyPerson/create.html',entity:'lxyPerson',width:600,height:450})">添加人员 </a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
			onclick="$.acooly.framework.removes('/manage/epei/lxyPerson/deleteJson.html','manage_lxyPerson_datagrid')">批量删除</a>
		<!-- <a href="#" class="easyui-menubutton"
			data-options="menu:'#manage_lxyPerson_exports_menu',iconCls:'icon-export'">批量导出</a>
		<div id="manage_lxyPerson_exports_menu" style="width: 150px;">
			<div data-options="iconCls:'icon-excel'"
				onclick="$.acooly.framework.exports('/manage/epei/lxyPerson/exportXls.html','manage_lxyPerson_searchform','LXY_ORDER_BASE')">Excel</div>
			<div data-options="iconCls:'icon-csv'"
				onclick="$.acooly.framework.exports('/manage/epei/lxyPerson/exportCsv.html','manage_lxyPerson_searchform','LXY_ORDER_BASE')">CSV</div>
		</div>
		<a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true"
			onclick="$.acooly.framework.imports({url:'/manage/epei/lxyPerson/importView.html',uploader:'manage_lxyPerson_import_uploader_file'});">批量导入</a> -->
	</div>
	
</div>
