package com.acooly.epei.web;

import java.io.OutputStream;
import java.io.Serializable;
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
import com.acooly.epei.domain.*;
import com.acooly.epei.service.*;
import com.acooly.epei.util.CodeUtils;
import com.acooly.epei.web.vo.HospitalVo;
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
import com.acooly.core.utils.Strings;

import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/manage/epei/orderPz")
public class OrderPzManagerController extends AbstractJQueryEntityController<OrderPz, OrderPzService> {
	
	private Logger logger = LoggerFactory.getLogger(OrderPzManagerController.class);
	
	public static final String regExp = "[0-9]{0,11}";
	
	@Autowired
	private OrderPzService orderPzService;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private PzServiceService pzServiceService;

	@Autowired
	private ServicePersonService servicePersonService;

	@Autowired
	private OrderBaseService orderBaseService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private ServicePriceService servicePriceService;
	
	@Autowired
	private CustomerFocusHospitalService customerFocusHospitalService;
	
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
		model.put("hospitals", hospitalService.getAllNoDelByType(ServiceTypeEnum.PZ.code()));
	}

	@Override
	protected void onCreate(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String,Object> params = new HashMap<>();
		params.put("EQ_deleted",0);
		model.addAttribute("hospitals",hospitalService.query(params,null));
	}

	@Override
	protected void onEdit(HttpServletRequest request, HttpServletResponse response, Model model, OrderPz entity) {
		Map<String,Object> params = new HashMap<>();
		params.put("EQ_deleted",0);
		model.addAttribute("hospitals",hospitalService.query(params,null));
		model.addAttribute("userName",customerService.findByCusNo(entity.getCusNo()).getUserName());
	}

	@Override
	public JsonEntityResult<OrderPz> updateJson(HttpServletRequest request, HttpServletResponse response) {
		JsonEntityResult<OrderPz> result = new JsonEntityResult<OrderPz>();
		try {
			result.setEntity(doSave(request, response, null, false));
			result.setMessage("更新成功");
		} catch (Exception e) {
			handleException(result, "更新", e);
		}
		return result;
	}
	
	@Override
	protected OrderPz doSave(HttpServletRequest request, HttpServletResponse response, Model model, boolean isCreate)
			throws Exception {
		OrderPz entity = loadEntity(request);
		if (entity == null) {
			entity = getEntityClass().newInstance();
		}
		onSave(request, response, model, entity, isCreate);
		return entity;
	}
	
	@Override
	protected OrderPz onSave(HttpServletRequest request, HttpServletResponse response, Model model, OrderPz entity, boolean isCreate) throws Exception{
		Customer customer = customerService.findCustomerByUsername(request.getParameter("userName"));
		if(customer==null)throw new BusinessException("无效的用户名");
		Patient p = patientService.findByCusNoAndName(customer.getCusNo(), request.getParameter("patient.name"));
		if(p!=null)entity.setPatient(p);
		//将doSave发方法中的doDataBinding移动到这里来了，这么做的目的是为了防止患者信息变更时自动存储到数据库中而导致查询病患信息时结果多个的情况（result returns more than one element...）
		doDataBinding(request, entity);
		if(entity.getFreeItem()==1){
			CustomerFamily customerFamily = customerFamilyService.findByCustomerFamilyIdCard(entity.getPatientIdCard());
			if(customerFamily==null)throw new BusinessException("该会员账号没有添加相应的家庭成员信息");
			entity.setCustomerFamilyIdCard(entity.getPatientIdCard());
			if(customer.getVipId()==null)throw new BusinessException("该用户不是VIP会员，无法使用免费服务项目");
			VipCustomerDiscount vipCustomerDiscount = vipCustomerDiscountService.findByCustomerId(customer.getId());
			JSONObject json = JSONObject.fromObject(vipCustomerDiscount.getDiscount());
			if(!json.isNullObject()){
				JSONObject item = json.getJSONObject("PZ");
				if(item.isNullObject() || item.getInt("count")<=0){
					throw new BusinessException("该用户暂无免费的陪诊服务项目");
				}
			}else{
				throw new BusinessException("该用户暂无相关优惠");
			}
		}
		
		entity.setPayStatus(0);
		entity.setAcceptance(3);
		entity.setPricingType(2);
		entity.setHospitalId(Long.parseLong(request.getParameter("hospital.id")));
		entity.setHospitalName(hospitalService.get(entity.getHospitalId()).getName());
		
		User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
		entity.setAdminName(loginUser.getRealName());
		entity.setAdminId(loginUser.getId());
		entity.setCusNo(customer.getCusNo());
		Patient patient = entity.getPatient();
		patient.setCusName(customer.getName());
		patient.setCusNo(customer.getCusNo());
		patient.setCusId(customer.getId());
		if(isCreate){
			entity.setAcceptTime(new Date());
			entity.setAcceptUserId(loginUser.getId());
			entity.setAcceptUserName(loginUser.getRealName());
			getEntityService().createPzOrder(entity);
		}
		else{
			getEntityService().updatePzOrder(entity);
		}
		return entity;
	}

	protected void onShow(HttpServletRequest request, HttpServletResponse response, Model model, OrderPz entity)
			throws Exception {
		convertEnum(entity);
		model.addAttribute("comment", commentService.findByOrderId(entity.getId()));
	}

	@Override
	public JsonListResult<OrderPz> listJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonListResult<OrderPz> result = new JsonListResult<OrderPz>();
		try {
			result.appendData(referenceData(request));
			User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
			Map<String, Object> paramMap = getSearchParams(request);
			UserFocusHospital userFocusHospital = userFocusHospitalService.findByUserId(loginUser.getId());
			if(userFocusHospital!=null){
				paramMap.put("EQ_hospital.id", userFocusHospital.getHospitalId());
			}
			PageInfo<OrderPz> pageInfo = orderPzService.query(getPageInfo(request), paramMap, getSortMap(request));
			result.setTotal(Long.valueOf(pageInfo.getTotalCount()));
			result.setRows(pageInfo.getPageResults());
		} catch (Exception e) {
			handleException(result, "分页查询", e);
		}
		return result;
	}
	
	@RequestMapping("confirmJson")
	@ResponseBody
	public JsonResult confirmJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult result = new JsonResult();
		try {
			Serializable[] ids = getRequestIds(request);			
			for(int i =0;i<ids.length;i++){
				OrderPz orderPz = orderPzService.get(ids[i]);
				if(orderPz!=null){
					if(orderPz.getState().equals("ORDERED")&&orderPz.getServicePersonId()!=null){
						orderPzService.confirmPzOrder(orderPz);
					}
				}				
			}
			result.setMessage("批量确认成功");
		} catch (Exception e) {
			handleException(result, "确认", e);
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
				OrderPz orderPz = orderPzService.get(ids[i]);
				if(orderPz!=null){
					if(orderPz.getState().equals("ORDERED")){
						orderPz.setState(OrderState.CANCELED.getCode());
						orderPzService.update(orderPz);
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
				OrderPz orderPz = orderPzService.get(ids[i]);
				if(orderPz!=null){
					if(orderPz.getState().equals("CONFIRMED")){
//						orderPzService.finishedPzOrder(orderPz);
					}
				}				
			}
			result.setMessage("批量完成成功");
		} catch (Exception e) {
			handleException(result, "完成", e);
		}
		return result;
	}
	
	@RequestMapping("goOrderPzConfirm")
	public String goOrderPzConfirm(Long id,Model model){
		OrderPz pz = orderPzService.get(id);
		convertEnum(pz);
		List<ServicePerson> servicePersons = servicePersonService.findServicePersonByHospitalIdAndServiceType(pz.getHospitalId(), "PZ");
		model.addAttribute("servicePersons",servicePersons);
		model.addAttribute("orderPz",pz);
		if(pz.getPayType()==4){
			model.addAttribute("Payee", pz.getPayeeName());
		}else{
			User loginUser= (User) SecurityUtils.getSubject().getPrincipal();
			model.addAttribute("Payee", loginUser.getRealName());
		}
		return "/manage/epei/orderPzConfirm";
	}

	@RequestMapping("orderPzConfirm")
	@ResponseBody
	public JsonResult orderPzConfirm(OrderPz orderPz){
		JsonResult result = new JsonResult();
		try{
			OrderPz order = orderPzService.get(orderPz.getId());
			if(order.getState().equals("ORDERED")){
				User loginUser= (User) SecurityUtils.getSubject().getPrincipal();
				orderPz.setPayeeId(loginUser.getId());
				orderPz.setPayeeName(loginUser.getRealName());
				orderPzService.confirmPzOrder(orderPz);
				result.setMessage("陪护单确认处理成功.");
			}else{
				throw new BusinessException("非预约状态下的订单无法确认");
			}	
		}
		catch (Exception e){
			handleException(result,"陪护单确认",e);
		}
		return  result;
	}
	
	@RequestMapping("goOrderPzFinished")
	public String goFinished(Long id,Model model){
		OrderPz order = orderPzService.get(id);
		convertEnum(order);
		model.addAttribute("orderPz",order);
		//陪诊服务项
		model.addAttribute("services",pzServiceService.getAll());
		//陪诊人员
		Map<String,Object> params = new HashMap<>();
		params.put("EQ_deleted",0);
		params.put("EQ_hospitalId",order.getHospital().getId());
		params.put("EQ_serviceType",ServiceTypeEnum.PZ.name());
		List<ServicePerson> servicePersons = servicePersonService.query(params, null);
		model.addAttribute("servicePersons",servicePersons);
		return "/manage/epei/orderPzFinished";
	}

	@RequestMapping("orderPzFinished")
	@ResponseBody
	public JsonResult orderPzFinished(OrderPz order, Long servicePersonId){
		JsonResult result  = new JsonResult();
		OrderPz o = orderPzService.get(order.getId());
		try{
			if(!o.getState().equals("CONFIRMED")){
				throw new BusinessException("没有确认的订单无法完成");
			}
			ServicePerson servicePerson = servicePersonService.get(servicePersonId);
			if(servicePerson!=null){
				servicePerson.setServiceState(0);
				servicePersonService.update(servicePerson);
			}
			orderPzService.finishedPzOrder(order);
			//记录会员某个时间在某个医院消费过
			try{
				CustomerFocusHospital customerFocusHospital = new CustomerFocusHospital();
				Customer c = customerService.findCustomerByUsername(o.getPatientMobile());
				if(c==null){
					c = customerService.getCustomerByCusNo(o.getCusNo());
				}
				customerFocusHospital.setCustomerId(c.getId());
				customerFocusHospital.setCustomerMobile(c.getMobile());
				customerFocusHospital.setHospitalId(o.getHospital().getId());
				customerFocusHospital.setHospitalName(o.getHospital().getName());
				customerFocusHospital.setDepartmentId(departmentService.findDepartmentByNameAndHospitalId(o.getDepartmentName(), o.getHospital().getId()).getId());
				customerFocusHospital.setDepartmentName(o.getDepartmentName());
				Boolean exist = false;
				for(CustomerFocusHospital cf:customerFocusHospitalService.queryListByCustomerId(customerFocusHospital.getCustomerId())){
					if(cf.getDepartmentId().equals(customerFocusHospital.getDepartmentId())){
						exist = true;
						break;
					}
				}
				if(!exist){
					customerFocusHospitalService.save(customerFocusHospital);
				}		
			}catch(Exception e){
				e.printStackTrace();
			}
		result.setMessage("陪诊单完成.");
		}
		catch (Exception e){
			handleException(result,"完成陪诊单",e);
		}
		return  result;
	}

	@RequestMapping("cancel")
	@ResponseBody
	public JsonResult cancel(Long id){
		JsonResult result  = new JsonResult();
		try{
			OrderPz pz = orderPzService.get(id);
			if(pz != null){
				orderBaseService.cancelOrder(pz.getOrderNo(),true);
				result.setMessage("陪诊单取消成功.");
			}
			else{
				result.setSuccess(false);
				result.setMessage("不存在的陪诊单,请试试刷新页面.");
			}

		}
		catch (Exception e){
			handleException(result,"陪诊单取消",e);
		}
		return  result;
	}

	private void convertEnum(OrderPz entity){
		entity.setOrign(OrderOriginalEnum.getMsgByCode(entity.getOrign()));
		entity.setState(OrderState.getMsgByCode(entity.getState()));
	}
	
	/*---------------------------------Excel导入开始---------------------------------------*/
	@Override
	public List<OrderPz> unmarshal(List<String[]> lines) {
		lines.remove(0);
		int row =2;
		List<OrderPz> entities = new LinkedList<OrderPz>();
		for (String[] line : lines) {
			if(StringUtils.isBlank(line[0]))break;
			//每次换行时new一个新的陪诊订单
			OrderPz op = new OrderPz();
			//生成订单号
			op.setOrderNo(CodeUtils.getOrderNo(OrderOriginalEnum.getByCode("PC")));
			//订单类型：PZ:陪诊
			//患者姓名					line[0]
			op.setPatientName(line[0].trim());
			if(Pattern.matches(regExp, line[3].trim())){
				op.setPatientMobile(line[3].trim());
			}else{
				throw new BusinessException("第"+row+"行，病患电话号码格式错误！");
			}
			
			//是否医保报销				line[4]
			if(line[4].equals("是")){
				op.setYbbx(true);
			}else if(line[4].equals("否")){
				op.setYbbx(false);
			}
			//挂号方式					line[5]
			if(line[5].contains("专家")){
				op.setRegistrationType("1");
			}else if(line[5].contains("普通")){
				op.setRegistrationType("0");
			}
			//就诊医院信息
			Hospital hospital = hospitalService.findHospitalByName(line[6].trim());
			if(hospital!=null){
				op.setHospitalId(hospital.getId());
				op.setHospitalName(hospital.getName());
			}else{
				throw new BusinessException("第"+row+"行信息有误，系统里不存在该医院");
			}
			//医院下有无该科室信息
			Department department;
			try{
				 department = departmentService.findDepartmentByNameAndHospitalId(line[7].trim(),hospital.getId());
				 op.setDepartmentName(department.getName());
				 op.setDepartmentId(department.getId());
			}catch(Exception e){
				throw new BusinessException("第"+row+"行信息有误，就诊医院下不存在该科室！");
			}
			if(Pattern.matches(regExp, line[9].trim())){
				op.setServicePersonMobile(line[9].trim());
			}else{
				throw new BusinessException("第"+row+"行信息有误，服务人员电话号码格式错误！");
			}
			
			try{
				long mobileLong = Long.parseLong(line[9].trim());
				ServicePerson sperson = servicePersonService.findServicePersonByNameAndMobileAndHospitalIdAndServiceType(line[8].trim(),mobileLong,hospital.getId(),"PZ");
				op.setServicePerson(sperson.getName());
				op.setServicePersonName(sperson.getName());
				op.setServicePersonId(sperson.getId());
			}catch(Exception e){
				throw new BusinessException("第"+row+"行信息有误，没有找到该陪诊人员");
			}
			op.setServiceGrade(line[10].trim());
			//服务方式信息
			ServicePrice sprice = servicePriceService.findServicePriceByHospitalIdAndServiceGradeAndServiceType(hospital.getId(),line[10].trim(),"PZ");
			if(sprice != null){
				op.setServicePriceId(sprice.getId());
			}
			//服务价格(元)				line[11]
			float price = Float.parseFloat(line[11].trim());
			op.setPrice(price);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			try {
				Date date = sdf.parse(line[12].trim());
				op.setOrderTime(date);
			} catch (ParseException e) {
				throw new BusinessException("第"+row+"行信息有误，预约时间格式有误");
			}
			//具体时间(上午、下午)		line[13]
			if(line[13].contains("上午")){
				op.setTimeInOneDay("AM");
			}else if(line[13].contains("下午")){
				op.setTimeInOneDay("PM");
			}
			//预约方式
			op.setAppointmentType(1);
			//检查完当前行最后一列没有问题后才进行病患信息的添加
			//根据病患姓名和手机号查询病患是否存在
			Patient patients = new Patient();
			Patient patient = patientService.findPatientByNameAndMobile(line[0].trim(),line[3].trim());
			//管理员信息
			User loginUser= (User) SecurityUtils.getSubject().getPrincipal();
			if(patient == null){
				patients.setCusName(loginUser.getRealName());
				patients.setCusNo(loginUser.getId());
				patients.setCusId(loginUser.getId());
				//病患信息
				patients.setName(line[0].trim());
				if(line[1].contains("男")){
					patients.setGender(1);
				}else if(line[1].contains("女")){
					patients.setGender(0);
				}
				patients.setBirthday(line[2].trim());
				patients.setMobile(line[3].trim());
				patientService.save(patients);
			}else{
				patients = patient;
			}
			op.setCusNo(patients.getCusNo());
			//录入的管理员姓名
			op.setAdminName(loginUser.getRealName());
			op.setPatient(patients);
			op.setOrign("PC");
			op.setHospital(hospital);
			op.setReceptionPosition(hospital.getReceptionPosition());
			entities.add(op);
			row++;
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
	public boolean doExportExcelBody(List<OrderPz> list, HttpServletRequest request, HttpServletResponse response) {
		WritableWorkbook workbook = null;
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			String[] headerNames = {
				"订单编号","病患姓名","病患性别","病患生日","病患电话","病患身份证","所属会员编号","所属会员姓名","是否医保报销","挂号方式","就诊医院","就诊科室",
				"服务名称","服务价格","预约日期","预约时间段","服务人员姓名","服务人员电话","订单状态","预约方式","付款方式","创建时间","修改时间"
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
			
			for (OrderPz op : list) {
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
				//是否医保报销
				if(op.getYbbx()){
					entityData.add("是");
				}else{
					entityData.add("否");
				}
				//挂号方式
				if(op.getRegistrationType() != null){
					if(op.getRegistrationType().equals("1")){
						entityData.add("专家");
					}else if(op.getRegistrationType().equals("0")){
						entityData.add("普通");
					}
				}else{
					entityData.add("普通");
				}
				//就诊医院
				entityData.add(op.getHospitalName());
				//就诊科室
				entityData.add(op.getDepartmentName());
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
					}else{
						entityData.add(null);
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
				}
				//预约方式
				if(String.valueOf(op.getAppointmentType()) != null){
					if(String.valueOf(op.getAppointmentType()).equals("0")){
						entityData.add("电话预约");
					}else if(String.valueOf(op.getAppointmentType()).equals("1")){
						entityData.add("主动预约");
					}else{
						entityData.add("主动预约");
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
					}else{
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
	@RequestMapping("downloadOrderPzExcelModel")
	public String downloadOrderPzExcelModel(HttpServletRequest request, HttpServletResponse response) {
		try {
			//XLS文件的一些属性
			doExportExcelHeader(request, response);
			//XLS文件内容
			WritableWorkbook workbook = null;
			OutputStream out = null;
			try {
				out = response.getOutputStream();
				String[] headerNames = {
					"病患姓名","性别","出生日期(格式19000101)","病患电话","是否医保报销","挂号方式(普通、专家)","就诊医院","就诊科室",
					"服务人员","服务人员电话","服务方式","服务价格(元)","预约时间(格式19980101)","具体时间(上午、下午)"
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
		return departmentService.getSamehosid(hosid);
	}

	@RequestMapping("contentModify")
	@ResponseBody
	public String contentModify(Long orderId, String content, int star){
		Comment comment = commentService.findByOrderId(orderId);
		User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
		OrderPz order = orderPzService.get(orderId);
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
				JSONObject item = json.getJSONObject("PZ");
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
		OrderPz orderPz =   orderPzService.get(id);
		User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
		if(orderPz.getAcceptance()==null || orderPz.getAcceptance()==1){
			orderPz.setAcceptance(2);
			orderPz.setAcceptTime(new Date());
			orderPz.setAcceptUserId(loginUser.getId());
			orderPz.setAcceptUserName(loginUser.getRealName());
			orderPzService.update(orderPz);
			return "YES";
		}else if(orderPz.getAcceptUserId()==loginUser.getId()){
			return "YES";
		}else{
			return "NO";
		}
	}
	
}
