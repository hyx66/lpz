package com.acooly.epei.web;

import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Strings;
import com.acooly.epei.dao.ServicePriceDao;
import com.acooly.epei.domain.Customer;
import com.acooly.epei.domain.CustomerFocusHospital;
import com.acooly.epei.domain.Department;
import com.acooly.epei.domain.Hospital;
import com.acooly.epei.domain.OfflineSalesRecords;
import com.acooly.epei.domain.OrderOriginalEnum;
import com.acooly.epei.domain.ServicePerson;
import com.acooly.epei.domain.ServicePrice;
import com.acooly.epei.domain.UserFocusHospital;
import com.acooly.epei.service.CustomerFocusHospitalService;
import com.acooly.epei.service.CustomerService;
import com.acooly.epei.service.DepartmentService;
import com.acooly.epei.service.HospitalService;
import com.acooly.epei.service.OfflineSalesRecordsService;
import com.acooly.epei.service.PatientService;
import com.acooly.epei.service.ServicePersonService;
import com.acooly.epei.service.ServicePriceService;
import com.acooly.epei.service.UserFocusHospitalService;
import com.acooly.epei.util.CodeUtils;
import com.acooly.epei.web.vo.HospitalVo;
import com.acooly.module.security.domain.User;

@Controller
@RequestMapping(value = "/manage/epei/offlineSalesRecords")
public class OfflineSalesRecordsManagerController extends AbstractJQueryEntityController<OfflineSalesRecords, OfflineSalesRecordsService> {

	private Logger logger = LoggerFactory.getLogger(OfflineSalesRecords.class);
	
	public static final String regExp = "[0-9]{0,11}";
	
	@Autowired
	private OfflineSalesRecordsService offlineSalesRecordsService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private ServicePersonService servicePersonService;
	
	@Autowired
	private ServicePriceService servicePriceService;
	
	@Autowired
	private UserFocusHospitalService userFocusHospitalService;
	@Autowired
	private CustomerFocusHospitalService customerFocusHospitalService;

