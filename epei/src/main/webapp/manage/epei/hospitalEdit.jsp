<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/manage/common/taglibs.jsp"%>
<script type="text/javascript">
  $(function(){
     //陪诊原始价格输入的话  陪诊现价必须输入
     $("#pzOrigPrice").change(function(){
         if($(this).val()){
             $("#pzCurrentPrice").validatebox({
                 required: true
             });
         }
         else{
             $("#pzCurrentPrice").validatebox({
                 required: false
             });
         }
     });

      $("#pzCurrentPrice").change(function(){
          if($(this).val()){
              $("#pzOrigPrice").validatebox({
                  required: true
              });
          }
          else{
              $("#pzOrigPrice").validatebox({
                  required: false
              });
          }
      });


      var phOrigPrice = $("#phOrigPrice");
      var phCurrentPrice = $("#phCurrentPrice");

      phOrigPrice.change(function(){

          if($(this).val()){
              phCurrentPrice.validatebox({
                  required: true
              });
          }
          else{
              phCurrentPrice.validatebox({
                  required: false
              });
          }
      });

      phCurrentPrice.change(function(){
          if($(this).val()){
              phOrigPrice.validatebox({
                  required: true
              });
          }
          else{
              phOrigPrice.validatebox({
                  required: false
              });
          }
      });
      $("input[name='service']").focus();
  });



    function  hiddenLogo(){
        $("#showLogo").hide();
    }


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

  $('#addPzPriceBtn').linkbutton({
      iconCls: 'icon-add'
  });
  $('#delPzPriceBtn').linkbutton({
      iconCls: 'icon-remove'
  });
  $('#addBtn').bind('click', function(){
      var tr =  $("#add_phprice_table_template tr").clone();
      $("#add_phprice_table").append(tr);
      resetTrNum('add_phprice_table');
  });
  $('#delBtn').bind('click', function(){
      $("#add_phprice_table").find("input:checked").parent().parent().remove();
      resetTrNum('add_phprice_table');
  });

  $('#addPzPriceBtn').bind('click', function(){
      var tr =  $("#add_pzprice_table_template tr").clone();
      $("#add_pzprice_table").append(tr);
      resetTrNum('add_pzprice_table');
  });
  $('#delPzPriceBtn').bind('click', function(){
      $("#add_pzprice_table").find("input:checked").parent().parent().remove();
      resetTrNum('add_pzprice_table');
  });

function serviceTypeChange(newvalue,oldValue){
    if(newvalue == "PZ"){
        $("#tt").tabs("select","陪诊价格");
        $("#tt").tabs("disableTab","陪护价格");
        $("#tt").tabs("enableTab","陪诊价格");
    }
    else if(newvalue == "PH"){
        $("#tt").tabs("select","陪护价格");
        $("#tt").tabs("disableTab","陪诊价格");
        $("#tt").tabs("enableTab","陪护价格");
    }
    else if(newvalue == "ALL"){
        $("#tt").tabs("select","陪诊价格");
        $("#tt").tabs("enableTab","陪诊价格");
        $("#tt").tabs("enableTab","陪护价格");
    }
}
function disableTab(){
    <c:choose>
        <c:when test="${hospital.serviceType == 'PZ'}">
            $("#tt").tabs("select","陪诊价格");
            $("#tt").tabs("disableTab","陪护价格");
            $("#tt").tabs("enableTab","陪诊价格");
        </c:when>
        <c:when test="${hospital.serviceType == 'PH'}">
            $("#tt").tabs("select","陪护价格");
            $("#tt").tabs("disableTab","陪诊价格");
            $("#tt").tabs("enableTab","陪护价格");
        </c:when>
        <c:otherwise>
            console.log("${hospital.serviceType}");
        </c:otherwise>
    </c:choose>

}

