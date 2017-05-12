package com.acooly.epei.web;

import java.io.OutputStream;
import java.io.Serializable;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.acooly.core.common.web.support.JsonEntityResult;
import com.acooly.core.utils.Strings;
import com.acooly.epei.domain.Department;
import com.acooly.epei.domain.Patient;
import com.acooly.epei.service.PatientService;

@Controller
@RequestMapping(value = "/manage/epei/patient")
public class PatientManagerController extends LogicalDelEntityController<Patient, PatientService> {

	@Autowired
	private PatientService patientService;
	
	/*******************************************Excel导出用到的一些方法********************************************/
	@Override
	public boolean doExportExcelBody(List<Patient> list,
			HttpServletRequest request, HttpServletResponse response) {
		WritableWorkbook workbook = null;
		OutputStream out = null;
		try {
			WritableFont font1 = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);
	        WritableCellFormat cellFormat = new WritableCellFormat(font1);
			out = response.getOutputStream();
			String[] headerNames = {"姓名","手机号","身份证号","生日","性别","医保卡号"};
			workbook = Workbook.createWorkbook(out);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			int row = 0;

			if (headerNames != null) {
				for (int i = 0; i < headerNames.length; i++) {
					sheet.addCell(new Label(i, row, headerNames[i], cellFormat));
				}
				row++;
			}
			for (Patient entity : list) {
				List<String> entityData = new ArrayList<String>();
				entityData.add(entity.getName());
				entityData.add(entity.getMobile());
				entityData.add(entity.getIdCard());
				entityData.add(entity.getBirthday());
				if(entity.getGender()==1){
					entityData.add("男");
				}else{
					entityData.add("女");
				}
				entityData.add(entity.getMedicareCard());
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
