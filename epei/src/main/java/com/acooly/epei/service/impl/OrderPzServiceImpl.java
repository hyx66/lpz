package com.acooly.epei.service.impl;

import com.acooly.epei.dao.*;
import com.acooly.epei.domain.*;
import com.acooly.epei.service.PatientService;
import com.acooly.epei.service.ServicePersonService;
import com.acooly.epei.service.VipCustomerDiscountService;
import com.acooly.epei.util.CodeTools;
import com.acooly.epei.util.CodeUtils;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.CustomerAcctRecordsService;
import com.acooly.epei.service.CustomerAcctService;
import com.acooly.epei.service.CustomerService;
import com.acooly.epei.service.OrderPzService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("orderPzService")
public class OrderPzServiceImpl extends EntityServiceImpl<OrderPz, OrderPzDao> implements OrderPzService {

	@Autowired
	private PzServiceDao pzServiceDao;
	
	@Autowired
	private ServicePersonDao servicePersonDao;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private HospitalDao hospitalDao;
	
	@Autowired
	private ServicePersonService servicePersonService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private VipCustomerDiscountService vipCustomerDiscountService;
	
	@Autowired
	private CustomerAcctService customerAcctService;
	
	@Autowired
	private CustomerAcctRecordsService customerAcctRecordsService;

	@Override
	@Transactional
	public void createPzOrder(OrderPz entity) {
		Customer customer = customerService.findByCusNo(entity.getCusNo());
		if(customer.getVipGrade()==null)throw new BusinessException("陪诊服务仅对VIP会员开放");
		Hospital hospital = hospitalDao.get(entity.getHospitalId());
		if(hospital.getPzCusNo()==null)throw new BusinessException("该医院暂不提供陪诊服务");
		entity.setHospital(hospital);
		entity.setReceptionPosition(hospital.getReceptionPosition());
		entity.setHospitalId(entity.getHospitalId());
		entity.setHospitalName(hospital.getName());
		entity.setOrderNo(CodeUtils.getOrderNo(OrderOriginalEnum.getByCode(entity.getOrign())));
		entity.setPayStatus(0);
		entity.setPricingType(2);
		Patient patient = patientService.noExistsSave(entity.getPatient());
		entity.setPatient(patient);
		entity.copyPatientProp();
		save(entity);
	}

	@Override
	@Transactional
	public void updatePzOrder(OrderPz entity) {
		OrderPz persistedOrder = getEntityDao().get(entity.getId());
		if(!persistedOrder.getState().equals(OrderState.ORDERED.name()))throw new BusinessException("只能修改预约状态下的订单");
		if(persistedOrder.getPayStatus()==1)throw new BusinessException("不能修改已付款的订单信息");
		persistedOrder.setOrderTime(entity.getOrderTime());
		Hospital hospital = hospitalDao.get(entity.getHospitalId());
		if(hospital.getPzCusNo()==null)throw new BusinessException("该医院暂不提供陪诊服务");
		persistedOrder.setHospital(hospital);
		persistedOrder.setReceptionPosition(hospital.getReceptionPosition());
		persistedOrder.setHospitalId(entity.getHospitalId());
		persistedOrder.setHospitalName(hospital.getName());
		persistedOrder.setAmount(entity.getAmount());
		persistedOrder.setFreeItem(entity.getFreeItem());
		persistedOrder.setAdminId(entity.getAdminId());
		persistedOrder.setAdminName(entity.getAdminName());
		persistedOrder.setCustomerAge(entity.getCustomerAge());
		persistedOrder.setBed(entity.getBed());
		persistedOrder.setDepartment(entity.getDepartment());
		persistedOrder.setDepartmentId(entity.getDepartmentId());
		persistedOrder.setDepartmentName(entity.getDepartmentName());
		persistedOrder.setPricingType(entity.getPricingType());
		persistedOrder.setNote(entity.getNote());
		Patient patient = patientService.noExistsUpdate(entity.getPatient());
		persistedOrder.setPatient(patient);
		persistedOrder.copyPatientProp();
		update(persistedOrder);
	}

	@Override
	@Transactional
	public void finishedPzOrder(OrderPz order) {
		OrderPz persistedOrder = get(order.getId());
		persistedOrder.setActualAmount(persistedOrder.getAmount());
		persistedOrder.setRefundAmount(new BigDecimal(0));
		//如果订单是在线支付的，完成订单的同时会把消费的费用从冻结金额中扣除
		createSpendRecords(persistedOrder);
		
		List<OrderPzDetail> details = new ArrayList<>();
		if(order.getDetails()!=null){
			for(OrderPzDetail detail : order.getDetails()){
				Long serviceId = detail.getServiceId();
				Long personId = detail.getServicePersonId();
				if(serviceId != null && personId != null){
					PzService service = pzServiceDao.get(detail.getServiceId());
					ServicePerson person = servicePersonDao.get(detail.getServicePersonId());
					detail.setServiceName(service.getService());
					detail.setServicePerson(person.getName());
					details.add(detail);
				}
			}
		}
		persistedOrder.setState(OrderState.FINISHED.getCode());
		persistedOrder.setDetails(details);
		update(persistedOrder);
	}
	
