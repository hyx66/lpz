<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>

<div>
	<form id="osasConfirmForm">
		<input name="id" type="hidden" value="${offlineSalesAcctSta.id}">
		<table class="tableForm" width="100%">
			<tr>
				<th>账期:</th>
				<td>${offlineSalesAcctSta.acctYmd}</td>
				<th>状态:</th>
				<td>${offlineSalesAcctSta.status == 0?'初始':(offlineSalesAcctSta.status == 1?'未对账':'已对账')}</td>
			</tr>
			<tr>
				<th><span style="color: red">入账总金额:</span></th>
				<td><span style="color: red">${offlineSalesAcctSta.offlineAmount}</span></td>
				<th><span style="color: red">实账总金额:</span></th>
				<td><span style="color: red">${offlineSalesAcctSta.acctAmount}</span></td>
			</tr>
			<tr>
				<th><span style="color: red">相差金额:</span></th>
				<td><span style="color: red">${offlineSalesAcctSta.diffAmount}</span></td>
			</tr>
			<tr>
				<th>录入人员:</th>
				<td>${offlineSalesAcctSta.createName}</td>
				<th>录入备注:</th>
				<td>${offlineSalesAcctSta.createMemo}</td>
			</tr>
			<tr>
				<th>创建时间:</th>
				<td><fmt:formatDate value="${offlineSalesAcctSta.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td> 
				<th>修改时间:</th>
				<td><fmt:formatDate value="${offlineSalesAcctSta.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr>
				<th><span style="color: red">*</span>确认总金额</th>
				<td><input type="text" name="amount" class="easyui-validatebox" required="true"/></td>
				<th><span style="color: red">*</span>对账备注:</th>
				<td><input type="text" name="acctMemo" class="easyui-validatebox" validType="byteLength[1,32]" required="true"/></td>
			</tr>
		</table>
	</form>		
</div>