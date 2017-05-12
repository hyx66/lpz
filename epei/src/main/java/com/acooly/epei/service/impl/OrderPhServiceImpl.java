package com.acooly.epei.service.impl;

import com.acooly.epei.dao.*;
import com.acooly.epei.domain.*;
import com.acooly.epei.service.CustomerAcctRecordsService;
import com.acooly.epei.service.CustomerAcctService;
import com.acooly.epei.service.CustomerService;
import com.acooly.epei.service.DepartmentService;
import com.acooly.epei.service.HospitalService;
import com.acooly.epei.service.PatientService;
import com.acooly.epei.service.ServicePersonService;
import com.acooly.epei.service.VipCustomerDiscountService;
import com.acooly.epei.util.CodeTools;
import com.acooly.epei.util.CodeUtils;
import com.acooly.module.security.domain.User;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.OrderPhService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderPhService")
public class OrderPhServiceImpl extends EntityServiceImpl<OrderPh, OrderPhDao> implements OrderPhService {

	@Autowired
	private ServicePersonDao servicePersonDao;
	@Autowired
	private PatientService patientService;
	@Autowired
	private HospitalDao hospitalDao;
	@Autowired
	private CouponDao couponDao;
	@Autowired
	DepartmentService departmentService;
	@Autowired
	HospitalService hospitalService;
	@Autowired
	CustomerService customerService;
	@Autowired
	CustomerAcctRecordsService customerAcctRecordsService;
	@Autowired
	CustomerAcctService customerAcctService;
	@Autowired
	ServicePersonService servicePersonService;
	@Autowired
	VipCustomerDiscountService vipCustomerDiscountService;
	
	@Override
	@Transactional
	public void createPhOrder(OrderPh entity) {
		Hospital hospital = hospitalDao.get(entity.getHospitalId());
		if(hospital.getPhCusNo()==null)throw new BusinessException("该医院暂不提供陪护服务");
		entity.setOrderNo(CodeUtils.getOrderNo(OrderOriginalEnum.getByCode(entity.getOrign())));
		entity.setPayStatus(0);
		entity.setAcceptance(1);
		entity.setPricingType(2);
		Patient patient = patientService.noExistsSave(entity.getPatient());
		entity.setPatient(patient);
		entity.copyPatientProp();
		save(entity);
	}

	@Override
	@Transactional
	public void updatePhOrder(OrderPh entity) {
		Hospital hospital = hospitalDao.get(entity.getHospitalId());
		if(hospital.getPhCusNo()==null)throw new BusinessException("该医院暂不提供陪护服务");
		if(!entity.getState().equals(OrderState.ORDERED.name()))throw new BusinessException("只能修改预约状态下的订单");
		Patient patient = patientService.noExistsUpdate(entity.getPatient());
		entity.setPatient(patient);
		entity.copyPatientProp();
		update(entity);
	}

	@Override
	@Transactional
	public void confirmPhOrder(OrderPh order){
		OrderPh pereistedOrder = get(order.getId());
		if(pereistedOrder.getAmount()==null)throw new BusinessException("请先核算订单总费用");
		pereistedOrder.setServicePerson(order.getServicePerson());
		pereistedOrder.setServicePersonId(order.getServicePersonId());
		ServicePerson servicePerson = servicePersonService.get(pereistedOrder.getServicePersonId());
		if(servicePerson.getServiceState()==0){
			servicePerson.setServiceState(1);
			servicePersonService.update(servicePerson);
		}else{
			throw new BusinessException("该服务人员正忙，请更换服务人员");
		}
		if(pereistedOrder.getServicePersonId()==null || servicePersonService.get(pereistedOrder.getServicePersonId())==null)throw new BusinessException("请先确定订单护理人员");
		pereistedOrder.setState(OrderState.CONFIRMED.name());
		pereistedOrder.setTicketNo(order.getTicketNo());
		pereistedOrder.setPayStatus(1);
		if(pereistedOrder.getPayType()!=4){
			pereistedOrder.setPayeeName(order.getPayeeName());
			pereistedOrder.setPayeeId(order.getPayeeId());
			pereistedOrder.setPayType(order.getPayType());
		}
		update(pereistedOrder);
	}

