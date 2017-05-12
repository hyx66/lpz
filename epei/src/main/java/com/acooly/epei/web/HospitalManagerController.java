package com.acooly.epei.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
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

import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.utils.Strings;
import com.acooly.epei.domain.Customer;
import com.acooly.epei.domain.Image;
import com.acooly.epei.domain.ServicePrice;
import com.acooly.epei.domain.ServiceTypeEnum;
import com.acooly.epei.service.CustomerService;
import com.acooly.epei.service.ImageService;
import com.acooly.epei.service.ServicePriceService;
import com.acooly.epei.web.vo.HospitalVo;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.epei.domain.Hospital;
import com.acooly.epei.service.HospitalService;

import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/manage/epei/hospital")
public class HospitalManagerController extends LogicalDelEntityController<Hospital, HospitalService> {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private ServicePriceService servicePriceService;

	@Autowired
	private ImageService imageService;

	{
		uploadConfig.setAllowExtentions("jpg,gif,png");
		uploadConfig.setNeedTimePartPath(true);
		uploadConfig.setUseMemery(true);
	}

	@Override
	public JsonEntityResult<Hospital> saveJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonEntityResult result = new JsonEntityResult();
		try {
			result.setEntity(doSave(request, response, null, true));
			Hospital entity = (Hospital) result.getEntity();
			if(StringUtils.isNotBlank(request.getParameter("pzCusNo"))){
				Customer cz = customerService.getCustomerByCusNo(Long.parseLong(request.getParameter("pzCusNo")));
				cz.setCustomerHosId(entity.getId());
				customerService.update(cz);
			}
			if(StringUtils.isNotBlank(request.getParameter("phCusNo"))){
				Customer ch = customerService.getCustomerByCusNo(Long.parseLong(request.getParameter("phCusNo")));
				ch.setCustomerHosId(entity.getId());
				customerService.update(ch);
			}
			result.setMessage("新增成功");
		} catch (Exception e) {
			handleException(result, "新增", e);
		}
		return result;
	}
	
	@RequestMapping("logo")
	public void hospitalLogo(HttpServletRequest request,HttpServletResponse response,Long id){
		Image image = imageService.get(id);
		response.setContentType("image/*");
		ServletOutputStream responseOutputStream = null;
		try {
			responseOutputStream = response.getOutputStream();
			if(image!=null){
				responseOutputStream.write(image.getContent());
				response.getOutputStream().write(image.getContent());
			}
			responseOutputStream.flush();
			responseOutputStream.close();

		} catch (IOException e) {
			handleException("",e,request);
		}
	}


	@RequestMapping("combobox")
	@ResponseBody
	public List<HospitalVo> combobox(String serviceType){
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

	@Override
	protected Hospital onSave(HttpServletRequest request, HttpServletResponse response, Model model, Hospital entity,
					   boolean isCreate) throws Exception {
		Map<String,UploadResult> uploadResult = doUpload(request);
		UploadResult logoFile = uploadResult.get("logo");

		if(null != logoFile){
			Image logo =  new Image();

			logo.setContent(input2byte(logoFile.getInputStream()));
			logo.setFileName(logoFile.getName());
			logo.setFileSize(logoFile.getSize());
			String fileName = logoFile.getName();
			if(fileName.indexOf(".") != -1){
				logo.setExt(fileName.substring(fileName.indexOf(".")+1));
			}

			entity.setHospitalLogo(logo);

		}


		for(int i = 0 ; i < entity.getPzPrices().size();i++){
			ServicePrice price = entity.getPzPrices().get(i);
			if(price.getPrice() == null || StringUtils.isBlank(price.getServiceGrade())){
				entity.getPzPrices().remove(price);
			}
			else{
				price.setServiceType(ServiceTypeEnum.PZ.code());
			}

		}

		for(int i = 0 ; i < entity.getPhPrices().size();i++){
			ServicePrice price = entity.getPhPrices().get(i);
			if(price.getPrice() == null || StringUtils.isBlank(price.getServiceGrade())){
				entity.getPhPrices().remove(price);
			}
			else{
				price.setServiceType(ServiceTypeEnum.PH.code());
			}

		}
		entity.setPrices(new ArrayList<ServicePrice>());
		entity.getPrices().addAll(entity.getPhPrices());
		entity.getPrices().addAll(entity.getPzPrices());

		return  entity;
	}

	@Override
	protected void onEdit(HttpServletRequest request, HttpServletResponse response, Model model, Hospital entity) {
		entity.setPhPrices(new ArrayList<ServicePrice>());
		entity.setPzPrices(new ArrayList<ServicePrice>());
		for(ServicePrice price : entity.getPrices()){
			if(ServiceTypeEnum.PH.code().equals(price.getServiceType())){
				entity.getPhPrices().add(price);
			}
			else if(ServiceTypeEnum.PZ.code().equals(price.getServiceType())){
				entity.getPzPrices().add(price);
			}
		}
		model.addAttribute("pzCusNoOld", entity.getPzCusNo());
		model.addAttribute("phCusNoOld", entity.getPhCusNo());
	}

	@Override
	protected void referenceData(HttpServletRequest request, Map<String, Object> model) {
		Map<String, Object> paramsCus = new HashMap<>();
		paramsCus.put("EQ_deleted", 0);				//数据是否显示	0:显示	1:不显示
		paramsCus.put("EQ_customerType", 1);		//用户类型	0:会员	1:管理员
//		paramsCus.put("EQ_customerHosId", -1);		//是否分配医院	-1:待定安排
		//加载医院管理员信息
		model.put("customers", customerService.query(paramsCus, null));
	}
	
	@Override
	protected void doRemove(HttpServletRequest request, HttpServletResponse response, Model model, Serializable... ids)
			throws Exception {
		if (ids == null || ids.length == 0) {
			throw new RuntimeException("请求参数中没有指定需要删除的实体Id");
		}
		if (ids.length == 1) {
			Hospital hospital =  getEntityService().get(ids[0]);
			if(hospital != null){
				hospital.setDeleted(1);
				getEntityService().update(hospital);
			}
			else{
				throw new RuntimeException("要删除的对象不存在,请刷新页面.");
			}
		}
	}

	private byte[] input2byte(InputStream inStream)
			throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}
	
	/*******************************************Excel导出用到的一些方法********************************************/
	@Override
	public boolean doExportExcelBody(List<Hospital> list,
			HttpServletRequest request, HttpServletResponse response) {
		WritableWorkbook workbook = null;
		OutputStream out = null;
		try {
			WritableFont font1 = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);
	        WritableCellFormat cellFormat = new WritableCellFormat(font1);
			out = response.getOutputStream();
			String[] headerNames = {"医院名称","接待地点","服务类型","陪诊负责人","陪诊负责人编号","陪诊负责人电话","陪护负责人","陪护负责人编号","陪护负责人电话","添加时间"};
			workbook = Workbook.createWorkbook(out);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			int row = 0;

			if (headerNames != null) {
				for (int i = 0; i < headerNames.length; i++) {
					sheet.addCell(new Label(i, row, headerNames[i], cellFormat));
				}
				row++;
			}
			for (Hospital entity : list) {
				List<String> entityData = new ArrayList<String>();
				entityData.add(entity.getName());
				entityData.add(entity.getReceptionPosition());
				entityData.add(entity.getServiceType());
				entityData.add(entity.getPzPrincipalName());
				entityData.add(entity.getPzCusNo()+"");
				entityData.add(entity.getPzPrincipalMobile());
				entityData.add(entity.getPhPrincipalName());
				entityData.add(entity.getPhCusNo()+"");
				entityData.add(entity.getPhPrincipalMobile());
				entityData.add(entity.getCreateTime()+"");
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
}
