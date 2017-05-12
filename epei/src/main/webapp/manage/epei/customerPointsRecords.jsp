<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_customerPointsRecords_searchform','manage_customerPointsRecords_datagrid');
});
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_customerPointsRecords_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
					创建时间:<input size="15" id="search_GTE_createTime" name="search_GTE_createTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" id="search_LTE_createTime" name="search_LTE_createTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" /> 	
					用户名:<input type="text" size="15" name="search_LIKE_customerName" value="${param.search_LIKE_customerName}"/>	
					会员电话:<input type="text" size="15" name="search_LIKE_customerMobile" value="${param.search_LIKE_customerMobile}"/>
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_customerPointsRecords_searchform','manage_customerPointsRecords_datagrid');">查询</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_customerPointsRecords_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/epei/customerPointsRecords/listJson.html" toolbar="#manage_customerPointsRecords_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="customerName">用户名</th>
			<th field="customerMobile">电话</th>
			<th field="title">标题</th>
			<th field="outNo">外部单号</th>
			<th field="recordsNo">流水号</th>
			<th field="dataType" data-options="formatter:function(value){return value==1?'收益':'支出'}">数据类型</th>
			<th field="points">积分数</th>
			<th field="spendType" data-options="formatter:function(value){return value==1?'陪护':(value==2?'陪诊':'')}">消费类型</th>
		    <th field="createTime" formatter="formatDate">创建时间</th>
		    <th field="updateTime" formatter="formatDate">更新时间</th>
		    <th field="memo">备注</th>
         </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_customerPointsRecords_action" style="display: none;">
      <a class="line-action icon-edit"
         onclick="$.acooly.framework.edit({url:'/manage/epei/customerPointsRecords/edit.html',id:'{0}',entity:'customerPointsRecords',width:600,height:450});" href="#" title="编辑"></a>
      <a class="line-action icon-show" onclick="$.acooly.framework.show('/manage/epei/customerPointsRecords/show.html?id={0}',500,400);" href="#" title="查看"></a>
    </div>
    
   <!-- 表格的工具栏 -->
    <div id="manage_customerPointsRecords_toolbar">
     <a href="#" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="$.acooly.framework.exports('/manage/epei/customerPointsRecords/exportXls.html','manage_customerPointsRecords_searchform','积分明细')">批量导出</a>
   	</div>
  </div>

</div>
