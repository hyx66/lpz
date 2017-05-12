package com.acooly.epei.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.epei.domain.ServiceItem;
import com.acooly.epei.service.ServiceItemService;
import com.acooly.core.common.web.AbstractJQueryEntityController;

@Controller
@RequestMapping(value = "/manage/epei/serviceItem")
public class ServiceItemManagerController extends AbstractJQueryEntityController<ServiceItem, ServiceItemService> {

	@Autowired
	private ServiceItemService serviceItemService;

}
