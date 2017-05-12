<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/manage/common/meta.jsp" />
<jsp:include page="/manage/common/include.jsp" />

<script type="text/javascript">
  
  function refreshCaptcha(){
	  $('#jcaptchaImage').attr("src","/jcaptcha.jpg?"+new Date());
  }
  
  $(function() {
    var formParam = {
      url : '${pageContext.request.contextPath}/manage/login.html',
      method:'POST',
      success : function(result) {
        var r = $.parseJSON(result);
        if (r.success) {
          $('#user_login_loginDialog').dialog('close');
          window.location.href="${pageContext.request.contextPath}/manage/index.jsp";
        } else {
        	refreshCaptcha();
        	$('#message').html(r.message);
        }
      }
    };
    $('#loginForm').form(formParam);
    $('#user_login_loginDialog').show().dialog({
      modal : true,
      title : '系统登录',
      closable : false,
      buttons : [ {
        text : '登录',
        handler : function() {
          $("#loginForm").submit();
        }
      } ]
    });
    
    $('#captcha').keydown(function(event){
    	if(event.keyCode == 13){
    		$("#loginForm").submit();
    	}
    });

  });
</script>

</head>

<body>
  <div id="user_login_loginDialog" style="display: none; width: 400px; height: 290px; overflow: hidden;">
    <div id="user_login_loginTab">
      <div title="系统登录" style="overflow: hidden;">
        <div>
          <div style="height: 45px; border-bottom: 1px solid #99BBE8;"><img src="<ap:static var="FRAMWORK_LOGO" clazz="com.acooly.module.security.SecurityConstants" />"></div>
        </div>
		<div>
			<div class="info">
				<div class="tip icon-tip"></div>
				<div id="message">请注意账户安全！</div>
			</div>
		</div>
        <div align="center" style="margin-top: 10px;">
          <form id="loginForm" method="post">
          	<input type="hidden" name="_csrf" value="${requestScope["org.springframework.security.web.csrf.CsrfToken"].token}"/>
            <table class="tableForm" width="100%">
              <tr>
                <th width="32%">登录名</th>
                <td><input name="username" class="easyui-validatebox"  data-options="required:true" style="width: 150px;height: 25px;padding-left: 2px;"/></td>
              </tr>
              <tr>
                <th>密&nbsp;&nbsp;码</th>
                <td><input type="password" name="password" class="easyui-validatebox" data-options="required:true" style="width: 150px;height: 25px;padding-left: 2px;" /></td>
              </tr>
              <tr>
                <th>验证码</th>
                <td><img id="jcaptchaImage" onclick="refreshCaptcha()" src="${pageContext.request.contextPath}/jcaptcha.jpg" height="30px" width="70px" align="top"> 
                <input type="text" id="captcha" name="captcha" class="easyui-validatebox" data-options="required:true" style="width: 73px;height: 25px;padding-left: 2px;" />
                </td>
              </tr>
            </table>
          </form>
        </div>
      </div>
    </div>
  </div>
</body>
</html>