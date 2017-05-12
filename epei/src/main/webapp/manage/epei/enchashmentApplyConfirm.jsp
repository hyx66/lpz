<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
    <form id="enchashmentApplyConfirmForm">
        <input name="id" value="${enchashmentApply.id}" type="hidden"/>
        <table class="tableForm" width="100%">
			<tr>
                <th width="25%">申请账号:</th>
                <td >${enchashmentApply.customerUserName}</td>
                <th >申请金额:</th>
                <td >${enchashmentApply.amount}</td>
            </tr>
            <tr>
                <th width="25%">申请说明:</th>
                <td>${enchashmentApply.explain}</td>
                <th>流水号:</th>
                <td>${enchashmentApply.applyNo}</td>
            </tr>
            <tr>
                <th>申请时间:</th>
                <td><fmt:formatDate value="${orderPh.createTime}" pattern="yyyy-MM-dd"/></td>
            </tr>
            <tr>
                <th>审批人：</th>
		        <td><input type="text" name="AuditorSign" class="easyui-validatebox" required="true"/></td>
		        <th>备注：</th>
		        <td><input type="text" name="memo"  class="easyui-validatebox"/></td>
            </tr> 
        </table>
    </form>
</div>