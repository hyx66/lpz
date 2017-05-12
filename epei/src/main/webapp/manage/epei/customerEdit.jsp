<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div>
    <form id="manage_customer_editform" action="${pageContext.request.contextPath}/manage/epei/customer/${action=='create'?'saveJson':'updateJson'}.html" method="post">
      <jodd:form bean="customer" scope="request">
        <input name="id" type="hidden" />
        <table class="tableForm" width="100%">
			<tr id="ifMobileExist">
				<th>账号(手机号)：</th>
				<td><input type="text" name="userName" class="easyui-validatebox" data-options="required:true" validType="phone"/></td>
			</tr>					
			<tr id="ifPasswordExist">
				<th>密码：</th>
				<td><input type="password" name="password" class="easyui-validatebox" data-options="required:true" validType="byteLength[8,18]"/></td>
			</tr>
			<tr id="ifCustomerTypeExist">
				<th>用户类型：</th>
				<td>
					<select name="customerType" style="width:153px" >
					  <option value ="0" <c:if test="${customer.customerType==0}">selected</c:if> >普通会员</option>
					  <option value ="1" <c:if test="${customer.customerType==1}">selected</c:if> >医院管理员</option>
					</select>
					<input type="hidden" name="customerHosId" value="-1"/>
				</td>
			</tr>
			<tr>
				<th>VIP级别：</th>
				<td><input id="vipId" name="vipId" class="easyui-validatebox" value="${customer.vipId}" /></td>
			</tr>	
			<tr style="height:1px;border:none;border-bottom:1px solid #87CEEB;">
				<th>VIP购买商：</th>
				<td><input id="vipCompanyId" name="vipCompanyId" class="easyui-validatebox" value="${customer.vipCompanyId}" validType="companyId" /></td>
			</tr>	
			<tr>
				<th>姓名：</th>
				<td><input type="text" name="name" data-options="required:true" class="easyui-validatebox" validType="byteLength[1,32]"/></td>
			</tr>
			<tr>
				<th>联系电话：</th>
				<td><input type="text" name="phoneNumber" data-options="required:true" class="easyui-validatebox" validType="byteLength[1,16]"/></td>
			</tr>
			<tr>
				<th>身份证号码：</th>
				<td><input type="text" name="idCard" data-options="required:true" class="easyui-validatebox" validType="idcard"/></td>
			</tr>
			<tr>
				<th>推荐人电话：</th>
				<td><input type="text" name="referenceMobile" class="easyui-validatebox" validType="byteLength[0,16]"/></td>
			</tr>
			<tr>
				<th>性别：</th>
				<td>
					<select name="sex" style="width:153px" >
					  <option value ="1" <c:if test="${customer.sex==1}">selected</c:if> >男</option>
					  <option value ="0" <c:if test="${customer.sex==0}">selected</c:if> >女</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>生日：</th>
				<td><input type="text" name="birthday" class="easyui-validatebox" placeholder="格式：19000101" validType="birthday"/></td>
			</tr>
			<tr>
				<th>籍贯：</th>
				<td><input type="text" name="nativePlace" class="easyui-validatebox" validType="byteLength[1,128]"/></td>
			</tr>
			<tr>
				<th>邮箱：</th>
				<td><input type="text" name="email" class="easyui-validatebox" validType="email"/></td>
			</tr>
			<tr>
				<th>职业：</th>
				<td><input type="text" name="profession" class="easyui-validatebox" validType="byteLength[1,32]"/></td>
			</tr>
			<tr>
				<th>地址：</th>
				<td><input type="text" name="address" class="easyui-validatebox" validType="byteLength[1,128]"/></td>
			</tr>
			<tr>
				<th>家庭成员：</th>
				<td><input type="text" name="family" class="easyui-validatebox" validType="byteLength[1,128]"/></td>
			</tr>
			<tr>
				<th>婚姻状况：</th>
				<td>
					<select name="maritalStatus" style="width:153px" >
					  <option value ="1" <c:if test="${customer.maritalStatus==1}">selected</c:if> >已婚</option>
					  <option value ="0" <c:if test="${customer.maritalStatus==0}">selected</c:if> >未婚</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>文化程度：</th>
				<td><input type="text" name="degreeOfEducation" class="easyui-validatebox" validType="byteLength[1,32]"/></td>
			</tr>
			<tr>
				<th>紧急联系人：</th>
				<td><input type="text" name="emergencyContactPerson" class="easyui-validatebox" validType="byteLength[1,32]"/></td>
			</tr>
			<tr>
				<th>与紧急联系人关系：</th>
				<td><input type="text" name="emergencyContactRelationship" class="easyui-validatebox" validType="byteLength[1,32]"/></td>
			</tr>
			<tr>
				<th>紧急联系人电话：</th>
				<td><input type="text" name="emergencyContactNumber" class="easyui-validatebox" validType="byteLength[1,16]"/></td>
			</tr>
			<tr>
				<th>个人既往病史：</th>
				<td><input type="text" name="medicalHistory" class="easyui-validatebox" validType="byteLength[1,64]"/></td>
			</tr>
			<tr>
				<th>家族遗传史：</th>
				<td><input type="text" name="geneticHistory" class="easyui-validatebox" validType="byteLength[1,64]"/></td>
			</tr>
			<tr>
				<th>生活习惯：</th>
				<td><input type="text" name="habits" class="easyui-validatebox" validType="byteLength[1,64]"/></td>
			</tr>
			<tr>
				<th>膳食结构：</th>
				<td><input type="text" name="diet" class="easyui-validatebox" validType="byteLength[1,64]"/></td>
			</tr>
			<tr>
				<th>体检情况：</th>
				<td><input type="text" name="physical" class="easyui-validatebox" validType="byteLength[1,64]"/></td>
			</tr>
			<tr>
				<th>会员来源：</th>
				<td>
				<select name="source" style="width:153px" >
				  <option value ="网络" <c:if test="${customer.source=='网络'}">selected</c:if> >网络</option>
				  <option value ="朋友推荐" <c:if test="${customer.source=='朋友推荐'}">selected</c:if> >朋友推荐</option>
				  <option value="医院偶然发现" <c:if test="${customer.source=='医院偶然发现'}">selected</c:if> >医院偶然发现</option>
				  <option value="活动现场推广" <c:if test="${customer.source=='活动现场推广'}">selected</c:if> >活动现场推广</option>
				</select>
				</td>
			</tr>
			<tr>
				<th>会员特权信息通知方式：</th>
				<td>
				<select name="receiveInfo" style="width:153px" >
				  <option value ="免费短信" <c:if test="${customer.receiveInfo=='免费短信'}">selected</c:if> >免费短信</option>
				  <option value ="电话" <c:if test="${customer.receiveInfo=='电话'}">selected</c:if> >电话</option>
				  <option value="邮箱" <c:if test="${customer.receiveInfo=='邮箱'}">selected</c:if> >邮箱</option>
				  <option value="QQ" <c:if test="${customer.receiveInfo=='QQ'}">selected</c:if> >QQ</option>
				</select>
				</td>
			</tr>
        </table>
      </jodd:form>
    </form>
