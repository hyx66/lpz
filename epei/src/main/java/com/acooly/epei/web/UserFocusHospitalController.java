package com.acooly.epei.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.epei.domain.UserFocusHospital;
import com.acooly.epei.service.HospitalService;
import com.acooly.epei.service.UserFocusHospitalService;
import com.acooly.epei.web.front.OrderController;
import com.acooly.module.security.service.UserService;

@Controller
@RequestMapping(value = "/manage/epei/userFocusHospital")
public class UserFocusHospitalController extends AbstractJQueryEntityController<UserFocusHospital, UserFocusHospitalService> {
	
	private Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private UserFocusHospitalService userFocusHospitalService;
	@Autowired
	private UserService userService;
	@Autowired
	private HospitalService hospitalService; 
	
	@Override
	public String create(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			model.addAllAttributes(referenceData(request));
			model.addAttribute("users", userService.getAll());
			model.addAttribute("hospitals", hospitalService.getAll());
			onCreate(request, response, model);
			model.addAttribute("action", "create");
		} catch (Exception e) {
			logger.warn(getExceptionMessage("create", e), e);
			handleException("新建", e, request);
		}
		return getEditView();
	}
	
	@Override
	public JsonEntityResult<UserFocusHospital> saveJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonEntityResult<UserFocusHospital> result = new JsonEntityResult<UserFocusHospital>();
		try {
			UserFocusHospital userFocusHospital = new UserFocusHospital();
			Long userId = Long.parseLong(request.getParameter("userId"));
			userFocusHospital.setUserId(userId);
			userFocusHospital.setUserName(userService.get(userId).getUsername());
			Long hospitalId = Long.parseLong(request.getParameter("hospitalId"));
			userFocusHospital.setHospitalId(hospitalId);
			userFocusHospital.setHospitalName(hospitalService.get(hospitalId).getName());
			userFocusHospitalService.save(userFocusHospital);
			result.setEntity(userFocusHospital);
			result.setMessage("新增成功");
		} catch (Exception e) {
			handleException(result, "新增", e);
		}
		return result;
	}
	
	@Override
	public String edit(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			model.addAllAttributes(referenceData(request));
			model.addAttribute("users", userService.getAll());
			model.addAttribute("hospitals", hospitalService.getAll());
			UserFocusHospital entity = loadEntity(request);
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
	public JsonEntityResult<UserFocusHospital> updateJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonEntityResult<UserFocusHospital> result = new JsonEntityResult<UserFocusHospital>();
		try {
			Long userFocusHospitalId = Long.parseLong(request.getParameter("id"));
			UserFocusHospital userFocusHospital = userFocusHospitalService.get(userFocusHospitalId);
			Long userId = Long.parseLong(request.getParameter("userId"));
			userFocusHospital.setUserId(userId);
			userFocusHospital.setUserName(userService.get(userId).getUsername());
			Long hospitalId = Long.parseLong(request.getParameter("hospitalId"));
			userFocusHospital.setHospitalId(hospitalId);
			userFocusHospital.setHospitalName(hospitalService.get(hospitalId).getName());
			userFocusHospitalService.update(userFocusHospital);
			result.setEntity(userFocusHospital);
			result.setMessage("更新成功");
		} catch (Exception e) {
			handleException(result, "更新", e);
		}
		return result;
	}
}
