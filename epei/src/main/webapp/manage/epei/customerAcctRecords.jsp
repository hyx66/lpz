<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_customerAcctRecords_searchform','manage_customerAcctRecords_datagrid');
});
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_customerAcctRecords_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
					创建时间:<input size="15" id="search_GTE_createTime" name="search_GTE_createTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" id="search_LTE_createTime" name="search_LTE_createTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" /> 	
					用户名:<input type="text" size="15" name="search_LIKE_customerName" value="${param.search_LIKE_customerName}"/>	
					会员电话:<input type="text" size="15" name="search_LIKE_customerMobile" value="${param.search_LIKE_customerMobile}"/>
					账期:<input size="15" name="search_LIKE_dateYmd" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd'})" />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_customerAcctRecords_searchform','manage_customerAcctRecords_datagrid');">查询</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_customerAcctRecords_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/epei/customerAcctRecords/listJson.html" toolbar="#manage_customerAcctRecords_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="customerName">用户名</th>
			<th field="customerMobile">电话</th>
			<th field="title">标题</th>
			<th field="outNo">外部单号</th>
			<th field="recordsNo">流水号</th>
			<th field="dataType" data-options="formatter:function(value){return value==1?'充值':'消费'}">数据类型</th>
			<th field="rechargeAmount" data-options="formatter:function(value){return value!=0?value:''}">充值金额</th>
			<th field="rechargeChannle" data-options="formatter:function(value){return value==1?'在线充值':(value==2?'线下充值':'')}">充值渠道</th>
			<th field="rechargeStatus" data-options="formatter:function(value){return value==1?'充值成功':(value==2?'充值失败':'')}">充值状态</th>
			<th field="spendAmount" data-options="formatter:function(value){return value==0?'':value}">消费金额</th>
			<th field="spendType" data-options="formatter:function(value){return value==1?'陪护':(value==2?'陪诊':(value==3?'提现':''))}">消费类型</th>
			<th field="spendStatus" data-options="formatter:function(value){return value==1?'消费成功':(value==2?'消费失败':'')}">消费状态</th>
			<th field="spendChannel" data-options="formatter:function(value){return value==1?'在线支付':(value==2?'线下支付':'')}">消费渠道</th>	
			<th field="balance">余额</th>
			<th field="userId">管理员ID</th>
			<th field="userName">管理员</th>
			<th field="dateYmd">账期</th>
		    <th field="createTime" formatter="formatDate">创建时间</th>
		    <th field="updateTime" formatter="formatDate">更新时间</th>
		    <th field="memo">备注</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_customerAcctRecords_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_customerAcctRecords_action" style="display: none;">
      <!-- <a class="line-action icon-edit"
         onclick="$.acooly.framework.edit({url:'/manage/epei/customerAcctRecords/edit.html',id:'{0}',entity:'customerAcctRecords',width:600,height:450});" href="#" title="编辑"></a> -->
      <a class="line-action icon-show" onclick="$.acooly.framework.show('/manage/epei/customerAcctRecords/show.html?id={0}',500,400);" href="#" title="查看"></a>
    </div>
    
     <!-- 表格的工具栏 -->
    <div id="manage_customerAcctRecords_toolbar">
     <!-- <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$.acooly.framework.create({url:'/manage/epei/customerAcctRecords/create.html',entity:'customerAcctRecords',width:500,height:400})">添加</a> -->
     <a href="#" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="$.acooly.framework.exports('/manage/epei/customerAcctRecords/exportXls.html','manage_customerAcctRecords_searchform','财务明细')">批量导出</a>
   	</div>
  </div>

</div>
