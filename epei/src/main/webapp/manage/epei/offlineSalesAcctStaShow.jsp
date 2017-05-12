<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>

<div>
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
				<th><span style="color: red">确认总金额:</span></th>
				<td>${offlineSalesAcctSta.amount}</td>
			</tr>
			<tr>
				<th><span style="color: red">对账人员:</span></th>
				<td><span style="color: red">${offlineSalesAcctSta.acctName}</span></td>
				<th><span style="color: red">对账备注:</span></th>
				<td><span style="color: red">${offlineSalesAcctSta.acctMemo}</span></td>
			</tr>
			<tr>
				<th><span style="color: red">对账日期:</span></th>
				<td><span style="color: red"><fmt:formatDate value="${offlineSalesAcctSta.acctTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></td>
			</tr>
			<tr>
				<th><span style="color: red">统计范围:</span></th>
				<td>${offlineSalesAcctSta.countScope == 1?'所有':(offlineSalesAcctSta.status == 2?'医院':'科室')}</td>
				<th><span style="color: red">统计类型:</span></th>
				<td>${offlineSalesAcctSta.orderType == 1?'陪诊':(offlineSalesAcctSta.status == 2?'陪护':'全部')}</td>
			</tr>
			<tr>
				<th><span style="color: red">统计数目:</span></th>
				<td>${offlineSalesAcctSta.dataCount}条</td>
			</tr>
			<tr>
				<th><span style="color: red">医院名称:</span></th>
				<td>${offlineSalesAcctSta.hospitalName}</td>
				<th><span style="color: red">医院ID:</span></th>
				<td>${offlineSalesAcctSta.hospitalId}</td>
			</tr>
			<tr>
				<th><span style="color: red">科室名称:</span></th>
				<td>${offlineSalesAcctSta.departmentName}</td>
				<th><span style="color: red">科室ID:</span></th>
				<td>${offlineSalesAcctSta.departmentId}</td>
			</tr>
	</table>			
</div>