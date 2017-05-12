package com.acooly.epei.web.front;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.mapper.JsonMapper;
import com.acooly.epei.domain.Comment;
import com.acooly.epei.domain.Customer;
import com.acooly.epei.domain.OrderBase;
import com.acooly.epei.domain.OrderOriginalEnum;
import com.acooly.epei.domain.OrderPh;
import com.acooly.epei.domain.OrderPz;
import com.acooly.epei.domain.Patient;
import com.acooly.epei.domain.ServicePerson;
import com.acooly.epei.service.CommentService;
import com.acooly.epei.service.CustomerAcctRecordsService;
import com.acooly.epei.service.CustomerAcctService;
import com.acooly.epei.service.CustomerService;
import com.acooly.epei.service.DepartmentService;
import com.acooly.epei.service.HospitalService;
import com.acooly.epei.service.OrderBaseService;
import com.acooly.epei.service.OrderPhService;
import com.acooly.epei.service.OrderPzService;
import com.acooly.epei.service.PatientService;
import com.acooly.epei.service.ServicePersonService;
import com.acooly.epei.util.LoginUserUtils;
import com.acooly.epei.web.vo.OrderVo;

import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 修订记录： liubin 2015-11-05 15:16 创建
 */
@Controller
@RequestMapping("order")
public class OrderController extends AbstractJQueryEntityController<OrderBase, OrderBaseService> {

	private Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderBaseService orderBaseService;

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private ServicePersonService servicePersonService;

	@Autowired
	private OrderPhService orderPhService;

	@Autowired
	private OrderPzService orderPzService;
	
	@Autowired
	private DepartmentService departmentService;

	@Autowired
	CustomerAcctRecordsService customerAcctRecordsService;
	
	@Autowired
	CustomerAcctService customerAcctService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CommentService commentService;

	@RequestMapping("/{type}/booking")
	public String booking(@PathVariable("type") String type, @RequestParam(required = false) String orderNo, Model model) {
		if (StringUtils.isNotBlank(orderNo)) {
			OrderBase order = orderBaseService.getByOrderNo(orderNo);
			if (order != null && LoginUserUtils.getCusNo().equals(order.getCusNo())) {	
				model.addAttribute("entity", order);
			}
		}
		model.addAttribute("hospitals", hospitalService.getAllNoDelByType(type));
		model.addAttribute("patients", patientService.findByCusNo(LoginUserUtils.getCusNo()));
		model.addAttribute("type", type);
		return "/front/mobile/orderForm";	
	}

	@RequestMapping("pz/bookingSubmit")
	public String pzBookingSave(HttpServletRequest request, OrderPz entity, RedirectAttributes redirectAttributes) {
		String rtnView = "";
		Customer customer = LoginUserUtils.getCustomer();
		try {
			// 获取设备类型
			detectClientDevice(entity, request);	
			if(entity.getOrderTime().before(new Date())) throw new BusinessException("预约时间不能小于当前时间");
			entity.setHospitalId(entity.getHospital().getId());
			entity.setReceptionPosition(entity.getHospital().getReceptionPosition());
			entity.setPayStatus(0);
			entity.setCusNo(customer.getCusNo());
			if(customer.getCustomerType().equals("1")){
				entity.setAdminName(customer.getName());
				entity.setAdminId(customer.getId());
			}
			
			Patient patient = entity.getPatient();
			if(patient.getId()!=null){
				patient = patientService.get(patient.getId());
				entity.setPatient(patient);
				patient.setName(request.getParameter("patient.name"));
				patient.setMobile(request.getParameter("patient.mobile"));
			}
			
			patient.setCusId(customer.getId());
			patient.setCusNo(customer.getCusNo());
			patient.setCusName(customer.getName());
			
			if (entity.getId() == null) {
				orderBaseService.createOrder(entity);
				redirectAttributes.addFlashAttribute("err_code_des", "预约成功");
				rtnView = "redirect:/order/detail.html?orderNo=" + entity.getOrderNo();
			} else {
				orderBaseService.updateOrder(entity);
				redirectAttributes.addFlashAttribute("err_code_des", "修改成功");
				rtnView = "redirect:/order/detail.html?orderNo=" + entity.getOrderNo();
			}
		} catch (BusinessException be) {
			logger.error("会员[" + customer.getCusNo() + "]新增陪诊单出错.", be);
			redirectAttributes.addFlashAttribute("entity", entity);
			redirectAttributes.addFlashAttribute("errorMsg", be.getMessage());
			rtnView = "redirect:/order/pz/booking.html";
		} catch (Exception e) {
			logger.error("会员[" + customer.getCusNo() + "]新增陪诊单出错.", e);
			redirectAttributes.addFlashAttribute("entity", entity);
			redirectAttributes.addFlashAttribute("errorMsg", "出错了,请稍后再试.");
			rtnView = "redirect:/order/pz/booking.html";
		}
		return rtnView;
	}
	
