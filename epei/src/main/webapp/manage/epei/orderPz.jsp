<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
$(function() {
	$.acooly.framework.registerKeydown('manage_orderPz_searchform','manage_orderPz_datagrid');
});

var originData = {"PC":"电脑","WECHAT":"微信","FZZ":"400"};
var orderState = {"ORDERED":"预约","CANCELED":"取消","CONFIRMED":"确认","FINISHED":"完成"};
var pzModel = {"normal":"普通陪诊","special":"专一陪诊"};
var orderpayType = {"1":"POS机","2":"支票","3":"现金","4":"在线支付","5":"企业二维码"};

var orderFinishedPanel;

function orderFormatAction(actionTemplate,value,row){
	var template = $("#"+actionTemplate).clone();
	if(row['payStatus'] == '1'){
		template.find(".icon-delete").attr("onclick","forbiddenAction('该订单已经付款，无法取消')");
    	template.find(".icon-edit").attr("onclick","forbiddenAction('该订单已经付款，无法修改')");
    }
	if(row['state'] == 'ORDERED'){
    	template.find(".icon-yes").attr("onclick","forbiddenAction('只有确认后的订单才能完成')");
    }
    if(row['state'] == 'CANCELED'){
    	template.find(".icon-audit-ok").attr("onclick","forbiddenAction('取消后的订单不能被确认')");
    	template.find(".icon-delete").attr("onclick","forbiddenAction('该订单已经取消，请勿重复操作')");
    	template.find(".icon-edit").attr("onclick","forbiddenAction('取消后的订单不能被编辑')");
    	template.find(".icon-yes").attr("onclick","forbiddenAction('只有确认后的订单才能完成')");
    }
    if(row['state'] == 'CONFIRMED'){
    	template.find(".icon-audit-ok").attr("onclick","forbiddenAction('该订单已经确认，请勿重复操作')");
    	template.find(".icon-delete").attr("onclick","forbiddenAction('确认后的订单不能被取消')");
    	template.find(".icon-edit").attr("onclick","forbiddenAction('确认后的订单不能被编辑')");
    }
    if(row['state'] == 'FINISHED'){
    	template.find(".icon-audit-ok").attr("onclick","forbiddenAction('完成后的订单不能被确认')");
    	template.find(".icon-delete").attr("onclick","forbiddenAction('完成后的订单不能被取消')");
    	template.find(".icon-edit").attr("onclick","forbiddenAction('完成后的订单不能被编辑')");
    	template.find(".icon-yes").attr("onclick","forbiddenAction('该订单已经完成，请勿重复操作')");
    }
	return formatString(template.html(), row.id);
}

function forbiddenAction(msg){
	$.messager.alert("出错了",msg,"error");
}

