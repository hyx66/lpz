<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
    <form id="orderPzConfirmForm">
        <input name="id" value="${orderPz.id}" type="hidden"/>
        <table class="tableForm" width="100%">
             <tr>
            <th width="25%">订单编号:</th>
            <td colspan="3">${orderPz.orderNo}</td>
        </tr>
        <tr>
            <th width="25%">病患姓名:</th>
            <td>${orderPz.patientName}</td>
            <th>病患身份证号:</th>
            <td>${orderPz.patientIdCard}</td>
        </tr>
        <tr>
            <th>医保卡号:</th>
            <td>${orderPz.patient.medicareCard}</td>
            <th>接待地点:</th>
            <td>${orderPz.receptionPosition}</td>
        </tr>
        <tr>
            <th>就诊医院:</th>
            <td>${orderPz.hospitalName}</td>
            <th>就诊科室:</th>
            <td>${orderPz.department}</td>
        </tr>
        <tr>
            <th>申请时间:</th>
            <td><fmt:formatDate value="${orderPz.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <th>预约时间:</th>
            <td><fmt:formatDate value="${orderPz.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>陪诊状态:</th>
            <td>${orderPz.state}</td>
            <th>预约来源:</th>
            <td>${orderPz.orign}</td>
        </tr>
        <c:if test="${orderPz.payStatus==0}">
            <th>收款人：</th>
		    <td><input type="text" name="payeeName" required="true" value="${Payee}" disabled="disabled" class="easyui-validatebox"/></td>
            <th>付款方式：</th>
	         	<td>
		         	<select name="payType">
			         	<option value="1">POS机</option>
			         	<option value="2">支票</option>
			         	<option value="3">现金</option>
			         	<option value="5">企业二维码</option>
		         	</select>
	         	</td>
        </c:if>
        <tr>
            <th>服务人员:</th>
            <td align="left">
	            <select name="servicePersonId" style="width:153px">
	                <c:forEach var="servicePerson" items="${servicePersons}">
	                    <option value="${servicePerson.id}" <c:if test="${servicePerson.id==orderPz.servicePersonId}">selected="selected"</c:if>>${servicePerson.name}</option>
	                </c:forEach>
	            </select>
        	</td>
        </tr>
        </table>
    </form>
</div>