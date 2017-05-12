<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
        <table class="tableForm" width="100%">
            <tr>
                <th>vip会员账号:</th>
                <td >${vipCustomerDiscount.customerUserName}</td>
            </tr>
            <c:forEach var="serviceItem" items="${serviceItems}">
            <tr>
                <th>${serviceItem.name}剩余次数:</th>
                <td>${serviceItem.count}次</td>
            </tr>
            </c:forEach>
        </table>
</div>