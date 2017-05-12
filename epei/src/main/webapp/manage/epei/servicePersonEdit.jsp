<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<script>
    $(function(){
        $("input[name='name']").focus();

        $.extend($.fn.validatebox.defaults.rules, {
            idcard: {// 验证身份证
                validator: function (value) {
                    return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
                },
                message: '身份证号码格式不正确'
            }});
        
        $.extend($.fn.validatebox.defaults.rules, {
            phone: {// 验证手机号
                validator: function (value) {
                    return  /^1\d{10}$/i.test(value);
                },
                message: '手机号码格式不正确'
            }});
    })


</script>
<div>
    <form id="manage_servicePerson_editform" action="${pageContext.request.contextPath}/manage/epei/servicePerson/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="servicePerson" scope="request">
        <c:choose>
            <c:when test="${action == 'create'}">
                <input name="serviceType" type="hidden" value="${serviceType}"/>
            </c:when>
            <c:otherwise>
                <input name="serviceType" type="hidden"/>
            </c:otherwise>
        </c:choose>
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr>
				<th width="25%"><span style="color: red">*</span>姓名：</th>
				<td><input type="text" name="name" class="easyui-validatebox"  validType="byteLength[1,32]"
                           required="true"/></td>
			</tr>					
			<tr>
				<th><span style="color: red">*</span>手机号：</th>
				<td><input type="text" name="mobile" class="easyui-numberbox"  validType="phone" required="true"/></td>
			</tr>					
			<tr>
				<th><span style="color: red">*</span>身份证号：</th>
				<td><input type="text" name="idCard" class="easyui-validatebox"  validType="idcard"
                           required="true"/></td>
			</tr>					
			<tr>
				<th>所属医院：</th>
				<td>
                    <select name="hospitalId" class="easyui-combobox"  data-options="panelHeight:'auto',editable:false"
                            style="width: 153px;" required="true" >
                        <c:forEach var="hospital" items="${hospitals}">
                            <option value="${hospital.id}">${hospital.name}</option>
                        </c:forEach>
                    </select>
                </td>
			</tr>
			<tr>
               	<th><span style="color: red">*</span>入职时间：</th>
                <td><input type="text" name="hireDate" class="easyui-validatebox" value="<fmt:formatDate
					value="${hireDate}" pattern="yyyy-MM-dd"/>" 
					onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"
					data-options="required:true"/>
				</td>
            </tr>
            <tr>
                <th>员工编号：</th>
                <td><input type="text" name="empNo" class="easyui-validatebox"/></td>
            </tr>
            <tr>
                <th>工作状态：</th>
                <td>
                <input type="radio" name="serviceState" value="0" checked class="easyui-validatebox"/>等待
                <input type="radio" name="serviceState" value="1" class="easyui-validatebox"/>正忙
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
</script>