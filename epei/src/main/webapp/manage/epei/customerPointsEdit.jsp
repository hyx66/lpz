<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<script>
    $(function(){
        $("input[name='name']").focus();
    })

</script>

<div>
    <form id="manage_customerPoints_editform" action="${pageContext.request.contextPath}/manage/epei/customerPoints/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="customerPoints" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th>会员ID：</th>
				<td><input type="text" name="customerId" class="easyui-validatebox" data-options="required:true" validType="byteLength[1,32]"/></td>
			</tr>
			<tr>
				<th>能力值：</th>
				<td><input type="text" name="capacityValue" class="easyui-validatebox" data-options="required:true" validType="byteLength[1,32]"/></td>
			</tr>
			<tr>
				<th>积分：</th>
				<td><input type="text" name="points" class="easyui-validatebox" data-options="required:true" validType="byteLength[1,32]"/></td>
			</tr>
			<tr>
				<th>变更时间：</th>
				<td><input type="text" name="modifyTime" class="easyui-validatebox" data-options="required:true" validType="byteLength[1,32]"/></td>
			</tr>
			<tr>
				<th>DAC：</th>
				<td><input type="text" name="dac" class="easyui-validatebox" data-options="required:true" validType="byteLength[1,32]"/></td>
			</tr>
			<tr>
				<th>状态：</th>
				<td><input type="text" name="status" class="easyui-validatebox" data-options="required:true" validType="byteLength[1,32]"/></td>
			</tr>
							
        </table>
      </jodd:form>
    </form>
</div>
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