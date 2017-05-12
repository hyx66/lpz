<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_lxyOrderBase_searchform','manage_lxyOrderBase_datagrid');
});

function orderFormatAction(actionTemplate,value,row){
	var template = $("#"+actionTemplate).clone();
	if(row['orderStatus'] == 2){
		template.find(".icon-audit-ok").attr("onclick","forbiddenAction('此订单已经预约过了')");
    }
	return formatString(template.html(), row.id);
}

function forbiddenAction(msg){
	$.messager.alert("出错了",msg,"error");
}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
	<div data-options="region:'north',border:false" style="padding: 5px; overflow: hidden;"
		align="left">
		<form id="manage_lxyOrderBase_searchform" onsubmit="return false">
			<table class="tableForm">
				<tr>
					<td align="left">
						订单编号:
						<input size="13" name="search_LIKE_orderId" value="${param.search_LIKE_orderId}"  />
						预约时间:<input size="15" id="search_GTE_orderTime" name="search_GTE_orderTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
						至<input size="15" id="search_LTE_orderTime" name="search_LTE_orderTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
						选择服务人员: 
						<!-- name更改为pzName -->
						<select name="search_LIKE_pzName" style="width:125px">
                        <option value="">全部</option>
                        <c:forEach var="lxyOrderBase" items="${lxyOrderBases}">
                        <option value="${lxyOrderBase.pzName}">${lxyOrderBase.pzName}</option>
                        </c:forEach>
                     	</select>
                     	
                     	<!-- 添加预约状态查询 -->
                     	预约状态:
                     	<select name="search_EQ_orderStatus" style="width: 125px;">
                        <option value="">全部</option>
                        <option value="1">未预约</option>
                        <option value="2">已预约</option>
                        <option value="3">已过期</option>
                     	</select>
                     	
						<a href="javascript:void(0);" class="easyui-linkbutton"
							data-options="iconCls:'icon-search',plain:true"
							onclick="$.acooly.framework.search('manage_lxyOrderBase_searchform','manage_lxyOrderBase_datagrid');">查询</a>
					</td>
				</tr>
			</table>
		</form>
	</div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_lxyOrderBase_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/epei/lxyOrderBase/listJson.html" toolbar="#manage_lxyOrderBase_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="orderId">订单编号</th>
			<th field="time">下单时间</th>
			<th field="name">用户姓名</th>
			<th field="phone">用户电话</th>
			<th field="theme">标题</th>
			<th field="hospitalName">医院</th>
			<th field="pzName">陪诊员</th>
			<th field="pzPhone">陪诊员电话</th>
			<th field="time">预约时间</th>
			<th field="total">消费总金额</th>
			<th field="orderStatus" data-options="formatter:function(value){return value==1?'未预约':(value==2?'已预约':'')}">预约状态</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return orderFormatAction('manage_lxyOrderBase_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_lxyOrderBase_action" style="display: none;">
      <a class="line-action icon-edit"
         onclick="$.acooly.framework.edit({url:'/manage/epei/lxyOrderBase/edit.html',id:'{0}',entity:'lxyOrderBase',width:600,height:450});" href="#" title="编辑"></a>
      <a class="line-action icon-show" onclick="$.acooly.framework.show('/manage/epei/lxyOrderBase/show.html?id={0}',700,520);" href="#" title="查看"></a>
    </div>
  </div>

</div>
