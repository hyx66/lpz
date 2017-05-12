<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_enchashmentApply_searchform','manage_enchashmentApply_datagrid');
});

function orderFormatAction(actionTemplate,value,row){
	var template = $("#"+actionTemplate).clone();
	if(row['status'] != 1){
		template.find(".icon-ok").attr("onclick","forbiddenAction('此申请已审批过了！')");
    }
	return formatString(template.html(), row.id);
}

function forbiddenAction(msg){
	$.messager.alert("出错了",msg,"error");
}

function confirmenchashmentApply(id){
    enchashmentApplyConfirmPanel = $('<div/>').dialog({
        href :"/manage/epei/enchashmentApply/goEnchashmentApplyConfirm.html?id="+id,
        method:'get',
        width : 700,
        height : 520,
        modal : true,
        title : "提现审批",
        buttons : [{
            text : '关闭',
            iconCls : 'icon-cancel',
            handler : function() {
                var d = $(this).closest('.window-body');
                d.dialog('close');
            }
        },{
            text : '确认',
            iconCls : 'icon-ok',
            handler : function(){
                if($("#enchashmentApplyConfirmForm").form("validate")){
                	$.post("/manage/epei/enchashmentApply/enchashmentApplyConfirm.html",$("#enchashmentApplyConfirmForm").serialize(),function(result){
                        if(result.success){
                            try{
                                $(enchashmentApplyConfirmPanel).dialog('close');
                                //刷新列表
                                $("#manage_enchashmentApply_datagrid").datagrid("reload");
                                $.messager.alert("提示",result.message,"info");
                            }
                            catch (err){
                                console.error(err);
                            }
                        }
                        else{
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
    <form id="manage_enchashmentApply_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
					申请时间:<input size="15" id="search_GTE_createTime" name="search_GTE_createTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
					至<input size="15" id="search_LTE_createTime" name="search_LTE_createTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" /> 	
					申请账号:<input type="text" size="15" name="search_LIKE_customerUserName" value="${param.search_LIKE_customerUserName}"/>	
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_enchashmentApply_searchform','manage_enchashmentApply_datagrid');">查询</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_enchashmentApply_datagrid" class="easyui-datagrid" url="${pageContext.request.contextPath}/manage/epei/enchashmentApply/listJson.html" toolbar="#manage_enchashmentApply_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
      <thead>
        <tr>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="applyNo">流水号</th>
			<th field="customerUserName">申请账号</th>
			<th field="createTime" formatter="formatDate">申请时间</th>
			<th field="amount">金额</th>
			<th field="explain">说明</th>
			<th field="status" data-options="formatter:function(value){return value==1?'未审批':'审批通过'}">申请状态</th>	
			<th field="AuditorSign">审批人</th>
			<th field="endTime">审批时间</th>
		    <th field="memo">备注</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return orderFormatAction('manage_enchashmentApply_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_enchashmentApply_action" style="display: none;">
 	<a class="line-action icon-ok" onclick="confirmenchashmentApply('{0}')"href="#" title="审批"></a>
    </div>
  </div>

</div>
