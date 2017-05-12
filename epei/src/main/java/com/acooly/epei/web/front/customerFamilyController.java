package com.acooly.epei.web.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.epei.domain.Customer;
import com.acooly.epei.domain.CustomerFamily;
import com.acooly.epei.domain.Patient;
import com.acooly.epei.service.CustomerFamilyService;
import com.acooly.epei.util.LoginUserUtils;

@RequestMapping("customerFamily")
@Controller
public class customerFamilyController extends
		AbstractJQueryEntityController<CustomerFamily, CustomerFamilyService> {

	private Logger logger = LoggerFactory
			.getLogger(customerFamilyController.class);
	
	@Autowired
	private CustomerFamilyService customerFamilyService;

	@RequestMapping("customerFamilyList")
	public String customerFamilyList(Model model){
		Customer customer = LoginUserUtils.getCustomer();
		List<CustomerFamily> customerFamilyList = customerFamilyService.findByCustomerId(customer.getId());
		model.addAttribute("customerFamilyList", customerFamilyList);
		return "/front/mobile/customerFamilyList";
	}
	
	@RequestMapping("customerFamilyEdit")
	public String customerFamilyEdit(Long id, Model model) {
		if (id != null) {
			CustomerFamily customerFamily = customerFamilyService.get(id);
			if (customerFamily != null && customerFamily.getCustomerId().equals(LoginUserUtils.getCusNo())) {
				model.addAttribute("customerFamily", customerFamily);
			}
		}
		return "/front/mobile/customerFamilyEdit";
	}
	
	@RequestMapping("customerFamilySave")
	@ResponseBody
	public JsonResult customerFamilySave(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult result = new JsonResult();
		try {
			CustomerFamily customerFamily = loadEntity(request);
			if (customerFamily == null) {
				customerFamily = getEntityClass().newInstance();
			}
			doDataBinding(request, customerFamily);
			Customer customer = LoginUserUtils.getCustomer();
			if(customer.getVipId()==null)throw new BusinessException("您还不是VIP会员，无法设置家庭成员");
			customerFamily.setCustomerUserName(customer.getUserName());
			customerFamily.setCustomerId(customer.getId());
			if (customerFamily.getId() == null) {
				customerFamilyService.customerFamilySave(customerFamily);
			} else {
				customerFamilyService.update(customerFamily);
			}
			result.setSuccess(true);
			result.setMessage("保存成功");
		} catch (Exception e) {
			logger.error("保存家庭成员出错,", e);
			result.setSuccess(false);
			result.setMessage("保存家庭成员失败,"+e.getMessage());
		}
		return result;
	}
	
}
