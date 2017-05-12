<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>

<script type="text/javascript">
	$(function() {
		$.acooly.framework.registerKeydown('manage_customerSpendReports_searchform','manage_customerSpendReports_datagrid');
	});
	
	var type = {"0":"有效"};
</script>

<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_customerSpendReports_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
					创建时间:<input size="15" id="search_GTE_createTime" name="search_GTE_createTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" id="search_LTE_createTime" name="search_LTE_createTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" /> 
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_customerSpendReports_searchform','manage_customerSpendReports_datagrid');">查询</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_customerSpendReports_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/epei/customerSpendReports/listJson.html" toolbar="#manage_customerSpendReports_toolbar" fit="true" border="false" fitColumns="true"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
        <tr>
        	<th field="dataYmd">账期</th>
        	<th field="spendType" data-options="formatter:function(value){return value==1?'陪护':''}">消费类型</th>
        	<th field="amount">总金额</th>
        	<th field="createTime" formatter="formatDate">创建时间</th>
        </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_customerSpendReports_action" style="display: none;">
    </div>
    
    <!-- 表格的工具栏 -->
    <div id="manage_customerSpendReports_toolbar">
      <a href="#" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="$.acooly.framework.exports('/manage/epei/customerSpendReports/exportXls.html','manage_customerSpendReports_searchform','消费报表')">批量导出</a>
   	</div>
  </div>

</div>
