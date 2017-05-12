<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<script type="text/javascript">
$(function() {
	$.acooly.framework.createUploadify({
				/** 上传导入的URL */
				url:'/manage/epei/servicePerson/importJson.html?_csrf=${requestScope["org.springframework.security.web.csrf.CsrfToken"].token}&splitKey=v',
				/** 导入操作消息容器 */
				messager:'manage_servicePerson_import_uploader_message',
				/** 上传导入文件表单ID */
				uploader:'manage_servicePerson_import_uploader_file',
				jsessionid:'<%=request.getSession().getId()%>'
	});	
});
</script>
<div align="center">
<table class="tableForm" width="100%">
  <tr>
    <th width="30%">文件类型：</th>
    <td>
    目前仅支持Excel2000(*.xls),导入的excel内容及格式请参考模板文件。&nbsp; <a href="#" onclick="$.acooly.framework.exports('/manage/epei/servicePerson/downloadServicePersonExcelModel.html','manage_ServicePerson_searchform','服务人员')">点击下载</a>
    </td>
  </tr>	
  <tr>
    <th width="30%" height="15"></th>
    <td><div id="manage_servicePerson_import_uploader_message" style="color: red;"></div></td>
  </tr>          				
  <tr>
    <th width="30%">文件：</th>
    <td>
    <input type="file" name="manage_servicePerson_import_uploader_file" id="manage_servicePerson_import_uploader_file" />
    </td>
  </tr>		
</table>
</div>