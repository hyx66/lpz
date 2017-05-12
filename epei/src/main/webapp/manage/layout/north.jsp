<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<style>

.topbox{ }
.bg{height:66px; padding:3px 5px 0px 5px;background: #27A9FB;
background: -moz-linear-gradient(left, #27A9FB 0%, #E0ECFF 33%, #E0ECFF 53%, #E0ECFF 61%, #27A9FB 100%);
background: -webkit-gradient(linear, left top, right top, color-stop(0%,#27A9FB), color-stop(33%,#E0ECFF), color-stop(53%,#E0ECFF), color-stop(61%,#27A9FB), color-stop(100%,#65BEF2));
background: -webkit-linear-gradient(left, #27A9FB 0%,#E0ECFF 33%,#E0ECFF 53%,#E0ECFF 61%,#27A9FB 100%); 
background: -o-linear-gradient(left, #27A9FB 0%,#E0ECFF 33%,#E0ECFF 53%,#E0ECFF 61%,#ff	7cd8 100%); 
filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#27A9FB', endColorstr='#E0ECFF',GradientType=1 ); 
background: linear-gradient(left, #27A9FB 0%,#E0ECFF 33%,#E0ECFF 53%,#E0ECFF 61%,#27A9FB 100%);
}

.top{ overflow:auto; clear:both;background: url(<ap:static var="FRAMWORK_LOGO" clazz="com.acooly.module.security.SecurityConstants" />) 0 0 no-repeat;}
.logobox{ display:inline;  float:left;
	height:34px;
	line-height:42px;
	overflow:hidden;
	padding-left: 75px;
	font-family: "微软雅黑","Microsoft YaHei", "宋体", "Segoe UI", Tahoma, Arial; 
	font-size: 16px;
	font-weight: bold;
	font-style:italic;
	color: #ffffff;
	letter-spacing:2px;
}
.topright{ float:right; margin-top:6px; text-align: right;}
.userinfo{ height:22px; line-height:22px;vertical-align:middle; float:left; margin-right:20px; color:#fff;}
.nav{ margin-top:6px;font-family: '微软雅黑';}
a.button{text-decoration:none; height:25px; float:left;  cursor:hand; color:#333; margin-right:5px;} 
a.button:hover{background:url(/manage/image/nav_button_bg.gif) left 0px;}
a.button span{float:left; height:25px; margin-left:6px; padding-right:6px; line-height:25px;} 
a.button:hover span{background:url(/manage/image/nav_button_bg.gif) right -25px;}
a.button span img{ width:16px; height:16px; border:none; vertical-align:middle; margin-top:-2px; margin-right:4px;}
a.buttonaft{background:url(/manage/image/nav_button_bg2.gif) left 0px; text-decoration:none; height:26px; float:left;  cursor:hand; color:#333; margin-right:5px;}
a.buttonaft span{background:url(/manage/image/nav_button_bg2.gif) right -26px;  float:left; height:26px; margin-left:6px; padding-right:6px; line-height:25px;} 
a.buttonaft span img{ width:16px; height:16px; border:none; vertical-align:middle; margin-top:-2px; margin-right:4px;}
a.button1{background:url(/manage/image/nav_button_bg1.gif) left top; text-decoration:none; height:22px; float:left;  cursor:hand; color:#fff; margin-right:5px;} 
a.button1:hover{background:url(/manage/image/nav_button_bg1.gif) left -44px;}
a.button1 span{background:url(/manage/image/nav_button_bg1.gif) right -22px;  float:left; height:22px; margin-left:6px; padding-right:6px; line-height:22px;} 
a.button1:hover span{background:url(/manage/image/nav_button_bg1.gif) right -66px;}
a.button1 span img{ width:16px; height:16px; border:none; vertical-align:middle; margin-top:-2px; margin-right:4px;}
</style>
<script type="text/javascript">
$(function() {
	$.acooly.layout.loadMenu();
});
</script>

<div class="topbox">
  <div class="bg">
  <div class="top">
  <div class="logobox"></div>
  <div class="topright">
    <div class="userinfo"><shiro:principal /></div>
    <div style="float: right;">
    <a href="javascript:void(0);" onclick="$.acooly.framework.changePassword();" class="button1"><span><img src="/manage/image/t7.png" />修改密码</span></a>
    <a href="javascript:void(0);" onclick="$.acooly.framework.logout();" class="button1"><span><img src="/manage/image/t8.png" />注销</span></a>
  	</div>
  </div>
  </div>
  <div id="mainMenu" class="nav"></div>
	<div style="position: absolute; right: 0px; bottom: 0px;">
	  <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_menu_theme',iconCls:'icon-theme'" style="color: white;">更换主题</a>
	</div>
	<div id="layout_menu_theme" style="width: 100px; display: none;">
	    <c:set var="cookieThemeName" value="${cookie.easyuiThemeName.value}" />
	    <c:if test="${cookieThemeName == ''}">
	      <c:set var="cookieThemeName" value="default" />
	    </c:if>
	    <div id="theme_default" ${cookieThemeName=='default'?"data-options=\"iconCls:'icon-ok'\"":""} onclick="changeTheme('default');">default</div>
	    <div id="theme_gray" ${cookieThemeName=='gray'?"data-options=\"iconCls:'icon-ok'\"":""} onclick="changeTheme('gray');">gray</div>
	    <div id="theme_metro" ${cookieThemeName=='metro'?"data-options=\"iconCls:'icon-ok'\"":""} onclick="changeTheme('metro');">metro</div>
	</div>
  </div>
</div>
<script type="text/javascript">
$(function() {
	window.setTimeout(runajax,1000*60);
});

function runajax(){
	$.ajax({
		   url:'/manage/epei/orderBase/findOrder.html',  
	       datatype:"json",  
	       type:"post",          
	       success: function (data) {
	    	   //data.length<500这个条件是为了防止登录用户没有赋予访问接口时data不为空但是提示信息有误的情况
	    	   if(data!="termination" && data.length<500){
	    		   if(data!=""){
		    		   $.messager.show({
			    	       title:'未受理订单提醒',
			    	       msg:data,
			    	       timeout:5000,
			    	       showType:'slide'
			    	});
		    		window.setTimeout(runajax,1000*60);
		    	   }
	    	   }
	       }
	   })  
}    
</script>