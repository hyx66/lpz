package com.acooly.epei.web;

import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
import net.sf.json.JSONObject;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Strings;
import com.acooly.epei.domain.*;
import com.acooly.epei.service.CommentService;
import com.acooly.epei.service.CustomerFamilyService;
import com.acooly.epei.service.CustomerFocusHospitalService;
import com.acooly.epei.service.CustomerService;
import com.acooly.epei.service.DepartmentService;
import com.acooly.epei.service.HospitalService;
import com.acooly.epei.service.OrderBaseService;
import com.acooly.epei.service.PatientService;
import com.acooly.epei.service.ServicePersonService;
import com.acooly.epei.service.ServicePriceService;
import com.acooly.epei.service.UserFocusHospitalService;
import com.acooly.epei.service.VipCustomerDiscountService;
import com.acooly.epei.util.CodeUtils;
import com.acooly.epei.web.vo.HospitalVo;
import com.acooly.epei.web.vo.ServicePersonVo;
import com.acooly.module.security.domain.User;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.epei.service.OrderPhService;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/manage/epei/orderPh")
public class OrderPhManagerController extends AbstractJQueryEntityController<OrderPh, OrderPhService> {

	private Logger logger = LoggerFactory.getLogger(OrderPhManagerController.class);
	
	public static final String regExp = "^1[0-9]{10}$";
	
	@Autowired
	private OrderPhService orderPhService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerFocusHospitalService customerFocusHospitalService;

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private OrderBaseService orderBaseService;
	
	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private ServicePersonService servicePersonService;
	
	@Autowired
	private ServicePriceService servicePriceService;
	
	@Autowired
	private UserFocusHospitalService userFocusHospitalService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private VipCustomerDiscountService vipCustomerDiscountService;
	