</div>
<script type="text/javascript">
$(function () {
	if("${action}"!="create"){
		$("#ifPasswordExist").html("");
		$("#ifCustomerTypeExist").html("");
	}
});

$.extend($.fn.validatebox.defaults.rules, {
    idcard: {// 验证身份证
        validator: function (value) {
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message: '请输入有效的身份证号'
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
    mobile : {     
         validator: function(value){     
             return /^1[0-9]{10}$/i.test(value);     
         },     
         message: '请输入正确的手机号'    
     }     
 });  
 
$.extend($.fn.validatebox.defaults.rules, {     
    email : {     
         validator: function(value){     
             return /^[a-zA-Z0-9_+.-]+\@([a-zA-Z0-9-]+\.)+[a-zA-Z0-9]{2,4}$/i.test(value);     
         },     
         message: '请输入正确的邮箱地址'    
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

$.extend($.fn.validatebox.defaults.rules, {
    companyId: {// 手机号
        validator: function () {
            var vipId = $("input[name='vipId']").val();
            var companyId = $("input[name='vipCompanyId']").val();
            if(vipId =="" && companyId !=""){
            	return false;
            }else{
            	return true;
            }
        },
        message: '非VIP会员无法设置VIP购买商'
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
//easyUI异步查询vip
$("#vipId").combobox({
	url:"/manage/epei/customer/queryVipAll.html",
	valueField:"id",
	textField:"name",
	formatter:function(row){
		return row.name;
	},
});
//easyUI异步查询购买VIP会员的企业方
$("#vipCompanyId").combobox({
	url:"/manage/epei/customer/queryCompanyAll.html",
	valueField:"id",
	textField:"name",
	formatter:function(row){
		return row.name;
	},
});
</script>