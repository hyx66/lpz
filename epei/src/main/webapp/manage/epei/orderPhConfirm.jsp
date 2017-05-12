<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
    <form id="orderPhConfirmForm">
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
                <td>${orderPh.customerSex == 1?'男':'女'}</td>
                <th>患者年龄:</th>
                <td>${orderPh.customerAge}</td>
            </tr>
            <tr>
                <th>预约医院:</th>
                <td>${orderPh.hospitalName}</td>
                <th>陪护人员:</th>
                <td>${orderPh.servicePerson}</td>
            </tr>
            <tr>
                <th>床号:</th>
                <td>${orderPh.bed}</td>
                <th>生活护理申请表单号:</th>
                <td>${orderPh.outNo}</td>
            </tr>
            <tr>
                <th>支付方式:</th>
                <td>${orderPh.payType == 2?'支票':(orderPh.payType == 1?'POS机':'现金')}</td>
                <th>小/支票号:</th>
                <td>${orderPh.customerAge}</td>
            </tr>
            <tr>
                <th>申请时间:</th>
                <td><fmt:formatDate value="${orderPh.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <th>预约时间:</th>
                <td><fmt:formatDate value="${orderPh.orderTime}" pattern="yyyy-MM-dd"/> ${orderPh.timeInOneDay == AM?'上午':'下午'}</td>
            </tr>
            <tr>
                <th>收款人:</th>
                <td>${orderPh.adminName}</td>
                <th>病症:</th>
                <td>${orderPh.disease}</td>
            </tr>
            <tr>
                <th>陪诊状态:</th>
                <td>${orderPh.state}</td>
                <th>预约来源:</th>
                <td>${orderPh.orign}</td>
            </tr>
            <tr>
                <th>服务开始日期:</th>
                <td><fmt:formatDate value="${orderPh.startTime}" pattern="yyyy-MM-dd"/></td>
                <th>服务结束日期:</th>
                <td><fmt:formatDate value="${orderPh.endTime}" pattern="yyyy-MM-dd"/></td>
            </tr>
            <tr>
            	<th>预约总天数:</th>
                <td>${orderPh.duration}</td>
                <th>总费用:</th>
                <td>${orderPh.amount}</td>
            </tr>
            <tr>
                <th>收款人：</th>
		        <td><input type="text" name="payeeName" required="true" value="${Payee}" disabled="disabled" class="easyui-validatebox"/></td>
		        <th>小/支票号：</th>
		        <td><input type="text" name=ticketNo  class="easyui-validatebox"/></td>
            </tr>
             <tr>
               <th>陪护人员:</th>
                <td>
                    <input name="servicePerson" type="hidden" value="${orderPh.servicePerson}"/>
                    <input name="servicePersonId" value="${orderPh.servicePersonId}" class="easyui-combobox"
                           required="true"
                           type="text"
                           data-options="url:'/manage/epei/orderPh/comboboxServicePerson.html?hospitalId=${orderPh.hospital.id}&serviceType=PH',
                           valueField:'id',
                           textField:'name',
                           onSelect:fillServicePersonName" class="easyui-validatebox"/>
                </td>
                <c:if test="${orderPh.payStatus==0}">
                <th>付款方式：</th>
            	<td>
            	<select name="payType">
            	<option value="5">企业二维码</option>
            	<option value="3">现金</option>
            	<option value="1">POS机</option>
            	<option value="2">支票</option>
            	</select>
            	</td>
                </c:if>
            </tr>
        </table>
    </form>
</div>

<script type="text/javascript">
    function fillServicePersonName(record){
        $("input[name='servicePerson']").val(record.name);
    }
    $.extend($.fn.validatebox.defaults.rules, {     
        money : {     
             validator: function(value){     
                 return /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/i.test(value);     
             },     
             message: '请输入正确的金额'    
         } 
     });  
</script>

