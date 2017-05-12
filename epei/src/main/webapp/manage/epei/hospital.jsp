<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_hospital_searchform','manage_hospital_datagrid');
});

function showLogo(value){
    return "<img width='80px' height='20px' src='${pageContext.request.contextPath}/manage/epei/hospital/logo.html?id="+value+"'/>";
}

    var serviceType = {"PZ":"陪诊","PH":"陪护","ALL":"陪诊和陪护"};
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_hospital_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
					医院名:<input type="text" size="15" name="search_LIKE_name" value="${param.search_LIKE_name}"  />
					陪诊负责人电话:<input type="text" size="15" name="search_LIKE_pzPrincipalMobile" value="${param.search_LIKE_pzPrincipalMobile}"/>
					陪护负责人电话:<input type="text" size="15" name="search_LIKE_phPrincipalMobile" value="${param.search_LIKE_phPrincipalMobile}"/>
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_hospital_searchform','manage_hospital_datagrid');">查询</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_hospital_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/epei/hospital/listJson.html" toolbar="#manage_hospital_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id">编号</th>
			<th field="name">医院名</th>
			<th field="serviceType" data-options="formatter:function(value, row, index){ return serviceType[value]}">服务类型</th>
			<th field="logoImageId" data-options="formatter:function(value, row, index){ return showLogo(value)}">医院logo</th>
            <th field="isCooperate" data-options="formatter:function(value){return value==1?'未合作':'已合作';}">合作关系</th>
			<th field="receptionPosition">接待地点</th>
			<th field="pzCusNo">陪诊负责人编号</th>
			<th field="pzPrincipalName">陪诊负责人姓名</th>
			<th field="pzPrincipalMobile">陪诊负责人电话</th>
			<th field="phCusNo">陪护负责人编号</th>
			<th field="phPrincipalName">陪护负责人姓名</th>
			<th field="phPrincipalMobile">陪护负责人电话</th>
		    <th field="createTime">创建时间</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_hospital_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_hospital_action" style="display: none;">
      <a class="line-action icon-edit"
         onclick="$.acooly.framework.edit({url:'/manage/epei/hospital/edit.html',id:'{0}',entity:'hospital',width:600,height:450});" href="#" title="编辑"></a>
      <a class="line-action icon-show" onclick="$.acooly.framework.show('/manage/epei/hospital/show.html?id={0}',500,400);" href="#" title="查看"></a>
      <a class="line-action icon-delete" onclick="$.acooly.framework.remove('/manage/epei/hospital/deleteJson.html','{0}','manage_hospital_datagrid');" href="#" title="删除"></a>
    </div>
    
    <!-- 表格的工具栏 -->
    <div id="manage_hospital_toolbar">
      <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
         onclick="$.acooly.framework.create({url:'/manage/epei/hospital/create.html',entity:'hospital',width:600,height:450})">添加</a>
      <a href="#" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="$.acooly.framework.exports('/manage/epei/hospital/exportXls.html','manage_hospital_searchform','医院')">批量导出</a>
    </div>
  </div>

</div>
