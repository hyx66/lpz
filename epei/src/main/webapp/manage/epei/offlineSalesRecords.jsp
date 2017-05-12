<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
	$(function(){
		$.acooly.framework.registerKeydown('manage_offlineSalesRecords_searchform','manage_offlineSalesRecords_datagrid');
	});
	
	var osrDataType = {"0":"陪诊","1":"陪护"};
	var osrPayType = {"0":"现金","1":"POS机","2":"支票"};
	var osrStatus = {"0":"初始","1":"有效","2":"无效"};
	
	function offlineSalesRecordsFormatAction(actionTemplate,value,row){
		var template = $("#"+actionTemplate).clone();
		if(row['status'] !=0){
			template.find(".icon-edit").attr("onclick","forbiddenAction('只有初始状态的消费记录才允许修改操作')");
			template.find(".icon-audit-ok").attr("onclick","forbiddenAction('只有初始状态的消费记录才允许确认操作')");
			template.find(".icon-no").attr("onclick","forbiddenAction('只有初始状态的消费记录才允许无效操作')");
		}
		if(row['status'] ==1){
			template.find(".icon-audit-ok").attr("onclick","forbiddenAction('该消费记录已确认')");
		}
		if(row['status'] !=2){
			template.find(".icon-reload").attr("onclick","forbiddenAction('只有无效状态的消费记录才允许重启用操作')");
			template.find(".icon-delete").attr("onclick","forbiddenAction('只有无效状态的消费记录才允许删除操作')");
		}
		return formatString(template.html(), row.id);
	}
	
	function forbiddenAction(msg){
	    $.messager.alert("出错了",msg,"error");
	}
	
	var osrConfirmPanel;
	function confirmOfflineSalesRecords(id){
		osrConfirmPanel = $('<div/>').dialog({
			href : "/manage/epei/offlineSalesRecords/goOfflineSalesRecordsConfirm.html?id="+id,
			method : "get",
			width : 600,
			height : 400,
			modal : true,
			title : "线下消费记录确认",
			buttons : [{
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function(){
					var d = $(this).closest('.window-body');
					d.dialog('close');
				}
			},{
				text : '确认',
				iconCls : 'icon-ok',
				handler : function(){
					if($("#osrConfirmForm").form("validate")){
						$.post("/manage/epei/offlineSalesRecords/offlineSalesRecordsConfirm.html",$("#osrConfirmForm").serialize(),function(result){
	                        if(result.success){
	                            try{
	                                $(osrConfirmPanel).dialog('close');
	                                //刷新列表
	                                $("#manage_offlineSalesRecords_datagrid").datagrid("reload");
	                                $.messager.alert("提示",result.message,"info");
	                            }catch (err){
	                                console.error(err);
	                            }
	                        }else{
	                            $.messager.alert("出错了",result.message,"error");
	                        }

	                    });
					}
				}
			}],
			onClose : function() {
	            $(this).dialog('destroy');
	        }
		});
	}
</script>

