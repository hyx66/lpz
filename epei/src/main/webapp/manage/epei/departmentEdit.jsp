<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<script>
    $(function(){
        $("input[name='name']").focus();
    })

</script>

<div>
    <form id="manage_department_editform" action="${pageContext.request.contextPath}/manage/epei/department/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="department" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th><span style="color: red">*</span>科室名称：</th>
				<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true"
                           validType="byteLength[1,64]"/></td>
			</tr>
			<tr>
				<th><span style="color: red">*</span>所属医院：</th>
				<td>
					<select name="hospitalId" style="width:153px">
					<c:forEach items="${hospitals}" var="hospital">
					<option value="${hospital.id}">${hospital.name}</option>
					</c:forEach>
					</select>
				</td>
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
$.extend($.fn.validatebox.defaults.rules, {     
    money : {     
         validator: function(value){     
             return /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/i.test(value);     
         },     
         message: '请输入正确的金额'    
     } 
 }); 
</script>