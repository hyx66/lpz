package com.acooly.epei.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.epei.domain.Company;
import com.acooly.epei.service.CompanyService;
import com.acooly.core.common.web.AbstractJQueryEntityController;

@Controller
@RequestMapping(value = "/manage/epei/company")
public class CompanyManagerController extends AbstractJQueryEntityController<Company, CompanyService> {

	@Autowired
	private CompanyService companyService;

}
