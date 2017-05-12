<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_customerAcct_searchform','manage_customerAcct_datagrid');
});

var acctStatus = {"0":"启用","1":"禁用"};

function customerAcctFormatAction(actionTemplate,value,row){
	var template = $("#"+actionTemplate).clone();
	if(row['status'] == 1){
		template.find(".icon-edit").attr("onclick","forbiddenAction('只有启用状态的账户才能进行此操作！')");
	}
	if(row['status'] == 0){
		template.find(".icon-reload").attr("onclick","forbiddenAction('该账户已经启用!')");
	}
	if(row['status'] == 1){
		template.find(".icon-no").attr("onclick","forbiddenAction('该账户已经被禁用!')");
	}
	return formatString(template.html(), row.id);
}

function forbiddenAction(msg){
	$.messager.alert("出错了",msg,"error");
}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
<!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_customerAcct_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
			会员编号:<input type="text" size="15" name="search_EQ_customerId" value="${param.search_EQ_customerId}"  />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_customerAcct_searchform','manage_customerAcct_datagrid');">查询</a>
          </td>
        </tr>
      </table>
    </form>
  </div>
  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_customerAcct_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/epei/customerAcct/listJson.html" toolbar="#manage_customerAcct_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="customerId">会员编号</th>
			<th field="totalAmount">充值总额</th>
			<th field="balance">财务余额</th>
			<th field="availableAmount">可用金额</th>
			<th field="freezAmount">冻结金额</th>
			<th field="updateTime">变更时间</th>
			<th field="status" data-options="formatter:function(value,row,index){return acctStatus[value];}">状态</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return customerAcctFormatAction('manage_customerAcct_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_customerAcct_action" style="display: none;">
      <!-- <a class="line-action icon-edit" onclick="$.acooly.framework.edit({url:'/manage/epei/customerAcct/edit.html',id:'{0}',entity:'customerAcct',width:600,height:450});" href="#" title="编辑"></a> -->
      <a class="line-action icon-no" onclick="$.acooly.framework.remove('/manage/epei/customerAcct/disableCustomerAcct.html','{0}','manage_customerAcct_datagrid','提醒','禁用后该用户账户将不可用！点击“确定”继续。');" href="#" title="禁用"></a>
      <a class="line-action icon-reload" onclick="$.acooly.framework.remove('/manage/epei/customerAcct/enableCustomerAcct.html','{0}','manage_customerAcct_datagrid','提醒','确定要重新启用该用户账户么？');" href="#" title="启用"></a>
    </div>
    
    <!-- 表格的工具栏 -->
    <div id="manage_customerAcct_toolbar">
      </div>
  </div>

</div>