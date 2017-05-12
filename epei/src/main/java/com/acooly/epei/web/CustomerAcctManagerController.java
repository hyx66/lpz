package com.acooly.epei.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.epei.dao.CustomerAcctDao;
import com.acooly.epei.domain.CustomerAcct;
import com.acooly.epei.service.CustomerAcctService;
import com.acooly.epei.util.CodeTools;

@Controller
@RequestMapping(value = "/manage/epei/customerAcct")
public class CustomerAcctManagerController extends AbstractJQueryEntityController<CustomerAcct, CustomerAcctService> {

	@Autowired
	private CustomerAcctService customerAcctService;
	@Autowired
	CustomerAcctDao customerAcctDao;

	@Override
	public JsonListResult<CustomerAcct> listJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonListResult<CustomerAcct> result = new JsonListResult<CustomerAcct>();
		try {
			result.appendData(referenceData(request));
			PageInfo<CustomerAcct> pageInfo = doList(request, response);
			result.setTotal(Long.valueOf(pageInfo.getTotalCount()));
			List<CustomerAcct> customerAcctList = new ArrayList<CustomerAcct>();
			for(CustomerAcct c : pageInfo.getPageResults()){
				String customerId = c.getCustomerId().toString().toString();
				c.setAvailableAmount(CodeTools.dncode(c.getAvailableAmount(), customerId));
				c.setBalance(CodeTools.dncode(c.getBalance(), customerId));
				c.setFreezAmount(CodeTools.dncode(c.getFreezAmount(), customerId));
				c.setTotalAmount(CodeTools.dncode(c.getTotalAmount(), customerId));
				customerAcctList.add(c);
			}
			result.setRows(customerAcctList);
		} catch (Exception e) {
			handleException(result, "分页查询", e);
		}
		return result;
	}
	
	@Override
	public JsonEntityResult<CustomerAcct> updateJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonEntityResult<CustomerAcct> result = new JsonEntityResult<CustomerAcct>();
		try {
			CustomerAcct customerAcct = customerAcctDao.findOne(Long.parseLong(request.getParameter("id")));
			String customerId = customerAcct.getCustomerId().toString();
			//冻结金额增加
			BigDecimal freezAmount = new BigDecimal(request.getParameter("freezAmount"));
			String newFreezAmountString = CodeTools.dncode(customerAcct.getFreezAmount(), customerId);
			BigDecimal newFreezAmount = new BigDecimal(newFreezAmountString).add(freezAmount);
			customerAcct.setFreezAmount(CodeTools.encode(newFreezAmount.toString(), customerId));
			//可用金额减少
			String availableAmountString = CodeTools.dncode(customerAcct.getAvailableAmount(), customerId);
			BigDecimal availableAmount = new BigDecimal(availableAmountString);
			BigDecimal newAvailableAmount = availableAmount.subtract(freezAmount);
			customerAcct.setAvailableAmount(CodeTools.encode(newAvailableAmount.toString(), customerId));
			if(newAvailableAmount.compareTo(new BigDecimal(0))!=-1){
				customerAcctService.update(customerAcct);
				customerAcct.setTotalAmount(CodeTools.dncode(customerAcct.getTotalAmount(), customerId));
				customerAcct.setBalance(CodeTools.dncode(customerAcct.getBalance(), customerId));
				customerAcct.setAvailableAmount(newAvailableAmount.toString());
				customerAcct.setFreezAmount(CodeTools.dncode(customerAcct.getFreezAmount(), customerId));
				result.setEntity(customerAcct);
				result.setMessage("操作成功！");
			}else{
				result.setMessage("冻结金额不能大于可用金额！");			
			}
			
		} catch (Exception e) {
			handleException(result, "更新", e);
		}
		return result;
	}
	
	@RequestMapping("enableCustomerAcct")
	@ResponseBody
	public JsonResult enableCustomerAcct(Long id){
		JsonResult result  = new JsonResult();
		try{
			CustomerAcct c = customerAcctDao.getOne(id);
			c.setStatus(0);
			customerAcctService.update(c);
			result.setMessage("该用户账户已启用成功！");
		}
		catch (Exception e ){
			result.setSuccess(false);
			result.setMessage("系统出错啦,请稍后再试.");
		}
		return result;
	}
	
	@RequestMapping("disableCustomerAcct")
	@ResponseBody
	public JsonResult disableCustomerAcct(Long id){
		JsonResult result  = new JsonResult();
		try{
			CustomerAcct c = customerAcctDao.getOne(id);
			c.setStatus(1);
			customerAcctService.update(c);
			result.setMessage("该用户账户已禁用成功！");
		}
		catch (Exception e ){
			result.setSuccess(false);
			result.setMessage("系统出错啦,请稍后再试.");
		}
		return result;
	}
	
}
