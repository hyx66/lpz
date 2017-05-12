package com.acooly.epei.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.epei.domain.OrderPzDetail;
import com.acooly.epei.service.OrderPzDetailService;

@Controller
@RequestMapping(value = "/manage/epei/orderPzDetail")
public class OrderPzDetailManagerController extends AbstractJQueryEntityController<OrderPzDetail, OrderPzDetailService> {


	@Autowired
	private OrderPzDetailService orderPzDetailService;

	

}
