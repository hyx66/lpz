package com.acooly.epei.web;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.epei.domain.OrderBase;
import com.acooly.epei.domain.OrderPh;
import com.acooly.epei.domain.OrderPz;
import com.acooly.epei.service.OrderBaseService;
import com.acooly.epei.service.OrderPhService;
import com.acooly.epei.service.OrderPzService;
import com.acooly.module.security.domain.User;

@Controller
@RequestMapping(value = "/manage/epei/orderBase")
public class OrderBaseManagerController extends AbstractJQueryEntityController<OrderBase, OrderBaseService> {


	@Autowired
	private OrderBaseService orderBaseService;
	
	@Autowired
	private OrderPhService orderPhService;
	
	@Autowired
	private OrderPzService orderPzService;

	@RequestMapping("findOrder")
	@ResponseBody
	public String findOrder(){
		User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
		String messege = "";
		if(loginUser.getDescn()!=null && loginUser.getDescn().trim().equals("服务中心")){
			List<OrderPh> listPh = orderPhService.getOrderByAcceptance(1);
			List<OrderPz> listPz = orderPzService.getOrderByAcceptance(1);
			if(listPh.size()!=0){
				messege = "陪护订单:"+listPh.size()+"个<br>";
			}
			if(listPz.size()!=0){
				messege = messege+"陪诊订单："+listPz.size()+"个";
			}
			return messege;
		}else{
			messege = "termination";
			return messege;
		}
	}

}
