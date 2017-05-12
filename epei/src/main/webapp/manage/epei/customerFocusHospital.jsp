<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_customerFocusHospital_searchform','manage_customerFocusHospital_datagrid');
});
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
<!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_customerFocusHospital_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          		会员编号:<input type="text" size="15" name="search_EQ_customerId" value="${param.search_EQ_customerId}"  />
          		医院编号:<input type="text" size="15" name="search_EQ_hospitalId" value="${param.search_EQ_hospitalId}"  />
          		科室编号:<input type="text" size="15" name="search_EQ_departmentId" value="${param.search_EQ_departmentId}"  />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_customerFocusHospital_searchform','manage_customerFocusHospital_datagrid');">查询</a>
          </td>
        </tr>
      </table>
    </form>
  </div>
  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_customerFocusHospital_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/epei/customerFocusHospital/listJson.html" toolbar="#manage_customerFocusHospital_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
       <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="customerId">会员编号</th>
			<th field="customerMobile">会员电话</th>
			<th field="hospitalId">医院编号</th>
			<th field="hospitalName">医院名称</th>
			<th field="departmentId">科室编号</th>
			<th field="departmentName">科室名称</th>
			<!-- 			 
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_customerFocusHospital_action',value,row)}">动作</th>
         	-->
        </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_customerFocusHospital_action" style="display: none;">
     </div>
    
  <!-- 表格的工具栏 -->
    <div id="manage_customerFocusHospital_toolbar">
      <%--<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="$.acooly.framework.removes('/manage/epei/customerFocusHospital/deleteJson.html','manage_customerFocusHospital_datagrid')">批量删除</a>
      --%>
      <a href="#" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="$.acooly.framework.exports('/manage/epei/customerFocusHospital/exportXls.html','manage_customerFocusHospital_searchform','会员医院记录')">批量导出</a>
   	</div>
  </div>

</div>