	@RequestMapping("ph/bookingSubmit")
	public String phBookingSave(HttpServletRequest request, OrderPh entity, RedirectAttributes redirectAttributes) {
		String rtnView = "";
		Customer customer = LoginUserUtils.getCustomer();
		try {
			// 获取设备类型
			detectClientDevice(entity, request);
			
			if(entity.getOrderTime().before(new Date())) throw new BusinessException("预约时间不能小于当前时间");
			entity.setHospitalId(entity.getHospital().getId());
			entity.setPayStatus(0);
			entity.setCusNo(customer.getCusNo());
			if(customer.getCustomerType().equals("1")){
				entity.setAdminName(customer.getName());
				entity.setAdminId(customer.getId());
			}
			
			Patient patient = entity.getPatient();
			patient.setCusId(customer.getId());
			patient.setCusNo(customer.getCusNo());
			patient.setCusName(customer.getName());
			
			if (entity.getId() == null) {
				orderBaseService.createOrder(entity);
				rtnView = "redirect:/order/detail.html?orderNo=" + entity.getOrderNo();
			} else {
				orderBaseService.updateOrder(entity);
				rtnView = "redirect:/order/detail.html?orderNo=" + entity.getOrderNo();
			}
		} catch (BusinessException be) {
			logger.error("会员[" + customer.getCusNo() + "]新增陪护单出错.", be);
			redirectAttributes.addFlashAttribute("entity", entity);
			redirectAttributes.addFlashAttribute("errorMsg", be.getMessage());
			rtnView = "redirect:/order/ph/booking.html";
		} catch (Exception e) {
			logger.error("会员[" + customer.getCusNo() + "]新增陪护单出错.", e);
			redirectAttributes.addFlashAttribute("entity", entity);
			redirectAttributes.addFlashAttribute("errorMsg", "出错了,请稍后再试.");
			rtnView = "redirect:/order/ph/booking.html";
		}
		return rtnView;
	}
	
	@RequestMapping("/ph/reBooking")
	public String pzOrder(Model model, Long originalOrderId,
			@RequestParam(required = false) Long orderId) {
		OrderPh originalOrder = orderPhService.get(originalOrderId);
		OrderPh orderPh = new OrderPh();
		if(orderId!=null){
			orderPh = orderPhService.get(orderId);
		}else{
			orderPh.setBed(originalOrder.getBed());
			orderPh.setCustomerSex(originalOrder.getCustomerSex());
			orderPh.setPatient(originalOrder.getPatient());//
			orderPh.setHospital(originalOrder.getHospital());
			orderPh.setHospitalName(originalOrder.getHospitalName());
			orderPh.setDepartmentId(originalOrder.getDepartmentId());
			orderPh.setDepartmentName(originalOrder.getDepartmentName());
			orderPh.setServicePersonId(originalOrder.getServicePersonId());
			orderPh.setServicePerson(originalOrder.getServicePerson());
		}
		model.addAttribute("entity", orderPh);
		model.addAttribute("originalOrderId", originalOrderId);
		model.addAttribute("hospitals", hospitalService.getAllNoDelByType("ph"));
		model.addAttribute("servicePersonName", orderPh.getServicePerson());
		model.addAttribute("patients",patientService.findByCusNo(LoginUserUtils.getCusNo()));
		model.addAttribute("departments", JsonMapper.nonEmptyMapper().toJson(departmentService.getAll()));
		model.addAttribute("servicepersons",JsonMapper.nonEmptyMapper().toJson(servicePersonService.getAllNoServicePerson("ph")));
		return "/front/mobile/orderPhRenewForm";
	}
	