	@Override
	@Transactional
	public void confirmPzOrder(OrderPz order) {
		OrderPz pereistedOrder = get(order.getId());
		if(pereistedOrder.getAmount()==null)throw new BusinessException("请先核算订单总费用");
		if(order.getServicePersonId()!=null){
			pereistedOrder.setServicePerson(servicePersonService.get(order.getServicePersonId()).getName());
		}
		ServicePerson servicePerson = servicePersonService.get(order.getServicePersonId());
		pereistedOrder.setServicePersonId(order.getServicePersonId());
		pereistedOrder.setServicePerson(servicePerson.getName());
		pereistedOrder.setServicePersonMobile(servicePerson.getMobile().toString());
		pereistedOrder.setServicePersonName(servicePerson.getName());
		pereistedOrder.setState(OrderState.CONFIRMED.name());
		pereistedOrder.setPayStatus(1);
		if(pereistedOrder.getPayType()!=4){
			pereistedOrder.setPayeeName(order.getPayeeName());
			pereistedOrder.setPayeeId(order.getPayeeId());
			pereistedOrder.setPayType(order.getPayType());
		}
		update(pereistedOrder);
	}

	@Override
	public List<OrderPz> getOrderByAcceptance(Integer acceptance) {
		return getEntityDao().findByAcceptance(acceptance);
	}
	
	@Transactional
	@Override
	public void createSpendRecords(OrderBase order) {
		Customer customer = customerService.findByCusNo(order.getCusNo());
		VipCustomerDiscount vipCustomerDiscount = vipCustomerDiscountService.findByCustomerId(customer.getId());
		if(order.getFreeItem()!=null && order.getFreeItem()==1){
			JSONObject discount = JSONObject.fromObject(vipCustomerDiscount.getDiscount());
			JSONObject item = discount.getJSONObject("PZ");
			if(item.getInt("count")>0){
				item.put("count", item.getInt("count")-1);
				discount.put("PZ", item);
				vipCustomerDiscount.setDiscount(discount.toString());
				vipCustomerDiscountService.update(vipCustomerDiscount);
			}else{
				throw new BusinessException("该会员免费服务项目次数已经用完");
			}	
		}
		if(order.getPayType()!=4)return;
		CustomerAcctRecords customerAcctRecords = new CustomerAcctRecords();
		customerAcctRecords.setCustomerId(customer.getId());
		customerAcctRecords.setCustomerMobile(customer.getMobile());
		customerAcctRecords.setCustomerName(customer.getName());
		customerAcctRecords.setTitle("订单实付款扣款");
		customerAcctRecords.setDataType(2);
		customerAcctRecords.setOutNo(order.getOrderNo());
		customerAcctRecords.setRechargeAmount(new BigDecimal(0));
		customerAcctRecords.setRecordsNo(CodeUtils.getOrderNo(OrderOriginalEnum.getByCode("WECHAT")));
		customerAcctRecords.setRechargeChannle(0);
		customerAcctRecords.setRechargeStatus(0);
		customerAcctRecords.setSpendAmount(order.getActualAmount());
		customerAcctRecords.setSpendType(2);
		customerAcctRecords.setSpendStatus(1);
		customerAcctRecords.setSpendChannel(1);
		customerAcctRecords.setMemo("订单实际需要支付的费用已从用户账户冻结金额中扣除，预支付费用超出实际需要支付的部分将自动转到用户可用金额中");
		customerAcctRecords.setDateYmd(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		
		String customerId = customer.getId().toString();
		String amount = CodeTools.encode(order.getAmount().toString(),customerId);//订单预付款
		String actualAmount = CodeTools.encode(order.getActualAmount().toString(),customerId);//实际消费的金额
		String oddChange = CodeTools.encode(order.getRefundAmount().toString(),customerId);//结算后剩余金额
		CustomerAcct customerAcct = customerAcctService.getCustomerAcctByCustomerId(customer.getId());
		customerAcct.setFreezAmount(CodeTools.subtract(customerAcct.getFreezAmount(), amount, customerId));//账户冻结金额=账户现有冻结金额-订单在线支付时冻结的金额（订单在线支付的钱是冻结在账户里面的）
		customerAcct.setAvailableAmount(CodeTools.add(customerAcct.getAvailableAmount(), oddChange, customerId));//账户可用金额=账户现有可用金额+订单结算后剩余金额
		customerAcct.setBalance(CodeTools.subtract(customerAcct.getBalance(), actualAmount, customerId));//账户余额=账户现有余额-实际消费的金额（即找零的钱）
		customerAcctRecords.setBalance(new BigDecimal(CodeTools.dncode(customerAcct.getBalance(), customerId)));
		if(order.getActualAmount().compareTo(new BigDecimal("0"))!=0){
			customerAcctRecordsService.save(customerAcctRecords);
		}
		customerAcctService.update(customerAcct);
	}
	
}
