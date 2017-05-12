<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_customerPoints_searchform','manage_customerPoints_datagrid');
});

var acctStatus = {"0":"启用","1":"禁用"};

function customerPointFormatAction(actionTemplate,value,row){
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
    <form id="manage_customerPoints_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
					创建时间:<input size="15" id="search_GTE_createTime" name="search_GTE_createTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" id="search_LTE_createTime" name="search_LTE_createTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					会员编号:<input type="text" size="15" name="search_EQ_customerId" value="${param.search_EQ_customerId}"  /> 	
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_customerPoints_searchform','manage_customerPoints_datagrid');">查询</a>
          </td>
        </tr>
      </table>
    </form>
  </div>
  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_customerPoints_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/epei/customerPoints/listJson.html" toolbar="#manage_customerPoints_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="customerId">会员编号</th>
			<th field="capacityValue">能力值</th>
			<th field="points">总积分数</th>
			<th field="modifyTime">变更时间</th>
			<th field="status" data-options="formatter:function(value,row,index){return acctStatus[value];}">状态</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return customerPointFormatAction('manage_customerPoints_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_customerPoints_action" style="display: none;">
    <!-- 
      <a class="line-action icon-edit" onclick="$.acooly.framework.edit({url:'/manage/epei/customerPoints/edit.html',id:'{0}',entity:'customerPoints',width:600,height:450});" href="#" title="编辑"></a>
    -->
      <a class="line-action icon-no" onclick="$.acooly.framework.remove('/manage/epei/customerPoints/disableCustomerPoints.html','{0}','manage_customerPoints_datagrid','提醒','禁用后该用户账户将不可用！点击“确定”继续。');" href="#" title="禁用"></a>
      <a class="line-action icon-reload" onclick="$.acooly.framework.remove('/manage/epei/customerPoints/enableCustomerPoints.html','{0}','manage_customerPoints_datagrid','提醒','确定要重新启用该用户账户么？');" href="#" title="启用"></a>
    </div>
    
    <!-- 表格的工具栏 -->
    <div id="manage_customerPoints_toolbar">
    <!-- 
    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$.acooly.framework.create({url:'/manage/epei/customerPoints/create.html',entity:'customerPoints',width:500,height:400})">添加</a>
      -->
    <a href="#" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="$.acooly.framework.exports('/manage/epei/customerPoints/exportXls.html','manage_customerPoints_searchform','积分')">批量导出</a>
   	</div>
  </div>

</div>
