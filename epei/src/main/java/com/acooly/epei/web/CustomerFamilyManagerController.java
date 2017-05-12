package com.acooly.epei.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.epei.domain.Customer;
import com.acooly.epei.domain.CustomerFamily;
import com.acooly.epei.service.CustomerFamilyService;
import com.acooly.epei.service.CustomerService;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.AbstractJQueryEntityController;

@Controller
@RequestMapping(value = "/manage/epei/customerFamily")
public class CustomerFamilyManagerController extends AbstractJQueryEntityController<CustomerFamily, CustomerFamilyService> {

	@Autowired
	private CustomerFamilyService customerFamilyService;
	
	@Autowired
	private CustomerService customerService;
	
	@Override
	protected CustomerFamily onSave(HttpServletRequest request, HttpServletResponse response, Model model, CustomerFamily entity,
			boolean isCreate) throws Exception {
		List<CustomerFamily> list = customerFamilyService.findByCustomerUserName(entity.getCustomerUserName());
		if(list==null || list.size()<3){
			Customer customer = customerService.findCustomerByUsername(entity.getCustomerUserName());
			if(customer !=null && customer.getVipId()!=null){
				entity.setCustomerId(customer.getId());
			}else{
				throw new BusinessException("无效的VIP会员账号");
			}
		}else{
			throw new BusinessException("每个VIP会员限定只能绑定两个家庭成员");
		}
		return entity;
	}
	
}
