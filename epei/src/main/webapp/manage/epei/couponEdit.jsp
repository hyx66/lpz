<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_coupon_editform" action="${pageContext.request.contextPath}/manage/epei/coupon/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="coupon" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%">优惠券类型：</th>
				<td><input type="text" name="couponType" class="easyui-validatebox" data-options="required:true" validType="byteLength[1,16]"/></td>
			</tr>					
			<tr>
				<th>优惠券使用目标：</th>
				<td><input type="text" name="useTarget" class="easyui-validatebox" data-options="required:true" validType="byteLength[1,16]"/></td>
			</tr>					
			<tr>
				<th>所属会员：</th>
				<td><input type="text" name="customerId" class="easyui-numberbox" data-options="required:true" validType="byteLength[1,20]"/></td>
			</tr>					
			<tr>
				<th>是否已使用：</th>
				<td><input type="text" name="used" class="easyui-numberbox"  validType="byteLength[1,1]"/></td>
			</tr>					
			<tr>
				<th>使用时间：</th>
				<td><input type="text" name="useTime" size="15" value="<fmt:formatDate value="${coupon.useTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"  /></td>
			</tr>					
			<tr>
				<th>面额：</th>
				<td><input type="text" name="denomination" class="easyui-numberbox"  validType="byteLength[1,4]"/></td>
			</tr>					
			<tr>
				<th>到期日：</th>
				<td><input type="text" name="expiryDate" size="15" value="<fmt:formatDate value="${coupon.expiryDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"  /></td>
			</tr>					
			<tr>
				<th>生成时间：</th>
				<td><input type="text" name="createTime" size="15" value="<fmt:formatDate value="${coupon.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" data-options="required:true" /></td>
			</tr>					
			<tr>
				<th>更新时间：</th>
				<td><input type="text" name="updateTime" size="15" value="<fmt:formatDate value="${coupon.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"  /></td>
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