<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_offlineSalesRecords_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
         	 账期:<input size="15" id="search_GTE_dataYmd" name="search_GTE_dataYmd" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd'})" />
			至<input size="15" id="search_LTE_dataYmd" name="search_LTE_dataYmd" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd'})" /> 	
			服务开始时间:<input size="15" id="search_GTE_serviceYmd" name="search_GTE_serviceYmd" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
			至<input size="15" id="search_LTE_serviceYmd" name="search_LTE_serviceYmd" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
			服务结束时间:<input size="15" id="search_GTE_serviceEndYmd" name="search_GTE_serviceEndYmd" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
			至<input size="15" id="search_LTE_serviceEndYmd" name="search_LTE_serviceEndYmd" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" /><br>
			客户姓名:<input type="text" size="15" name="search_LIKE_customerName" value="${param.search_LIKE_customerName}"/>
			客户手机号:<input type="text" size="15" name="search_LIKE_customerMobile" value="${param.search_LIKE_customerMobile}"/>
			所属医院:<input type="text" size="15" name="search_LIKE_hospital" value="${param.search_LIKE_hospital}"/>
			所属科室:<input type="text" size="15" name="search_LIKE_departName" value="${param.search_LIKE_departName}"/>
			护工电话:<input type="text" size="15" name="search_LIKE_servicePersonMobile" value="${param.search_LIKE_servicePersonMobile}"/>						
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_offlineSalesRecords_searchform','manage_offlineSalesRecords_datagrid');">查询</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_offlineSalesRecords_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/epei/offlineSalesRecords/listJson.html" 
    	toolbar="#manage_offlineSalesRecords_toolbar" fit="true" border="false" fitColumns="false" border="false"
		pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" 
		sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id">编号</th>
			<th field="dataType" data-options="formatter:function(value,row,index){return osrDataType[value];}">数据类型</th>
			<th field="customerName">客户姓名</th>
			<th field="customerMobile">客户手机号</th>
			<th field="customerSex" data-options="formatter:function(value){return value==1?'男':(value==0?'女':'')}">性别</th>
			<th field="customerAge">年龄</th>
			<th field="hospital">所属医院</th>
			<th field="departName">科室名称</th>
			<th field="servicePersonName">服务人员姓名</th>
			<th field="servicePersonMobile">服务人员手机号</th>
			<th field="serviceYmd" formatter="formatDate" sortable="true">服务开始时间</th>
			<th field="serviceEndYmd" formatter="formatDate" sortable="true">服务结束时间</th>
			<th field="serviceDays">服务总天数</th>
			<th field="customerBed">床位</th>
			<th field="amount">金额</th>
			<th field="payeeName">收款人</th>
			<th field="ticketNo">小/支票号</th>
			<th field="payType" data-options="formatter:function(value,row,index){return osrPayType[value];}">支付类型</th>
			<th field="dataYmd">账期</th>
			<th field="outNo">外部单号</th>
			<th field="createName">录入人员</th>
			<th field="updateName">修改人员</th>
			<th field="memo">备注</th>
			<th field="recordsNo">流水号</th>
		    <th field="createTime" sortable="true">创建时间</th>
		    <th field="updateTime" sortable="true">修改时间</th>
		    <th field="status" data-options="formatter:function(value,row,index){return osrStatus[value];}">数据状态</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return offlineSalesRecordsFormatAction('manage_offlineSalesRecords_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_offlineSalesRecords_action" style="display: none;">
      <a class="line-action icon-edit" onclick="$.acooly.framework.edit({url:'/manage/epei/offlineSalesRecords/edit.html',id:'{0}',entity:'offlineSalesRecords',width:500,height:400});" href="#" title="编辑"></a>
      <a class="line-action icon-audit-ok" onclick="confirmOfflineSalesRecords('{0}')" href="#" title="确认"></a>
      <a class="line-action icon-no" onclick="$.acooly.framework.remove('/manage/epei/offlineSalesRecords/cancelOfflineSalesRecords.html','{0}','manage_offlineSalesRecords_datagrid','提醒','次操作会使交易记录无效化.');" href="#" title="无效"></a>
      <a class="line-action icon-reload" onclick="$.acooly.framework.remove('/manage/epei/offlineSalesRecords/cancelOfflineSalesRecords.html','{0}','manage_offlineSalesRecords_datagrid','提醒','确定重启用该条交易记录吗？');" href="#" title="重启用"></a>
      <a class="line-action icon-show" onclick="$.acooly.framework.show('/manage/epei/offlineSalesRecords/show.html?id={0}',600,400);" href="#" title="查看"></a>
      <a class="line-action icon-delete" onclick="$.acooly.framework.remove('/manage/epei/offlineSalesRecords/deleteJson.html','{0}','manage_offlineSalesRecords_datagrid');" href="#" title="删除"></a>
    </div>
    
    <!-- 表格的工具栏 -->
    <div id="manage_offlineSalesRecords_toolbar">
      <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$.acooly.framework.create({url:'/manage/epei/offlineSalesRecords/create.html',entity:'offlineSalesRecords',width:500,height:400})">添加</a> 
      <%--<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="$.acooly.framework.removes('/manage/epei/offlineSalesRecords/deleteJson.html','manage_offlineSalesRecords_datagrid')">批量删除</a> --%>
      <a href="#" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="$.acooly.framework.exports('/manage/epei/offlineSalesRecords/exportXls.html','manage_offlineSalesRecords_searchform','补录消费记录')">批量导出</a>
      <a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" onclick="$.acooly.framework.imports({url:'/manage/epei/offlineSalesRecords/importView.html',uploader:'manage_offlineSalesRecords_import_uploader_file'});">批量导入</a>
      <a href="#" class="easyui-linkbutton" iconCls="icon-audit-ok" plain="true" onclick="$.acooly.framework.removes('/manage/epei/offlineSalesRecords/confirmJson.html','manage_offlineSalesRecords_datagrid')">批量确认</a> 
      <a href="#" class="easyui-linkbutton" iconCls="icon-no" plain="true" onclick="$.acooly.framework.removes('/manage/epei/offlineSalesRecords/invalidJson.html','manage_offlineSalesRecords_datagrid')">批量无效</a> 
      <a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="$.acooly.framework.removes('/manage/epei/offlineSalesRecords/reuseJson.html','manage_offlineSalesRecords_datagrid')">批量重启用</a> 
      <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="$.acooly.framework.removes('/manage/epei/offlineSalesRecords/deleteJson.html','manage_offlineSalesRecords_datagrid')">批量删除</a> 
    </div>
  </div>

</div>
