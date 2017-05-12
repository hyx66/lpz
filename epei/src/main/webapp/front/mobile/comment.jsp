<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
 <meta charset="utf-8"> 
 <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
 <title>评论 －医护陪诊第一平台</title>
<link rel="stylesheet" href="/front/mobile/css/normalize-min.css" />
<link rel="stylesheet" href="/front/mobile/css/style.css" />
<style type="text/css">
#a {
  padding-left: 0;
  overflow: hidden;
 }
#a li {
  float: left;
  list-style: none;
  width: 27px;
  height: 27px;
  background: url(/front/mobile/images/star.gif)
 }
 #a li a {
  display: block;
  width: 100%;
  padding-top: 27px;
  overflow: hidden;
 }
 #a li.light {
  background-position: 0 -29px;
 }
 #fwry {
  	position:absolute;
	left:5%;
	top:17%;
 }
  #star {
  	position:absolute;
	left:31%;
	top:28%;
  }
  #text{
  	position:absolute;
  	left:11%;
	top:40%; 
  }
.button {
display: inline-block;
zoom: 1; /* zoom and *display = ie7 hack for display:inline-block */
*display: inline;
vertical-align: baseline;
margin: 0 2px;
outline: none;
cursor: pointer;
text-align: center;
text-decoration: none;
font: 14px/100% Arial, Helvetica, sans-serif;
padding: .5em 2em .55em;
text-shadow: 0 1px 1px rgba(0,0,0,.3);
-webkit-border-radius: .5em;
-moz-border-radius: .5em;
border-radius: .5em;
-webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2);
-moz-box-shadow: 0 1px 2px rgba(0,0,0,.2);
box-shadow: 0 1px 2px rgba(0,0,0,.2);
}  
.button:hover {
text-decoration: none;
}
.button:active {
position: relative;
top: 1px;
} 
</style>
</head>
<body>
<div class="breadcrumb">
	<div class="path" onclick="window.history.go(-1)"><i><img src="/front/mobile/images/leftarrow.png"></i>
		服务评价
	</div>
</div>
<h4 id = 'fwry'><span style="color:red">${order.servicePerson}</span>为您服务</h4>

<input id ="grade" type ="hidden" name="fraction" value="${order.star}">
<input id ="result" type ="hidden" name="orderNo" value="${order.orderNo}">

<div id="star">
	<ul id = "a">
		 <li  class="light"><a href="javascript:;"></a></li>
		 <li><a href="javascript:;"></a></li>
		 <li><a href="javascript:;"></a></li>
		 <li><a href="javascript:;"></a></li>
		 <li><a href="javascript:;"></a></li>
	</ul>
</div>

<div id = "text" align="center">
	<textarea  maxlength="100" id = 'txt' name="comment" cols=40 rows=4></textarea>
	<div><br/>
		<button id ='tj'  class="button" style="width:46%" type="button" >提交</button>
		<button id = 'qx' class="button" style="width:46%"   >清空</button> 
	</div>
</div>

<jsp:include page="mobile_footer.jsp"></jsp:include>
<jsp:include page="popup.jsp"></jsp:include>

<script type="text/javascript" src="/front/mobile/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
var num=finalnum = tempnum= 0;
var lis = document.getElementsByTagName("li");
//num:传入点亮星星的个数
//finalnum:最终点亮星星的个数
//tempnum:一个中间值
 function fnShow(num) {
 finalnum= num || tempnum;//如果传入的num为0，则finalnum取tempnum的值
 for (var i = 0; i < lis.length; i++) {
  lis[i].className = i < finalnum? "light" : "";//点亮星星就是加class为light的样式
 }
}
for (var i = 1; i <= lis.length; i++) {
 lis[i - 1].index = i;
 lis[i - 1].onmouseover = function() { //鼠标经过点亮星星。
  fnShow(this.index);//传入的值为正，就是finalnum
 }
 lis[i - 1].onmouseout = function() { //鼠标离开时星星变暗
  fnShow(0);//传入值为0，finalnum为tempnum,初始为0
 }
 lis[i - 1].onclick = function() { //鼠标点击,同时会调用onmouseout,改变tempnum值点亮星星
  tempnum= this.index;
 $("#grade").val(tempnum);
 }
} 
$(document).ready(function(){
	$("#qx").click(function(){	
		$("#txt").val('');
	})
	$("#tj").click(function(){
		var fraction = $("#grade").val();
		var orderNo = $("#result").val();
		var comment = $("#txt").val();
		if(fraction==''){
			popup("请给我们的服务人员评分哟~~")
		}else{
			if(comment==''){
				popup("评论内容不可以为空哟~~")
			}else{
		$.ajax({
	        type: "get",
	        dataType: "json",
	        url: '${pageContext.request.contextPath}/order/commentInsert.html',
	        data:{"fraction":fraction,"orderNo":orderNo,"comment":comment},  
	        success: function (data) {
	           if(data=="操作成功"){
	        	   alert(data);
	        	   window.location.href='/order/detail.html?orderNo='+orderNo;
	           }else{
	        	   popup("您已评论过了哦~~~");
	           }
	        }
	    });	
		}
		}
	})
})
</script> 
</body>
</html>