	@Override
	@Transactional
	public void finishedPhOrder(OrderPh order) {

		OrderPh pereistedOrder = get(order.getId());
		pereistedOrder.setTicketNo(order.getTicketNo());
		pereistedOrder.setState(OrderState.FINISHED.name());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		pereistedOrder.setDataYmd(sdf.format(new Date()));
		User loginUser= (User) SecurityUtils.getSubject().getPrincipal();
		pereistedOrder.setUpdateId(loginUser.getId());
		pereistedOrder.setUpdateName(loginUser.getRealName());
		
		pereistedOrder.setActualAmount(order.getActualAmount());
		pereistedOrder.setRefundAmount(order.getRefundAmount());
		pereistedOrder.setActualDuration(order.getActualDuration());
		pereistedOrder.setAdminName(loginUser.getRealName());
		pereistedOrder.setAdminId(loginUser.getId());
		
		//如果订单是在线支付的，完成订单的同时会把结算的费用退回到用户的账户里面
		if(pereistedOrder.getPayType()==4){
			pereistedOrder.setRefundType(2);
			createSpendRecords(pereistedOrder);
		}else{
			pereistedOrder.setPayeeName(order.getPayeeName());
			pereistedOrder.setPayeeId(order.getPayeeId());
			pereistedOrder.setRefundType(1);
		}
		
		update(pereistedOrder);

		//修改陪护员工  工作状态0:未服务
		ServicePerson servicePerson = servicePersonDao.get(pereistedOrder.getServicePersonId());
		servicePerson.setServiceState(0);
		servicePersonDao.update(servicePerson);
		
		//如果是首单
		if(PayMode.FIRST_FREE.code().equals(pereistedOrder.getPayMode())){
			Coupon coupon = couponDao.findByOrderNo(pereistedOrder.getOrderNo());
			Assert.isTrue(coupon.getUsed() == 0,"首单免费优惠券已经被使用,orderNo:"+order.getOrderNo());
			coupon.setUsed(1);
			coupon.setUseTime(new Date());
			couponDao.update(coupon);
		}
	}

	public int calculateDays(Date start,Date end){
		return ((Long)((end.getTime()-start.getTime())/(24*60*60*1000))).intValue();
	}

	@Override
	public List<OrderPh> queryOrderPhByDataYmd(String dataYmd) {
		Map<String,Object> params = new HashMap<>();
		params.put("EQ_dataYmd",dataYmd);
		List<OrderPh> orderPhs = query(params, null);
		return orderPhs;
	}
	
	public int getAge(Date birthDay){ 
        //获取当前系统时间
        Calendar cal = Calendar.getInstance(); 
        //如果出生日期大于当前时间，则抛出异常
        if (cal.before(birthDay)) { 
            throw new IllegalArgumentException( 
                "出生日期不能大于当前时间"); 
        } 
        //取出系统当前时间的年、月、日部分
        int yearNow = cal.get(Calendar.YEAR); 
        int monthNow = cal.get(Calendar.MONTH); 
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); 
        //将日期设置为出生日期
        cal.setTime(birthDay); 
        //取出出生日期的年、月、日部分  
        int yearBirth = cal.get(Calendar.YEAR); 
        int monthBirth = cal.get(Calendar.MONTH); 
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH); 
        //当前年份与出生年份相减，初步计算年龄
        int age = yearNow - yearBirth; 
        //当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
        if (monthNow <= monthBirth) { 
            //如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
            if (monthNow == monthBirth) { 
                if (dayOfMonthNow < dayOfMonthBirth) age--; 
            }else{ 
                age--; 
            } 
        } 
        return age; 
    }

	@Override
	public void calculateAndSetAmount(OrderPh orderPh) {
		Department d = departmentService.get(orderPh.getDepartmentId());
		orderPh.setDepartmentName(d.getName());
		try{
			Hospital h = hospitalService.get(orderPh.getHospitalId());
			if(h!=null){
				if(h.getIsCooperate()==1){
					BigDecimal phServicePrice = h.getPhServicePrice();
					if(phServicePrice!=null){
						BigDecimal amount = h.getPhServicePrice().multiply(new BigDecimal(orderPh.getDuration()));
						orderPh.setPhServicePrice(phServicePrice);
						orderPh.setAmount(amount);
						orderPh.setPricingType(1);//计算费用方式  1：系统根据设置的收费标准定价
					}
				}
			}
		}catch(Exception e){
		}
	}
	
	@Transactional
	@Override
	public void createSpendRecords(OrderPh order) {
		Customer customer = customerService.findByCusNo(order.getCusNo());
		VipCustomerDiscount vipCustomerDiscount = vipCustomerDiscountService.findByCustomerId(customer.getId());
		if(order.getFreeItem()!=null && order.getFreeItem()==1){
			JSONObject discount = JSONObject.fromObject(vipCustomerDiscount.getDiscount());
			JSONObject item = discount.getJSONObject("PH");
			if(item.getInt("count")>0){
				item.put("count", item.getInt("count")-1);
				discount.put("PH", item);
				vipCustomerDiscount.setDiscount(discount.toString());
				vipCustomerDiscountService.update(vipCustomerDiscount);
			}else{
				throw new BusinessException("该会员免费服务项目次数已经用完");
			}	
		}
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
		customerAcctRecords.setSpendType(1);
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

	@Override
	public List<OrderPh> getOrderByAcceptance(Integer acceptance) {
		return getEntityDao().findByAcceptance(acceptance);
	}
	
	
}