	@Autowired
	private CustomerFamilyService customerFamilyService;
	
	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		//加载医院信息
		model.put("hospitals", hospitalService.getAllNoDelByType(ServiceTypeEnum.PH.code()));
	}
	
	@Override
	protected void onEdit(HttpServletRequest request, HttpServletResponse response, Model model, OrderPh entity) {
		model.addAttribute("userName",customerService.findByCusNo(entity.getCusNo()).getUserName());
	}
	
	@Override
	public JsonEntityResult<OrderPh> saveJson(HttpServletRequest request, HttpServletResponse response) {
	JsonEntityResult<OrderPh> result = new JsonEntityResult<OrderPh>();
	try {
		String message = "新增成功";
		Customer customer = customerService.findCustomerByUsername(request.getParameter("patient.mobile"));
		if(customer!=null){
			message = "新增成功，该用户是平台会员";
		}
		result.setEntity(doSave(request, response, null, true));
		result.setMessage(message);
	} catch (Exception e) {
		handleException(result, "新增", e);
	}
		return result;
	}
	
	@Override
	public JsonEntityResult<OrderPh> updateJson(HttpServletRequest request, HttpServletResponse response) {
		 JsonEntityResult<OrderPh> result = new JsonEntityResult<OrderPh>();
		 try {
			 result.setEntity(doSave(request, response, null, false));
			 result.setMessage("更新成功");
			 } catch (Exception e) {
				 handleException(result, "更新", e);
				 }
		 return result;
		 }
	
	@Override
	protected OrderPh doSave(HttpServletRequest request, HttpServletResponse response, Model model, boolean isCreate)
			throws Exception {
		OrderPh entity = loadEntity(request);
		if (entity == null) {
			entity = getEntityClass().newInstance();
			doDataBinding(request, entity);
		}
		else{
			Long oldHospitalId = entity.getHospital().getId();
			String orignal = entity.getOrign();
			doDataBinding(request, entity);
			entity.setOrign(orignal);
			entity.getHospital().setId(oldHospitalId);
		}
		handle(request,entity);
		return entity;
	}

	public void handle(HttpServletRequest request,OrderPh entity) throws ParseException{
		if(!entity.getState().equals("ORDERED")){
			throw new BusinessException("订单状态已变更，无法操作");
		}
		if(entity.getPayStatus()!=null && entity.getPayStatus()==1){
			throw new BusinessException("订单状态已变更，无法操作");//已支付的订单不能修改
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if(StringUtils.isNotBlank(request.getParameter("patient.birthday"))){
			Date date = sdf.parse(request.getParameter("patient.birthday"));
			int age = orderPhService.getAge(date);
			entity.setCustomerAge(age);
		}
		Department department = departmentService.findDepartmentByNameAndHospitalId(request.getParameter("departmentName"), Long.parseLong(request.getParameter("hospital.id")));
		if(department!=null){
			entity.setDepartmentId(department.getId());
		}else{
			throw new BusinessException("所选医院下不存在该科室");
		}
		entity.setDataType(1);
		entity.setHospitalId(Long.parseLong(request.getParameter("hospital.id")));
		entity.setHospitalName(hospitalService.get(entity.getHospitalId()).getName());
		
		Customer c = customerService.findCustomerByUsername(request.getParameter("userName"));
		if(c==null)throw new BusinessException("无效的会员账号");
		User loginUser= (User) SecurityUtils.getSubject().getPrincipal();
		Patient patient = entity.getPatient();
		entity.setOrign(OrderOriginalEnum.FZZ.name());
		entity.setAdminName(loginUser.getRealName());
		entity.setAdminId(loginUser.getId());
		entity.setAcceptance(3);
		entity.setCusNo(c.getCusNo());
		entity.setCustomerId(c.getId());
		patient.setCusName(c.getName());
		patient.setCusNo(c.getCusNo());
		patient.setCusId(c.getId());

		entity.setStartTime(entity.getOrderTime());
		entity.setEndTime(orderBaseService.calculateDate(entity.getOrderTime(), entity.getDuration()));
		entity.setPricingType(2);
		entity.setPayStatus(0);
		if(entity.getFreeItem()==1){
			CustomerFamily customerFamily = customerFamilyService.findByCustomerFamilyIdCard(entity.getCustomerFamilyIdCard());
			if(customerFamily==null)throw new BusinessException("该会员账号没有添加相应的家庭成员信息");
			entity.setCustomerFamilyIdCard(entity.getPatientIdCard());
			entity.setAmount(entity.getPhServicePrice().multiply(new BigDecimal(entity.getDuration()-1)));
			if(c.getVipId()==null)throw new BusinessException("该用户不是VIP会员，无法使用免费服务项目");
			VipCustomerDiscount vipCustomerDiscount = vipCustomerDiscountService.findByCustomerId(c.getId());
			JSONObject json = JSONObject.fromObject(vipCustomerDiscount.getDiscount());
			if(!json.isNullObject()){
				JSONObject item = json.getJSONObject("PH");
				if(item.isNullObject() || item.getInt("count")<=0){
					throw new BusinessException("该用户暂无免费的陪护服务项目");
				}
			}else{
				throw new BusinessException("该用户暂无相关优惠");
			}
		}else{
			entity.setAmount(entity.getPhServicePrice().multiply(new BigDecimal(entity.getDuration())));
		}
		// 这里服务层默认是根据entity的Id是否为空自动判断是SAVE还是UPDATE.
		if(entity.getId() == null){
			getEntityService().createPhOrder(entity);
		}
		else{
			getEntityService().updatePhOrder(entity);
		}
	}

	protected void onShow(HttpServletRequest request, HttpServletResponse response, Model model, OrderPh entity)
			throws Exception {
		convertEnum(entity);
		model.addAttribute("comment", commentService.findByOrderId(entity.getId()));
	}
	
	@RequestMapping("goRenew")
	public String goRenew(Long id,Model model){
		OrderPh ph = orderPhService.get(id);
		model.addAttribute("orderPh",ph);
		return "/manage/epei/orderPhRenew";
	}

	@RequestMapping("renew")
	@ResponseBody
	public JsonResult renew(HttpServletRequest request,OrderPh orderPh){
		JsonResult result = new JsonResult();
		result.setMessage("订单创建成功！");
		try{
			OrderPh op = new OrderPh();
			op.setPatient(orderPh.getPatient());
			op.setHospital(orderPh.getHospital());
			op.setCustomerSex(orderPh.getCustomerSex());
			op.setPhServicePrice(orderPh.getPhServicePrice());
			op.setBed(orderPh.getBed());
			op.setOrign(OrderOriginalEnum.FZZ.code());
			op.setDepartmentName(orderPh.getDepartmentName());
			op.setOrderTime(orderPh.getOrderTime());
			op.setTimeInOneDay(orderPh.getTimeInOneDay());
			op.setDuration(orderPh.getDuration());
			op.setAppointmentType(orderPh.getAppointmentType());
			op.setOutNo(orderPh.getOutNo());
			op.setDisease(orderPh.getDisease());
			op.setNote(orderPh.getNote());
			op.setOriginalOrderId(orderPh.getId());
			op.setEndTime(orderBaseService.calculateDate(op.getOrderTime(), op.getDuration()));
			OrderPh originalOrder = orderPhService.get(op.getOriginalOrderId());
			if(originalOrder.getFinallyEndDate()!=null)throw new BusinessException("请勿重复创建续单记录！");
			if(!originalOrder.getState().equals("FINISHED"))throw new BusinessException("该订单还未完成，不能续单");
			handle(request,op);
		}
		catch (Exception e){
			handleException(result,"续单",e);
		}
		return  result;
	}
	
	@Override
	public JsonListResult<OrderPh> listJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonListResult<OrderPh> result = new JsonListResult<OrderPh>();
		try {
			result.appendData(referenceData(request));
			User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
			Map<String, Object> paramMap = getSearchParams(request);
			UserFocusHospital userFocusHospital = userFocusHospitalService.findByUserId(loginUser.getId());
			if(userFocusHospital!=null){
				paramMap.put("EQ_hospital.id", userFocusHospital.getHospitalId());
			}
			PageInfo<OrderPh> pageInfo = orderPhService.query(getPageInfo(request), paramMap, getSortMap(request));
			result.setTotal(Long.valueOf(pageInfo.getTotalCount()));
			result.setRows(pageInfo.getPageResults());
		} catch (Exception e) {
			handleException(result, "分页查询", e);
		}
		return result;
	}
	
	@RequestMapping("invalidJson")
	@ResponseBody
	public JsonResult invalidJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult result = new JsonResult();
		try {
			Serializable[] ids = getRequestIds(request);			
			for(int i =0;i<ids.length;i++){
				OrderPh orderPh = orderPhService.get(ids[i]);
				if(orderPh!=null){
					if(orderPh.getState().equals("ORDERED")&&orderPh.getPayType()!=4){
						orderPh.setState(OrderState.CANCELED.getCode());
						orderPhService.update(orderPh);
					}
				}				
			}
			result.setMessage("批量取消成功");
		} catch (Exception e) {
			handleException(result, "取消", e);
		}
		return result;
	}
	
	@RequestMapping("finishJson")
	@ResponseBody
	public JsonResult finishJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult result = new JsonResult();
		try {
			Serializable[] ids = getRequestIds(request);			
			for(int i =0;i<ids.length;i++){
				OrderPh orderPh = orderPhService.get(ids[i]);
				if(orderPh!=null){
					if(orderPh.getState().equals("CONFIRMED")&&orderPh.getPayType()!=4){
						orderPhService.finishedPhOrder(orderPh);
					}
				}				
			}
			result.setMessage("批量完成成功");
		} catch (Exception e) {
			handleException(result, "完成", e);
		}
		return result;
	}
	
	private void convertEnum(OrderBase entity){
		entity.setOrign(OrderOriginalEnum.getMsgByCode(entity.getOrign()));
		entity.setState(OrderState.getMsgByCode(entity.getState()));
	}

	@RequestMapping("goOrderPhConfirm")
	public String goOrderPhConfirm(Long id,Model model){
		OrderPh ph = orderPhService.get(id);
		convertEnum(ph);
		model.addAttribute("orderPh",ph);
		if(ph.getPayType()==4){
			model.addAttribute("Payee", ph.getPayeeName());
		}else{
			User loginUser= (User) SecurityUtils.getSubject().getPrincipal();
			model.addAttribute("Payee", loginUser.getRealName());
		}
		return "/manage/epei/orderPhConfirm";
	}


	@RequestMapping("orderPhConfirm")
	@ResponseBody
	public JsonResult orderPhConfirm(OrderPh orderPh){
		JsonResult result = new JsonResult();
		try{
			if(orderPh.getState().equals("ORDERED")){
				User loginUser= (User) SecurityUtils.getSubject().getPrincipal();
				orderPh.setPayeeId(loginUser.getId());
				orderPh.setPayeeName(loginUser.getRealName());
				orderPhService.confirmPhOrder(orderPh);
				result.setMessage("陪护单确认处理成功.");
			}else{
				throw new BusinessException("只能确认预约状态下的订单");
			}
		}
		catch (Exception e){
			handleException(result,"陪护单确认",e);
		}
		return  result;
	}

	@RequestMapping("goOrderPhFinished")
	public String goOrderPhFinished(Long id,Model model){
		OrderPh ph = orderPhService.get(id);
		convertEnum(ph);
		if(ph.getPayType()==4){
			model.addAttribute("Payee", ph.getPayeeName());
		}else{
			User loginUser= (User) SecurityUtils.getSubject().getPrincipal();
			model.addAttribute("Payee", loginUser.getRealName());
		}
		model.addAttribute("orderPh",ph);
		return "/manage/epei/orderPhFinish";
	}

	@RequestMapping("orderPhFinished")
	@ResponseBody
	public JsonResult orderPhFinished(OrderPh orderPh){
		OrderPh o = orderPhService.get(orderPh.getId());
		User loginUser= (User) SecurityUtils.getSubject().getPrincipal();
		orderPh.setPayeeId(loginUser.getId());
		orderPh.setPayeeName(loginUser.getRealName());
		JsonResult result = new JsonResult();
		try{
			if(!o.getState().equals("CONFIRMED")){
				throw new BusinessException("请先确认订单");
			}
			if(orderPh.getActualDuration()>o.getDuration()||orderPh.getActualDuration()<0){
				throw new BusinessException("实际服务天数不能大于预约总天数或小于0");
			}else{
				BigDecimal oddChange = o.getPhServicePrice().multiply(new BigDecimal(o.getDuration()-orderPh.getActualDuration()));//计算应退还多少钱
				orderPh.setActualAmount(o.getAmount().subtract(oddChange));
				orderPh.setRefundAmount(oddChange);
			}
			orderPhService.finishedPhOrder(orderPh);
			//记录会员某个时间在某个医院消费过
			try{
				CustomerFocusHospital customerFocusHospital = new CustomerFocusHospital();
				Customer customer = customerService.getCustomerByCusNo(o.getCusNo());
				if(customer!=null){
					customerFocusHospital.setCustomerId(customer.getId());
					customerFocusHospital.setCustomerMobile(customer.getMobile());
					Hospital h = hospitalService.get(o.getHospital().getId());
					customerFocusHospital.setHospitalId(h.getId());
					customerFocusHospital.setHospitalName(h.getName());
					customerFocusHospital.setDepartmentId(o.getDepartmentId());
					customerFocusHospital.setDepartmentName(o.getDepartmentName());
					Boolean exist = false;
					for(CustomerFocusHospital cf:customerFocusHospitalService.queryListByCustomerId(customerFocusHospital.getCustomerId())){
						if(customerFocusHospital.getDepartmentId().equals(cf.getDepartmentId())){
							exist = true;
							break;
						}
					}
					if(!exist){
						customerFocusHospitalService.save(customerFocusHospital);
					}	
				}
			}catch(Exception e){
			}
			result.setMessage("陪护单完成处理成功.");
		}
		catch (Exception e){
			handleException(result,"陪护单完成",e);
		}
		return  result;
	}

	@RequestMapping("cancel")
	@ResponseBody
	public JsonResult cancel(Long id){
		JsonResult result  = new JsonResult();
		try{
			OrderPh ph = orderPhService.get(id);
			if(ph != null){
				orderBaseService.cancelOrder(ph.getOrderNo(), true);
				if(ph.getOriginalOrderId()!=null){
					orderBaseService.update(orderBaseService.get(ph.getOriginalOrderId()));
				}
				result.setMessage("陪护单取消成功.");
			}
			else{
				result.setSuccess(false);
				result.setMessage("不存在的陪护单,请试试刷新页面.");
			}
		}
		catch (Exception e){
			handleException(result,"陪护单取消",e);
		}
		return  result;
	}

	/*---------------------------------Excel导入开始---------------------------------------*/
	/**
	 * 读取文件后，转换为主实体对象前，进行预处理。可选：参数检测；主实体对象相关的子对象的导入处理等。 <br>
	 * 可选这里可以进行合法性检测，剔除格式错误的行，返回正确格式的行
	 * ，然后在Message中记录错误的提示。如果选择图略错误进行保存正确格式的数据，则不抛出异常，否则抛出异常，终止批量保存。 <br>
	 * 默认实现是返回传入的集合，不做任何处理
	 * 
	 * @param lines
	 */
	@Override
	public List<String[]> beforeUnmarshal(List<String[]> lines) {
		lines.remove(0);
		int row = 1;	//行
		for (String[] line : lines) {
			row ++;
			//判断病患手机号码格式是否正确
			if(!Pattern.matches(regExp, line[3])){
				throw new BusinessException("病患手机号码格式错误！");
			}
			//就诊医院信息
			Hospital hospital = hospitalService.findHospitalByName(line[4]);
			if(null == hospital){
				throw new BusinessException("第"+row+"行，"+"第5列，"+"，就诊医院不存在！");
			}else{
				//医院下有无该科室信息
				Department department = departmentService.findDepartmentByNameAndHospitalId(line[5],hospital.getId());
				if(null == department){
					throw new BusinessException("第"+row+"行，"+"第6列，"+"，就诊医院下不存在该科室！");
				}
			}
			//判断服务人员手机号码格式是否正确
			if(!Pattern.matches(regExp, line[8])){
				throw new BusinessException("服务人员手机号码格式错误！");
			}else{
				//服务人员信息
				long mobileLong = Long.parseLong(line[8]);
				ServicePerson sperson = servicePersonService.findServicePersonByNameAndMobileAndHospitalIdAndServiceType(line[7],mobileLong,hospital.getId(),"PH");
				if(sperson == null){
					throw new BusinessException("第"+row+"行，"+"第8列，"+"，就诊医院下不存在该服务人员！");
				}
			}
		}
		return lines;
	}
	
	/**
	 * 转换读取的数据为实体
	 * 
	 * @param uploadResults
	 */
	@Override
	public List<OrderPh> unmarshal(List<String[]> lines) {
		List<OrderPh> entities = new LinkedList<OrderPh>();
		for (String[] line : lines) {
			//每次换行时new一个新的陪护订单
			OrderPh op = new OrderPh();
			//生成订单号
			op.setOrderNo(CodeUtils.getOrderNo(OrderOriginalEnum.getByCode("PC")));
			//患者姓名					line[0]
			op.setPatientName(line[0]);
			//性别					line[1]
			//出生日期					line[2]
			//病患电话					line[3]
			op.setPatientMobile(line[3]);
			//就诊医院信息
			Hospital hospital = hospitalService.findHospitalByName(line[4]);
			//医院ID
			op.setHospitalId(hospital.getId());
			//就诊医院					line[4]
			op.setHospitalName(line[4]);
			//就诊科室					line[5]
			op.setDepartmentName(line[5]);
			//床位					line[6]
			op.setBed(line[6]);
			//服务人员					line[7]		服务人员电话				line[8]
			op.setServicePerson(line[7]);
			//服务人员信息
			long mobileLong = Long.parseLong(line[8]);
			ServicePerson sperson = servicePersonService.findServicePersonByNameAndMobileAndHospitalIdAndServiceType(line[7],mobileLong,hospital.getId(),"PH");
			//服务人员ID
			op.setServicePersonId(sperson.getId());
			//服务方式					line[9]
			op.setServiceGrade(line[9]);
			//服务方式信息
			ServicePrice sprice = servicePriceService.findServicePriceByHospitalIdAndServiceGradeAndServiceType(hospital.getId(),line[10],"PH");
			if(sprice != null){
				//服务方式ID
				op.setServicePriceId(sprice.getId());
			}
			//服务价格(元)				line[10]
			float price = Float.parseFloat(line[10]);
			op.setPrice(price);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date orderTime = sdf.parse(line[11]);
				Date startTime = sdf.parse(line[13]);
				Date endTime = sdf.parse(line[14]);
				
				op.setOrderTime(orderTime);
				op.setStartTime(startTime);
				op.setEndTime(endTime);
				
				//时长(天)			(结算时间 - 开始时间)+1
				op.setDuration((((Long)((endTime.getTime()-startTime.getTime())/(24*60*60*1000))).intValue())+1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//具体时间(上午、下午)		line[12]
			if(line[12].equals("上午")){
				op.setTimeInOneDay("AM");
			}else if(line[12].equals("下午")){
				op.setTimeInOneDay("PM");
			}
			
			//检查完当前行最后一列没有问题后才进行病患信息的添加
			//根据病患姓名和手机号查询病患是否存在
			Patient patients = new Patient();
			Patient patient = patientService.findPatientByNameAndMobile(line[0],line[3]);
			//管理员信息
			User loginUser= (User) SecurityUtils.getSubject().getPrincipal();
			if(patient == null){
				patients.setCusName(loginUser.getRealName());
				patients.setCusNo(loginUser.getId());
				patients.setCusId(loginUser.getId());
				//病患信息
				patients.setName(line[0]);
				if(line[1].equals("男")){
					patients.setGender(1);
				}else if(line[1].equals("女")){
					patients.setGender(0);
				}
				patients.setBirthday(line[2]);
				patients.setMobile(line[3]);
				patientService.save(patients);
			}else{
				patients = patient;
			}
			//录入的管理员ID
			op.setCusNo(loginUser.getId());
			//录入的管理员姓名
			op.setAdminName(loginUser.getRealName());
			op.setPatient(patients);
			op.setOrign("Excel 导入");
			op.setHospital(hospital);
			
			entities.add(op);
		}
		return entities;
	}
	/*---------------------------------Excel导入结束---------------------------------------*/
	
	/*---------------------------------Excel导出开始---------------------------------------*/
	/**
	 * 导出Excel实现。 <br>
	 * <li>可选使用jxl,poi方式直接输出流到reponse,不需要导出页面。该方法返回fase; <li>
	 * 可选使用页面方式实现，需要返回到导出页面。该方法返回true
	 * 
	 * @param list
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	public boolean doExportExcelBody(List<OrderPh> list, HttpServletRequest request, HttpServletResponse response) {
		WritableWorkbook workbook = null;
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			String[] headerNames = {
				"订单编号","病患姓名","病患性别","病患生日","病患电话","病患身份证","所属会员编号","所属会员姓名","就诊医院","就诊科室","床位","服务名称","服务价格",
				"预约日期","预约时间段","服务人员姓名","服务人员电话","陪护开始时间","陪护结束时间","订单状态","预约方式","付款方式","创建时间","修改时间"
			};
			workbook = Workbook.createWorkbook(out);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			//设置第一行栏目名称的字体
			WritableFont font1 = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);
	        WritableCellFormat cellFormat = new WritableCellFormat(font1);
			int row = 0;
			// 写入header
			if (headerNames != null) {
				for (int i = 0; i < headerNames.length; i++) {
					sheet.addCell(new Label(i, row, headerNames[i], cellFormat));
				}
				row++;
			}
			
			for (OrderPh op : list) {
				List<String> entityData = new ArrayList<String>();
				//订单编号
				entityData.add(op.getOrderNo());
				//病患姓名
				entityData.add(op.getPatientName());
				//病患性别
				entityData.add(String.valueOf(op.getPatient().getGender()).equals("1")?"男":"女");
				//病患生日
				entityData.add(op.getPatient().getBirthday());
				//病患电话
				entityData.add(op.getPatientMobile());
				//病患身份证
				entityData.add(op.getPatientIdCard());
				//所属会员编号
				entityData.add(String.valueOf(op.getCusNo()));
				//所属会员姓名
				entityData.add(op.getPatient().getCusName());
				//就诊医院
				entityData.add(op.getHospitalName());
				//就诊科室
				entityData.add(op.getDepartmentName());
				//床位
				entityData.add(op.getBed());
				//服务名称
				entityData.add(op.getServiceGrade());
				//服务价格
				entityData.add(String.valueOf(op.getPrice()));
				//预约日期
				entityData.add(String.valueOf(op.getOrderTime()));
				//预约时间段
				if(op.getTimeInOneDay() != null){
					if(op.getTimeInOneDay().equals("AM")){
						entityData.add("上午");
					}else if(op.getTimeInOneDay().equals("PM")){
						entityData.add("下午");
					}
				}else{
					entityData.add(null);
				}
				if(op.getServicePersonId() != null){
					ServicePerson sperson = servicePersonService.get(op.getServicePersonId());
					if(sperson != null){
						//服务人员姓名
						entityData.add(sperson.getName());
						//服务人员电话
						entityData.add(String.valueOf(sperson.getMobile()));
					}else{
						entityData.add(op.getServicePersonName());
						entityData.add(op.getServicePersonMobile());
					}
				}else{
					entityData.add(op.getServicePersonName());
					entityData.add(op.getServicePersonMobile());
				}
				//陪护开始时间
				entityData.add(String.valueOf(op.getStartTime()));
				//陪护结束时间
				entityData.add(String.valueOf(op.getEndTime()));
				//订单状态
				if(op.getState() != null){
					if(op.getState().equals("ORDERED")){
						entityData.add("预约");
					}else if(op.getState().equals("CANCELED")){
						entityData.add("取消");
					}else if(op.getState().equals("CONFIRMED")){
						entityData.add("确认");
					}else if(op.getState().equals("FINISHED")){
						entityData.add("完成");
					}
				}else{
					entityData.add(null);
				}
				//预约方式
				if(String.valueOf(op.getAppointmentType()) != null){
					if(String.valueOf(op.getAppointmentType()).equals("0")){
						entityData.add("电话预约");
					}else if(String.valueOf(op.getAppointmentType()).equals("1")){
						entityData.add("主动预约");
					}else{
						entityData.add(null);
					}
				}else{
					entityData.add("主动预约");
				}
				//付款方式
				if(op.getPayMode() != null){
					if(op.getPayMode().equals("FIRST_FREE")){
						entityData.add("首单免费");
					}else if(op.getPayMode().equals("OFFLINE")){
						entityData.add("线下收取");
					}
				}else{
					entityData.add("线下收取");
				}
				//创建时间
				entityData.add(String.valueOf(op.getCreateTime()));
				//修改时间
				entityData.add(String.valueOf(op.getUpdateTime()));
				for (int i = 0; i < entityData.size(); i++) {
					sheet.addCell(new Label(i, row, Strings.trimToEmpty(entityData.get(i))));
				}
				row++;
			}
			workbook.write();
			out.flush();
		} catch (Exception e) {
			throw new RuntimeException("执行导出过程失败[" + e.getMessage() + "]");
		} finally {
			try {
				workbook.close();
			} catch (Exception e2) {
			}
			IOUtils.closeQuietly(out);
		}
		return false;
	}
	
	/**
	 * 导出Excel模版
	 */
	@RequestMapping("downloadOrderPhExcelModel")
	public String downloadOrderPhExcelModel(HttpServletRequest request, HttpServletResponse response) {
		try {
			//XLS文件的一些属性
			doExportExcelHeader(request, response);
			//XLS文件内容
			WritableWorkbook workbook = null;
			OutputStream out = null;
			try {
				out = response.getOutputStream();
				String[] headerNames = {
						"病患姓名","性别","出生日期(格式19000101)","病患电话","就诊医院","就诊科室","床位","服务人员",
						"服务人员电话","服务方式","服务价格(元)","预约时间(格式1900-01-01)","具体时间(上午、下午)",
						"陪护开始日期(格式1900-01-01)","陪护结束日期(格式1900-01-01)"
				};
				workbook = Workbook.createWorkbook(out);
				WritableSheet sheet = workbook.createSheet("Sheet1", 0);
				int row = 0;
				//设置第一行栏目名称的字体
				WritableFont font1 = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);
		        WritableCellFormat cellFormat = new WritableCellFormat(font1);
				if (headerNames != null) {
					for (int i = 0; i < headerNames.length; i++) {
						sheet.addCell(new Label(i, row, headerNames[i], cellFormat));
					}
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
			return null;
		} catch (Exception e) {
			logger.warn(getExceptionMessage("exportExcel", e), e);
			handleException("导出Excel", e, request);
		}
		return getSuccessView();
	}
	/*---------------------------------Excel导出结束---------------------------------------*/
	
	@RequestMapping("comboboxHospital")
	@ResponseBody
	public List<HospitalVo> comboboxHospital(String serviceType){
		List<Hospital> hospitals = null;
		User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
		UserFocusHospital userFocusHospital = userFocusHospitalService.findByUserId(loginUser.getId());
		if(userFocusHospital!=null){
			hospitals = hospitalService.getNoDelByHospitalIdAndType(userFocusHospital.getHospitalId(), serviceType);
		}else{
			hospitals = hospitalService.getAllNoDelByType(serviceType);
		}
		List<HospitalVo> vos  = new ArrayList<>();
		for(Hospital hospital : hospitals){
			vos.add(new HospitalVo(hospital));
		}
		return vos;
	}
	
	@RequestMapping("servicePrices")
	@ResponseBody
	public List<ServicePrice> servicePrices(Long hospitalId,String type){
		List<ServicePrice> prices =  servicePriceService.getAllServiceByHospitalAndType(hospitalId,type);
		for(ServicePrice price : prices){
			price.setServiceGrade(price.getServiceGrade()+"-"+price.getPrice());
		}

		return  prices;
	}
	
	@RequestMapping("comboboxDepartment")
	@ResponseBody
	public List<Department> comboboxDepartment(Long hosid){
		List<Department> Departments = departmentService.getSamehosid(hosid);
		return Departments;
	}
		
	@RequestMapping("comboboxServicePerson")
	@ResponseBody
	public List<ServicePersonVo> combobox(Long hospitalId,String serviceType){
		Map<String,Object> params = new HashMap<>();
		params.put("EQ_deleted",0);
		params.put("EQ_hospitalId",hospitalId);
		params.put("EQ_serviceType",serviceType);
		params.put("EQ_serviceState",0);

		List<ServicePerson> persons = servicePersonService.query(params,null);
		List<ServicePersonVo> vos = new ArrayList<>();

		for(ServicePerson person :  persons){
			vos.add(new ServicePersonVo(person));
		}

		return  vos;
	}
	
	@RequestMapping("noteModify")
	@ResponseBody
	public String noteModify(HttpServletRequest request){
		Long id = Long.parseLong(request.getParameter("id"));
		String note = request.getParameter("note");
		System.out.println("备注信息："+note);
		OrderPh o = orderPhService.get(id);
		o.setNote(note);
		orderPhService.update(o);
		return note;
	}
	
	@RequestMapping("contentModify")
	@ResponseBody
	public String contentModify(Long orderId, String content, int star){
		Comment comment = commentService.findByOrderId(orderId);
		User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
		OrderPh order = orderPhService.get(orderId);
		Customer customer = customerService.getCustomerByCusNo(order.getCusNo());
		if(comment == null){
			comment = new Comment();
			comment.setOrderId(order.getId());
			comment.setCustomerId(customer.getId());
			comment.setCustomerUserName(customer.getUserName());
			comment.setContent(content);
			comment.setStar(star);
			comment.setOperatorId(loginUser.getId());
			commentService.save(comment);
		}else{
			comment.setContent(content);
			comment.setStar(star);
			comment.setOperatorId(loginUser.getId());
			commentService.update(comment);
		}
		return content;
	}
	
	@ResponseBody
	@RequestMapping("checkCustomer")
	public JsonResult checkCustomer(String userName){
		JsonResult result = new JsonResult();
		Customer customer = customerService.findCustomerByUsername(userName);
		if(customer == null){
			result.setSuccess(false);
			result.setMessage("未注册");
		}else if(customer.getVipGrade() != null){
			result.setSuccess(true);
			result.setMessage("VIP会员");
			VipCustomerDiscount vipCustomerDiscount = vipCustomerDiscountService.findByCustomerId(customer.getId());
			JSONObject json = JSONObject.fromObject(vipCustomerDiscount.getDiscount());
			if(json.isNullObject()){
				result.appendData("count", 0);
			}else{
				JSONObject item = json.getJSONObject("PH");
				if(item.isNullObject() || item.get("count") == null){
					result.appendData("count", 0);
				}else{
					result.appendData("count", item.get("count"));
				}
			}
		}else{
			result.setSuccess(false);
			result.setMessage("普通会员");
		}
		return result;
	}
	
	@RequestMapping("queryAcceptance")
	@ResponseBody
	public synchronized String queryAcceptance (Long id){
		OrderPh orderPh =   orderPhService.get(id);
		User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
		if(orderPh.getAcceptance()==null || orderPh.getAcceptance()==1){
			orderPh.setAcceptance(2);
			orderPh.setAcceptTime(new Date());
			orderPh.setAcceptUserId(loginUser.getId());
			orderPh.setAcceptUserName(loginUser.getRealName());
			orderPhService.update(orderPh);
			return "YES";
		}else if(orderPh.getAcceptUserId()==loginUser.getId()){
			return "YES";
		}else{
			return "NO";
		}
	}

}
