<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<script>
 $.extend($.fn.validatebox.defaults.rules, {  
	articleUrl : {     
       validator: function(value){    
  return /^((https?|ftp|news):\/\/)?([a-z]([a-z0-9\-]*[\.。])+([a-z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel)|(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))(\/[a-z0-9_\-\.~]+)*(\/([a-z0-9_\-\.]*)(\?[a-z0-9+_\-\.%=&]*)?)?(#[a-z][a-z0-9_]*)?$/i.test(value);   
     },     
       message: '请选择正确的文章地址'
    }     
});  
$.extend($.fn.validatebox.defaults.rules, {     
	memo : {     
         validator: function(value){     
             return /^\S{0,100}$/i.test(value);     
         },     
         message: '输入超过限制内容，请输入一百字以内'  
     }     
 });  
</script>
<div>
	<form id="manage_advertisement_editform"
		action="${pageContext.request.contextPath}/manage/epei/advertisement/${action=='create'?'saveJson':'updateJson'}.html" method="post">
		<jodd:form bean="advertisement" scope="request">
			<input name="id" type="hidden" />
			<table class="tableForm" width="100%">
				<tr>
					<th><span style="color: red">*</span>文章地址</th>
					<td><input type="text" name="articleUrl"
						class="easyui-validatebox" required="true" validType="articleUrl" /></td>
				</tr>
				<tr>
					<th><span style="color: red">*</span>图片地址</th>
					<td><input type="text" name="imageUrl"
						class="easyui-validatebox" required="true" validType="imageUrl" /></td>
				</tr>
				<tr>
					<th><span style="color: red">*</span>优先级</th>
					<td><input type="text" name="priority"
						class="easyui-validatebox" required="true" /></td>
				</tr>
				<tr>
					<th><span style="color: red">*</span>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</th>
					<td><input  type="text" name="memo" class="easyui-validatebox"
					 required="true" validType="memo"  maxlength='100' missingMessage="请输入1~100字"/></td>
				</tr>

			</table>
		</jodd:form>
	</form>
</div>