	@Transactional
	@RequestMapping("ph/reBookingSubmit")
	public String reBookingSubmit(HttpServletRequest request, RedirectAttributes redirectAttributes, OrderPh entity, Long originalOrderId) throws ParseException {
		OrderPh originalOrder = orderPhService.get(originalOrderId);
		String errorReturn;
		if(entity.getId()!=null){
			originalOrder.getFinallyEndDate();//如果是修改订单，则最后服务时间为原订单上记录的最后服务时间
			errorReturn = "redirect:/order/ph/reBooking.html?originalOrderId="+originalOrderId+"&orderId="+entity.getId();
		}else{
			errorReturn = "redirect:/order/ph/reBooking.html?originalOrderId="+originalOrderId;
		}
		if(orderBaseService.calculateDays(originalOrder.getEndTime(), entity.getOrderTime())<=0){
			redirectAttributes.addFlashAttribute("errorMsg","续约日期必须在服务期之后！");
			return errorReturn;
		}
		
		Customer customer = LoginUserUtils.getCustomer();
		detectClientDevice(entity, request);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse(request.getParameter("patient.birthday"));
		entity.setCustomerAge(orderPhService.getAge(date));
		entity.setHospital(entity.getHospital());
		entity.setHospitalId(entity.getHospital().getId());
		entity.setDepartmentName(entity.getDepartmentName());
		entity.setBed(entity.getBed());
		entity.setPayStatus(0);
		entity.setOriginalOrderId(originalOrderId);
		entity.setCusNo(customer.getCusNo());
		entity.setCustomerId(LoginUserUtils.getCusId());
		entity.setStartTime(entity.getOrderTime());
		entity.setEndTime(orderBaseService.calculateDate(entity.getStartTime(),entity.getDuration()));	
		entity.setPricingType(null);
		originalOrder.setFinallyEndDate(entity.getEndTime());
		orderPhService.update(originalOrder);
		if(entity.getServicePersonId()!=null){
			ServicePerson servicePerson = servicePersonService.get(entity.getServicePersonId());
			entity.setServicePerson(servicePerson.getName());
		}
		
		//非合作医院直接由系统设置价格,若是系统中没有预先设置价格，则不对价格，费用，费用计算方式赋值
		orderPhService.calculateAndSetAmount(entity);				
		Patient patient = entity.getPatient();
		patient.setCusName(customer.getName());
		patient.setCusId(customer.getId());
		patient.setCusNo(customer.getCusNo());
		
		if(customer.getCustomerType().equals("1")){
			entity.setAdminName(customer.getName());
		}
		if(entity.getId()==null){
			orderPhService.createPhOrder(entity);
		}else{
			orderPhService.updatePhOrder(entity);
		}
		return "redirect:/order/detail.html?orderNo="+entity.getOrderNo();
	}
	
