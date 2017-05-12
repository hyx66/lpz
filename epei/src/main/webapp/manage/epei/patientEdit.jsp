<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<script type="text/javascript">
    $.extend($.fn.validatebox.defaults.rules, {
        idcard: {// 验证身份证
            validator: function (value) {
                return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
            },
            message: '身份证号码格式不正确'
        }});
    
    $.extend($.fn.validatebox.defaults.rules, {     
    	birthday : {     
             validator: function(value){     
                 return /^[12]\d{3}(0\d|1[0-2])([0-2]\d|3[01])$/i.test(value);     
             },     
             message: '曰期格式:19010101'    
         }     
     }); 
    
    $.extend($.fn.validatebox.defaults.rules, {
        phone: {// 手机号
            validator: function (value) {
                return /^1\d{10}$/i.test(value);
            	},
            message: '手机号码格式不正确'
            }
    });
</script>
<div>
    <form id="manage_patient_editform" action="${pageContext.request.contextPath}/manage/epei/patient/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="patient" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th><span style="color: red">*</span>姓名：</th>
				<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true" validType="byteLength[1,32]"/></td>
			</tr>					
			<tr>
				<th><span style="color: red">*</span>手机号：</th>
				<td><input type="text" name="mobile" class="easyui-validatebox" required="true"
                           validType="phone"/></td>
			</tr>
			<tr>
				<th><span style="color: red">*</span>生日：</th>
				<td><input type="text" name="birthday" class="easyui-validatebox" required="true"
                           validType="birthday"/></td>
			</tr>
			<tr>
				<th><span style="color: red">*</span>性别：</th>
				<td>
				<input type="radio" name="gender" value="1" checked="checked"/>男
				<input type="radio" name="gender" value="0"/>女
				</td>
			</tr>			
			<tr>
				<th><span style="color: red">*</span>身份证号：</th>
				<td><input type="text" name="idCard" class="easyui-validatebox" required="true"
                           validType="idcard"/></td>
			</tr>					
			<tr>
				<th>医保卡号：</th>
				<td><input type="text" name="medicareCard" class="easyui-validatebox"  validType="byteLength[1,32]"/></td>
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