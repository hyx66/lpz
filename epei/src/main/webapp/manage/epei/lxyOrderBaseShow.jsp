<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
        <table class="tableForm" width="100%">
         	<tr>
                <th width="25%">订单编号:</th>
                <td >${lxyOrderBase.orderId}</td>
                <th>预约医院:</th>
                <td>${lxyOrderBase.hospitalName}</td>
            </tr>
            <tr>
            	<th >下单时间:</th>
                <td ><fmt:formatDate value="${lxyOrderBase.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
             	<th width="25%">订单价格:</th>
                <td>${lxyOrderBase.total}</td>
            </tr>
            <tr>
                <th>陪诊员姓名:</th>
                <td>${lxyOrderBase.pzName}</td>
                <th>陪诊员电话:</th>
                <td>${lxyOrderBase.pzPhone}</td>
            </tr>
            <tr>
            	<th>订单内容:</th>
            	<c:forEach items="${lxyOrderBase.item}" var="item">
                	<td>${item.itemName}</td>
                </c:forEach>
            </tr>
            <tr>
                <th>宝宝姓名:</th>
                <td>${lxyOrderBase.babyName}</td>
                <th>宝宝性别:</th>
                <td>${lxyOrderBase.babySex==0?'女':(lxyOrderBase.babySex==1?'男':'')}</td>
            </tr>
            <tr>
                <th>家长姓名:</th>
                <td>${lxyOrderBase.patriarchName}</td>
                <th>家长电话:</th>
                <td>${lxyOrderBase.phone}</td>
            </tr>
             <tr>
                <th>家长身份证号:</th>
                <td>${lxyOrderBase.patriarchIdCard}</td>
                 <th>家庭地址:</th>
                <td>${lxyOrderBase.site}</td>
            </tr>
        </table>
</div>