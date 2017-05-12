package com.acooly.epei.web;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
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

import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Strings;
import com.acooly.epei.domain.Department;
import com.acooly.epei.domain.Hospital;
import com.acooly.epei.domain.OfflineSalesAcctSta;
import com.acooly.epei.domain.UserFocusHospital;
import com.acooly.epei.service.DepartmentService;
import com.acooly.epei.service.HospitalService;
import com.acooly.epei.service.OfflineSalesAcctStaService;
import com.acooly.epei.service.UserFocusHospitalService;
import com.acooly.epei.web.vo.HospitalVo;
import com.acooly.module.security.domain.User;

@Controller
@RequestMapping(value = "/manage/epei/offlineSalesAcctSta")
public class OfflineSalesAcctStaManagerController extends AbstractJQueryEntityController<OfflineSalesAcctSta, OfflineSalesAcctStaService> {

	private Logger logger = LoggerFactory.getLogger(OfflineSalesAcctSta.class);
	
	private static final String numberExp = "^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$";
	
	@Autowired
	private OfflineSalesAcctStaService offlineSalesAcctStaService;
	@Autowired
	HospitalService hospitalService;
	@Autowired
	UserFocusHospitalService userFocusHospitalService;
	@Autowired
	DepartmentService departmentService;
	
	@Override
	public String index(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
			UserFocusHospital userFocusHospital = userFocusHospitalService.findByUserId(loginUser.getId());
			model.addAttribute("userFocusHospital", userFocusHospital);
			model.addAllAttributes(referenceData(request));
		} catch (Exception e) {
			logger.warn(getExceptionMessage("index", e), e);
			handleException("主界面", e, request);
		}
		return getListView();
	}
	
	@Override
	public String create(HttpServletRequest request,
			HttpServletResponse response, Model model) {
		try {
			User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
			UserFocusHospital userFocusHospital = userFocusHospitalService.findByUserId(loginUser.getId());
			model.addAttribute("userFocusHospital", userFocusHospital);
			model.addAllAttributes(referenceData(request));
			onCreate(request, response, model);
			model.addAttribute("action", "create");
		} catch (Exception e) {
			logger.warn(getExceptionMessage("create", e), e);
			handleException("新建", e, request);
		}
		return getEditView();
	}
	
	@Override
	public JsonListResult<OfflineSalesAcctSta> listJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonListResult<OfflineSalesAcctSta> result = new JsonListResult<OfflineSalesAcctSta>();
		try {
			result.appendData(referenceData(request));
			User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
			Map<String, Object> paramMap = getSearchParams(request);
			UserFocusHospital userFocusHospital = userFocusHospitalService.findByUserId(loginUser.getId());
			if(userFocusHospital!=null){
				paramMap.put("EQ_hospitalId", userFocusHospital.getHospitalId());
			}		
			PageInfo<OfflineSalesAcctSta> pageInfo = offlineSalesAcctStaService.query(getPageInfo(request), paramMap, getSortMap(request));
			result.setTotal(Long.valueOf(pageInfo.getTotalCount()));
			result.setRows(pageInfo.getPageResults());
			
		} catch (Exception e) {
			handleException(result, "分页查询", e);
		}
		return result;
	}
	
	@Override
	protected OfflineSalesAcctSta onSave(HttpServletRequest request, HttpServletResponse response, Model model, OfflineSalesAcctSta entity, boolean isCreate) throws Exception {
		if(entity.getId() == null){
			//录入
			User loginUser = (User) SecurityUtils.getSubject().getPrincipal();
			entity.setCreateId(loginUser.getId());
			entity.setCreateName(loginUser.getRealName());
			entity.setCountScope(Integer.parseInt(request.getParameter("countScope")));
			entity.setDataCount(Integer.parseInt(request.getParameter("dataCount")));
			if(entity.getCountScope()==1){
				entity.setOrderType(3);
			}else if(entity.getCountScope()==2){
				Long hospitalId = Long.parseLong(request.getParameter("hospitalId"));
				entity.setHospitalId(hospitalId);
				entity.setHospitalName(hospitalService.get(hospitalId).getName());
				entity.setOrderType(3);
			}else{
				Long hospitalId = Long.parseLong(request.getParameter("hospitalId"));
				entity.setHospitalId(hospitalId);
				entity.setHospitalName(hospitalService.get(hospitalId).getName());
				Long departmentId = Long.parseLong(request.getParameter("departmentId"));
				entity.setDepartmentId(departmentId);
				entity.setDepartmentName(departmentService.get(departmentId).getName());
				entity.setOrderType(Integer.parseInt(request.getParameter("orderType")));
			}

			//状态-"0":"初始","1":"未对账","2":"已对账"
			entity.setStatus(0);
			UserFocusHospital userFocusHospital = userFocusHospitalService.findByUserId(loginUser.getId());
			if(userFocusHospital!=null){
				entity.setHospitalName(userFocusHospital.getHospitalName());
				entity.setHospitalId(userFocusHospital.getHospitalId());
			}
		}else{
			//补录入
			//管理员可以添加以往的入账金额
			//相差金额 = 入账总金额 - 实账总金额
			BigDecimal offlineAmount = entity.getOfflineAmount() != null?entity.getOfflineAmount():new BigDecimal(0);
			BigDecimal acctAmount = entity.getAcctAmount() != null?entity.getAcctAmount():new BigDecimal(0);
			if(offlineAmount.compareTo(acctAmount)!=-1){
				BigDecimal diffAmount = offlineAmount.subtract(acctAmount);
				entity.setDiffAmount(diffAmount);
			}
			//状态-"0":"初始","1":"未对账","2":"已对账"
			entity.setStatus(1);
		}
		return entity;
	}
	
	@RequestMapping("goofflineSalesAcctStaConfirm")
	public String goofflineSalesAcctStaConfirm(Long id, Model model){
		OfflineSalesAcctSta osas = offlineSalesAcctStaService.get(id);
		model.addAttribute("offlineSalesAcctSta", osas);
		return "/manage/epei/offlineSalesAcctStaConfirm";
	}
	
	@RequestMapping("offlineSalesAcctStaConfirm")
	@ResponseBody
	public JsonResult offlineSalesAcctStaConfirm(OfflineSalesAcctSta osas){
		JsonResult result = new JsonResult();
		try {
			offlineSalesAcctStaService.offlineSalesAcctStaConfirm(osas);
			result.setMessage("线下交易对账记录确认处理成功.");
		} catch (Exception e) {
			handleException(result,"线下交易记录确认出错",e);
		}
		return result;
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
			if(!Pattern.compile(numberExp).matcher(line[0]).find()){
				throw new BusinessException("第"+row+"行，"+"账期输入有误！");
			}
			if(!Pattern.compile(numberExp).matcher(line[0]).find()){
				throw new BusinessException("第"+row+"行，"+"入账总金额输入有误！");
			}
			if(!Pattern.compile(numberExp).matcher(line[0]).find()){
				throw new BusinessException("第"+row+"行，"+"实账总金额输入有误！");
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
	public List<OfflineSalesAcctSta> unmarshal(List<String[]> lines) {
		List<OfflineSalesAcctSta> entities = new LinkedList<OfflineSalesAcctSta>();
		for (String[] line : lines) {
			//每次换行时new一个新的线下消费入账统计
			OfflineSalesAcctSta osas = new OfflineSalesAcctSta();
			//账期			line[0]
			osas.setAcctYmd(line[0]);
			//入账总金额		line[1]
			osas.setOfflineAmount(new BigDecimal(line[1]));
			//实账总金额		line[2]
			osas.setAcctAmount(new BigDecimal(line[2]));
			//相差金额
			BigDecimal diffAmount = new BigDecimal(line[1]).subtract(new BigDecimal(line[2]));
			osas.setDiffAmount(diffAmount);
			//状态
			osas.setStatus(0);
			//管理员信息
			User loginUser= (User) SecurityUtils.getSubject().getPrincipal();
			//录入人员ID
			osas.setCreateId(loginUser.getId());
			//录入人员
			osas.setCreateName(loginUser.getRealName());
			osas.setDataCount(Integer.parseInt(line[3]));
			if(line[4].equals("所有医院")){
				osas.setCountScope(1);
				osas.setOrderType(3);
			}else if(line[4].equals("单个医院")){
				osas.setCountScope(2);
				osas.setOrderType(3);
				Hospital h = hospitalService.findHospitalByName(line[5]);
				if(h!=null){
					osas.setHospitalId(h.getId());
					osas.setHospitalName(h.getName());
				}else{
					throw new BusinessException("在系统中找不到表格中填写的某些医院");
				}
			}else{
				Hospital h = hospitalService.findHospitalByName(line[5]);
				if(h==null){
					throw new BusinessException("在系统中找不到表格中填写的某些医院");
				}else{
					osas.setHospitalId(h.getId());
					osas.setHospitalName(h.getName());
					Department d = departmentService.findDepartmentByNameAndHospitalId(line[6], h.getId());
					System.out.println(line[6]+h.getId());
					if(d==null){
						throw new BusinessException("在系统中找不到表格中填写的某些科室");
					}else{
						osas.setDepartmentId(d.getId());
						osas.setDepartmentName(d.getName());
					}
				}
				osas.setCountScope(3);
				if(line[7].equals("陪诊")){
					osas.setOrderType(1);
				}else{
					osas.setOrderType(2);
				}
			}
			
			entities.add(osas);
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
	public boolean doExportExcelBody(List<OfflineSalesAcctSta> list, HttpServletRequest request, HttpServletResponse response) {
		WritableWorkbook workbook = null;
		OutputStream out = null;
		try {
			//设置第一行栏目名称的字体
			WritableFont font1 = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);
	        WritableCellFormat cellFormat = new WritableCellFormat(font1);
			out = response.getOutputStream();
			String[] headerNames = {
					"账期(20160101)","入账总金额","实账总金额","相差金额","确认总金额","状态",
					"录入人员","录入备注","对账人员","对账备注","对账日期","创建时间","修改时间","统计条数","统计范围","统计医院","统计科室","统计订单类型"
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
			
			for (OfflineSalesAcctSta osas : list) {
				List<String> entityData = new ArrayList<String>();
				//账期(20160101)
				entityData.add(osas.getAcctYmd());
				//入账总金额
				entityData.add(String.valueOf(osas.getOfflineAmount()));
				//实账总金额
				entityData.add(String.valueOf(osas.getAcctAmount()));
				//相差金额
				entityData.add(String.valueOf(osas.getDiffAmount()));
				//确认总金额
				entityData.add(String.valueOf(osas.getAmount()));
				//状态
				entityData.add(osas.getStatus()==0?"初始":osas.getStatus()==1?"未对账":"已对账");
				//录入人员
				entityData.add(osas.getCreateName());
				//录入备注
				entityData.add(osas.getCreateMemo());
				//对账人员
				entityData.add(osas.getAcctName());
				//对账备注
				entityData.add(osas.getAcctMemo());
				//对账日期
				entityData.add(String.valueOf(osas.getAcctTime()));
				//创建时间
				entityData.add(String.valueOf(osas.getCreateTime()));
				//修改时间
				entityData.add(String.valueOf(osas.getUpdateTime()));
				entityData.add(osas.getDataCount()+"");
				if(osas.getCountScope()==1){
					entityData.add("所有医院");
				}else if(osas.getCountScope()==2){
					entityData.add("单个医院");
				}else{
					entityData.add("单个科室");
				}
				entityData.add(osas.getHospitalName());
				entityData.add(osas.getDepartmentName());
				if(osas.getOrderType()==1){
					entityData.add("陪诊");
				}else if(osas.getOrderType()==2){
					entityData.add("陪护");
				}else{
					entityData.add("全部");
				}
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
	@RequestMapping("downloadOfflineSalesAcctStaExcelModel")
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
					"账期(20160101)","入账总金额","实账总金额","统计数目","统计范围（所有医院、单个医院、单个科室）","统计医院（填写医院名称,默认不填，当统计范围不为所有医院时填写此项）","统计科室名称（默认不填，当统计范围为单个科室时填写此项）","统计类型（陪诊、陪护，默认不填，只有当统计范围为单个科室时填写此项）"
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
		List<Hospital> hospitals = new ArrayList<Hospital>();
		if(serviceType.equals("all")){
			hospitals = hospitalService.getAllNoDel();
		}else{
			hospitals = hospitalService.getAllNoDelByType(serviceType);
		}
		List<HospitalVo> vos  = new ArrayList<>();
		for(Hospital hospital : hospitals){
			vos.add(new HospitalVo(hospital));
		}
		return vos;
	}
	
	@RequestMapping("comboboxDepartment")
	@ResponseBody
	public List<Department> comboboxDepartment(Long hosid){
		return departmentService.getSamehosid(hosid);
	}
}
