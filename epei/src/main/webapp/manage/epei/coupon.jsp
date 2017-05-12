<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>

<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_coupon_searchform','manage_coupon_datagrid');
});
    var couponType = {"FIRST_FREE":"首单免费"};
    var couponTarget = {"PZ":"陪诊","PH":"陪护","ALL":"通用"};

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_coupon_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
					优惠券类型:
                    <select class="easyui-combobox" name="search_LIKE_couponType" value="${param.search_LIKE_couponType}" data-options="panelHeight:'auto'">
                        <option value="">请选择</option>
                        <option value="FIRST_FREE">首单免费</option>
                    </select>
					优惠券使用目标:
                  <select class="easyui-combobox" name="search_LIKE_useTarget" value="${param.search_LIKE_useTarget}"   data-options="panelHeight:'auto'">
                      <option value="">请选择</option>
                      <option value="PH">陪护</option>
                      <option value="PZ">陪诊</option>
                      <option value="ALL">通用</option>
                  </select>
					所属会员:<input type="text" size="15" name="search_EQ_customerUserName" value="${param.search_EQ_customerUserName}"  />
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_coupon_searchform','manage_coupon_datagrid');">查询</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_coupon_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/epei/coupon/listJson.html" <%--toolbar="#manage_coupon_toolbar" --%>fit="true" border="false" fitColumns="true"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id">ID</th>
			<th field="couponType" data-options="formatter:function(value){return couponType[value];}">优惠券类型</th>
			<th field="useTarget" data-options="formatter:function(value){return couponTarget[value];}">优惠券使用目标</th>
			<th field="customerUserName">所属会员</th>
			<th field="used" data-options="formatter:function(value){return value == 1?'已使用':'未使用';}">是否已使用</th>
		    <th field="useTime" >使用时间</th>
		    <th field="orderNo">消费订单</th>
			<th field="denomination">面额</th>
		    <th field="expiryDate" formatter="formatDate">到期日</th>
		    <th field="createTime">生成时间</th>
          	<%--<th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_coupon_action',value,row)}">动作</th>
       --%> </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_coupon_action" style="display: none;">
     <%-- <a class="line-action icon-edit" onclick="$.acooly.framework.edit({url:'/manage/epei/coupon/edit.html',id:'{0}',entity:'coupon',width:500,height:400});" href="#" title="编辑"></a>
      <a class="line-action icon-show" onclick="$.acooly.framework.show('/manage/epei/coupon/show.html?id={0}',500,400);" href="#" title="查看"></a>--%>
     <%-- <a class="line-action icon-delete" onclick="$.acooly.framework.remove('/manage/epei/coupon/deleteJson.html','{0}','manage_coupon_datagrid');" href="#" title="删除"></a>
   --%> </div>
    
    <!-- 表格的工具栏 -->
    <div id="manage_coupon_toolbar">
      <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$.acooly.framework.create({url:'/manage/epei/coupon/create.html',entity:'coupon',width:500,height:400})">添加</a>
      <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="$.acooly.framework.removes('/manage/epei/coupon/deleteJson.html','manage_coupon_datagrid')">批量删除</a>
      <a href="#" class="easyui-menubutton" data-options="menu:'#manage_coupon_exports_menu',iconCls:'icon-export'">批量导出</a>
      <div id="manage_coupon_exports_menu" style="width:150px;">
        <div data-options="iconCls:'icon-excel'" onclick="$.acooly.framework.exports('/manage/epei/coupon/exportXls.html','manage_coupon_searchform','EP_COUPON')">Excel</div>  
        <div data-options="iconCls:'icon-csv'" onclick="$.acooly.framework.exports('/manage/epei/coupon/exportCsv.html','manage_coupon_searchform','EP_COUPON')">CSV</div> 
      </div>
      <a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" onclick="$.acooly.framework.imports({url:'/manage/epei/coupon/importView.html',uploader:'manage_coupon_import_uploader_file'});">批量导入</a> 
    </div>
  </div>

</div>