function finishedPzOrder(id){
    orderFinishedPanel = $('<div/>').dialog({
        href :"/manage/epei/orderPz/goOrderPzFinished.html?id="+id,
        width : 700,
        height : 520,
        modal : true,
        title : "陪诊完成",
        buttons : [{
            text : '关闭',
            iconCls : 'icon-cancel',
            handler : function() {
                var d = $(this).closest('.window-body');
                d.dialog('close');
            }
        },{
            text : '完成',
            iconCls : 'icon-ok',
            handler : function(){
                $.post("/manage/epei/orderPz/orderPzFinished.html",$("#orderPzDetailsForm").serialize(),function(result){
                    if(result.success){
                        try{
                            $(orderFinishedPanel).dialog('close');
                            //刷新列表
                            $("#manage_orderPz_datagrid").datagrid("reload");
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
        }],
        onClose : function() {
            $(this).dialog('destroy');
        }
    });
}

function confirmPzOrder(id){
    orderPzConfirmPanel = $('<div/>').dialog({
        href :"/manage/epei/orderPz/goOrderPzConfirm.html?id="+id,
        method:'get',
        width : 700,
        height : 520,
        modal : true,
        title : "陪诊确认",
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
                if($("#orderPzConfirmForm").form("validate")){
                    $.post("/manage/epei/orderPz/orderPzConfirm.html",$("#orderPzConfirmForm").serialize(),function(result){
                        if(result.success){
                            try{
                                $(orderPzConfirmPanel).dialog('close');
                                //刷新列表
                                $("#manage_orderPz_datagrid").datagrid("reload");
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

function markFirstFree(index,row){
    if(row["payMode"] == "FIRST_FREE"){
        return 'background-color:#00FF66;';
    }
}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <!-- 查询条件 -->
  <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
    <form id="manage_orderPz_searchform" onsubmit="return false">
      <table class="tableForm" width="100%">
        <tr>
          <td align="left">
              预约时间:<input size="15" id="search_GTE_orderTime" name="search_GTE_orderTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
              至<input size="15" id="search_LTE_orderTime" name="search_LTE_orderTime" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
              患者电话:<input type="text" size="15" name="search_LIKE_patientMobile" value="${param.search_LIKE_patientMobile}"  />
              医院: <select name="search_LIKE_hospitalName" class="easyui-combobox"  data-options="panelHeight:'auto',editable:false"
                          >
                          <option value="">全部</option>
                          <c:forEach var="hospital" items="${hospitals}">
                              <option value="${hospital.name}">${hospital.name}</option>
                          </c:forEach>
                     </select>
               受理状态:<select class="easyui-combobox" name="search_EQ_acceptance" value="${param.search_EQ_acceptance}"
                    style="width: 60px;" data-options="panelHeight:'auto',editable:false">
                   <option value="">全部</option>
                   <option value="1">未受理</option>
                   <option value="2">受理中</option>
                   <option value="3">已受理</option>
               </select>
               受理人：<input type="text" size="15" name="search_LIKE_acceptUserName" value="${param.search_LIKE_acceptUserName}"  />        
          	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_orderPz_searchform','manage_orderPz_datagrid');">查询</a>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <!-- 列表和工具栏 -->
  <div data-options="region:'center',border:false">
    <table id="manage_orderPz_datagrid" class="easyui-datagrid" 
			url="${pageContext.request.contextPath}/manage/epei/orderPz/listJson.html" 
			toolbar="#manage_orderPz_toolbar" fit="true" border="false" fitColumns="false"
      pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
      checkOnSelect="true" selectOnCheck="true" rowStyler="markFirstFree">
      <thead>
        <tr>
            <th field="payMode" data-options="hidden:true"></th>
        	<th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
			<th field="orderNo">流水号</th>
			<th field="createTime">创建时间</th>
			<th field="orign" data-options="formatter:function(value,row,index){return originData[value];}">来源</th>
			<th field="cusNo">会员编号</th>
            <th field="patientName">病患姓名</th>
            <th field="hospitalName">就诊医院</th>
			<th field="departmentName">就诊科室</th>
            <th field="orderTime">预约时间</th>
			<th field="acceptance" data-options="formatter:function(value){return value==1?'未受理':(value==2?'受理中':'已受理')}">受理状态</th>
			<th field="acceptUserName">受理人</th>
			<th field="amount">总费用</th>
	        <th field="payStatus" data-options="formatter:function(value){return value==1?'已支付':(value==0?'未支付':'')}">支付状态</th>
          	<th field="payType" data-options="formatter:function(value,row,index){return orderpayType[value];}">支付类型</th>
          	<th field="payeeName">收款人</th>
          	<th field="state" data-options="formatter:function(value,row,index){return orderState[value];}">订单状态</th>
          	<th field="rowActions" data-options="formatter:function(value, row, index){return orderFormatAction('manage_orderPz_action',value,row)}">动作</th>
        </tr>
      </thead>
    </table>
    
    <!-- 每行的Action动作模板 -->
    <div id="manage_orderPz_action" style="display: none;">
      <a class="line-action icon-edit"
         onclick="$.acooly.framework.edit({url:'/manage/epei/orderPz/edit.html',id:'{0}',entity:'orderPz',width:500,height:400});" href="#" title="编辑"></a>
      <a class="line-action icon-show"
         onclick="$.acooly.framework.show('/manage/epei/orderPz/show.html?id={0}',700,520);" href="#" title="查看"></a>
      <a class="line-action icon-delete"
         onclick="$.acooly.framework.remove('/manage/epei/orderPz/cancel.html','{0}','manage_orderPz_datagrid','提醒','取消后该订单将不可用,确定取消?');"
         href="#" title="取消"></a>
      <a class="line-action icon-audit-ok" 
   		 onclick="confirmPzOrder('{0}')" href="#" title="确认"></a>
      <a class="line-action icon-yes" 
      	 onclick="finishedPzOrder('{0}');" href="#" title="完成"></a>
    </div>

    <!-- 表格的工具栏 -->
    <div id="manage_orderPz_toolbar">
      <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$.acooly.framework.create({url:'/manage/epei/orderPz/create.html',entity:'orderPz',width:500,height:400})">添加</a> 
      <a href="#" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="$.acooly.framework.exports('/manage/epei/orderPz/exportXls.html','manage_orderPz_searchform','陪诊')">批量导出</a>
      <a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" onclick="$.acooly.framework.imports({url:'/manage/epei/orderPz/importView.html',uploader:'manage_orderPz_import_uploader_file'});">批量导入</a>
<!--       <a href="#" class="easyui-linkbutton" iconCls="icon-audit-ok" plain="true" onclick="$.acooly.framework.removes('/manage/epei/orderPz/confirmJson.html','manage_orderPz_datagrid')">批量确认</a> 
      <a href="#" class="easyui-linkbutton" iconCls="icon-yes" plain="true" onclick="$.acooly.framework.removes('/manage/epei/orderPz/finishJson.html','manage_orderPz_datagrid')">批量完成</a> 
      <a href="#" class="easyui-linkbutton" iconCls="icon-delete" plain="true" onclick="$.acooly.framework.removes('/manage/epei/orderPz/invalidJson.html','manage_orderPz_datagrid')">批量取消</a>  -->
    </div>
  </div>

</div>