	@RequestMapping("/confirmAmount")
	@ResponseBody
	public String confirmAmount(Long orderId, BigDecimal phServicePrice, Long servicePersonId) {
		OrderPh o = orderPhService.get(orderId);
		if(o.getPayStatus()!=0){
			throw new BusinessException("订单状态已变更，无法操作");
		}
		o.setPhServicePrice(phServicePrice);
		o.setAmount(phServicePrice.multiply(new BigDecimal(o.getDuration())));
		o.setPricingType(2);//2：管理员定价
		
		try{
			ServicePerson s = servicePersonService.get(servicePersonId);
			o.setServicePerson(s.getName());
			o.setServicePersonId(servicePersonId);
		}catch(Exception e){
		}
		
		orderPhService.update(o);
		String result = "费用已确认，等待患者付款";
		return result;
	}
	
	private void detectClientDevice(OrderBase entity, HttpServletRequest request) {
		// 获取设备类型
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
		DeviceType deviceType = userAgent.getOperatingSystem().getDeviceType();
		if (DeviceType.COMPUTER.equals(deviceType)) {
			entity.setOrign(OrderOriginalEnum.PC.name());
		} else {
			entity.setOrign(OrderOriginalEnum.WECHAT.name());
		}

	}

	@RequestMapping("showList")
	public String orderList(Model model) {
		Map<String, Object> params = new HashMap<>();
		params.put("EQ_cusNo", LoginUserUtils.getCusNo());
		Map<String, Boolean> sort = new HashMap<>();
		sort.put("id", false);
		List<OrderBase> orders = orderBaseService.query(params, sort);
		List<OrderVo> vos = new ArrayList<>();
		for (OrderBase order : orders) {
			OrderVo vo = new OrderVo(order);
			if ("PH".equals(orderBaseService.getOrderType(order.getId()))) {
				vo.setOrderType("陪护");
			} else {
				vo.setOrderType("陪诊");
			}
			vos.add(vo);
		}
		model.addAttribute("orders", vos);
		return "/front/mobile/orderShowList";
	}

	@RequestMapping("detail")
	public String orderDetail(String orderNo, Model model) throws ParseException {
		if (StringUtils.isNotBlank(orderNo)) {
			OrderBase order = orderBaseService.getByOrderNo(orderNo);
			if (order != null) {
				if (order.getCusNo().equals(LoginUserUtils.getCusNo())) {
					OrderVo vo = new OrderVo(order);
					Comment comment = commentService.findByOrderId(order.getId());
					if(comment!=null){
						vo.setComment(comment.getContent());
						vo.setStar(comment.getStar());
					}
					vo.setPatientBirthday(order.getPatient().getBirthday());
					vo.setPatientIdCard(order.getPatient().getIdCard());
					vo.setPatientMedicareCard(order.getPatient().getMedicareCard());
					if ("PH".equals(orderBaseService.getOrderType(order.getId()))) {
						OrderPh ph = (OrderPh) order;
						vo.setOrderType("陪护");
						vo.setStartTime(ph.getStartTime());
						vo.setEndTime(ph.getEndTime());
						vo.setDuration(ph.getDuration());
						vo.setServicePerson(ph.getServicePerson());
						vo.setEditable(true);
						int interval = orderBaseService.calculateDays(new Date(),order.getOrderTime());
						if (interval < 0) {
							vo.setEditable(false);
						}
						if(ph.getCustomerSex()!=null){
							if(ph.getCustomerSex().equals("0")){
								vo.setCustomerSex("女");
							}else{
								vo.setCustomerSex("男");
							}
						}
					} else {
						vo.setOrderType("陪诊");
					}
					model.addAttribute("order", vo);
				}
			}
		}

		return "/front/mobile/orderDetail";
	}

	@RequestMapping("orderCancel")
	@ResponseBody
	public JsonResult orderCancel(String orderNo) {
		JsonResult result = new JsonResult();
		try {
			OrderBase order = orderBaseService.getByOrderNo(orderNo);
			if (LoginUserUtils.getCusNo().equals(order.getCusNo())) {
				orderBaseService.cancelOrder(orderNo, false);
				result.setSuccess(true);
				result.setMessage("订单已取消.");
			} else {
				result.setMessage("无权限操作的订单");
				result.setSuccess(false);
			}
		} catch (IllegalStateException ise) {
			logger.error("取消订单发生错误,订单编号[" + orderNo + "],异常信息:", ise);
			result.setMessage(ise.getMessage());
			result.setSuccess(false);
		} catch (Exception e) {
			handleException(result, "取消订单", e);
		}

		return result;
	}

