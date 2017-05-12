package com.acooly.epei.web;

import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Strings;
import com.acooly.epei.common.Constants;
import com.acooly.epei.dao.CustomerAcctRecordsDao;
import com.acooly.epei.domain.Customer;
import com.acooly.epei.domain.CustomerAcct;
import com.acooly.epei.domain.CustomerAcctRecords;
import com.acooly.epei.domain.OrderOriginalEnum;
import com.acooly.epei.service.CustomerAcctRecordsService;
import com.acooly.epei.service.CustomerAcctService;
import com.acooly.epei.service.CustomerService;
import com.acooly.epei.util.CodeTools;
import com.acooly.epei.util.CodeUtils;
import com.acooly.module.security.domain.User;
import com.acooly.module.sms.ShortMessageSendService;

@Controller
@RequestMapping(value = "/manage/epei/customerAcctRecords")
public class CustomerAcctRecordsManagerController extends AbstractJQueryEntityController<CustomerAcctRecords, CustomerAcctRecordsService> {

	@Autowired
	private CustomerAcctRecordsService customerAcctRecordsService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerAcctService customerAcctService;
	@Autowired
	private CustomerAcctRecordsDao customerAcctRecordsDao;
	@Autowired
	private ShortMessageSendService shortMessageSendService;
	
