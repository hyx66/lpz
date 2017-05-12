<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
<table class="tableForm" width="100%">
	<tr>
		<th width="25%">优惠券类型:</th>
		<td>${coupon.couponType}</td>
	</tr>					
	<tr>
		<th>优惠券使用目标:</th>
		<td>${coupon.useTarget}</td>
	</tr>					
	<tr>
		<th>所属会员:</th>
		<td>${coupon.customerId}</td>
	</tr>					
	<tr>
		<th>是否已使用:</th>
		<td>${coupon.used}</td>
	</tr>					
	<tr>
		<th>使用时间:</th>
		<td><fmt:formatDate value="${coupon.useTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>面额:</th>
		<td>${coupon.denomination}</td>
	</tr>					
	<tr>
		<th>到期日:</th>
		<td><fmt:formatDate value="${coupon.expiryDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>生成时间:</th>
		<td><fmt:formatDate value="${coupon.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
	<tr>
		<th>更新时间:</th>
		<td><fmt:formatDate value="${coupon.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>					
</table>
</div>
