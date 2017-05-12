package com.acooly.epei.service.impl;

import com.acooly.epei.common.Constants;
import com.acooly.epei.domain.*;
import com.acooly.epei.service.CustomerAcctRecordsService;
import com.acooly.epei.service.CustomerAcctService;
import com.acooly.epei.service.CustomerService;
import com.acooly.epei.service.OrderPhService;
import com.acooly.epei.service.OrderPzService;
import com.acooly.epei.service.WechatPaymentLogService;
import com.acooly.module.sms.ShortMessageSendService;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.core.utils.net.HttpResult;
import com.acooly.core.utils.net.Https;
import com.acooly.epei.service.OrderBaseService;
import com.acooly.epei.util.CodeTools;
import com.acooly.epei.util.CodeUtils;
import com.acooly.epei.util.JaxbUtil;
import com.acooly.epei.util.LoginUserUtils;
import com.acooly.epei.util.UnifiedOrderResult;
import com.acooly.epei.util.UnifiedorderRequest;
import com.acooly.epei.util.WxUtils;
import com.acooly.epei.util.WxpayConfig;
import com.acooly.epei.dao.OrderBaseDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("OrderBaseService")
public class OrderBaseServiceImpl extends EntityServiceImpl<OrderBase, OrderBaseDao> implements OrderBaseService {

