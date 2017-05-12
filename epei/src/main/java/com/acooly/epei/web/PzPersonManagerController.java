package com.acooly.epei.web;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.epei.domain.ServicePerson;
import com.acooly.epei.service.ServicePersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 修订记录：
 * liubin 2015-10-23 9:47 创建
 */
@Controller
@RequestMapping("/manage/epei/pzPerson")
public class PzPersonManagerController extends AbstractJQueryEntityController<ServicePerson, ServicePersonService> {

	@Autowired
	private ServicePersonService servicePersonService;
}
