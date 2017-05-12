<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
	$(function() {
		$.acooly.framework.registerKeydown('manage_advertisement_searchform',
				'manage_advertisement_datagrid');
	});
	function advertisementFormatActionInAdvertisement(actionTemplate,value,row){
	    var template = $("#"+actionTemplate).clone();

	    //已经锁定的话  显示解锁
	    if(row['deleted'] == 1){
	        template.find(".icon-lock").attr("onclick","$.acooly.framework.remove('/manage/epei/advertisement/recoverAdvertisement.html','{0}','manage_advertisement_datagrid','提醒','确定要解锁该广告.');");
	        template.find(".icon-lock").attr("title","解除锁定");
	        template.find(".icon-lock").removeClass("icon-lock").addClass("icon-key");
	    }

	    return formatString(template.html(), row.id);
	}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<!-- 查询条件 -->
	<div data-options="region:'north',border:false"
		style="padding: 5px; overflow: hidden;" align="left">
		<form id="manage_advertisement_searchform" onsubmit="return false">
			<table class="tableForm" width="100%">
				<tr>
					<td align="left">
					载入时间:<input size="15" id="search_GTE_createTime" name="search_GTE_createTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" id="search_LTE_createTime" name="search_LTE_createTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" /> 	
					 <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_advertisement_searchform','manage_advertisement_datagrid');">查询</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

	<!-- 列表和工具栏 -->
	<div data-options="region:'center',border:false">
		<table id="manage_advertisement_datagrid" class="easyui-datagrid"
			url="${pageContext.request.contextPath}/manage/epei/advertisement/listJson.html"
			toolbar="#manage_advertisement_toolbar" fit="true" border="false"
			fitColumns="true" pagination="true" idField="id" pageSize="20"
			pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
			checkOnSelect="true" selectOnCheck="true">
			<thead>
				<tr>
					<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
					<th field="articleUrl" width="10%">文本地址</th>
					<th field="imageUrl" width="10%">图片地址</th>
					<th field="priority" width="10%">优先级</th>
					<th field="memo">备注</th>
					<th field="createTime" formatter="formatDate">载入时间</th>
					<th field="deleted" data-options="formatter:function(value){return value==1?'已取消':'正常';}">状态</th>
				    <th field="rowActions" data-options="formatter:function(value, row, index){return advertisementFormatActionInAdvertisement('manage_advertisement_action',value,row)}">动作</th>
				</tr>
			</thead>
		</table>

		<!-- 每行的Action动作模板 -->
		<div id="manage_advertisement_action" style="display: none;">
	      <a class="line-action icon-edit" onclick="$.acooly.framework.edit({url:'/manage/epei/advertisement/edit.html',id:'{0}',entity:'advertisement',width:500,height:400});" href="#" title="编辑"></a>
          <!-- <a class="line-action icon-show" onclick="$.acooly.framework.show('/manage/epei/advertisement/theAdvertisement.html?id={0}',500,400);" href="#" title="查看"></a> -->
          <a class="line-action icon-delete" onclick="$.acooly.framework.remove('/manage/epei/advertisement/deleteJson.html','{0}','manage_advertisement_datagrid');" href="#" title="删除"></a>
          <a class="line-action icon-lock"
         onclick="$.acooly.framework.remove('/manage/epei/advertisement/cancelAdvertisement.html','{0}','manage_advertisement_datagrid','提醒','确认后该广告将不能展示.');" href="#" title="锁定"></a>
		</div>

		<!-- 表格的工具栏 -->
		<div id="manage_advertisement_toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
				onclick="$.acooly.framework.create({url:'/manage/epei/advertisement/create.html',entity:'advertisement',width:500,height:400})">添加</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="$.acooly.framework.removes('/manage/epei/advertisement/deleteJson.html','manage_advertisement_datagrid')">批量删除</a>
     
		</div>
	</div>

</div>