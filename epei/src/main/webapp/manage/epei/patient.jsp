<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_patient_searchform','manage_patient_datagrid');
});
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_patient_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          注册时间:<input size="15" id="search_GTE_createTime" name="search_GTE_createTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" id="search_LTE_createTime" name="search_LTE_createTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					姓名:<input type="text" size="15" name="search_LIKE_name" value="${param.search_LIKE_name}"  />
					身份证号:<input type="text" size="15" name="search_LIKE_idCard" value="${param.search_LIKE_idCard}"  />	 			
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_patient_searchform','manage_patient_datagrid');">查询</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_patient_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/epei/patient/listJson.html" toolbar="#manage_patient_toolbar" fit="true" border="false" fitColumns="true"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="cusNo">会员编号</th>
			<th field="cusName">会员姓名</th>
			<th field="name">姓名</th>
			<th field="mobile">手机号</th>
			<th field="idCard">身份证号</th>
			<th field="birthday">生日</th>
			<th field="gender" data-options="formatter:function(value){ if(value=='1'){return '男';} return '女';}">性别</th>
			<th field="medicareCard">医保卡号</th>
		    <th field="createTime" formatter="formatDate">注册时间</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_patient_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_patient_action" style="display: none;">
      <a class="line-action icon-edit" onclick="$.acooly.framework.edit({url:'/manage/epei/patient/edit.html',id:'{0}',entity:'patient',width:500,height:400});" href="#" title="编辑"></a>
      <a class="line-action icon-show" onclick="$.acooly.framework.show('/manage/epei/patient/show.html?id={0}',500,400);" href="#" title="查看"></a>
      <a class="line-action icon-delete" onclick="$.acooly.framework.remove('/manage/epei/patient/deleteJson.html','{0}','manage_patient_datagrid');" href="#" title="删除"></a>
    </div>
    
    <!-- 表格的工具栏 -->
    <div id="manage_patient_toolbar">
      <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$.acooly.framework.create({url:'/manage/epei/patient/create.html',entity:'patient',width:500,height:400})">添加</a>
      <%--<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="$.acooly.framework.removes('/manage/epei/patient/deleteJson.html','manage_patient_datagrid')">批量删除</a>
      --%>
		<a href="#" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="$.acooly.framework.exports('/manage/epei/patient/exportXls.html','manage_patient_searchform','病患')">批量导出</a>
      <%--<a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" onclick="$.acooly.framework.imports({url:'/manage/epei/patient/importView.html',uploader:'manage_patient_import_uploader_file'});">批量导入</a>
   --%> </div>
  </div>

</div>
