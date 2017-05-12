<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>


<script type="text/javascript">
    $(function() {
        $.acooly.framework.registerKeydown('manage_servicePerson_${serviceType}_searchform','manage_servicePerson_${serviceType}_datagrid');
    });
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_servicePerson_${serviceType}_searchform" onsubmit="return false">
            <table class="tableForm" width="100%">
                <tr>
                    <td align="left">
                    	入职时间:<input size="15" id="search_GTE_hireDate" name="search_GTE_hireDate" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" />
						至<input size="15" id="search_LTE_hireDate" name="search_LTE_hireDate" size="15" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})" /> 	
				                        姓名:<input type="text" size="15" name="search_LIKE_name" value="${param.search_LIKE_name}"  />
				                        手机号:<input type="text" size="15" name="search_EQ_mobile" value="${param.search_EQ_mobile}"  />
				                        身份证号:<input type="text" size="15" name="search_LIKE_idCard" value="${param.search_LIKE_idCard}"  />
				                        所属医院:
                        <select name="search_LIKE_hospital" class="easyui-combobox"  data-options="panelHeight:'auto',editable:false">
                            <option value="">全部</option>
                            <c:forEach var="hospital" items="${hospitals}">
                                <option value="${hospital.name}">${hospital.name}</option>
                            </c:forEach>
                        </select>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false" onclick="$.acooly.framework.search('manage_servicePerson_${serviceType}_searchform','manage_servicePerson_${serviceType}_datagrid');">查询</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_servicePerson_${serviceType}_datagrid" class="easyui-datagrid"
               url="${pageContext.request.contextPath}/manage/epei/servicePerson/listJson.html?search_EQ_serviceType=${serviceType}"
               toolbar="#manage_servicePerson_${serviceType}_toolbar" fit="true" border="false" fitColumns="true"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc" checkOnSelect="true" selectOnCheck="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id">编号</th>
                <th field="name">姓名</th>
                <th field="mobile">手机号</th>
                <th field="idCard">身份证号</th>
                <th field="hospital">所属医院</th>
                <th field="hireDate" formatter="formatDate">入职时间</th>
                <th field="empNo">员工编号</th>
                <th field="createTime" >创建时间</th>
                <th field="rowActions" data-options="formatter:function(value, row, index){return formatAction('manage_servicePerson_${serviceType}_action',value,row)}">动作</th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_servicePerson_${serviceType}_action" style="display: none;">
            <a class="line-action icon-edit"
               onclick="$.acooly.framework.edit({url:'/manage/epei/servicePerson/edit.html',id:'{0}',form:'manage_servicePerson_editform',datagrid:'manage_servicePerson_${serviceType}_datagrid',width:500,height:400});" href="#" title="编辑"></a>
            <a class="line-action icon-show" onclick="$.acooly.framework.show('/manage/epei/servicePerson/show.html?id={0}',500,400);" href="#" title="查看"></a>
            <a class="line-action icon-delete" onclick="$.acooly.framework.remove('/manage/epei/servicePerson/deleteJson.html','{0}','manage_servicePerson_${serviceType}_datagrid');" href="#" title="删除"></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_servicePerson_${serviceType}_toolbar">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="$.acooly.framework.create({url:'/manage/epei/servicePerson/create.html?serviceType=${serviceType}',entity:'servicePerson',width:500,height:400})">添加</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="$.acooly.framework.exports('/manage/epei/servicePerson/exportXls.html?search_EQ_serviceType=${serviceType}','manage_servicePerson_${serviceType}_searchform','${serviceType.message}人员')">批量导出</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-import" plain="true" onclick="$.acooly.framework.imports({url:'/manage/epei/servicePerson/importView.html',uploader:'manage_servicePerson_import_uploader_file'});">批量导入</a>
          </div>
    </div>

</div>
