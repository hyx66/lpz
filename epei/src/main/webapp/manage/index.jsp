<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/manage/common/meta.jsp" />
<jsp:include page="/manage/common/include.jsp" />
<script type="text/javascript">
	//注册全局ajax错误处理：Session过期
	$(document).ajaxComplete(function(event, xhr, settings) {
		var sessionState = xhr.getResponseHeader("SessionState");
		if (sessionState && sessionState == '1') {
			$.messager.alert('提示', '会话过期，请重新登录', 'info', function() {
				window.location.href = '${pageContext.request.contextPath}/manage/logout.html';
			});
		}
	});
	var token = $("meta[name='X-CSRF-TOKEN']").attr("content");//从meta中获取token
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader("X-CSRF-TOKEN", token);//每次ajax提交请求都会加入此token
	});
</script>
</head>

<body id="mainLayout" class="easyui-layout" style="margin-left: 2px; margin-right: 2px;">
	<div data-options="region:'north',border:false,href:'${pageContext.request.contextPath}/manage/layout/north.jsp'" style="height: 66px; overflow: hidden;"></div>
	<div id="mainWestLayout" title="功能菜单" data-options="tools: [{ iconCls:'icon-refresh',handler:function(){$.acooly.layout.reloadMenu();}}],region:'west',href:'${pageContext.request.contextPath}/manage/layout/west.jsp'" style="width: 210px; overflow: hidden;"></div>
	<div data-options="region:'center',href:'${pageContext.request.contextPath}/manage/layout/center.jsp'" style="overflow: hidden;"></div>
</body>
</html>
