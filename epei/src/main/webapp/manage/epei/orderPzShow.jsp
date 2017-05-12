<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">
        <table class="tableForm" width="100%">
            <input type="hidden" value="${orderPz.id}" name="id"/>
            <tr>
                <th width="25%">订单编号:</th>
                <td>${orderPz.orderNo}</td>
                <th>患者电话:</th>
	            <td>${orderPz.patientMobile}</td>
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
                <td>${orderPz.departmentName}</td>
            </tr>
            <tr>
                <th>申请时间:</th>
                <td><fmt:formatDate value="${orderPz.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <th>预约时间:</th>
                <td><fmt:formatDate value="${orderPz.orderTime}" pattern="yyyy-MM-dd HH:mm"/></td>
            </tr>
            <tr>
                <th>陪诊状态:</th>
                <td>${orderPz.state}</td>
                <th>预约来源:</th>
                <td>${orderPz.orign}</td>
            </tr>
            <tr>
	            <th>服务人员:</th>
	            <td>${orderPz.servicePerson}</td>
	            <th>服务人员ID:</th>
	            <td>${orderPz.servicePersonId}</td>
        	</tr>
        	<tr>
                <th>费用:</th>
                <td>${orderPz.amount}</td>
                <th>支付方式:</th>
                <td>${orderPz.payType == 2?'支票':(orderPz.payType == 1?'POS机':(orderPz.payType==3?'现金':(orderPz.payType==4?'在线支付':(orderPz.payType==5?'企业二维码':''))))}</td>
            </tr>
        	<c:choose>
            <c:when test="${orderPz.state == '完成'}"> 
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

<c:if test="${orderPz.state == '完成'}">
<script>
var checkedStar = "${comment.star}";
$('input:radio[name="Fruit"]').each(function(){
	var star = $(this).val();
	if(star == checkedStar){
		$(this).attr('checked', 'true');
	}
  });

function contentModify(){
	var orderId = ${orderPz.id};
	var content = $("#content").val();
	var  star = $('input:radio[name="Fruit"]:checked').val();
	$.ajax({
		 type: "POST",
		 url: "/manage/epei/orderPz/contentModify.html",
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