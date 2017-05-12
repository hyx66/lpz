<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
    <form id="orderPhFinishedForm">
        <input name="id" value="${orderPh.id}" type="hidden"/>
        <table class="tableForm" width="100%">
            <tr>
                <th width="25%">订单编号:</th>
                <td >${orderPh.orderNo}</td>
                <th >会员编号:</th>
                <td >${orderPh.cusNo}</td>
            </tr>
            <tr>
                <th width="25%">病患姓名:</th>
                <td>${orderPh.patientName}</td>
                <th>病患身份证号:</th>
                <td>${orderPh.patientIdCard}</td>
            </tr>
            <tr>
                <th>手机号:</th>
                <td>${orderPh.patientMobile}</td>
                <th>出生年月日:</th>
                <td>${orderPh.patient.birthday}</td>
            </tr>
            <tr>
                <th>患者性别:</th>
                <td>${orderPh.patient.gender == 1?'男':'女'}</td>
                <th>预约医院:</th>
                <td>${orderPh.hospitalName}</td>
            </tr>
            <tr>
                <th>申请时间:</th>
                <td><fmt:formatDate value="${orderPh.createTime}" pattern="yyyy-MM-dd"/></td>
                <th>预约时间:</th>
                <td><fmt:formatDate value="${orderPh.orderTime}" pattern="yyyy-MM-dd"/></td>
            </tr>
            <tr>
                <th>陪诊状态:</th>
                <td>${orderPh.state}</td>
                <th>预约来源:</th>
                <td>${orderPh.orign}</td>
            </tr>
            <tr>
                <th>定价方式:</th>
                <td>
                    ${orderPh.pricingType==1?'系统定价':'医院管理员定价'}</td>
                <th>服务价格:</th>
                <td>${orderPh.phServicePrice}</td>
            </tr>
            <tr>
                <th>陪护人员:</th>
                <td>
                    ${orderPh.servicePerson}</td>
                <th>总费用:</th>
                <td>${orderPh.amount}</td>
            </tr>
             <tr>
                <th>陪护开始日期:</th>
                <td><fmt:formatDate value="${orderPh.startTime}" pattern="yyyy-MM-dd"/></td>
                <th>陪护结束日期:</th>
                <td><fmt:formatDate value="${orderPh.endTime}" pattern="yyyy-MM-dd"/></td>
            </tr>
           <tr>
                <th>收款人：</th>
		        <td><input type="text" name="payeeName" required="true" value="${Payee}" disabled="disabled" class="easyui-validatebox"/></td>
		        <th>小/支票号：</th>
		        <td><input type="text" name=ticketNo  class="easyui-validatebox"/></td>
            </tr> 
            <tr>
                <th>服务总天数：</th>
		        <td><input type="text" name="actualDuration" value="${orderPh.duration}" class="easyui-validatebox" data-options="required:true" validType="posint"/></td>
		        <%-- <th>结算方式：</th>
		        <td>
		        <input type="radio" name="refundType" value="1" checked="checked"/>线下结算&nbsp;&nbsp;&nbsp;
		        <c:if test="${orderPh.payType==4}">
		        <input type="radio" name="refundType" value="2"/>系统结算
		        </c:if>
		        </td> --%>
            </tr>  
        </table>
        
    </form>
</div>
<script>
$.extend($.fn.validatebox.defaults.rules, {     
    money : {     
         validator: function(value){     
             return /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/i.test(value);     
         },     
         message: '请输入正确的金额'    
     } 
 });  
$.extend($.fn.validatebox.defaults.rules, {     
	posint : {     
         validator: function(value){     
             return /^[0-9]*$/i.test(value);     
         },     
         message: '只能填写数字'    
     } 
 });  
</script>
