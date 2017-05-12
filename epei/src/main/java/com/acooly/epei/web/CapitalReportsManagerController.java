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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.domain.AbstractEntity;
import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.core.utils.Strings;
import com.acooly.epei.domain.CapitalReports;
import com.acooly.epei.service.CapitalReportsService;
import com.acooly.epei.web.front.OrderController;

@Controller
@RequestMapping(value = "/manage/epei/capitalReports")
public class CapitalReportsManagerController extends AbstractJQueryEntityController<CapitalReports, CapitalReportsService> {
	
	private Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private CapitalReportsService capitalReportsService;
	
	@Override
	protected boolean doExportExcelBody(List<CapitalReports> list,
			HttpServletRequest request, HttpServletResponse response) {
		WritableWorkbook workbook = null;
		OutputStream out = null;
		try {
			//设置第一行栏目名称的字体
			WritableFont font1 = new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);
	        WritableCellFormat cellFormat = new WritableCellFormat(font1);
			out = response.getOutputStream();
			String[] headerNames = {"账期","金额"};
			workbook = Workbook.createWorkbook(out);
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			int row = 0;

			if (headerNames != null) {
				for (int i = 0; i < headerNames.length; ++i) {
					sheet.addCell(new Label(i, row, headerNames[i], cellFormat));
				}
				++row;
			}

			for (CapitalReports entity : list) {
				List<String> entityData = new ArrayList<String>();
				entityData.add(entity.getDataYmd());
				entityData.add(entity.getAmount().toString());
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
