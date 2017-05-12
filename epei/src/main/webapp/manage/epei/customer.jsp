<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_customer_searchform','manage_customer_datagrid');
});

function customFormatActionInCustomer(actionTemplate,value,row){
    var template = $("#"+actionTemplate).clone();

    //已经锁定的话  显示解锁
    if(row['deleted'] == 1){
        template.find(".icon-lock").attr("onclick","$.acooly.framework.remove('/manage/epei/customer/recoverCustomer.html','{0}','manage_customer_datagrid','提醒','确定要解锁该用户.');");
        template.find(".icon-lock").attr("title","解除锁定");
        template.find(".icon-lock").removeClass("icon-lock").addClass("icon-key");
    }

    return formatString(template.html(), row.id);
}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_customer_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          		注册时间:<input size="15" id="search_GTE_createTime" name="search_GTE_createTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
				至<input size="15" id="search_LTE_createTime" name="search_LTE_createTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" /> 	
				姓名:<input type="text" size="15" name="search_LIKE_name" value="${param.search_LIKE_name}"  />
				账户:<input type="text" size="15" name="search_LIKE_userName" value="${param.search_LIKE_userName}"  />
				VIP会员级别:<input type="text" size="15" name="search_EQ_vipGrade" value="${param.search_EQ_vipGrade}"  />
				用户类型:<select class="easyui-combobox" name="search_LIKE_customerType"
									style="width: 90px;" data-options="panelHeight:'auto',editable:false">
							<option value="">全部</option>
                            <option value="0">普通会员</option>
                            <option value="1">医院管理员</option>
					   </select>	
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_customer_searchform','manage_customer_datagrid');">查询</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_customer_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/epei/customer/listJson.html" toolbar="#manage_customer_toolbar" fit="true" border="false" fitColumns="true"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="cusNo">会员编号</th>
			<th field="userName">账户</th>
			<th field="name">姓名</th>
			<th field="mobile">手机号</th>
			<th field="regOrigin" data-options="formatter:function(value){return value=='PC'?'网站':'微信'; }">注册来源</th>
		    <th field="createTime" formatter="formatDate">注册时间</th>
		    <th field="deleted" data-options="formatter:function(value){return value==1?'已取消':'正常';}">状态</th>
		    <th field="customerType" data-options="formatter:function(value){return value==1?'医院管理员':'普通会员'}">用户类型</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return customFormatActionInCustomer('manage_customer_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_customer_action" style="display: none;">
      <a class="line-action icon-edit" onclick="$.acooly.framework.edit({url:'/manage/epei/customer/edit.html',id:'{0}',entity:'customer',width:500,height:400});" href="#" title="编辑"></a>
      <a class="line-action icon-show" onclick="$.acooly.framework.show('/manage/epei/customer/theCustomer.html?id={0}',500,400);" href="#" title="查看"></a>
      <a class="line-action icon-lock"
         onclick="$.acooly.framework.remove('/manage/epei/customer/cancelCustomer.html','{0}','manage_customer_datagrid','提醒','取消后该用户将不能登录系统.');" href="#" title="锁定"></a>
    </div>
    
    <!-- 表格的工具栏 -->
    <div id="manage_customer_toolbar">
     <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$.acooly.framework.create({url:'/manage/epei/customer/create.html',entity:'customer',width:500,height:400})">添加</a>
      <%--<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="$.acooly.framework.removes('/manage/epei/customer/deleteJson.html','manage_customer_datagrid')">批量删除</a>
      --%>
	<a href="#" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="$.acooly.framework.exports('/manage/epei/customer/exportXls.html','manage_customer_searchform','会员')">批量导出</a>
     <a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" onclick="$.acooly.framework.imports({url:'/manage/epei/customer/importView.html',uploader:'manage_customer_import_uploader_file'});">批量导入</a>
   	</div>
  </div>

</div>
