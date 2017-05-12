<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th>标题:</th>
		<td>${customerAcctRecords.title}</td>
	</tr>
	<tr>
		<th>用户名:</th>
		<td>${customerAcctRecords.customerName}</td>
	</tr>					
	<tr>
		<th>会员账号:</th>
		<td>${customerAcctRecords.customerMobile}</td>
	</tr>
	<tr>
		<th>外部单号:</th>
		<td>${customerAcctRecords.outNo}</td>
	</tr>	
	<tr>
		<th>充值金额:</th>
		<td>${customerAcctRecords.rechargeAmount}</td>
	</tr>
	<c:if test="${customerAcctRecords.dataType==1}">
	<tr>
		<th>充值渠道:</th>
		<td>${customerAcctRecords.rechargeChannle==1?'在线充值':'线下充值'}</td>
	</tr>
	<tr>
		<th>充值状态:</th>
		<td>${customerAcctRecords.rechargeStatus==1?'充值成功':'充值失败'}</td>
	</tr>
	</c:if>		
	<tr>
		<th>余额:</th>
		<td>${customerAcctRecords.balance}</td>
	</tr>
		<tr>
		<th>管理员ID:</th>
		<td>${customer.userId}</td>
	</tr>					
	<tr>
		<th>账期：</th>
		<td>${customerAcctRecords.dateYmd}</td>
	</tr>	
	<tr>
		<th>备注:</th>
		<td>${customerAcctRecords.memo}</td>
	</tr>										
</table>
</div>
