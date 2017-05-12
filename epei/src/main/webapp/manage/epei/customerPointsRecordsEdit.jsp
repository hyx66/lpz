<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_customerPointsRecords_editform" action="${pageContext.request.contextPath}/manage/epei/customerPointsRecords/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="customerPointsRecords" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
		<c:if test="${action=='create'}">
			<tr>
				<th>会员ID：</th>
				<td><input type="text" name="customerId" data-options="required:true" class="easyui-validatebox"/></td>
			</tr>
        	<tr>
				<th>会员名字：</th>
				<td><input type="text" name="customerName" data-options="required:true" class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>电话：</th>
				<td><input type="text" name="customerMobile" data-options="required:true" class="easyui-validatebox"/></td>
			</tr>
			<tr>
				<th>标题：</th>
				<td><input type="text" name="title" class="easyui-validatebox"/></td>
			</tr>
			<tr>
				<th>流水号：</th>
				<td><input type="text" name="recordsNo" class="easyui-validatebox"/></td>
			</tr>
			<tr>
				<th>外部单号：</th>
				<td><input type="text" name="outNo" class="easyui-validatebox"/></td>
			</tr>
			<tr>
				<th>积分数：</th>
				<td><input type="text" name="points" class="easyui-validatebox"/></td>
			</tr>
			<tr>
				<th>消费类型：</th>
				<td><input type="text" name="spendType" class="easyui-validatebox"/></td>
			</tr>
			<tr>
				<th>数据类型：</th>
				<td><input type="text" name="dataType" class="easyui-validatebox"/></td>
			</tr>
			<tr>
				<th>DAC：</th>
				<td><input type="text" name="dac" class="easyui-validatebox"/></td>
			</tr>
			<tr>
				<th>备注：</th>
				<td><input type="text" name="memo" class="easyui-validatebox"/></td>
			</tr>
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
</script>
<script>
//通过上、下按键移动焦点
$(document).ready(function () {
	$(':input:enabled').addClass('enterIndex');
	textboxes = $('.enterIndex');
	if ($.browser.mozilla) {
		$(textboxes).bind('keypress', CheckForEnter);
	}else {
		$(textboxes).bind('keydown', CheckForEnter);
	}
});
function CheckForEnter(event) {
	if (event.keyCode == 40 && $(this).attr('type') != 'button' && $(this).attr('type') != 'submit' && $(this).attr('type') != 'textarea' && $(this).attr('type') != 'reset') {
		var i = $('.enterIndex').index($(this));
		var n = $('.enterIndex').length;
		if (i < n) {
		if ($(this).attr('type') != 'radio'){
			NextDOM($('.enterIndex'),i);
		}
		else {
			var last_radio = $('.enterIndex').index($('.enterIndex[type=radio][name=' + $(this).attr('name') + ']:last'));
			NextDOM($('.enterIndex'),last_radio);
			}
		}
		return false;
	}
	if (event.keyCode == 38 && $(this).attr('type') != 'button' && $(this).attr('type') != 'submit' && $(this).attr('type') != 'textarea' && $(this).attr('type') != 'reset') {
		var i = $('.enterIndex').index($(this));
		var n = $('.enterIndex').length;
		if (i < n) {
		if ($(this).attr('type') != 'radio'){
			PreDOM($('.enterIndex'),i);
		}
		else {
			var last_radio = $('.enterIndex').index($('.enterIndex[type=radio][name=' + $(this).attr('name') + ']:first'));
			PreDOM($('.enterIndex'),last_radio);
			}
		}
		return false;
	}
	
}
function NextDOM(myjQueryObjects,counter) {
	if (myjQueryObjects.eq(counter+1)[0].disabled) {
		NextDOM(myjQueryObjects, counter + 1);
	}
	else {
		myjQueryObjects.eq(counter + 1).trigger('focus');
	}
}
function PreDOM(myjQueryObjects,counter) {
	if (myjQueryObjects.eq(counter-1)[0].disabled) {
		PreDOM(myjQueryObjects, counter - 1);
	}
	else {
		myjQueryObjects.eq(counter - 1).trigger('focus');
	}
}
</script>