<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_olog_searchform','manage_olog_datagrid');
});
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_olog_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
					操作名称:<input type="text" size="10" name="search_LIKE_actionName" value="${param.search_LIKE_actionName}"  />
					功能模块:<input type="text" size="10" name="search_LIKE_moduleName" value="${param.search_LIKE_moduleName}"  />
					操作结果:<select name="search_EQ_operateResult" editable="false" style="width: 80px;" panelHeight="auto" class="easyui-combobox"><option value="">所有</option><c:forEach var="e" items="${allOperateResults}"><option value="${e.key}" ${param.search_EQ_operateResult == e.key?'selected':''}>${e.value}</option></c:forEach></select>
					操作时间:<input id="search_GTE_operateTime" name="search_GTE_operateTime" size="10" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input id="search_LTE_operateTime" name="search_LTE_operateTime" size="10" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" /> 			
					操作员:<input type="text" size="15" name="search_LIKE_operateUser" value="${param.search_LIKE_operateUser}"  />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="$.acooly.framework.search('manage_olog_searchform','manage_olog_datagrid');">查询</a> 
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_olog_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/module/olog/olog/listJson.html" toolbar="#manage_olog_toolbar" fit="true" border="false" fitColumns="true"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
        <tr>
			<th field="id">ID</th>
            <th field="moduleName">功能模块</th>
			<th field="actionName">操作</th>
            <th field="operateUser">操作员</th>
            <th field="operateTime" formatter="formatDate">操作时间</th>
			<th field="executeMilliseconds">执行时间(ms)</th>
			<th field="requestParameters" data-options="formatter:function(value){ if(value==null)value=''; value=(value.length>48?value.substring(0,48)+' ...':value); return $('<div/>').text(value).html();}">请求参数</th>
            <th field="operateResult" data-options="formatter:function(value){ return formatRefrence('manage_olog_datagrid','allOperateResults',value);} ">结果</th>
			<th field="operateMessage">消息</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_olog_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_olog_action" style="display: none;">
      <a class="line-action icon-show" title="详情" onclick="$.acooly.framework.show('/manage/module/olog/olog/show.html?id={0}',500,350);" href="#"></a>
    </div>
    
    <!-- 表格的工具栏 -->
    <div id="manage_olog_toolbar">
      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_olog_exports_menu',iconCls:'icon-export'">批量导出</a>
      <div id="manage_olog_exports_menu" style="width:150px;">
        <div data-options="iconCls:'icon-excel'" onclick="$.acooly.framework.exports('/manage/system/olog/exportXls.html','manage_olog_searchform','操作日志')">Excel</div>  
        <div data-options="iconCls:'icon-csv'" onclick="$.acooly.framework.exports('/manage/system/olog/exportCsv.html','manage_olog_searchform','操作日志')">CSV</div> 
      </div>
    </div>
  </div>

</div>
