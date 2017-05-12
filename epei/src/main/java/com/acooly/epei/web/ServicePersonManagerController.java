package com.acooly.epei.web;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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

import com.acooly.epei.domain.Hospital;
import com.acooly.epei.domain.ServiceTypeEnum;
import com.acooly.epei.domain.UserFocusHospital;
import com.acooly.epei.service.HospitalService;
import com.acooly.epei.service.UserFocusHospitalService;
import com.acooly.epei.web.vo.ServicePersonVo;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.utils.Strings;
import com.acooly.epei.domain.ServicePerson;
import com.acooly.epei.service.ServicePersonService;
import com.acooly.module.security.domain.User;

import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/manage/epei/servicePerson")
public class ServicePersonManagerController extends AbstractJQueryEntityController<ServicePerson, ServicePersonService> {


	@Autowired
	private ServicePersonService servicePersonService;

	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private UserFocusHospitalService userFocusHospitalService;

	@Override
	public JsonListResult<ServicePerson> listJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonListResult<ServicePerson> result = new JsonListResult<ServicePerson>();
		try {
			result.appendData(referenceData(request));
			User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
			Map<String, Object> paramMap = getSearchParams(request);
			UserFocusHospital userFocusHospital = userFocusHospitalService.findByUserId(loginUser.getId());
			if(userFocusHospital!=null){
				paramMap.put("EQ_hospitalId", userFocusHospital.getHospitalId());
			}
			PageInfo<ServicePerson> pageInfo = servicePersonService.query(getPageInfo(request), paramMap, getSortMap(request));
			result.setTotal(Long.valueOf(pageInfo.getTotalCount()));
			result.setRows(pageInfo.getPageResults());
		} catch (Exception e) {
			handleException(result, "分页查询", e);
		}
		return result;
	}
	
	@RequestMapping("pzPerson")
	public String pzPerson(HttpServletRequest request){
		request.setAttribute("serviceType", ServiceTypeEnum.PZ);
		return "forward:/manage/epei/servicePerson/index.html";
	}

	@RequestMapping("phPerson")
	public String phPerson(HttpServletRequest request){
		request.setAttribute("serviceType", ServiceTypeEnum.PH);
		return "forward:/manage/epei/servicePerson/index.html";
	} 

	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		Map<String,String[]> params = request.getParameterMap();
		for(Map.Entry<String,String[]> entry : params.entrySet()){
			model.put(entry.getKey(),entry.getValue()[0]);
		}

		Map<String,Object> paramsHos = new HashMap<>();
		paramsHos.put("EQ_deleted",0);
		//加载医院信息
		model.put("hospitals", hospitalService.query(paramsHos, null));
		
	}

	@Override
	protected void onCreate(HttpServletRequest request, HttpServletResponse response, Model model) {

		Map<String,Object> params = new HashMap<>();
		params.put("EQ_deleted",0);
		User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
		UserFocusHospital userFocusHospital = userFocusHospitalService.findByUserId(loginUser.getId());
		if(userFocusHospital!=null){
			params.put("EQ_id", userFocusHospital.getHospitalId());
		}
		//新增陪护陪诊人员 加载医院信息
		model.addAttribute("hospitals",hospitalService.query(params,null));
	}

	@Override
	protected void onEdit(HttpServletRequest request, HttpServletResponse response, Model model, ServicePerson entity) {
		Map<String,Object> params = new HashMap<>();
		User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
		UserFocusHospital userFocusHospital = userFocusHospitalService.findByUserId(loginUser.getId());
		if(userFocusHospital!=null){
			params.put("EQ_id", userFocusHospital.getHospitalId());
		}
		params.put("EQ_deleted",0);
		//新增陪护陪诊人员 加载医院信息
		model.addAttribute("hospitals",hospitalService.query(params,null));
	}

	@Override
	protected ServicePerson onSave(HttpServletRequest request, HttpServletResponse response, Model model, ServicePerson entity, boolean isCreate)
			throws Exception {
		Hospital hospital = hospitalService.get(entity.getHospitalId());
		if(null != hospital){
			entity.setHospital(hospital.getName());
		}

		return entity;
	}

	@RequestMapping("combobox")
	@ResponseBody
	public List<ServicePersonVo> combobox(Long hospitalId,String serviceType){
		Map<String,Object> params = new HashMap<>();
		params.put("EQ_deleted",0);
		params.put("EQ_hospitalId",hospitalId);
		params.put("EQ_serviceType",serviceType);

		List<ServicePerson> persons = servicePersonService.query(params,null);
		List<ServicePersonVo> vos = new ArrayList<>();

		for(ServicePerson person :  persons){
			vos.add(new ServicePersonVo(person));
		}

		return  vos;
	}

	/*******************************************Excel导出用到的一些方法********************************************/
	@Override
	public boolean doExportExcelBody(List<ServicePerson> list,
			HttpServletRequest request, HttpServletResponse response) {
		WritableWorkbook workbook = null;
		OutputStream out = null;
		try {
			WritableFont font1 = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);
	        WritableCellFormat cellFormat = new WritableCellFormat(font1);
			out = response.getOutputStream();
			String[] headerNames = {"姓名","手机号","身份证号","所属医院","服务类型","入职时间","员工编号"};
			workbook = Workbook.createWorkbook(out);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			int row = 0;

			if (headerNames != null) {
				for (int i = 0; i < headerNames.length; i++) {
					sheet.addCell(new Label(i, row, headerNames[i], cellFormat));
				}
				row++;
			}
			for (ServicePerson entity : list) {
				List<String> entityData = new ArrayList<String>();
				entityData.add(entity.getName());
				entityData.add(entity.getMobile()+"");
				entityData.add(entity.getIdCard());
				entityData.add(entity.getHospital());
				if(entity.getServiceType().equals("PZ")){
					entityData.add("陪诊");
				}else{
					entityData.add("陪护");
				}
				if(entity.getHireDate()!=null){
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					entityData.add(df.format(entity.getHireDate()));
				}else{
					entityData.add("");
				}
				entityData.add(entity.getEmpNo());
				for (int i = 0; i < entityData.size(); i++) {
					sheet.addCell(new Label(i, row, Strings
							.trimToEmpty((String) entityData.get(i))));
				}
				row++;
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
	
	/***************************************服务人员导入用到的excel模板下载***********************************************/
	@RequestMapping("downloadServicePersonExcelModel")
	public String downloadServicePersonExcelModel(HttpServletRequest request,
			HttpServletResponse response) {
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
				String[] headerNames = {"姓名","手机号","身份证号","所属医院名称","服务类型（陪诊/陪护）","入职时间（格式：19990101）","员工编号"};
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
			handleException("导出Excel", e, request);
		}
		return getSuccessView();
	}
	
	/******************************************Excel导入用到的一些方法*********************************************/	
	@Override
	protected List<ServicePerson> unmarshal(List<String[]> lines) {
		List<ServicePerson> entities = new LinkedList<ServicePerson>();
		for(int i =1; i<lines.size(); i++){
			ServicePerson s = doUnmarshalEntity(lines.get(i), i);
			if(s!=null)entities.add(s);
		}
		return entities;
	}

	protected ServicePerson doUnmarshalEntity(String[] line, int lineCount) {
		ServicePerson servicePerson = new ServicePerson();
		if(StringUtils.isNotBlank(line[0])){
			servicePerson.setName(line[0]);
		}else{
			throw new BusinessException("第"+lineCount+"行信息有误，服务人员姓名不能为空");
		}
		if(StringUtils.isNotBlank(line[1])){
			servicePerson.setMobile(Long.parseLong(line[1]));
		}else{
			throw new BusinessException("第"+lineCount+"行信息有误，服务人员电话不能为空");
		}
		if(StringUtils.isNotBlank(line[2])){
			servicePerson.setIdCard(line[2]);
		}else{
			throw new BusinessException("第"+lineCount+"行信息有误，服务人员身份证号不能为空");
		}
		try{
			servicePerson.setHospital(line[3]);
			servicePerson.setHospitalId(hospitalService.findHospitalByName(line[3]).getId());
		}catch(Exception e){
			throw new BusinessException("第"+lineCount+"行信息有误，所填写医院有误");
		}
		if(line[4].equals("陪诊")){
			servicePerson.setServiceType("PZ");
		}else if(line[4].equals("陪护")){
			servicePerson.setServiceType("PH");
		}else{
			throw new BusinessException("第"+lineCount+"行信息有误，请填写服务人员服务类型");
		}
		SimpleDateFormat sim=new SimpleDateFormat("yyyyMMdd");
		try {
			servicePerson.setHireDate(sim.parse(line[5]));
		} catch (ParseException e) {
			throw new BusinessException("第"+lineCount+"行信息有误，请输入正确的日期格式");
		}
		servicePerson.setEmpNo(line[6]);
		servicePerson.setDeleted(0);
		servicePerson.setServiceState(0);
		return servicePerson;
	}
	
}
