<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<div style="padding: 5px;font-family:微软雅黑;">

<script type="text/javascript">
    function resetTrNum(tableId) {
        $tbody = $("#" + tableId + "");
        $tbody.find('>tr').each(function(i){
            $(':input, select', this).each(function() {
                var $this = $(this), name = $this.attr('name'), val = $this.val();
                if (name != null) {
                    if (name.indexOf("#index#") >= 0) {
                        $this.attr("name",name.replace('#index#',i));
                    } else {
                        var s = name.indexOf("[");
                        var e = name.indexOf("]");
                        var new_name = name.substring(s + 1,e);
                        $this.attr("name",name.replace(new_name,i));
                    }

                    if($(this)[0].tagName == "SELECT"){
                        $(this).combobox({
                            panelHeight:'auto'
                        });
                    }

                }
            });
        });
    }

    $('#addBtn').linkbutton({
        iconCls: 'icon-add'
    });
    $('#delBtn').linkbutton({
        iconCls: 'icon-remove'
    });
    $('#addBtn').bind('click', function(){
        var tr =  $("#add_pzservice_table_template tr").clone();
        $("#add_pzservice_table").append(tr);
        resetTrNum('add_pzservice_table');
    });
    $('#delBtn').bind('click', function(){
        $("#add_pzservice_table").find("input:checked").parent().parent().remove();
        resetTrNum('add_pzservice_table');
    });

</script>

<form id="orderPzDetailsForm" method="post">
    <table class="tableForm" width="100%">
        <input type="hidden" value="${orderPz.id}" name="id"/>
        <input type="hidden" value="${orderPz.servicePersonId}" name="servicePersonId"/>
        <tr>
            <th width="25%">订单编号:</th>
            <td colspan="3">${orderPz.orderNo}</td>
        </tr>
        <tr>
            <th width="25%">病患姓名:</th>
            <td>${orderPz.patientName}</td>
            <th>病患身份证号:</th>
            <td>${orderPz.patientIdCard}</td>
        </tr>
        <tr>
            <th>医保卡号:</th>
            <td>${orderPz.patient.medicareCard}</td>
            <th>接待地点:</th>
            <td>${orderPz.receptionPosition}</td>
        </tr>
        <tr>
            <th>就诊医院:</th>
            <td>${orderPz.hospitalName}</td>
            <th>就诊科室:</th>
            <td>${orderPz.department}</td>
        </tr>
        <tr>
            <th>申请时间:</th>
            <td><fmt:formatDate value="${orderPz.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <th>预约时间:</th>
            <td><fmt:formatDate value="${orderPz.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
        <tr>
            <th>陪诊状态:</th>
            <td>${orderPz.state}</td>
            <th>预约来源:</th>
            <td>${orderPz.orign}</td>
        </tr>
        <tr>
            <th>服务人员:</th>
            <td>${orderPz.servicePerson}</td>
            <th>服务人员ID:</th>
            <td>${orderPz.servicePersonId}</td>
        </tr>
    </table>
    

     <div style="width: auto; height: 200px;">
         <div id="tt" class="easyui-tabs" data-options="onSelect:function(t){$('#tt .panel-body').css('width','auto');}">
             <div title="服务明细">
                 <div style="padding: 3px; height: 25px; width: auto;" class="datagrid-toolbar">
                     <a id="addBtn" href="#">添加</a>
                     <a id="delBtn" href="#">删除</a>
                 </div>
                 <table border="0" cellpadding="2" cellspacing="0" id="pzservice_table" width="100%">
                     <tr bgcolor="#E6E6E6">
                         <td align="center" bgcolor="#EEEEEE">序号</td>
                         <td align="left" bgcolor="#EEEEEE">服务项</td>
                         <td align="left" bgcolor="#EEEEEE">服务人员</td>
                         <td align="left" bgcolor="#EEEEEE">备注</td>
                     </tr>
                     <tbody id="add_pzservice_table">
                         
                     </tbody>
                 </table>
             </div>
        </div>
     </div>
</form>
</div>

<table style="display: none">
    <tbody id="add_pzservice_table_template">
    <tr>
        <td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
        <td align="left">
            <select name="details[#index#].serviceId">
                <c:forEach var="service" items="${services}">
                    <option value="${service.id}">${service.service}</option>
                </c:forEach>
            </select>
        </td>
        <td align="left">
            <select name="details[#index#].servicePersonId">
                <c:forEach var="person" items="${servicePersons}">
                    <option value="${person.id}">${person.name}</option>
                </c:forEach>
            </select>
        </td>
        <td align="left">
            <input   name="details[#index#].remark"  type="text"
                     class="easyui-validatebox" validType="byteLength[1,128]" >
        </td>
    </tr>
    </tbody>
</table>