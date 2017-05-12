<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<style type="text/css">
.ztree li span.button.switch.level0 {visibility:hidden; width:1px;}
.ztree li ul.level0 {padding:0; background:none;}
</style>
<script type="text/javascript">
$(function() {
	setTimeout("$.acooly.layout.loadTree()",1000);
});
</script>
<div id="menuLayout" class="easyui-layout" data-options="fit:true,border:false">
  <div data-options="region:'center',border:false">
    <ul id="layout_west_tree" class="ztree" style="margin-top: 5px;margin-left: 5px;">加载中...</ul>
  </div>
</div>
