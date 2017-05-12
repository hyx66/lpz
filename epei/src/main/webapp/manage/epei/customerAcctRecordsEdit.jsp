<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_customerAcctRecords_editform" action="${pageContext.request.contextPath}/manage/epei/customerAcctRecords/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="customerAcctRecords" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
        <c:if test="${action=='create'}">
        	<tr>
				<th>会员账号：</th>
				<td><input onchange="clearCheckResult()" type="text" id="customerMobile" name="customerMobile" data-options="required:true" class="easyui-validatebox" validType="mobile"/> <button type="button" onclick="checkAccount()">检测</button></td>
			</tr>
			<tr id="checkTr">
				<th></th>
				<td id="checkResult" style="color:red"></td>
			</tr>
			<tr>
                <th>充值金额：</th>
                <td>
                    <select name="rechargeAmount" style="width:153px;" data-options="editable:false" class="easyui-combobox">
                        <option value="50" >50元</option>
                        <option value="200" >200元</option>
                        <option value="300" >300元</option>
                        <option value="500" >500元</option>
                        <option value="700" >700元</option>
                        <option value="1000" >1000元</option>
                     </select>
                </td>
            </tr>
			<tr>
				<th>备注：</th>
				<td><input type="text" name="memo" class="easyui-validatebox"/></td>
			</tr>
        </c:if>
        
        <c:if test="${action!='create'}">
        <c:if test="${customerAcctRecords.dataType==1}">
         <!-- 
        	<tr>
				<th>会员账号：</th>
				<td><input type="text" name="customerMobile" data-options="required:true" class="easyui-validatebox" validType="mobile"/></td>
			</tr>
		-->
			<tr>
                <th>会员账户：</th>
                <td>
                    ${customerAcctRecords.customerMobile}
                </td>
            </tr>
            <tr>
                <th>充值金额：</th>
                <td>
                    ${customerAcctRecords.rechargeAmount}
                </td>
            </tr>
			<tr>
                <th>当前状态：</th>
                <td>
                    <c:if test="${customerAcctRecords.rechargeStatus==1}">充值成功</c:if>
                    <c:if test="${customerAcctRecords.rechargeStatus==2}">充值失败</c:if>
                </td>
            </tr>
			<tr>
                <th>修改状态：</th>
                <td>
                    <select name="rechargeStatus" style="width:153px;" data-options="editable:false" class="easyui-combobox">
                        <option value="2" >充值失败</option>
                        <option value="1" >充值成功</option>
                     </select>
                </td>
            </tr>
			<tr>
				<th>备注：</th>
				<td><input type="text" name="memo" class="easyui-validatebox"/></td>
			</tr>
        </c:if>
        <c:if test="${customerAcctRecords.dataType==2}">
        	 暂时没有需要处理的业务项
        </c:if>
        </c:if>
        </table>
      </jodd:form>
    </form>
</div>
<script type="text/javascript">
$.extend($.fn.validatebox.defaults.rules, {     
    mobile : {     
         validator: function(value){     
             return /^1[0-9]{10}$/i.test(value);     
         },     
         message: '请输入正确的手机号'    
     }     
 });

function checkAccount(){
	var account = $("#customerMobile").val();
	//第一步：查询所有相关科室的医院
	$.ajax({
		 type: "GET",
		 url: "/manage/epei/customerAcctRecords/checkAccount.html",
		 data: {userName:account},
		 dataType: "json",
		 success: function(result){
			 $("#checkTr").show();
			 $("#checkResult").text("账号："+result.userName+",会员ID："+result.id);		 
		},
		 error : function() {   
			 $("#checkTr").show();
			 $("#checkResult").text("您输入的账号不存在");	
		}  
	});
}

$(function () {
	if("${action}"=="create"){
		clearCheckResult();
	}
});

function clearCheckResult(){
	$("#checkTr").hide();
	$("#checkResult").text("");
}
</script>