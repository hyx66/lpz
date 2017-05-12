package com.acooly.epei.web;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.utils.Strings;
import com.acooly.epei.domain.Customer;
import com.acooly.epei.domain.Department;
import com.acooly.epei.domain.Hospital;
import com.acooly.epei.domain.OfflineSalesRecords;
import com.acooly.epei.domain.ServicePerson;
import com.acooly.epei.service.DepartmentService;
import com.acooly.epei.service.HospitalService;
import com.acooly.module.security.domain.User;

import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/manage/epei/department")
public class DepartmentManagerController extends AbstractJQueryEntityController<Department, DepartmentService> {
	
	private static final Logger logger = LoggerFactory.getLogger(DepartmentManagerController.class);

	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private HospitalService hospitalService;

	@Override
	public String create(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			model.addAttribute("hospitals", hospitalService.getAllNoDel());
			model.addAllAttributes(referenceData(request));
			onCreate(request, response, model);
			model.addAttribute("action", "create");
		} catch (Exception e) {
			logger.warn(getExceptionMessage("create", e), e);
			handleException("新建", e, request);
		}
		return getEditView();
	}
	
	@RequestMapping({ "edit" })
	public String edit(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			model.addAttribute("hospitals", hospitalService.getAllNoDel());
			model.addAllAttributes(referenceData(request));
			Department entity = loadEntity(request);
			model.addAttribute("action", "edit");
			model.addAttribute(getEntityName(), entity);
			onEdit(request, response, model, entity);
		} catch (Exception e) {
			logger.warn(getExceptionMessage("edit", e), e);
			handleException("编辑", e, request);
		}
		return getEditView();
	}
	
	@Override
	protected Department onSave(HttpServletRequest request, HttpServletResponse response, Model model, Department entity, boolean isCreate) throws Exception {
		Hospital hospital = hospitalService.get(entity.getHospitalId());
		if(hospital!=null){
			entity.setHospitalName(hospital.getName());
		}
		return entity;
	}
	
	@RequestMapping("combobox")
	@ResponseBody
	public List<Department> combobox(Long hosid){
		return departmentService.getSamehosid(hosid);
	}
	
	@RequestMapping({ "saveJson" })
	@ResponseBody
	public JsonEntityResult<Department> saveJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonEntityResult<Department> result = new JsonEntityResult<Department>();
		try {
			String name = request.getParameter("name");
			Long hospitalId = Long.parseLong(request.getParameter("hospitalId"));
			if(departmentService.queryDepartmentByNameAndHospitalId(name, hospitalId).size()==0){
				result.setEntity(doSave(request, response, null, true));
				result.setMessage("新增成功");
			}else{
				throw new BusinessException("操作失败，该医院已存在此科室！");
			}
		} catch (Exception e) {
			handleException(result, "新增", e);
		}
		return result;
	}
	
	/*******************************************Excel导出用到的一些方法********************************************/
	@Override
	public boolean doExportExcelBody(List<Department> list,
			HttpServletRequest request, HttpServletResponse response) {
		WritableWorkbook workbook = null;
		OutputStream out = null;
		try {
			WritableFont font1 = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);
	        WritableCellFormat cellFormat = new WritableCellFormat(font1);
			out = response.getOutputStream();
			String[] headerNames = {"科室名称","医院名称"};
			workbook = Workbook.createWorkbook(out);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			int row = 0;

			if (headerNames != null) {
				for (int i = 0; i < headerNames.length; i++) {
					sheet.addCell(new Label(i, row, headerNames[i], cellFormat));
				}
				row++;
			}
			for (Department entity : list) {
				List<String> entityData = new ArrayList<String>();
				entityData.add(entity.getName());
				if(hospitalService.get(entity.getHospitalId())!=null){
					entityData.add(hospitalService.get(entity.getHospitalId()).getName());
				}else{
					entityData.add("");
				}
				for (int i = 0; i < entityData.size(); i++) {
					sheet.addCell(new Label(i, row, Strings
							.trimToEmpty((String) entityData.get(i))));
				}
				row++;
			}
			workbook.write();
			out.flush();
		} catch (Exception e) {
		} finally {
			try {
				workbook.close();
			} catch (Exception localException2) {
			}
			IOUtils.closeQuietly(out);
		}
		return false;
	}	
}
