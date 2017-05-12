package com.acooly.epei.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.epei.domain.PzService;
import com.acooly.epei.service.PzServiceService;

@Controller
@RequestMapping(value = "/manage/epei/pzService")
public class PzServiceManagerController extends AbstractJQueryEntityController<PzService, PzServiceService> {


	@Autowired
	private PzServiceService pzServiceService;

	

}