	private Logger logger = LoggerFactory.getLogger(OrderBaseServiceImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private OrderPzService orderPzService;

	@Autowired
	private OrderPhService orderPhService;

	@Autowired
	private ShortMessageSendService shortMessageSendService;
	
	@Autowired
	private CustomerAcctRecordsService customerAcctRecordsService;
	
	@Autowired
	private CustomerAcctService customerAcctService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private WechatPaymentLogService wechatPaymentLogService;
	
	@Override 
	public String getOrderType(Long orderId) {
		String sql = "select order_type from ep_order_base where id = ?";
		Map<String,Object> result = jdbcTemplate.queryForMap(sql, orderId);
		return result.get("order_type").toString();
	}

	@Override public OrderBase getByOrderNo(String orderNo) {
		return getEntityDao().findByOrderNo(orderNo);
	}
	
	
	@Override
	public List<OrderBase> getTheCustomerOrders(Long cusNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("EQ_cusNo", cusNo);				//0:是否被禁用
		List<OrderBase> orderBase = query(params, null);
		return orderBase;
	}

	@Override
	@Transactional
	public void cancelOrder(String orderNo,boolean back) {
		OrderBase order = getByOrderNo(orderNo);
		long interval = (order.getOrderTime().getTime() - new Date().getTime())/(1000*60);
		if(interval < 0)throw new IllegalStateException("预约时间已过，无法取消订单 ");
		if(!order.getState().equals("ORDERED"))throw new IllegalStateException("只有预约状态的订单才能取消 ");
		if(order.getPayStatus()==1)throw new IllegalStateException("已付款的订单不能取消 ");
		order.setState(OrderState.CANCELED.code());
		update(order);
	}

	@Override
	@Transactional
	public void createOrder(OrderBase order) {
		if(order instanceof OrderPz){
			orderPzService.createPzOrder((OrderPz) order);
		}
		else if(order instanceof  OrderPh){
			orderPhService.createPhOrder((OrderPh) order);
		}
	}
	
	@Override
	@Transactional
	public void updateOrder(OrderBase order) {
		if(!OrderState.ORDERED.getCode().equals(order.getState()))throw new IllegalStateException("已经确认的陪护单不能修改哦.");
		if(order instanceof  OrderPz){
			orderPzService.updatePzOrder((OrderPz) order);
		}
		else if(order instanceof  OrderPh){
			orderPhService.updatePhOrder((OrderPh) order);
		}
	}

	@Override public void notifyPatients() {
		String[] states = new String[]{OrderState.ORDERED.code(),OrderState.CONFIRMED.code()};
		Calendar startTime = Calendar.getInstance();
		//加一天
		startTime.add(Calendar.DAY_OF_YEAR,1);

		//时分秒置为0
		startTime.set(Calendar.HOUR_OF_DAY,0);
		startTime.set(Calendar.MINUTE,0);
		startTime.set(Calendar.SECOND,0);
		startTime.set(Calendar.MILLISECOND,0);

		Calendar endTime = Calendar.getInstance();
		endTime.setTime(startTime.getTime());
		endTime.add(Calendar.DAY_OF_YEAR,1);
		endTime.add(Calendar.SECOND,-1);

		List<OrderBase> orders = getEntityDao().findByStateInAndOrderTimeBetween(states, startTime.getTime(),
				endTime.getTime());
		logger.info("需要短信提醒明日有预约的订单量为:"+orders.size());

		for(OrderBase order : orders){
			notifyCustomer(order);
			logger.info("提醒用户["+order.getPatientName()+"]明日有预约成功.");
		}
	}

	private void notifyCustomer(OrderBase orderBase){

		String mobile = orderBase.getPatientMobile();
		String template = "";
		Map<String,Object> params = new HashMap<>();
		if(orderBase instanceof  OrderPh){
			template = Constants.PZ_ORDERED_SMS_TEMPLATE;
			params.put("hospital",orderBase.getHospitalName());
			params.put("patient",orderBase.getPatientName());
			params.put("orderTime", DateFormatUtils.format(orderBase.getOrderTime(), "yyyy-MM-dd HH:mm:ss"));
			params.put("address",orderBase.getHospital().getReceptionPosition());
		}else if(orderBase instanceof OrderPz){
			template = Constants.PH_ORDERED_SMS_TEMPLATE;
			params.put("hospital",orderBase.getHospitalName());
			params.put("patient",orderBase.getPatientName());
		}

		shortMessageSendService.sendByTemplateAsync(mobile,template,params);
	}

	/**
	 * 生成页面调用微信支付接口的JS需要用到的参数
	 */
	public void insertAndPay(RedirectAttributes redirectAttributes, String orderDetail,String orderNo, String rechargeAmount) throws Exception {
		String appId = WxpayConfig.APPID;
		String body = orderDetail;
		String mch_id = WxpayConfig.MCHI_ID;
		String notify_url = WxpayConfig.ORDER_PAY_NOTIFY_URL;
		String openid = LoginUserUtils.getCustomer().getOpenid();
		String out_trade_no = orderNo;
		String total_fee = rechargeAmount;
		String trade_type = "JSAPI";
		String key = WxpayConfig.PATERNER_KEY;
		String nonce_str = WxUtils.randomStr();
		String timeStamp1 = new Date().getTime() + "";
		UnifiedorderRequest ur = new UnifiedorderRequest();
		ur.setAppid(appId);
		ur.setBody(body);
		ur.setMch_id(mch_id);
		ur.setNonce_str(nonce_str);
		ur.setNotify_url(notify_url);
		ur.setOpenid(openid);
		ur.setOut_trade_no(out_trade_no);
		ur.setTimeStamp(timeStamp1);
		ur.setTotal_fee(total_fee);
		ur.setTrade_type(trade_type);
		Map<String,Object> map = WxUtils.beanToMap(ur);
		String signU = WxUtils.getSign(map, key);
		ur.setSign(signU);
		String xml = JaxbUtil.convertToXml(ur,"UTF-8");
		HttpResult hr = Https.getInstance().post("https://api.mch.weixin.qq.com/pay/unifiedorder", xml);
		UnifiedOrderResult ret = JaxbUtil.converyToJavaBean(hr.getBody(), UnifiedOrderResult.class);
		if(ret!=null && "SUCCESS".equals(ret.getReturn_code()) && "SUCCESS".equals(ret.getResult_code())){
			String package1 = "prepay_id="+ret.getPrepay_id();
			String nonceStr = WxUtils.randomStr();
			String timestamp = new Date().getTime()+"";
			Map<String,Object> paramP = new HashMap<String,Object>();
			paramP.put("appId", appId);
			paramP.put("nonceStr", nonceStr);
			paramP.put("package", package1);
			paramP.put("signType", "MD5");
			paramP.put("timeStamp", timestamp);
			String sign = WxUtils.getSign(paramP, key);
			redirectAttributes.addFlashAttribute("appId",appId);
			redirectAttributes.addFlashAttribute("paySign",sign);
			redirectAttributes.addFlashAttribute("nonceStr",nonceStr);
			redirectAttributes.addFlashAttribute("timestamp",timestamp);
			redirectAttributes.addFlashAttribute("wxPackage",package1);//请勿用package作为名称，否则页面会报错。具体原因还未去寻找。
			redirectAttributes.addFlashAttribute("signType","MD5");
		}else{
			redirectAttributes.addFlashAttribute("err_code_des", ret.getErr_code_des());
		}
	}
	
	@Override
	public int calculateDays(Date start,Date end) throws ParseException{
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = dateFormater.format(start);
		start = dateFormater.parse(date1);
		String date2 = dateFormater.format(end);
		end = dateFormater.parse(date2);
		return ((Long)((end.getTime()-start.getTime())/(24*60*60*1000))).intValue();
	}
	
	/**根据起始日期和天数，计算结束日期。因为业务需要，当days=1时，start和end相等*/
	@Override
	public Date calculateDate(Date start, int days){  
        Calendar calendar = Calendar.getInstance();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTime(start);
        calendar.add(Calendar.DATE,days-1);
        String temp = sdf.format(calendar.getTime());
        try {
			return sdf.parse(temp);
		} catch (ParseException e) {
			return null;
		}
    }  
	
	//日期格式转换:将日期转换为格式为yyyy-MM-dd的形式
	@Override
	public Date dateChangeFormat(Date date) throws ParseException{
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = dateFormater.format(date);
		return dateFormater.parse(dateString);
	}

	@Transactional
	@Override
	public CustomerAcctRecords createRechargetRecords(OrderBase order) {
		CustomerAcctRecords customerAcctRecords = new CustomerAcctRecords();
		Customer customer = customerService.findByCusNo(order.getCusNo());
		customerAcctRecords.setCustomerId(customer.getId());
		customerAcctRecords.setCustomerMobile(customer.getMobile());
		customerAcctRecords.setCustomerName(customer.getName());
		customerAcctRecords.setTitle("订单预付款充值");
		customerAcctRecords.setDataType(1);
		customerAcctRecords.setOutNo(order.getOrderNo());
		customerAcctRecords.setRechargeAmount(order.getAmount());
		customerAcctRecords.setRecordsNo(CodeUtils.getOrderNo(OrderOriginalEnum.getByCode("WECHAT")));
		customerAcctRecords.setRechargeChannle(1);
		customerAcctRecords.setRechargeStatus(1);
		customerAcctRecords.setSpendAmount(new BigDecimal(0));
		customerAcctRecords.setSpendType(0);
		customerAcctRecords.setSpendStatus(0);
		customerAcctRecords.setSpendChannel(0);
		customerAcctRecords.setMemo("订单在线预支付的费用已进入用户账户冻结金额中");
		customerAcctRecords.setDateYmd(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		
		String customerId = customer.getId().toString();
		String changeAmount = CodeTools.encode(order.getAmount().toString(),customerId);
		CustomerAcct customerAcct = customerAcctService.getCustomerAcctByCustomerId(customer.getId());
		if(customerAcct==null){
			customerAcctRecords.setBalance(order.getAmount());
			customerAcct=new CustomerAcct();
			customerAcct.setCustomerId(customer.getId());
			customerAcct.setTotalAmount(changeAmount);
			customerAcct.setBalance(changeAmount);
			customerAcct.setAvailableAmount(CodeTools.encode("0",customerId));
			customerAcct.setFreezAmount(changeAmount);
			customerAcctRecordsService.save(customerAcctRecords);
			customerAcctService.save(customerAcct);
		}else{
			customerAcctRecords.setBalance(new BigDecimal(CodeTools.dncode(customerAcct.getBalance(), customerId)).add(order.getAmount()));
			customerAcct.setBalance(CodeTools.add(customerAcct.getBalance(), changeAmount, customerId));
			customerAcct.setFreezAmount(CodeTools.add(customerAcct.getFreezAmount(), changeAmount, customerId));
			customerAcct.setTotalAmount(CodeTools.add(customerAcct.getTotalAmount(), changeAmount, customerId));
			customerAcctRecordsService.save(customerAcctRecords);
			customerAcctService.update(customerAcct);
		}
		return customerAcctRecords;
	}

	@Override
	@Transactional
	public void handlePaymentResult(String outNo) {
		OrderBase order = getEntityDao().findByOrderNo(outNo);
		//为了防止重复调用引发金额错误，处理前需要验证是否已经处理过了
		if(order.getPayStatus()==0){
			order.setPayType(4);
			order.setPayStatus(1);
			order.setPayeeId(0l);
			order.setPayeeName("微信商户平台");
			getEntityDao().update(order);
			//根据订单信息创建账务明细，支付的费用进入用户账户冻结金额中，待订单完成时，再从冻结金额中扣除实际消费的部分，余下部分转移到用户可用金额中
			CustomerAcctRecords customerAcctRecords = createRechargetRecords(order);
			//生成微信支付日志
			try{
				WechatPaymentLog wechatPaymentLog = new WechatPaymentLog();
				wechatPaymentLog.setTitle("订单微信支付回调记录");
				wechatPaymentLog.setDataOne(order.getCusNo()+"");
				wechatPaymentLog.setDataTwo(order.getOrderNo());
				wechatPaymentLog.setAmount(order.getAmount());
				wechatPaymentLog.setContent("{customerAcctRecordsId:"+customerAcctRecords.getId()+"}");//暂时只保存账户明细记录的ID，格式为json格式，方便以后添加元素
				wechatPaymentLog.setUserId(order.getCustomerId());
				wechatPaymentLog.setUserName(customerService.get(customerAcctRecords.getCustomerId()).getName());
				wechatPaymentLog.setUserType(2);
				wechatPaymentLogService.save(wechatPaymentLog);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			try{
				Patient patient = order.getPatient();
				Hospital hospital = order.getHospital();
				if(getOrderType(order.getId()).equals("PH")){
					Map<String,Object> params_yyfzr = new HashMap<>();
					params_yyfzr.put("hospitalName", order.getHospitalName());
					params_yyfzr.put("principalName", hospital.getPhPrincipalName());
					params_yyfzr.put("patient", patient.getName());
					params_yyfzr.put("mobile", patient.getMobile());
					params_yyfzr.put("departmentName", order.getDepartmentName());
					params_yyfzr.put("bed", order.getBed());
					params_yyfzr.put("orderTime", DateFormatUtils.format(order.getOrderTime(),"yyyy-MM-dd"));
					shortMessageSendService.sendByTemplateAsync(hospital.getPhPrincipalMobile(), Constants.PHFZR_ORDERED_SMS_TEMPLATE, params_yyfzr);
				}else{
					Map<String,Object> params_yyfzr = new HashMap<>();
					params_yyfzr.put("hospitalName", order.getHospitalName());
					params_yyfzr.put("principalName", hospital.getPzPrincipalName());
					params_yyfzr.put("patient", patient.getName().trim());
					params_yyfzr.put("mobile", patient.getMobile());
					params_yyfzr.put("brthday", patient.getBirthday());
					params_yyfzr.put("orderTime", DateFormatUtils.format(order.getOrderTime(),"yyyy-MM-dd HH:mm:ss"));
					params_yyfzr.put("address", hospital.getReceptionPosition());
					shortMessageSendService.sendByTemplateAsync(hospital.getPzPrincipalMobile(), Constants.PZFZR_ORDERED_SMS_TEMPLATE, params_yyfzr);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
	
}
