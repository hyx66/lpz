<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
        <table class="tableForm" width="100%">
            <tr>
                <th width="25%">名称:</th>
                <td >${vip.name}</td>
            </tr>
            <tr>
                <th width="25%">vip等级:</th>
                <td >${vip.grade}</td>
            </tr>
            <c:forEach var="serviceItem" items="${serviceItems}">
            <tr>
                <th width="25%">${serviceItem.name}:</th>
                <td >${serviceItem.count}次</td>
            </tr>
            </c:forEach>
        </table>
</div>