</script>
<div>
    <form id="manage_hospital_editform" action="${pageContext.request.contextPath}/manage/epei/hospital/${action=='create'?'saveJson':'updateJson'}.html" method="post" enctype="multipart/form-data">
      <jodd:form bean="hospital" scope="request">
        <input name="id" type="hidden" />
        <input name="pzPrincipalName" value="" type="hidden"/>
        <input name="pzPrincipalMobile" value="" type="hidden"/>
        <input name="phPrincipalName" value="" type="hidden"/>
        <input name="phPrincipalMobile" value="" type="hidden"/>
        <input name="pzCusNoOld" value="${pzCusNoOld}" type="hidden"/>
        <input name="phCusNoOld" value="${phCusNoOld}" type="hidden"/>
        <table class="tableForm" value="" width="100%">
			<tr>
				<th width="25%"><span style="color: red">*</span>医院名：</th>
				<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true" validType="byteLength[1,64]"/></td>
			</tr>					
			<tr>
				<th>医院logo：</th>
				<td>
                    <img id="showLogo" width='80px' height='20px'
                         src='${pageContext.request.contextPath}/manage/epei/hospital/logo.html?id=${hospital.logoImageId}'/>
                    <input type="file" name="logo" accept="image/gif,image/jpeg,image/png" onclick="hiddenLogo()">
                </td>

			</tr>
            <tr>
                <th>服务类型：</th>
                <td>
                    <select name="serviceType" class="easyui-combobox" data-options="panelHeight:'auto',onChange:serviceTypeChange">
                        <option value="ALL" <c:if test="${hospital.serviceType == 'ALL'}"> selected </c:if>>陪诊和陪护</option>
                        <option value="PZ" <c:if test="${hospital.serviceType == 'PZ'}"> selected </c:if>>陪诊</option>
                        <option value="PH" <c:if test="${hospital.serviceType == 'PH'}"> selected </c:if>>陪护</option>
                    </select>
                </td>
            </tr>
            <tr>
				<th><span style="color: red">*</span>合作关系：</th>
				<td><input type="radio" name="isCooperate" value="0" checked />已合作&nbsp;&nbsp;<input type="radio" name="isCooperate" value="1">非合作</td>
			</tr>			
			<tr>
				<th><span style="color: red">*</span>接待地点：</th>
				<td><input type="text" name="receptionPosition" class="easyui-validatebox"  data-options="required:true" validType="byteLength[1,128]"/></td>
			</tr>		
			<tr id="pz">
				<th><span style="color: red">*</span>陪诊负责人：</th>
				<td>
					<select id="pzCusNo" name="pzCusNo" class="easyui-combobox"  data-options="panelHeight:'auto',editable:false" required="false">
                		<option value="">请选择陪诊负责人</option>
							<c:forEach var="customer" items="${customers}">
							<option value="${customer.cusNo}">${customer.name} : ${customer.mobile}</option>
							</c:forEach>
					</select>
				</td>
			</tr>
			<tr id="ph">
				<th><span style="color: red">*</span>陪护负责人：</th>
				<td>
					<select name="phCusNo" class="easyui-combobox"  data-options="panelHeight:'auto',editable:false" required="false">
                		<option value="">请选择陪护负责人</option>
							<c:forEach var="customer" items="${customers}">
							<option value="${customer.cusNo}">${customer.name} : ${customer.mobile}</option>
							</c:forEach>
					</select>
				</td>
			</tr>
        </table>
        <div style="width: auto; height: 200px;">
              <div id="tt" class="easyui-tabs"
                   data-options="onSelect:function(t){$('#tt .panel-body').css('width','auto');},onLoad:disableTab">
                   <div title="陪诊价格">
                      <div style="padding: 3px; height: 25px; width: auto;" class="datagrid-toolbar">
                          <a id="addPzPriceBtn" href="#">添加</a>
                          <a id="delPzPriceBtn" href="#">删除</a>
                      </div>
                      <table border="0" cellpadding="2" cellspacing="0" id="pzprice_table" width="100%">
                          <tr bgcolor="#E6E6E6">
                              <td align="center" bgcolor="#EEEEEE">选择</td>
                              <td align="left" bgcolor="#EEEEEE">服务级别</td>
                              <td align="left" bgcolor="#EEEEEE">价格(元)</td>
                          </tr>
                          <tbody id="add_pzprice_table">
                          <c:choose>
                              <c:when test="${ not empty hospital.pzPrices}">
                                  <c:forEach var="pzPrice" items="${hospital.pzPrices}" varStatus="status">
                                      <tr>
                                          <td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
                                          <td align="left">
                                              <input   name="pzPrices[${status.index}].serviceGrade"  type="text"
                                                       value="${pzPrice.serviceGrade}"/>
                                          </td>
                                          <td align="left">
                                              <input   name="pzPrices[${status.index}].price"  type="text"
                                                       value="${pzPrice.price}"/>
                                          </td>
                                      </tr>
                                  </c:forEach>
                              </c:when>
                              <c:otherwise>
                                  <tr>
                                      <td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
                                      <td align="left">
                                          <input   name="pzPrices[0].serviceGrade"  type="text" >
                                      </td>
                                      <td align="left">
                                          <input   name="pzPrices[0].price"  type="text" >
                                      </td>
                                  </tr>
                              </c:otherwise>
                          </c:choose>
                          </tbody>
                      </table>
                  </div>
                  <div title="陪护价格" hidden="true">
                      <div style="padding: 3px; height: 25px; width: auto;" class="datagrid-toolbar">
                          <a id="addBtn" href="#">添加</a>
                          <a id="delBtn" href="#">删除</a>
                      </div>
                      <table border="0" cellpadding="2" cellspacing="0" id="phprice_table" width="100%">
                          <tr bgcolor="#E6E6E6">
                              <td align="center" bgcolor="#EEEEEE">选择</td>
                              <td align="left" bgcolor="#EEEEEE">服务级别</td>
                              <td align="left" bgcolor="#EEEEEE">价格(元)</td>
                          </tr>
                          <tbody id="add_phprice_table">
                              <c:choose>
                                  <c:when test="${ not empty hospital.phPrices}">
                                      <c:forEach var="phPrice" items="${hospital.phPrices}" varStatus="status">
                                          <tr>
                                              <td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
                                              <td align="left">
                                                  <input   name="phPrices[${status.index}].serviceGrade"  type="text"
                                                           value="${phPrice.serviceGrade}"/>
                                              </td>
                                              <td align="left">
                                                  <input   name="phPrices[${status.index}].price"  type="text"
                                                           value="${phPrice.price}"/>
                                              </td>
                                          </tr>
                                      </c:forEach>
                                  </c:when>
                                  <c:otherwise>
                                      <tr>
                                          <td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
                                          <td align="left">
                                              <input   name="phPrices[0].serviceGrade"  type="text" >
                                          </td>
                                          <td align="left">
                                              <input   name="phPrices[0].price"  type="text" >
                                          </td>
                                      </tr>
                                  </c:otherwise>
                              </c:choose>
                          </tbody>
                      </table>
                  </div>
                  
              </div>
          </div>
      </jodd:form>
    </form>
