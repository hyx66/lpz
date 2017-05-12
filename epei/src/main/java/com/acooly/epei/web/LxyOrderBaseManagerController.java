package com.acooly.epei.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.epei.domain.Department;
import com.acooly.epei.domain.Hospital;
import com.acooly.epei.domain.LxyOrderBase;
import com.acooly.epei.domain.LxyPerson;
import com.acooly.epei.service.DepartmentService;
import com.acooly.epei.service.HospitalService;
import com.acooly.epei.service.LxyOrderBaseService;
import com.acooly.epei.service.LxyPersonService;

@Controller
@RequestMapping(value = "/manage/epei/lxyOrderBase")
public class LxyOrderBaseManagerController extends AbstractJQueryEntityController<LxyOrderBase, LxyOrderBaseService> {
	
	@Autowired
	private LxyOrderBaseService lxyOrderBaseService;
	
	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private LxyPersonService lxyPersonService;
	
	@Autowired
	private DepartmentService departmentService;

	@RequestMapping({ "index" })
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			lxyOrderBaseService.dataSynchronization(new Date());
			model.addAllAttributes(referenceData(request));
			model.addAttribute("lxyOrderBases", lxyOrderBaseService.getAll());
			model.addAttribute("lxyPersons", lxyPersonService.getAll());
		} catch (Exception e) {
			handleException("主界面", e, request);
		}
		return getListView();
	}

	@Override
	protected void onEdit(HttpServletRequest request, HttpServletResponse response, Model model, LxyOrderBase entity) {
		Map<String,Object> params = new HashMap<>();
		params.put("EQ_deleted",0);
		model.addAttribute("hospitals",hospitalService.query(params,null));
		model.addAttribute("lxyPersons", lxyPersonService.getAll());
		lxyOrderBaseService.setEntityTheme(entity);
	}
	
	@Override
	protected LxyOrderBase onSave(HttpServletRequest request, HttpServletResponse response, Model model, LxyOrderBase entity, boolean isCreate){
		try{
			Hospital hospital = hospitalService.get(entity.getHospitalId());
			entity.setHospitalName(hospital.getName());
		}catch(Exception e){
			throw new BusinessException("请选择医院");
		}
		LxyPerson lxyPerson = lxyPersonService.get(entity.getPzId());
		entity.setPzName(lxyPerson.getName());
		lxyOrderBaseService.setEntityTheme(entity);
		long id =  Long.parseLong(request.getParameter("id"));
		LxyOrderBase lxyOrderBase = lxyOrderBaseService.get(id);
		if (lxyOrderBase != null) {
			lxyOrderBase.setOrderStatus(2);
			lxyOrderBaseService.update(lxyOrderBase);
		} 
		return entity;
	}
	
	@RequestMapping("comboboxDepartment")
	@ResponseBody
	public List<Department> comboboxDepartment(Long hosid){
		List<Department> Departments = departmentService.getSamehosid(hosid);
		return Departments;
	}
	
	@Override
	public JsonListResult<LxyOrderBase> listJson(HttpServletRequest request, HttpServletResponse response) {
		JsonListResult<LxyOrderBase> result = new JsonListResult<LxyOrderBase>();
		try {
			result.appendData(referenceData(request));
			PageInfo<LxyOrderBase> pageInfo = doList(request, response);
			result.setTotal(Long.valueOf(pageInfo.getTotalCount()));
			List<LxyOrderBase> list = pageInfo.getPageResults();
			List<LxyOrderBase> showList = new ArrayList<LxyOrderBase>();
			for(LxyOrderBase order : list){
				lxyOrderBaseService.setEntityTheme(order);
				showList.add(order);
			}
			result.setRows(showList);
		} catch (Exception e) {
			handleException(result, "分页查询", e);
		}
		return result;
	}
	
	@Override
	protected void onShow(HttpServletRequest request, HttpServletResponse response, Model model, LxyOrderBase entity) throws Exception {
		lxyOrderBaseService.setEntityTheme(entity);
	}
	
}
