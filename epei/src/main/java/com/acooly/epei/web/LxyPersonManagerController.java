package com.acooly.epei.web;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.exception.GeneralException;
import com.acooly.core.common.web.AbstractCrudController;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.epei.domain.Hospital;
import com.acooly.epei.domain.LxyPerson;
import com.acooly.epei.service.HospitalService;
import com.acooly.epei.service.LxyPersonService;

@Controller
@RequestMapping(value = "/manage/epei/lxyPerson")
public class LxyPersonManagerController extends AbstractJQueryEntityController<LxyPerson, LxyPersonService> {

	private static final Logger logger = LoggerFactory.getLogger(AbstractCrudController.class);
	@Autowired
	private LxyPersonService lxyPersonService;
	@Autowired
	private HospitalService hospitalService;

	{
		uploadConfig.setAllowExtentions("jpg,gif,png");
		uploadConfig.setNeedTimePartPath(false);
		uploadConfig.setUseMemery(false);
		uploadConfig.setStorageRoot("");
		uploadConfig.getStorageRoot();
	}

	@Override
	protected LxyPerson onSave(HttpServletRequest request, HttpServletResponse response, Model model, LxyPerson entity,
			boolean isCreate) throws Exception {
		try{
			Hospital hospital = hospitalService.get(entity.getHospitalId());
			entity.setHospitalName(hospital.getName());
		}catch(Exception e){
			throw new BusinessException("请选择医院");
		}
		try {
			uploadConfig.setStorageRoot(request.getSession().getServletContext().getRealPath("/") + "upload" + File.separator);
			Map<String, UploadResult> uploadResult = doUpload(request);
			UploadResult image = uploadResult.get("upImage");
			if (image != null) {
				entity.setImage(image.getFile().getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	@Override
	public String create(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAttribute("hospitals", hospitalService.getAll());
			model.addAllAttributes(referenceData(request));
			onCreate(request, response, model);
			model.addAttribute("action", "create");
		} catch (Exception e) {
			logger.warn(getExceptionMessage("create", e), e);
			handleException("新建", e, request);
		}
		return getEditView();
	}
	
	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAttribute("hospitals",hospitalService.getAll());
			model.addAllAttributes(referenceData(request));
			LxyPerson entity = loadEntity(request);
			model.addAttribute("action", "edit");
			model.addAttribute(getEntityName(), entity);
			onEdit(request, response, model, entity);
		} catch (Exception e) {
			logger.warn(getExceptionMessage("edit", e), e);
			handleException("编辑", e, request);
		}
		return getEditView();
	}
	


}