	@RequestMapping("/{type}/orderList")
	public String adminOrderList(Model model,
			@PathVariable("type") String type, HttpServletRequest request) {
		// CustomerType 1:医院管理员
		if ("PH".equals(type) && LoginUserUtils.getCustomer().getCustomerType().equals("1")) {
			PageInfo<OrderPh> paramPageInfo = getPageInfoType(request);
			Map<String, Object> paramMap = getSearchParams(request);//订单分页返回的是一个空的TreeMap()
			paramMap.put("EQ_hospital.id", LoginUserUtils.getCustomer().getCustomerHosId());
			paramPageInfo = orderPhService.query(paramPageInfo, paramMap);
			model.addAttribute("paramPageInfo", paramPageInfo);
			model.addAttribute("orderType", "PH");
		} else if ("PZ".equals(type) && LoginUserUtils.getCustomer().getCustomerType().equals("1")) {
			PageInfo<OrderPz> paramPageInfo = getPageInfoType(request);
			Map<String, Object> paramMap = getSearchParams(request);
			paramMap.put("EQ_hospital.id", LoginUserUtils.getCustomer().getCustomerHosId());
			paramPageInfo = orderPzService.query(paramPageInfo, paramMap);
			model.addAttribute("paramPageInfo", paramPageInfo);
			model.addAttribute("orderType", "PZ");
		}
		return "/front/mobile/adminShowList";
	}

	// 特殊查询
	private PageInfo getPageInfoType(HttpServletRequest request) {
		PageInfo pageinfo = new PageInfo();
		pageinfo.setCountOfCurrentPage(10);
		String page = request.getParameter("page");
		if (StringUtils.isNotBlank(page)) {
			pageinfo.setCurrentPage(Integer.parseInt(page));
		}
		String rows = request.getParameter("rows");
		if (StringUtils.isNotBlank(rows)) {
			pageinfo.setCountOfCurrentPage(Integer.parseInt(rows));
		}
		return pageinfo;
	}

	@RequestMapping("adminOrderDetail")
	public String adminOrderDetail(String orderNo, String orderType, Model model) throws ParseException {
		OrderBase order = orderBaseService.getByOrderNo(orderNo);
		if(order.getServicePersonId()!=null){
			ServicePerson person = servicePersonService.get(order.getServicePersonId());
			model.addAttribute("person", person);
		}
		// 查询陪诊陪护人员
		List<ServicePerson> sp = servicePersonService.getAllPersonAndServiceType(orderType, order.getHospital().getId());
		model.addAttribute("servicePerson", sp);
		model.addAttribute("order", order);
		model.addAttribute("orderType", orderType);
		return "/front/mobile/adminOrderDetail";
	}

	/**
	 * 订单支付
	 */
	@RequestMapping(value = "weixinPay", method = RequestMethod.POST)
	public String weixinPay(RedirectAttributes redirectAttributes, HttpServletRequest request, String orderNo) throws Exception{
		OrderBase orderBase = orderBaseService.getByOrderNo(orderNo);
		String orderDetail = orderBase.getHospitalName()+"在线订单";
		if(orderBase.getPayStatus()!=0){
			redirectAttributes.addFlashAttribute("err_code_des","您已经支付过订单费用了");
			return "redirect:/order/detail.html?orderNo="+orderBase.getOrderNo();
		}
		orderBaseService.insertAndPay(redirectAttributes, orderDetail, orderBase.getOrderNo(), orderBase.getAmount().multiply(new BigDecimal(100)).intValue()+"");
		return "redirect:/order/detail.html?orderNo="+orderBase.getOrderNo();
	}
	
