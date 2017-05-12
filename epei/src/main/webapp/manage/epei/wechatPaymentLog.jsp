<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_wechatPaymentLog_searchform','manage_wechatPaymentLog_datagrid');
});
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
<!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_wechatPaymentLog_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
			外部单号:<input type="text" size="20" name="search_LIKE_dataOne" value="${param.search_LIKE_dataOne}"/>	
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_wechatPaymentLog_searchform','manage_wechatPaymentLog_datagrid');">查询</a>
          </td>
        </tr>
      </table>
    </form>
  </div>
  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_wechatPaymentLog_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/epei/wechatPaymentLog/listJson.html" toolbar="#manage_wechatPaymentLog_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
       <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="title">标题</th>
			<th field="dataOne">外部单号</th>
			<th field="dataTwo">流水号</th>
			<th field="amount">金额</th>
			<th field="content">内容</th>
			<th field="userId">操作员ID</th>
			<th field="userName">操作员姓名</th>
			<th field="userType" data-options="formatter:function(value){return value==1?'管理员':(value==2?'会员':'系统')}">操作员类型</th>
        </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_wechatPaymentLog_action" style="display: none;">
     </div>
    
  <!-- 表格的工具栏 -->
    <div id="manage_wechatPaymentLog_toolbar">
      <a href="#" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="$.acooly.framework.exports('/manage/epei/wechatPaymentLog/exportXls.html','manage_wechatPaymentLog_searchform','会员医院记录')">批量导出</a>
   	</div>
  </div>

</div>