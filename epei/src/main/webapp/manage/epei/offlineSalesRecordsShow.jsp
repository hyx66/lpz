<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>

<div>
	<table class="tableForm" width="100%">
		<tr>
			<th width="25%">数据类型:</th>
			<td>${offlineSalesRecords.dataType == 0?'陪诊':'陪护'}</td>
			<th width="25%">支付类型:</th>
			<td>${offlineSalesRecords.payType == 0?'现金':(offlineSalesRecords.payType == 1?'POS机':'支票')}</td>
		</tr>
		<tr>
			<th>客户姓名:</th>
			<td>${offlineSalesRecords.customerName}</td>
			<th>客户手机号:</th>
			<td>${offlineSalesRecords.customerMobile}</td>
		</tr>
		<tr>
			<th>性别:</th>
			<td>${offlineSalesRecords.customerSex == 0?'女':(offlineSalesRecords.customerSex == 1?'男':'')}</td>
			<th>年龄:</th>
			<td>${offlineSalesRecords.customerAge}</td>
		</tr>
		<tr>
			<th>所属医院:</th>
			<td>${offlineSalesRecords.hospital}</td>
			<th>科室:</th>
			<td>${offlineSalesRecords.departName}</td>
		</tr>
		<tr>
			<th>服务项目名称:</th>
			<td>${offlineSalesRecords.serviceItemsName}</td>
			<th><span style="color: red">金额:</span></th>
			<td><span style="color: red">${offlineSalesRecords.amount}</span></td>
		</tr>
		<tr>
			<th>服务人员姓名:</th>
			<td>${offlineSalesRecords.servicePersonName}</td>
			<th>服务人员手机号:</th>
			<td>${offlineSalesRecords.servicePersonMobile}</td>
		</tr>
		<tr>
			<th>服务开始日期:</th>
			<td><fmt:formatDate value="${offlineSalesRecords.serviceYmd}" pattern="yyyy-MM-dd"/></td>
			<th>流水号:</th>
			<td>${offlineSalesRecords.recordsNo}</td>
		</tr>
		<tr>
			<th>服务结束日期:</th>
			<td><fmt:formatDate value="${offlineSalesRecords.serviceEndYmd}" pattern="yyyy-MM-dd"/></td>
			<th>服务天数:</th>
			<td>${offlineSalesRecords.serviceDays}</td>
		</tr>
		<tr>
			<th>收款人:</th>
			<td>${offlineSalesRecords.payeeName}</td>
			<th><span style="color: red">小票号:</span></th>
			<td><span style="color: red">${offlineSalesRecords.ticketNo}</span></td>
		</tr>
		<tr>
			<th>录入人员:</th>
			<td>${offlineSalesRecords.createName}</td>
			<th>修改人员:</th>
			<td>${offlineSalesRecords.updateName}</td>
		</tr>
		<tr>
			<th>账期:</th>
			<td>${offlineSalesRecords.dataYmd}</td>
			<th>数据状态:</th>
			<td>${offlineSalesRecords.status == 0?'初始':(offlineSalesRecords.status == 1?'有效':'无效')}</td>
		</tr>
		<tr>
			<th>床位:</th>
			<td>${offlineSalesRecords.customerBed}</td>
			<th>备注:</th>
			<td>${offlineSalesRecords.memo}</td>
		</tr>
		<tr>
			<th>生活护理申请表单号:</th>
			<td>${offlineSalesRecords.outNo}</td>
			<th>病症:</th>
			<td>${offlineSalesRecords.disease}</td>
		</tr>
		<tr>
			<th>创建时间:</th>
			<td><fmt:formatDate value="${offlineSalesRecords.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td> 
			<th>修改时间:</th>
			<td><fmt:formatDate value="${offlineSalesRecords.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
	</table>			
</div>