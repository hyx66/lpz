<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- easyui控件 -->
<link id="easyuiTheme" rel="stylesheet" href="${pageContext.request.contextPath}/manage/plugin/jquery-easyui/themes/<c:out value="${cookie.easyuiThemeName.value}" default="default"/>/easyui.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/manage/plugin/jquery-easyui/themes/icon.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/manage/plugin/jquery/jquery-1.9.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/manage/plugin/jquery/jquery-migrate-1.1.0.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/manage/plugin/jquery-easyui/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/manage/plugin/jquery-easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/manage/plugin/jquery-easyui/plugins/datagrid-detailview.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/manage/plugin/jquery-easyui/plugins/datagrid-groupview.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/manage/script/acooly.jquery.easyui.js" charset="utf-8"></script>
<!-- easyui portal插件 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/manage/plugin/jquery-easyui-portal/portal.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/manage/plugin/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/manage/plugin/jquery-plugin/jquery.cookie.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/manage/plugin/jquery-plugin/jquery.form.js" charset="utf-8"></script>
<!-- 自己定义的样式和JS扩展 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/manage/script/acooly.framework.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/manage/script/acooly.layout.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/manage/script/acooly.system.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/manage/script/acooly.portal.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/manage/style/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/manage/style/basic.css">
<!-- my97日期控件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/manage/plugin/My97DatePicker/WdatePicker.js" charset="utf-8"></script>
<!-- uploadify -->
<script type="text/javascript" src="${pageContext.request.contextPath}/manage/plugin/jquery-uploadify/jquery.uploadify.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/manage/plugin/jquery-uploadify/uploadify.css" />
<!-- ztree -->
<script type="text/javascript" src="${pageContext.request.contextPath}/manage/plugin/jquery-ztree/js/jquery.ztree.core-3.5.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/manage/plugin/jquery-ztree/css/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/manage/plugin/jquery-ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/manage/plugin/jquery-ztree/js/jquery.ztree.exedit-3.5.js"></script>
<!--kindEditor插件库 -->
<script charset="utf-8" src="${pageContext.request.contextPath}/manage/plugin/kindeditor/kindeditor-all-min.js"></script>
<script charset="utf-8" src="${pageContext.request.contextPath}/manage/plugin/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript">
	var contextPath = '${pageContext.request.contextPath}';
</script>