	@Override
	@Transactional
	public synchronized JsonEntityResult<CustomerAcctRecords> saveJson(HttpServletRequest request, HttpServletResponse response) {
		JsonEntityResult<CustomerAcctRecords> result = new JsonEntityResult<CustomerAcctRecords>();
		try {
			CustomerAcctRecords customerAcctRecords = new CustomerAcctRecords();
			String customerMobile = request.getParameter("customerMobile");
			BigDecimal rechargeAmount = new BigDecimal(request.getParameter("rechargeAmount"));
			String memo = request.getParameter("memo");
			Customer customer = customerService.findCustomerByUsername(customerMobile);
			CustomerAcct customerAcct = customerAcctService.getCustomerAcctByCustomerId(customer.getId());
			String customerId = customer.getId().toString();
			//财务明细记录创建
			customerAcctRecords.setMemo(memo);
			User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
			customerAcctRecords.setUserId(loginUser.getId());
			customerAcctRecords.setUserName(loginUser.getUsername());
			customerAcctRecords.setCustomerId(customer.getId());
			customerAcctRecords.setCustomerMobile(customer.getMobile());
			customerAcctRecords.setCustomerName(customer.getName());
			customerAcctRecords.setTitle("线下充值");
			customerAcctRecords.setDataType(1);
			customerAcctRecords.setOutNo(CodeUtils.getOrderNo(OrderOriginalEnum.getByCode("WECHAT")));
			customerAcctRecords.setRechargeAmount(rechargeAmount);
			customerAcctRecords.setRecordsNo(CodeUtils.getOrderNo(OrderOriginalEnum.getByCode("WECHAT")));
			customerAcctRecords.setRechargeChannle(2);
			customerAcctRecords.setRechargeStatus(1);
			customerAcctRecords.setSpendAmount(new BigDecimal("0"));
			customerAcctRecords.setSpendType(0);
			customerAcctRecords.setSpendStatus(0);
			customerAcctRecords.setSpendChannel(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String dateYmd = sdf.format(new Date());
			customerAcctRecords.setDateYmd(dateYmd);
			Boolean success = false;
			if(customerAcct==null){
				customerAcctRecords.setBalance(rechargeAmount);
				customerAcct=new CustomerAcct();
				customerAcct.setCustomerId(customer.getId());
				customerAcct.setTotalAmount(CodeTools.encode(rechargeAmount.toString(),customerId));
				customerAcct.setBalance(CodeTools.encode(rechargeAmount.toString(),customerId));
				customerAcct.setFreezAmount(CodeTools.encode("0",customerId));
				customerAcct.setAvailableAmount(CodeTools.encode(rechargeAmount.toString(),customerId));			
				customerAcctService.save(customerAcct);
				customerAcctRecordsService.save(customerAcctRecords);
				success = true;
			}else if(customerAcct.getStatus()==0){
				customerAcctRecords.setBalance(new BigDecimal(CodeTools.dncode(customerAcct.getBalance(),customerId)).add(rechargeAmount));
				BigDecimal newTotalAmount = new BigDecimal(CodeTools.dncode(customerAcct.getTotalAmount(),customerId)).add(rechargeAmount);
				customerAcct.setTotalAmount(CodeTools.encode(newTotalAmount.toString(),customerId));
				BigDecimal newBalance = new BigDecimal(CodeTools.dncode(customerAcct.getBalance(),customerId)).add(rechargeAmount);
				customerAcct.setBalance(CodeTools.encode(newBalance.toString(),customerId));
				BigDecimal newAvailableAmount = new BigDecimal(CodeTools.dncode(customerAcct.getAvailableAmount(),customerId)).add(rechargeAmount);
				customerAcct.setAvailableAmount(CodeTools.encode(newAvailableAmount.toString(),customerId));
				customerAcctRecordsService.save(customerAcctRecords);
				customerAcctService.update(customerAcct);
				success = true;
			}
			//发送短信通知
			if(success){
				Map<String,Object> params_yyfzr = new HashMap<>();
				params_yyfzr.put("mobile", customer.getMobile());
				params_yyfzr.put("rechargeAmount", customerAcctRecords.getRechargeAmount());
				/**线下充值成功发送短信给用户*/
				shortMessageSendService.sendByTemplateAsync(customer.getMobile(), Constants.SMS_RECHARGE_TMP, params_yyfzr);
			}else{
				throw new BusinessException("操作失败，请先修改该账户状态为【启用】");
			}
			result.setEntity(customerAcctRecords);
			result.setMessage("操作成功！");
		} catch (Exception e) {
			handleException(result, "新增", e);
		}
		return result;
	}
	
	@Override
	@Transactional
	public synchronized JsonEntityResult<CustomerAcctRecords> updateJson(HttpServletRequest request, HttpServletResponse response) {
		JsonEntityResult<CustomerAcctRecords> result = new JsonEntityResult<CustomerAcctRecords>();
		try {			
			CustomerAcctRecords customerAcctRecords = customerAcctRecordsDao.findOne(Long.parseLong(request.getParameter("id")));
			Customer customer = customerService.findCustomerById(customerAcctRecords.getCustomerId());
			CustomerAcct customerAcct = customerAcctService.getCustomerAcctByCustomerId(customer.getId());
			BigDecimal rechargeAmount = customerAcctRecords.getRechargeAmount();
			String customerId = customer.getId().toString();
			String cue = "操作失败，只能修改【充值失败】状态的【充值】记录";
			if(customerAcctRecords.getDataType()==1&&customerAcctRecords.getRechargeStatus()==2&&Integer.parseInt(request.getParameter("rechargeStatus"))==1){
				Boolean success = false;
				customerAcctRecords.setRechargeStatus(1);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String dateYmd = sdf.format(new Date());
				customerAcctRecords.setDateYmd(dateYmd);
				User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
				customerAcctRecords.setUserId(loginUser.getId());
				customerAcctRecords.setUserName(loginUser.getUsername());
				customerAcctRecords.setMemo(request.getParameter("memo"));
				customerAcctRecords.setTitle("线下补录");
				if(customerAcct==null){
					customerAcctRecords.setBalance(rechargeAmount);
					customerAcct=new CustomerAcct();
					customerAcct.setCustomerId(customer.getId());
					customerAcct.setTotalAmount(CodeTools.encode(rechargeAmount.toString(),customerId));
					customerAcct.setBalance(CodeTools.encode(rechargeAmount.toString(),customerId));
					customerAcct.setFreezAmount(CodeTools.encode("0",customerId));
					customerAcct.setAvailableAmount(CodeTools.encode(rechargeAmount.toString(),customerId));			
					customerAcctService.save(customerAcct);
					customerAcctRecordsService.save(customerAcctRecords);
					success = true;
				}else if(customerAcct.getStatus()==0){
					customerAcctRecords.setBalance(new BigDecimal(CodeTools.dncode(customerAcct.getBalance(),customerId)).add(rechargeAmount));
					BigDecimal newTotalAmount = new BigDecimal(CodeTools.dncode(customerAcct.getTotalAmount(),customerId)).add(rechargeAmount);
					customerAcct.setTotalAmount(CodeTools.encode(newTotalAmount.toString(),customerId));
					BigDecimal newBalance = new BigDecimal(CodeTools.dncode(customerAcct.getBalance(),customerId)).add(rechargeAmount);
					customerAcct.setBalance(CodeTools.encode(newBalance.toString(),customerId));
					BigDecimal newAvailableAmount = new BigDecimal(CodeTools.dncode(customerAcct.getAvailableAmount(),customerId)).add(rechargeAmount);
					customerAcct.setAvailableAmount(CodeTools.encode(newAvailableAmount.toString(),customerId));
					customerAcctService.update(customerAcct);
					customerAcctRecordsService.save(customerAcctRecords);	
					success = true;
				}
				if(success){
					cue = "操作成功！";
					Map<String,Object> params_yyfzr = new HashMap<>();
					params_yyfzr.put("mobile", customer.getMobile());
					params_yyfzr.put("rechargeAmount", customerAcctRecords.getRechargeAmount());
					/**线下充值成功发送短信给用户*/
					shortMessageSendService.sendByTemplateAsync(customer.getMobile(), Constants.SMS_RECHARGE_TMP, params_yyfzr);
				}else{
					throw new BusinessException("操作失败，请先修改该账户状态为【启用】");
				}
			}
			
			result.setEntity(customerAcctRecords);
			result.setMessage(cue);
		} catch (Exception e) {
			handleException(result, "更新", e);
		}
		return result;
	}

	@Override
	protected boolean doExportExcelBody(List<CustomerAcctRecords> list,
			HttpServletRequest request, HttpServletResponse response) {
		WritableWorkbook workbook = null;
		OutputStream out = null;
		try {
			//设置第一行栏目名称的字体
			WritableFont font1 = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);
	        WritableCellFormat cellFormat = new WritableCellFormat(font1);
			out = response.getOutputStream();
			String[] headerNames = {"用户名","电话","标题","外部单号","流水号","数据类型","充值金额","充值渠道","充值状态","消费金额","消费类型","消费状态","消费渠道","余额","管理员","管理员ID","账期","备注"};
			workbook = Workbook.createWorkbook(out);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			int row = 0;

			if (headerNames != null) {
				for (int i = 0; i < headerNames.length; ++i) {
					sheet.addCell(new Label(i, row, headerNames[i], cellFormat));
				}
				++row;
			}

			for (CustomerAcctRecords entity : list) {
				List<String> entityData = new ArrayList<String>();
				entityData.add(entity.getCustomerName());
				entityData.add(entity.getCustomerMobile());
				entityData.add(entity.getTitle());
				entityData.add(entity.getOutNo());
				entityData.add(entity.getRecordsNo());
				
				if(entity.getDataType()==1){
					entityData.add("充值");
				}else{
					entityData.add("消费");
				}
				
				if(entity.getRechargeAmount().compareTo(new BigDecimal("0"))==0){
					entityData.add("");
				}else{
					entityData.add(entity.getRechargeAmount()+"");
				}
				
				if(entity.getRechargeChannle()==1){
					entityData.add("在线充值");
				}else if(entity.getRechargeChannle()==2){
					entityData.add("线下充值");
				}else{
					entityData.add("");
				}
				
				if(entity.getRechargeStatus()==1){
					entityData.add("充值成功");
				}else if(entity.getRechargeStatus()==2){
					entityData.add("充值失败");
				}else{
					entityData.add("");
				}
				
				if(entity.getSpendAmount().compareTo(new BigDecimal("0"))==0){
					entityData.add("");
				}else{
					entityData.add(entity.getSpendAmount()+"");
				}
				
				
				if(entity.getSpendType()==1){
					entityData.add("陪护");
				}else if(entity.getSpendType()==2){
					entityData.add("陪诊");
				}else{
					entityData.add("");
				}
				
				if(entity.getSpendStatus()==1){
					entityData.add("消费成功");
				}else if(entity.getSpendStatus()==2){
					entityData.add("消费失败");
				}else{
					entityData.add("");
				}
				
				if(entity.getSpendChannel()==1){
					entityData.add("在线支付");
				}else if(entity.getSpendChannel()==2){
					entityData.add("线下支付");
				}else{
					entityData.add("");
				}
				
				entityData.add(entity.getBalance()+"");
				entityData.add(entity.getUserName());
				entityData.add(entity.getUserId()+"");
				entityData.add(entity.getDateYmd());
				entityData.add(entity.getMemo());
				for (int i = 0; i < entityData.size(); ++i) {
					sheet.addCell(new Label(i, row, Strings
							.trimToEmpty((String) entityData.get(i))));
				}
				++row;
			}
			workbook.write();
			out.flush();
		} catch (Exception e) {
		} finally {
			try {
				workbook.close();
			} catch (Exception localException2) {
			}
			IOUtils.closeQuietly(out);
		}
		return false;
	}
	
	@Override
	public JsonResult deleteJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult result = new JsonResult();
		try {
			Serializable[] ids = getRequestIds(request);
			System.out.println("本次要删除的信息条数为："+ids.length);
			onRemove(request, response, null, ids);
			doRemove(request, response, null, ids);
			result.setMessage("删除成功");
		} catch (Exception e) {
			handleException(result, "删除", e);
		}
		return result;
	}
	
	@RequestMapping("checkAccount")
	@ResponseBody
	public Customer checkAccount(HttpServletRequest request){
		String userName = request.getParameter("userName");
		Customer customer = customerService.findCustomerByUsername(userName);
		if(customer!=null){
			return customer;
		}else{
			return null;
		}
	}
}