</div>

<table style="display: none">
    <tbody id="add_phprice_table_template">
        <tr>
            <td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
            <td align="left">
                <input   name="phPrices[#index#].serviceGrade"  type="text" >
            </td>
            <td align="left">
                <input   name="phPrices[#index#].price"  type="text">
            </td>
        </tr>
    </tbody>
</table>


<table style="display: none">
    <tbody id="add_pzprice_table_template">
    <tr>
        <td align="center"><input style="width: 20px;" type="checkbox" name="ck" /></td>
        <td align="left">
            <input   name="pzPrices[#index#].serviceGrade"  type="text">
        </td>
        <td align="left">
            <input   name="pzPrices[#index#].price"  type="text">
        </td>
    </tr>
    </tbody>
</table>
<script>
//通过上、下按键移动焦点
$(document).ready(function () {
	$(':input:enabled').addClass('enterIndex');
	textboxes = $('.enterIndex');
	if ($.browser.mozilla) {
		$(textboxes).bind('keypress', CheckForEnter);
	}else {
		$(textboxes).bind('keydown', CheckForEnter);
	}
});
function CheckForEnter(event) {
	if (event.keyCode == 40 && $(this).attr('type') != 'button' && $(this).attr('type') != 'submit' && $(this).attr('type') != 'textarea' && $(this).attr('type') != 'reset') {
		var i = $('.enterIndex').index($(this));
		var n = $('.enterIndex').length;
		if (i < n) {
		if ($(this).attr('type') != 'radio'){
			NextDOM($('.enterIndex'),i);
		}
		else {
			var last_radio = $('.enterIndex').index($('.enterIndex[type=radio][name=' + $(this).attr('name') + ']:last'));
			NextDOM($('.enterIndex'),last_radio);
			}
		}
		return false;
	}
	if (event.keyCode == 38 && $(this).attr('type') != 'button' && $(this).attr('type') != 'submit' && $(this).attr('type') != 'textarea' && $(this).attr('type') != 'reset') {
		var i = $('.enterIndex').index($(this));
		var n = $('.enterIndex').length;
		if (i < n) {
		if ($(this).attr('type') != 'radio'){
			PreDOM($('.enterIndex'),i);
		}
		else {
			var last_radio = $('.enterIndex').index($('.enterIndex[type=radio][name=' + $(this).attr('name') + ']:first'));
			PreDOM($('.enterIndex'),last_radio);
			}
		}
		return false;
	}
	
}
function NextDOM(myjQueryObjects,counter) {
	if (myjQueryObjects.eq(counter+1)[0].disabled) {
		NextDOM(myjQueryObjects, counter + 1);
	}
	else {
		myjQueryObjects.eq(counter + 1).trigger('focus');
	}
}
function PreDOM(myjQueryObjects,counter) {
	if (myjQueryObjects.eq(counter-1)[0].disabled) {
		PreDOM(myjQueryObjects, counter - 1);
	}
	else {
		myjQueryObjects.eq(counter - 1).trigger('focus');
	}
}
$.extend($.fn.validatebox.defaults.rules, {     
    money : {     
         validator: function(value){     
             return /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/i.test(value);     
         },     
         message: '请输入正确的金额'    
     } 
 });  
</script>