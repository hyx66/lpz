package com.acooly.epei.web;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.dao.support.PageInfo;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.common.web.support.JsonListResult;
import com.acooly.core.common.web.support.JsonResult;
import com.acooly.core.utils.Strings;
import com.acooly.epei.dao.CustomerPointsDao;
import com.acooly.epei.domain.CustomerAcct;
import com.acooly.epei.domain.CustomerAcctRecords;
import com.acooly.epei.domain.CustomerPoints;
import com.acooly.epei.service.CustomerPointsService;
import com.acooly.epei.util.CodeTools;

@Controller
@RequestMapping(value = "/manage/epei/customerPoints")
public class CustomerPointsManagerController extends AbstractJQueryEntityController<CustomerPoints, CustomerPointsService> {

	@Autowired
	private CustomerPointsService customerPointsService;
	
	@Autowired
	private CustomerPointsDao customerPointsDao;
	
	@Override
	public JsonListResult<CustomerPoints> listJson(HttpServletRequest request,
			HttpServletResponse response) {
		JsonListResult<CustomerPoints> result = new JsonListResult<CustomerPoints>();
		try {
			result.appendData(referenceData(request));
			PageInfo<CustomerPoints> pageInfo = doList(request, response);
			result.setTotal(Long.valueOf(pageInfo.getTotalCount()));
			List<CustomerPoints> customerPointsList = new ArrayList<CustomerPoints>();
			for(CustomerPoints c : pageInfo.getPageResults()){
				String customerId = c.getCustomerId().toString();
				c.setPoints(CodeTools.dncode(c.getPoints(), customerId));
				customerPointsList.add(c);
			}
			result.setRows(customerPointsList);
		} catch (Exception e) {
			handleException(result, "分页查询", e);
		}
		return result;
	}
	
	@Override
	protected boolean doExportExcelBody(List<CustomerPoints> list,
			HttpServletRequest request, HttpServletResponse response) {
		WritableWorkbook workbook = null;
		OutputStream out = null;
		try {
			//设置第一行栏目名称的字体
			WritableFont font1 = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);
	        WritableCellFormat cellFormat = new WritableCellFormat(font1);
			out = response.getOutputStream();
			String[] headerNames = {"会员编号","能力值","总积分数","变更时间","状态"};
			workbook = Workbook.createWorkbook(out);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			int row = 0;

			if (headerNames != null) {
				for (int i = 0; i < headerNames.length; ++i) {
					sheet.addCell(new Label(i, row, headerNames[i], cellFormat));
				}
				++row;
			}

			for (CustomerPoints entity : list) {
				List<String> entityData = new ArrayList<String>();
				entityData.add(entity.getCustomerId().toString());
				entityData.add(entity.getCapacityValue()+"");
				entityData.add(CodeTools.dncode(entity.getPoints(), entity.getCustomerId().toString()));
				entityData.add(entity.getModifyTime()+"");
				if(entity.getStatus()==0){
					entityData.add("启用");
				}else{
					entityData.add("禁用");
				}
				
				for (int i = 0; i < entityData.size(); ++i) {
					sheet.addCell(new Label(i, row, Strings
							.trimToEmpty((String) entityData.get(i))));
				}
				++row;
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
	
	@RequestMapping("enableCustomerPoints")
	@ResponseBody
	public JsonResult enableCustomerPoint(Long id){
		JsonResult result  = new JsonResult();
		try{
			CustomerPoints c = customerPointsDao.getOne(id);
			c.setStatus(0);
			customerPointsService.update(c);
			result.setMessage("该用户账户已启用成功！");
		}
		catch (Exception e ){
			result.setSuccess(false);
			result.setMessage("系统出错啦,请稍后再试.");
		}
		return result;
	}
	
	@RequestMapping("disableCustomerPoints")
	@ResponseBody
	public JsonResult disableCustomerPoint(Long id){
		JsonResult result  = new JsonResult();
		try{
			CustomerPoints c = customerPointsDao.getOne(id);
			c.setStatus(1);
			customerPointsService.update(c);
			result.setMessage("该用户账户已禁用成功！");
		}
		catch (Exception e ){
			result.setSuccess(false);
			result.setMessage("系统出错啦,请稍后再试.");
		}
		return result;
	}
}
