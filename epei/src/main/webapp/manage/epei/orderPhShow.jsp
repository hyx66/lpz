<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
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
                <td>${orderPh.payType == 2?'支票':(orderPh.payType == 1?'POS机':(orderPh.payType==3?'现金':(orderPh.payType==4?'在线支付':(orderPh.payType==5?'企业二维码':''))))}</td>
                <th>小/支票号:</th>
                <td>${orderPh.outNo}</td>
            </tr>
            <tr>
                <th>申请时间:</th>
                <td><fmt:formatDate value="${orderPh.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <th>预约时间:</th>
                <td><fmt:formatDate value="${orderPh.orderTime}" pattern="yyyy-MM-dd"/> ${orderPh.timeInOneDay == AM?'上午':'下午'}</td>
            </tr>
            <tr>
                <th>陪护开始时间:</th>
                <td><fmt:formatDate value="${orderPh.startTime}" pattern="yyyy-MM-dd"/></td>
                <th>陪护结束时间:</th>
                <td><fmt:formatDate value="${orderPh.endTime}" pattern="yyyy-MM-dd"/> </td>
            </tr>
            <tr>
                <th>陪护时长(天):</th>
                <td>${orderPh.duration}</td>
                <th>总费用(元):</th>
                <td>${orderPh.amount}</td>
            </tr>
            <tr>
                <th>收款人:</th>
                <td>${orderPh.payeeName}</td>
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
                <th width="25%">找零:</th>
                <td>${orderPh.refundAmount}</td>
                <th>结算方式:</th>
                <td>${orderPh.refundType==1?'线下结算':(orderPh.refundType==1?'系统结算':'')}</td>
            </tr>
            <tr>
                <th width="25%">实际服务天数:</th>
                <td>${orderPh.actualDuration}</td>
                <th>实际服务总费用:</th>
                <td>${orderPh.actualAmount}</td>
            </tr>
            <tr>
                <th width="25%">备注:</th>
                <td><input id="note" name="note" value="${orderPh.note}"/>
                <button type="button" onclick="noteModify()">修改备注</button>
                </td>
            </tr>
             <c:choose>
            <c:when test="${orderPh.state == '完成'}"> 
        	<tr>
            	<th>服务打分:</th>
            	<td>
            	<input name="Fruit" type="radio" value="1" />1分
            	<input name="Fruit" type="radio" value="2" />2分
            	<input name="Fruit" type="radio" value="3" />3分
            	<input name="Fruit" type="radio" value="4" checked/>4分
            	<input name="Fruit" type="radio" value="5" />5分
            	</td>
            </tr>
            <tr>
                <th>服务评论:</th>
                <td>
                <input id="content" name="fraction" value="${comment.content}"/>
                <button onclick="contentModify()">提交评价</button>
                </td>
            </tr>
            </c:when>
            </c:choose>
        </table>
</div>
<script>
function noteModify(){
	var orderId = ${orderPh.id};
	var orderNote = $("#note").val();
	$.ajax({
		 type: "POST",
		 url: "/manage/epei/orderPh/noteModify.html",
		 data: {id:orderId,note:orderNote},
		 dataType: "json",
		 success: function(result){
			alert("修改成功!"); 
		},
		 error : function() {   
			 alert("修改失败！");
		}  
	});
}
</script>
<c:if test="${orderPh.state == '完成'}">
<script>
var checkedStar = "${comment.star}";
$('input:radio[name="Fruit"]').each(function(){
	var star = $(this).val();
	if(star == checkedStar){
		$(this).attr('checked', 'true');
	}
  });

function contentModify(){
	var orderId = ${orderPh.id};
	var content = $("#content").val();
	var  star = $('input:radio[name="Fruit"]:checked').val();
	$.ajax({
		 type: "POST",
		 url: "/manage/epei/orderPh/contentModify.html",
		 data: {"orderId":orderId,"content":content,"star":star},
		 dataType: "json",
		 success: function(result){
			alert("修改成功!"); 
		},
		 error : function() {   
			 alert("修改失败！");
		}  
	});
}
</script>
</c:if>