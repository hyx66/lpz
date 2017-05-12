<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div align="center">
  <table class="tableForm" width="100%">
    <tr>
      <th width="30%">ID：</th>
      <td>${olog.id}</td>
    </tr>
    <tr>
      <th width="30%">功能模块：</th>
      <td>${olog.moduleName}[${olog.module}]</td>
    </tr>    
    <tr>
      <th width="30%">操作：</th>
      <td>${olog.actionName}[${olog.action}]</td>
    </tr>
    <tr>
      <th width="30%">客户端信息：</th>
      <td>${olog.clientInformations}</td>
    </tr>
    <tr>
      <th width="30%">执行时间：</th>
      <td>${olog.executeMilliseconds}</td>
    </tr>

    <tr>
      <th width="30%">操作时间：</th>
      <td>${olog.operateTime}</td>
    </tr>
    <tr>
      <th width="30%">操作员：</th>
      <td>${olog.operateUser}</td>
    </tr>
    <tr>
      <th width="30%">请求参数：</th>
      <td>${olog.requestParameters}</td>
    </tr>
    <tr>
      <th width="30%">操作结果：</th>
      <td>${allOperateResults[olog.operateResult]}${olog.operateMessage != null?':':''}${olog.operateMessage}</td>
    </tr> 
    <tr>
      <th width="30%">备注：</th>
      <td>${olog.descn}</td>
    </tr>       
  </table>
</div>
