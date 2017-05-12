<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>

<script type="text/javascript">
	$(function(){
		$.acooly.framework.registerKeydown('manage_offlineSalesAcctSta_searchform','manage_offlineSalesAcctSta_datagrid');
	});
	
	var osasStatus = {"0":"初始","1":"未对账","2":"已对账"};
	
	function offlineSalesAcctStaFormatAction(actionTemplate,value,row){
		var template = $("#"+actionTemplate).clone();
		if(row['status'] != 0){
			template.find(".icon-edit").attr("onclick","forbiddenAction('只有初始状态才可补录入操作！')");
		}
		if(row['status'] != 1){
			template.find(".icon-audit-ok").attr("onclick","forbiddenAction('只有未对账状态才可确认操作!')");
		}
		if(row['status'] == 2){
			template.find(".icon-audit-ok").attr("onclick","forbiddenAction('该对账记录已对账!')");
			template.find(".icon-delete").attr("onclick","forbiddenAction('对账后的记录不能删除')");
		}
		return formatString(template.html(), row.id);
	}
	
	function forbiddenAction(msg){
		$.messager.alert("出错了",msg,"error");
	}
	
	var osasConfirmPanel;
	function confirmofflineSalesAcctSta(id){
		osasConfirmPanel = $('<div/>').dialog({
			href : "/manage/epei/offlineSalesAcctSta/goofflineSalesAcctStaConfirm.html?id="+id,
			method : "get",
			width :	600 ,
			height : 400 ,
			modal : true ,
			title : "入账确认",
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
					if($("#osasConfirmForm").form("validate")){
						$.post("/manage/epei/offlineSalesAcctSta/offlineSalesAcctStaConfirm.html",$("#osasConfirmForm").serialize(),function(result){
	                        if(result.success){
	                            try{
	                                $(osasConfirmPanel).dialog('close');
	                                //刷新列表
	                                $("#manage_offlineSalesAcctSta_datagrid").datagrid("reload");
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
    <form id="manage_offlineSalesAcctSta_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
          		账期:<input size="15" id="search_GTE_acctYmd" name="search_GTE_acctYmd" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd'})" />
				至<input size="15" id="search_LTE_acctYmd" name="search_LTE_acctYmd" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd'})" /> 	
          		医院名称:<input type="text" size="15" name="search_LIKE_hospitalName" value="${param.search_LIKE_hospitalName}"  />
          		科室名称:<input type="text" size="15" name="search_LIKE_departmentName" value="${param.search_LIKE_departmentName}"  />
                                                   统计范围：
                 <select class="easyui-combobox" name="search_EQ_countScope" value="${param.search_EQ_countScope}"   data-options="panelHeight:'auto'">
                     <option value="">请选择</option>
                     <option value="1">所有医院</option>
                     <option value="2">单个医院</option>
                     <option value="3">单个科室</option>
                 </select>
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_offlineSalesAcctSta_searchform','manage_offlineSalesAcctSta_datagrid');">查询</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_offlineSalesAcctSta_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/epei/offlineSalesAcctSta/listJson.html" 
    	toolbar="#manage_offlineSalesAcctSta_toolbar" fit="true" border="false" fitColumns="false" border="false"
		pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" 
		sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="id">编号</th>
			<th field="acctYmd">账期</th>
			<th field="countScope" data-options="formatter:function(value){return value==1?'所有':(value==2?'医院':'科室')}">统计范围</th>
			<th field="orderType" data-options="formatter:function(value){return value==1?'陪诊':(value==2?'陪护':'全部')}">统计类型</th>
			<th field="dataCount">统计数目</th>
			<th field="hospitalName">医院名称</th>
			<th field="departmentName">科室名称</th>
			<th field="offlineAmount">入账总金额</th>
			<th field="acctAmount">实账总金额</th>
			<th field="diffAmount">相差金额</th>
			<th field="amount">确认总金额</th>
			<th field="createName">录入人员</th>
			<th field="createMemo">录入备注</th>
			<th field="acctName">对账人员</th>
			<th field="acctMemo">对账备注</th>
			<th field="acctTime">对账日期</th>
		    <th field="status" data-options="formatter:function(value,row,index){return osasStatus[value];}">状态</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return offlineSalesAcctStaFormatAction('manage_offlineSalesAcctSta_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_offlineSalesAcctSta_action" style="display: none;">
      <a class="line-action icon-edit" onclick="$.acooly.framework.edit({url:'/manage/epei/offlineSalesAcctSta/edit.html',id:'{0}',entity:'offlineSalesAcctSta',width:500,height:400});" href="#" title="补录入"></a>   
      <c:if test="${empty userFocusHospital}">
      <a class="line-action icon-audit-ok" onclick="confirmofflineSalesAcctSta('{0}')" href="#" title="对账"></a>
      </c:if>
      <a class="line-action icon-show" onclick="$.acooly.framework.show('/manage/epei/offlineSalesAcctSta/show.html?id={0}',600,400);" href="#" title="查看"></a>
      <a class="line-action icon-delete" onclick="$.acooly.framework.remove('/manage/epei/offlineSalesAcctSta/deleteJson.html','{0}','manage_offlineSalesAcctSta_datagrid');" href="#" title="删除"></a>
    </div>
    
    <!-- 表格的工具栏 -->
    <div id="manage_offlineSalesAcctSta_toolbar">
      <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$.acooly.framework.create({url:'/manage/epei/offlineSalesAcctSta/create.html',entity:'offlineSalesAcctSta',width:500,height:400})">录入</a> 
      <a href="#" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="$.acooly.framework.exports('/manage/epei/offlineSalesAcctSta/exportXls.html','manage_offlineSalesAcctSta_searchform','线下消费入账统计')">批量导出</a>
      <a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" onclick="$.acooly.framework.imports({url:'/manage/epei/offlineSalesAcctSta/importView.html',uploader:'manage_offlineSalesAcctSta_import_uploader_file'});">批量导入</a> 
    </div>
  </div>

</div>