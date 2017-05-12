package com.acooly.epei.web;

import java.io.OutputStream;
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

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.utils.Strings;
import com.acooly.epei.domain.CustomerPoints;
import com.acooly.epei.domain.CustomerPointsRecords;
import com.acooly.epei.service.CustomerPointsRecordsService;
import com.acooly.epei.util.CodeTools;

@Controller
@RequestMapping(value = "/manage/epei/customerPointsRecords")
public class CustomerPointsRecordsManagerController extends AbstractJQueryEntityController<CustomerPointsRecords, CustomerPointsRecordsService> {

	@Autowired
	private CustomerPointsRecordsService customerPointsRecordsService;

	@Override
	protected boolean doExportExcelBody(List<CustomerPointsRecords> list,
			HttpServletRequest request, HttpServletResponse response) {
		WritableWorkbook workbook = null;
		OutputStream out = null;
		try {
			//设置第一行栏目名称的字体
			WritableFont font1 = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);
	        WritableCellFormat cellFormat = new WritableCellFormat(font1);
			out = response.getOutputStream();
			String[] headerNames = {"用户名","电话","标题","外部单号","流水号","数据类型","积分数","消费类型","日期","备注"};
			workbook = Workbook.createWorkbook(out);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			int row = 0;

			if (headerNames != null) {
				for (int i = 0; i < headerNames.length; ++i) {
					sheet.addCell(new Label(i, row, headerNames[i], cellFormat));
				}
				++row;
			}

			for (CustomerPointsRecords entity : list) {
				List<String> entityData = new ArrayList<String>();
				entityData.add(entity.getCustomerName());
				entityData.add(entity.getCustomerMobile());
				entityData.add(entity.getTitle());
				entityData.add(entity.getOutNo());
				entityData.add(entity.getRecordsNo());
				
				if(entity.getDataType()==1){
					entityData.add("收益");
				}else{
					entityData.add("支出");
				}
				
				entityData.add(entity.getPoints()+"");
				
				if(entity.getSpendType()==1){
					entityData.add("陪护");
				}else if(entity.getSpendType()==2){
					entityData.add("陪诊");
				}else{
					entityData.add("");
				}
				
				entityData.add(entity.getCreateTime()+"");
				entityData.add(entity.getMemo());
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
}