	/**
	 * 微信支付回调函数
	 */
	@Transactional
	@RequestMapping("notify")
	public synchronized void notify(HttpServletRequest request,HttpServletResponse response){
		logger.info("***************notify*****************");
		StringBuffer xml = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while((line = reader.readLine()) != null) {
                xml.append(line);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        logger.info(xml.toString());
		Document document = null;
		try {
			document = DocumentHelper.parseText(xml.toString());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element returnInfo = document.getRootElement();
		String resultCode = returnInfo.elementText("result_code");
		String returnCode = returnInfo.elementText("return_code");
		
		//以下是支付成功后回调函数处理的业务逻辑部分
		if(resultCode.equals("SUCCESS") && returnCode.equals("SUCCESS")){
			String outNo = returnInfo.elementText("out_trade_no");
			orderBaseService.handlePaymentResult(outNo); 
		}
		try {
			response.getWriter().print(returnCode);
		} catch (IOException e) {
		}
	}
	
	@RequestMapping("comment")
	public String orderComment(Long orderId, Model model) {
		OrderBase order = orderBaseService.get(orderId);
		OrderVo vo = new OrderVo(order);
		Comment comment = commentService.findByOrderId(order.getId());
		if(comment!=null){
			vo.setComment(comment.getContent());
			vo.setStar(comment.getStar());
		}
		model.addAttribute("order",vo);
		return "/front/mobile/comment";
	}
	
	@ResponseBody
	@RequestMapping("commentInsert") 
	public String CommmentInsert(HttpServletRequest request,String orderNo,String fraction,String comment) throws UnsupportedEncodingException{
		int star = Integer.parseInt(fraction);
		String sign = "操作失败";
	    String inputer   = new String(comment.getBytes("8859_1") , "utf8");    
		Customer customer = LoginUserUtils.getCustomer();
		//先拉通过后回来改  数据库不允许为空,需要考虑用户重复点击  考虑数据库限制订单id不能重复
		if(customer.getCustomerType().equals("0")){
			try{ 
				Comment commentTemp = new Comment();
				commentTemp.setOrderId(orderBaseService.getByOrderNo(orderNo).getId());	
				commentTemp.setContent(inputer);
				commentTemp.setStar(star);
				commentTemp.setCustomerId(customer.getId()); 
				commentTemp.setCustomerUserName(customer.getUserName());
				OrderBase o = orderBaseService.getByOrderNo(orderNo);
				orderBaseService.update(o);
				commentService.save(commentTemp);
				sign = "操作成功";
				return sign;
				  }catch(Exception e){
					  return sign;
					
				  }
			
		}else if(customer.getCustomerType().equals("1")){
			try{ 
				Comment commentTemp = new Comment();
				commentTemp.setOrderId(orderBaseService.getByOrderNo(orderNo).getId());	
				commentTemp.setContent(inputer);
				commentTemp.setStar(star);
				commentTemp.setCustomerId(customerService.getCustomerByCusNo(orderBaseService.getByOrderNo(orderNo).getCusNo()).getId()); 
				commentTemp.setCustomerUserName(customerService.getCustomerByCusNo(orderBaseService.getByOrderNo(orderNo).getCusNo()).getUserName());
				commentTemp.setOperatorId(customer.getId());
				OrderBase o = orderBaseService.getByOrderNo(orderNo);
				orderBaseService.update(o);
				commentService.save(commentTemp);
				sign = "操作成功";
				   return sign;
				  }catch(Exception e){
					  return sign;
				  }
		}
		return sign;
		
	}
	
	@RequestMapping("commmentQuery")
	@ResponseBody
	public String CommmentQuery(Long orderId) {
		Comment comment = commentService.findByOrderId(orderId);
		if(comment == null) {
			return "success";
		}else{
			return comment.getContent();
		}
	}
	
}
