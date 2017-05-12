package com.acooly.epei.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.epei.domain.EnchashmentApply;
import com.acooly.epei.service.CustomerAcctService;
import com.acooly.epei.service.CustomerService;
import com.acooly.epei.service.EnchashmentApplyService;

@Controller
@RequestMapping(value = "/manage/epei/enchashmentApply")
public class EnchashmentApplyManagerController extends AbstractJQueryEntityController<EnchashmentApply, EnchashmentApplyService> {
	
	@Autowired
	EnchashmentApplyService enchashmentApplyService;
	@Autowired
	CustomerService customerService;
	@Autowired
	CustomerAcctService customerAcctService;
	
	@RequestMapping("goEnchashmentApplyConfirm")
	public String goRenew(Long id,Model model){
		EnchashmentApply enchashmentApply = enchashmentApplyService.get(id);
		model.addAttribute("enchashmentApply",enchashmentApply);
		return "/manage/epei/enchashmentApplyConfirm";
	}
	
	@RequestMapping("enchashmentApplyConfirm")
	@ResponseBody
	public synchronized JsonResult enchashmentApplyConfirm(HttpServletRequest request,EnchashmentApply enchashmentApply){
		JsonResult result  = new JsonResult();
		try{
			EnchashmentApply e = enchashmentApplyService.get(enchashmentApply.getId());
			e.setAuditorSign(enchashmentApply.getAuditorSign());
			e.setMemo(enchashmentApply.getMemo());
			enchashmentApplyService.transfer(e);
			result.setMessage("提现成功！");
		}
		catch (Exception e ){
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
}
