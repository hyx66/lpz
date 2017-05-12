package com.acooly.epei.web.front;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.epei.common.Constants;
import com.acooly.epei.domain.OrderBase;
import com.acooly.epei.domain.OrderPh;
import com.acooly.epei.domain.OrderPz;
import com.acooly.epei.domain.Patient;
import com.acooly.epei.domain.ServicePerson;
import com.acooly.epei.service.OrderBaseService;
import com.acooly.epei.service.OrderPhService;
import com.acooly.epei.service.OrderPzService;
import com.acooly.epei.service.PatientService;
import com.acooly.epei.service.ServicePersonService;
import com.acooly.epei.util.LoginUserUtils;
import com.acooly.module.sms.ShortMessageSendService;

/**
 * 修订记录： liubin 2015-11-07 13:00 创建
 */
@RequestMapping("servicedPerson")
@Controller
public class ServicedPersonController extends
		AbstractJQueryEntityController<Patient, PatientService> {

	private Logger logger = LoggerFactory
			.getLogger(ServicedPersonController.class);

	@Autowired
	private ServicePersonService servicePersonService;
	
	@Autowired
	private OrderBaseService orderBaseService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private ShortMessageSendService shortMessageSendService;
	
	@Autowired
	private OrderPhService orderPhService;
	
	@Autowired
	private OrderPzService orderPzService;

	@RequestMapping("personList")
	public String personList(Model model) {
		model.addAttribute("persons",
				patientService.findByCusNo(LoginUserUtils.getCusNo()));
		return "/front/mobile/personList";
	}

	@RequestMapping("personEdit")
	public String personEdit(Long id, Model model) {
		if (id != null) {
			Patient patient = patientService.get(id);
			if (patient != null && patient.getCusNo().equals(LoginUserUtils.getCusNo())) {
				model.addAttribute("person", patient);
			}
		}
		return "/front/mobile/personEdit";
	}

	@RequestMapping("personSave")
	@ResponseBody
	public JsonResult save(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult result = new JsonResult();
		try {
			Patient entity = loadEntity(request);
			if (entity == null) {
				entity = getEntityClass().newInstance();
			}
			doDataBinding(request, entity);
			entity.setCusId(LoginUserUtils.getCusId());
			entity.setCusNo(LoginUserUtils.getCusNo());
			entity.setMobile(entity.getMobile().trim());
			entity.setIdCard(entity.getIdCard().trim());
			entity.setMedicareCard(entity.getMedicareCard().trim());
			if (entity.getId() == null) {
				getEntityService().createPatient(entity);
			} else {
				getEntityService().updatePatient(entity);
			}
			result.setSuccess(true);
			result.setMessage("保存成功");
		} catch (Exception e) {
			logger.error("保存需服务人员出错,", e);
			result.setSuccess(false);
			result.setMessage("保存需服务人员失败.");
		}
		return result;
	}

	@RequestMapping("personDel")
	@ResponseBody
	public JsonResult personDel(Long id) {
		JsonResult result = new JsonResult();
		try {
			Patient patient = patientService.get(id);
			if (patient != null
					&& patient.getCusNo().equals(LoginUserUtils.getCusNo())) {
				patient.setDeleted(1);
				patientService.update(patient);
				result.setMessage("成功删除");
				result.setSuccess(true);
			} else {
				result.setMessage("非法操作.");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error("删除需服务人员出错,", e);
			result.setMessage("出错了,请稍后再试.");
			result.setSuccess(true);
		}
		return result;
	}

	@Override
	protected Patient doSave(HttpServletRequest request,
			HttpServletResponse response, Model model, boolean isCreate)
			throws Exception {
		Patient entity = loadEntity(request);
		if (entity == null) {
			entity = getEntityClass().newInstance();
		}
		doDataBinding(request, entity);
		onSave(request, response, model, entity, isCreate);
		if (isCreate) {
			getEntityService().createPatient(entity);
		} else {
			getEntityService().update(entity);
		}
		return entity;
	}

	/**
	 * 医院管理员分配任务
	 * @param 
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="assignTask")
	public String assignTask(RedirectAttributes redirectAttributes, ServicePerson sp, OrderBase ob, HttpServletRequest request,Model model) throws ParseException {
		//修改陪诊陪护人员服务状态	0:未服务	1:服务中
		ServicePerson servicePerson = servicePersonService.get(sp.getId());
		OrderBase orderBase = orderBaseService.getByOrderNo(ob.getOrderNo());
		String orderType = orderBaseService.getOrderType(orderBase.getId());
		if(!orderBase.getState().equals("ORDERED")||orderBase.getPayStatus()!=1){
			model.addAttribute("person", servicePerson);
			List<ServicePerson> spList = servicePersonService.getAllPersonAndServiceType(orderType, orderBase.getHospital().getId());
			model.addAttribute("servicePerson", spList);
			model.addAttribute("order", orderBase);
			model.addAttribute("orderType", orderType);
			model.addAttribute("message", "只有预约状态下且已付款的订单才能确认！");
			return "/front/mobile/adminOrderDetail";
		}
		if(orderType.equals("PH")){
			if(servicePerson.getServiceState()==0){
				servicePerson.setServiceState(1);
			}else{
				model.addAttribute("person", servicePerson);
				List<ServicePerson> spList = servicePersonService.getAllPersonAndServiceType("PH", orderBase.getHospital().getId());
				model.addAttribute("servicePerson", spList);
				model.addAttribute("order", orderBase);
				model.addAttribute("orderType", "PH");
				model.addAttribute("message", "该服务人员正忙，请跟换服务人员！");
				return "/front/mobile/adminOrderDetail";
			}		
		}
		
		//订单由谁负责服务
		orderBase.setServicePersonId(servicePerson.getId());
		orderBase.setServicePersonMobile(servicePerson.getMobile().toString());
		orderBase.setServicePersonName(servicePerson.getName());
		orderBase.setState("CONFIRMED");//	订单状态CONFIRMED--确认
		//尊敬的工作人员${spName},您接到一个新的任务。你需要服务的病患姓名:${pName}、电话:${pMobile}、生日:${pBirthday}、
		//预约时间:${pOrderTime}、接待地点:${receptionPosition}。请及时联系，谢谢。
		Map<String,Object> params_assignTask = new HashMap<>();
		params_assignTask.put("spName", servicePerson.getName());
		params_assignTask.put("pName", orderBase.getPatientName());
		params_assignTask.put("pMobile", orderBase.getPatientMobile());
		params_assignTask.put("pBirthday", orderBase.getPatient().getBirthday());
		params_assignTask.put("pOrderTime", DateFormatUtils.format(orderBase.getOrderTime(),"yyyy-MM-dd"));
		params_assignTask.put("receptionPosition", orderBase.getHospital().getReceptionPosition());
		params_assignTask.put("departmentName", orderBase.getDepartmentName());
		params_assignTask.put("bed", orderBase.getBed());
		params_assignTask.put("spName", servicePerson.getName());
		params_assignTask.put("spMobile", String.valueOf(servicePerson.getMobile()));	
		String sex = orderBase.getPatient().getGender()==0?"女":"男";
		params_assignTask.put("gender",sex );
		//尊敬的客户${pName}，您预约的服务已受理，您的服务人姓名:${spName}、电话:${spMobile}，请及时联系。详细信息请咨询服务电话:4008837622。
		Map<String,Object> params_remind = new HashMap<>();
		params_remind.put("pName", orderBase.getPatientName());
		params_remind.put("spName", servicePerson.getName());
		params_remind.put("spMobile", String.valueOf(servicePerson.getMobile()));
		params_remind.put("departmentName", orderBase.getDepartmentName());
		params_remind.put("pMobile", orderBase.getPatientMobile());
		params_remind.put("pBirthday", orderBase.getPatient().getBirthday());
		servicePersonService.update(servicePerson);
		orderBaseService.update(orderBase);
		
		if(orderType.equals("PZ")){
			OrderPz orderPz = orderPzService.get(Long.parseLong(request.getParameter("obId")));
			orderPz.setServicePersonId(servicePerson.getId());
			orderPz.setServicePerson(servicePerson.getName());
			//给患者发短信
			params_remind.put("orderTime", DateFormatUtils.format(orderBase.getOrderTime(),"yyyy-MM-dd HH:mm"));
			shortMessageSendService.sendByTemplateAsync(orderBase.getPatientMobile(), Constants.PZ_ORDERED_SMS_TEMPLATE, params_remind);
			//给陪诊人员发短信
			shortMessageSendService.sendByTemplateAsync(Long.toString(servicePerson.getMobile()), Constants.PZ_ASSIGN_TASK_SMS_TEMPLATE, params_assignTask);
		} else if(orderType.equals("PH")){
			//陪护订单相关信息	工作人员信息
			OrderPh orderPh = orderPhService.get(Long.parseLong(request.getParameter("obId")));
			orderPh.setServicePersonId(servicePerson.getId());
			orderPh.setServicePerson(servicePerson.getName());
			SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
			orderPh.setStartTime(dateFormater.parse(dateFormater.format(orderBase.getOrderTime())));
			orderPh.setEndTime(orderBaseService.calculateDate(orderPh.getStartTime(), orderPh.getDuration()));
			orderPhService.update(orderPh);
			params_remind.put("orderTime", DateFormatUtils.format(orderBase.getOrderTime(),"yyyy-MM-dd"));
			shortMessageSendService.sendByTemplateAsync(orderBase.getPatientMobile(), Constants.PH_ORDERED_SMS_TEMPLATE, params_remind);
		}
		redirectAttributes.addFlashAttribute("message", "订单确认完成！");
		return "redirect:/order/adminOrderDetail.html?orderNo="+orderBase.getOrderNo()+"&orderType="+orderType;
	}

}
