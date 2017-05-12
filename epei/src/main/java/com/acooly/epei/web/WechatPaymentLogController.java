package com.acooly.epei.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.epei.domain.WechatPaymentLog;
import com.acooly.epei.service.WechatPaymentLogService;

@Controller
@RequestMapping(value = "/manage/epei/wechatPaymentLog")
public class WechatPaymentLogController extends AbstractJQueryEntityController<WechatPaymentLog, WechatPaymentLogService> {
	
	@Autowired
	private WechatPaymentLogService wechatPaymentLogService;
	
}