	@Override
	public String create(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			model.addAllAttributes(referenceData(request));
			onCreate(request, response, model);
			model.addAttribute("action", "create");
			User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
			UserFocusHospital userFocusHospital = userFocusHospitalService.findByUserId(loginUser.getId());
			model.addAttribute("userFocusHospital", userFocusHospital);
		} catch (Exception e) {
			logger.warn(getExceptionMessage("create", e), e);
			handleException("新建", e, request);
		}
		return getEditView();
	}
	
	@Override
	public String edit(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
			UserFocusHospital userFocusHospital = userFocusHospitalService.findByUserId(loginUser.getId());
			model.addAttribute("userFocusHospital", userFocusHospital);
			model.addAllAttributes(referenceData(request));
			OfflineSalesRecords entity = loadEntity(request);
			model.addAttribute("action", "edit");
			model.addAttribute(getEntityName(), entity);
			onEdit(request, response, model, entity);
		} catch (Exception e) {
			logger.warn(getExceptionMessage("edit", e), e);
			handleException("编辑", e, request);
		}
		return getEditView();
	}
	
	@Override
	public JsonListResult<OfflineSalesRecords> listJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonListResult<OfflineSalesRecords> result = new JsonListResult<OfflineSalesRecords>();
		try {
			result.appendData(referenceData(request));
			User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
			Map<String, Object> paramMap = getSearchParams(request);
			UserFocusHospital userFocusHospital = userFocusHospitalService.findByUserId(loginUser.getId());
			if(userFocusHospital!=null){
				paramMap.put("EQ_hospitalId", userFocusHospital.getHospitalId());
			}
			PageInfo<OfflineSalesRecords> pageInfo = offlineSalesRecordsService.query(getPageInfo(request), paramMap, getSortMap(request));
			result.setTotal(Long.valueOf(pageInfo.getTotalCount()));
			result.setRows(pageInfo.getPageResults());
			
		} catch (Exception e) {
			handleException(result, "分页查询", e);
		}
		return result;
	}
	
	@Override
	public JsonResult deleteJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult result = new JsonResult();
		try {
			Serializable[] ids = getRequestIds(request);			
			for(int i =0;i<ids.length;i++){
				OfflineSalesRecords offlineSalesRecords = offlineSalesRecordsService.get(ids[i]);
				if(offlineSalesRecords!=null){
					if(offlineSalesRecords.getStatus()==2){
						offlineSalesRecordsService.remove(offlineSalesRecords);
					}
				}				
			}
			result.setMessage("删除成功");
		} catch (Exception e) {
			handleException(result, "删除", e);
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
				OfflineSalesRecords offlineSalesRecords = offlineSalesRecordsService.get(ids[i]);
				if(offlineSalesRecords!=null){
					if(offlineSalesRecords.getStatus()==0){
						offlineSalesRecords.setStatus(2);
						offlineSalesRecordsService.update(offlineSalesRecords);
					}
				}				
			}
			result.setMessage("批量无效成功");
		} catch (Exception e) {
			handleException(result, "无效", e);
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
				OfflineSalesRecords offlineSalesRecords = offlineSalesRecordsService.get(ids[i]);
				if(offlineSalesRecords!=null){
					if(offlineSalesRecords.getStatus()==0){
						offlineSalesRecords.setStatus(1);
						offlineSalesRecordsService.update(offlineSalesRecords);
					}
				}				
			}
			result.setMessage("批量确认成功");
		} catch (Exception e) {
			handleException(result, "确认", e);
		}
		return result;
	}
	
	@RequestMapping("reuseJson")
	@ResponseBody
	public JsonResult reuseJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult result = new JsonResult();
		try {
			Serializable[] ids = getRequestIds(request);			
			for(int i =0;i<ids.length;i++){
				OfflineSalesRecords offlineSalesRecords = offlineSalesRecordsService.get(ids[i]);
				if(offlineSalesRecords!=null){
					if(offlineSalesRecords.getStatus()==2){
						offlineSalesRecords.setStatus(0);
						offlineSalesRecordsService.update(offlineSalesRecords);
					}
				}				
			}
			result.setMessage("批量重启用成功");
		} catch (Exception e) {
			handleException(result, "重启用", e);
		}
		return result;
	}
	
	@Override
	public JsonEntityResult<OfflineSalesRecords> saveJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonEntityResult<OfflineSalesRecords> result = new JsonEntityResult<OfflineSalesRecords>();
		try {
			String message = "新增成功";
			Customer customer = customerService.findCustomerByUsername(request.getParameter("customerMobile"));
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
	
	public JsonEntityResult<OfflineSalesRecords> updateJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonEntityResult<OfflineSalesRecords> result = new JsonEntityResult<OfflineSalesRecords>();
		try {
			String message = "更新成功";
			Customer customer = customerService.findCustomerByUsername(request.getParameter("customerMobile"));
			if(customer!=null){
				message = "更新成功，该用户是平台会员";
			}
			result.setEntity(doSave(request, response, null, false));
			result.setMessage(message);
		} catch (Exception e) {
			handleException(result, "更新", e);
		}
		return result;
	}
	
	@Override
	protected OfflineSalesRecords onSave(HttpServletRequest request, HttpServletResponse response, Model model, OfflineSalesRecords entity, boolean isCreate) throws Exception {
		int serviceDays = offlineSalesRecordsService.calculateDays(entity.getServiceYmd(),entity.getServiceEndYmd())+1;
		if(serviceDays<=0){
			throw new BusinessException("服务结束时间不能早于服务开始时间！");
		}else{
			entity.setServiceDays(serviceDays);
		}
		
		entity.setHospitalId(entity.getHospitalId());
		entity.setDepartName(departmentService.get(entity.getDepartId()).getName());
		//根据客户姓名和手机号码，查询会员是否存在，如果会员存在就把会员ID加进去
		Customer customer = customerService.findCustomerByUsername(entity.getCustomerMobile());
		if(customer!=null){
			entity.setCustomerId(customer.getId());
		}
		Hospital hospital = hospitalService.get(entity.getHospitalId());
		entity.setHospital(hospital.getName());
		entity.setSources("1");
		if(StringUtils.isNotBlank(entity.getServicePersonMobile())){
			ServicePerson servicePerson = servicePersonService.findServicePersonByMobile(entity.getServicePersonMobile());
			if(servicePerson!=null){
				entity.setServicePersonId(servicePerson.getId());
			}
		}
		User loginUser= (User) SecurityUtils.getSubject().getPrincipal();
		// 这里服务层默认是根据entity的Id是否为空自动判断是SAVE还是UPDATE.
		if(entity.getId() == null){
			//初始状态0：初始	1：有效	2：无效
			entity.setStatus(0);
			entity.setCreateId(loginUser.getId());
			entity.setCreateName(loginUser.getRealName());
			//流水号
			String recordsNo = recordsNoString(entity);
			entity.setRecordsNo(recordsNo);
		}else{
			entity.setUpdateId(loginUser.getId());
			entity.setUpdateName(loginUser.getRealName());
		}
		if(entity.getPayType()==0){
			entity.setTicketNo(null);
		}
		return entity;
	}
	
	/**
	 * 流水号：当前日期+数据（订单）类型+支付类型+序列号
	 */
	public String recordsNoString(OfflineSalesRecords entity){
		//根据当前日期查询 线下消费流水表 当前日期的最大流水号 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
		String time = sdf.format(new Date());
		String time2 = sdf2.format(new Date());
		String osrRecordsNo = offlineSalesRecordsService.getOfflineSalesRecordsByCreateTime(time2);
		String recordsNo = "";
		if(osrRecordsNo == null){
			recordsNo = time + entity.getDataType() + entity.getPayType() + "10000000001" ;
		}else{
			String str = osrRecordsNo.substring(10);
			Long i = Long.parseLong(str) + 1;
			recordsNo = time + entity.getDataType() + entity.getPayType() + i + "";
		}
		return recordsNo;
	}
	
	@Override
	protected void onEdit(HttpServletRequest request, HttpServletResponse respones, Model model, OfflineSalesRecords offlineSalesRecords){
		if(offlineSalesRecords.getDataType()==0){
			model.addAttribute("hospitalIdpz", offlineSalesRecords.getHospitalId());
		}else if(offlineSalesRecords.getDataType()==1){
			model.addAttribute("hospitalIdph", offlineSalesRecords.getHospitalId());
		}
		model.addAttribute("serviceYmd", offlineSalesRecords.getServiceYmd());
		model.addAttribute("serviceEndYmd", offlineSalesRecords.getServiceEndYmd());
	}
	
	@RequestMapping("goOfflineSalesRecordsConfirm")
	public String goOfflineSalesRecordsConfirm(Long id, Model model){
		OfflineSalesRecords osr = offlineSalesRecordsService.get(id);
		model.addAttribute("offlineSalesRecords", osr);
		return "/manage/epei/offlineSalesRecordsConfirm";
	}
	
	@RequestMapping("offlineSalesRecordsConfirm")
	@ResponseBody
	public JsonResult offlineSalesRecordsConfirm(OfflineSalesRecords osr){
		JsonResult result = new JsonResult();
		try {
			offlineSalesRecordsService.offlineSalesRecordsConfirm(osr);
			try{
				OfflineSalesRecords off = offlineSalesRecordsService.get(osr.getId());
				CustomerFocusHospital customerFocusHospital = new CustomerFocusHospital();
				Customer c = customerService.findCustomerByUsername(off.getCustomerMobile());
				customerFocusHospital.setCustomerId(c.getId());
				customerFocusHospital.setCustomerMobile(c.getMobile());
				customerFocusHospital.setHospitalId(off.getHospitalId());
				customerFocusHospital.setHospitalName(off.getHospital());
				customerFocusHospital.setDepartmentId(off.getDepartId());
				customerFocusHospital.setDepartmentName(off.getDepartName());
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
				
			}
			
			result.setMessage("线下交易记录确认处理成功.");
		} catch (Exception e) {
			handleException(result,"线下交易记录确认出错",e);
		}
		return result;
	}
	
	@RequestMapping("cancelOfflineSalesRecords")
	@ResponseBody
	public JsonResult cancelOfflineSalesRecords(Long id){
		JsonResult result  = new JsonResult();
		try{
			offlineSalesRecordsService.cancelOfflineSalesRecords(id);
			result.setMessage("交易记录状态修改成功");
		}
		catch (Exception e ){
			logger.error("交易记录状态发生异常,",e);
			result.setSuccess(false);
			result.setMessage("系统出错啦,请稍后再试.");
		}
		return result;
	}
	
	/*---------------------------------Excel导入开始---------------------------------------*/
	@Override
	public List<OfflineSalesRecords> unmarshal(List<String[]> lines) {
		lines.remove(0);
		List<OfflineSalesRecords> entities = new LinkedList<OfflineSalesRecords>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int row = 2;
		for (String[] line : lines) {
			//根据第一列是否有内容进行导入操作
			if(StringUtils.isBlank(line[0]))break;
			//每次换行时new一个新的线下消费流水表
			OfflineSalesRecords osr = new OfflineSalesRecords();
			//订单类型：陪诊、陪护			line[0]
			String serviceType = line[0].contains("陪诊")?"PZ":"PH";
			osr.setDataType(serviceType.equals("PZ")?0:1);
			//所属医院信息
			Hospital hospital;
			try{
				hospital = hospitalService.findHospitalByName(line[1].trim());
				//医院ID
				osr.setHospitalId(hospital.getId());
				//所属医院
				osr.setHospital(hospital.getName());
			}catch(Exception e){
				throw new BusinessException("第"+row+"行信息有误，系统中不存在该医院");
			}
			//科室名称
			osr.setDepartName(line[2].trim());
			Department department;
			try{
				department = departmentService.findDepartmentByNameAndHospitalId(line[2].trim(),hospital.getId());
				osr.setDepartId(department.getId());
			}catch(Exception e){
				throw new BusinessException("第"+row+"行信息有误，所选医院下找不到此科室");
			}
			//患者姓名	
			osr.setCustomerName(line[3].trim());
			if(line[4].contains("女")){
				osr.setCustomerSex("0");
			}else{
				osr.setCustomerSex("1");
			}
			if(StringUtils.isNotBlank(line[5])){
				osr.setCustomerAge(Integer.parseInt(line[5].trim()));
			}
			//客户手机号
			if(Pattern.matches(regExp, line[6].trim())){
				osr.setCustomerMobile(line[6].trim());	
			}else{
				throw new BusinessException("第"+row+"行信息有误，系统无法识别该患者电话");
			}
			//根据客户姓名和手机号码，查询会员是否存在，如果会员存在就把会员ID加进去
			try{
				osr.setCustomerId(customerService.findCustomerByNameAndMobile(line[3].trim(),line[6].trim()).getId());
			}catch(Exception e){
				e.printStackTrace();
			}
			osr.setDisease(line[7]);
			//外部单号
			osr.setOutNo(line[8].trim());
			osr.setCustomerBed(line[9].trim());
			osr.setServicePersonName(line[10].trim());
			if(Pattern.matches(regExp, line[11].trim())){
				osr.setServicePersonMobile(line[11].trim());
			}else{
				throw new BusinessException("第"+row+"行信息有误，系统无法识别该服务人员电话");
			}
			try{
				osr.setServicePersonId(servicePersonService.findServicePersonByMobile(osr.getServicePersonMobile()).getId());
			}catch(Exception e){
				e.printStackTrace();
			}
			//服务开始时间
			Date date1 = null;
			try {
				date1 = sdf.parse(line[12].trim());
			} catch (ParseException e) {
				throw new BusinessException("第"+row+"行信息有误，请规范服务开始时间的日期格式");
			}
			osr.setServiceYmd(date1);
			Date date2 = null;
			try {
				date2 = sdf.parse(line[13].trim());
			} catch (ParseException e) {
				throw new BusinessException("第"+row+"行信息有误，请规范服务结束时间的日期格式");
			}
			osr.setServiceEndYmd(date2);
			int serviceDays = offlineSalesRecordsService.calculateDays(date1, date2)+1;
			if(serviceDays<=0){
				throw new BusinessException("第"+row+"行信息有误，服务结束时间不能早于服务开始时间");
			}else{
				osr.setServiceDays(serviceDays);
			}
			
			//费用(元)
			try{
				BigDecimal amount = new BigDecimal(line[14].trim());
				osr.setAmount(amount);
			}catch(Exception e){
				throw new BusinessException("第"+row+"行信息有误，系统无法识别所填写的费用");
			}	
			//收款人
			if(StringUtils.isNotBlank(line[15])){
				osr.setPayeeName(line[15].trim());
			}else{
				throw new BusinessException("第"+row+"行信息有误，请填写收款人姓名");
			}
			osr.setPayType(line[16].contains("现金")?0:(line[12].contains("支票")?2:1));
			//小/支票号
			osr.setTicketNo(line[17].trim());
			//备注
			osr.setMemo(line[18]);
			//流水号
			String recordsNo = CodeUtils.getOrderNo(OrderOriginalEnum.getByCode("PC"));
			osr.setRecordsNo(recordsNo);
			//管理员信息
			User loginUser= (User) SecurityUtils.getSubject().getPrincipal();
			//录入的管理员ID
			osr.setCreateId(loginUser.getId());
			//录入的管理员姓名
			osr.setCreateName(loginUser.getRealName());
			osr.setSources("1");
			entities.add(osr);
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
	public boolean doExportExcelBody(List<OfflineSalesRecords> list, HttpServletRequest request, HttpServletResponse response) {
		WritableWorkbook workbook = null;
		OutputStream out = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			//设置第一行栏目名称的字体
			WritableFont font1 = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);
	        WritableCellFormat cellFormat = new WritableCellFormat(font1);
			out = response.getOutputStream();
			String[] headerNames = {
					"流水号","数据类型","客户姓名","客户手机号","所属医院","科室名称","服务开始时间",
					"服务人员姓名","服务人员手机号","服务结束时间","金额(元)","账期","收款人","小/支票号",
					"支付类型","数据状态","外部单号","录入人员","修改人员","备注","创建时间","修改时间","性别","年龄","床号","病症"
			};
			workbook = Workbook.createWorkbook(out);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			int row = 0;
			// 写入header
			if (headerNames != null) {
				for (int i = 0; i < headerNames.length; i++) {
					sheet.addCell(new Label(i, row, headerNames[i], cellFormat));
				}
				row++;
			}
			
			for (OfflineSalesRecords osr : list) {
				List<String> entityData = new ArrayList<String>();
				//流水号
				entityData.add(osr.getRecordsNo());
				//数据类型(陪诊、陪护)
				if(osr.getDataType() == 0){
					entityData.add("陪诊");
				}else if(osr.getDataType() == 1){
					entityData.add("陪护");
				}
				//客户姓名
				entityData.add(osr.getCustomerName());
				//客户手机号
				entityData.add(osr.getCustomerMobile());
				//所属医院
				entityData.add(osr.getHospital());
				//科室名称
				entityData.add(osr.getDepartName());
				//服务开始时间
				if(osr.getServiceYmd()!=null){
					entityData.add(sdf.format(osr.getServiceYmd()));
				}else{
					entityData.add(null);	
				}
				//服务人员姓名
				entityData.add(osr.getServicePersonName());
				//服务人员手机号
				entityData.add(osr.getServicePersonMobile());
				//服务结束时间
				if(osr.getServiceEndYmd()!=null){
					entityData.add(sdf.format(osr.getServiceEndYmd()));
				}else{
					entityData.add(null);
				}
				//金额(元)
				entityData.add(String.valueOf(osr.getAmount()));
				//账期
				entityData.add(osr.getDataYmd());
				//收款人
				entityData.add(osr.getPayeeName());
				//小/支票号
				entityData.add(osr.getTicketNo());
				//支付类型
				if(osr.getPayType() == 0){
					entityData.add("现金");
				}else if(osr.getPayType() == 1){
					entityData.add("POS机");
				}else if(osr.getPayType() == 2){
					entityData.add("支票");
				}
				//数据状态
				if(osr.getStatus() == 0){
					entityData.add("初始");
				}else if(osr.getStatus() == 1){
					entityData.add("有效");
				}else if(osr.getStatus() == 2){
					entityData.add("无效");
				}
				//外部单号
				entityData.add(osr.getOutNo());
				//录入人员
				entityData.add(osr.getCreateName());
				//修改人员
				entityData.add(osr.getUpdateName());
				//备注
				entityData.add(osr.getMemo());
				//创建时间
				entityData.add(String.valueOf(osr.getCreateTime()));
				//修改时间
				entityData.add(String.valueOf(osr.getUpdateTime()));
				if(osr.getCustomerSex()!=null){
					if(osr.getCustomerSex().equals("1")){
						entityData.add("男");
					}else{
						entityData.add("女");
					}
				}else{
					entityData.add(null);
				}
				entityData.add(osr.getCustomerAge()+"");
				entityData.add(osr.getCustomerBed());
				entityData.add(osr.getDisease());
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
	@RequestMapping("downloadOfflineSalesRecordsExcelModel")
	public String downloadOfflineSalesRecordsExcelModel(HttpServletRequest request, HttpServletResponse response) {
		try {
			//XLS文件的一些属性
			doExportExcelHeader(request, response);
			//XLS文件内容
			WritableWorkbook workbook = null;
			OutputStream out = null;
			try {
				//设置第一行栏目名称的字体
				WritableFont font1 = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);
		        WritableCellFormat cellFormat = new WritableCellFormat(font1);
				out = response.getOutputStream();
				String[] headerNames = {
					"数据类型(陪诊、陪护)","医院名称","科室名称","患者姓名","患者性别","患者年龄","患者电话","病症","外部单号","床号","服务人员姓名","服务人员电话","服务开始时间(19990101)","服务结束时间(19990101)","费用(元)","收款人","支付类型(现金、POS机、支票)","小/支票号","备注"
				};
				workbook = Workbook.createWorkbook(out);
				WritableSheet sheet = workbook.createSheet("Sheet1", 0);
				int row = 0;
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
		List<Hospital> hospitals = hospitalService.getAllNoDelByType(serviceType);
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
	
	
	@RequestMapping("comboboxServicePerson")
	@ResponseBody
	public List<ServicePerson> comboboxServicePerson(String type, Long hospitalId){
		return servicePersonService.getAllPersonAndServiceType(type, hospitalId);
	}
}
