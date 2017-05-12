package com.acooly.epei.timer;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acooly.epei.domain.CapitalReports;
import com.acooly.epei.service.CapitalReportsService;
import com.acooly.epei.service.CustomerAcctRecordsService;

@Component("cREndOfDayStatistics")
public class CREndOfDayStatistics {

	private static Logger logger  = LoggerFactory.getLogger(CREndOfDayStatistics.class);

	@Autowired
	private CustomerAcctRecordsService customerAcctRecordsService;
	
	@Autowired
	private CapitalReportsService capitalReportsService;

	public void main(String[] args){
		logger.info("日终任务开始");

		Date dNow = new Date();						//当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);						//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);	//设置为前一天
		dBefore = calendar.getTime();				//得到前一天的时间

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");	//设置时间格式
		String beforeDate = sdf.format(dBefore);//格式化前一天
		//数据类型：充值、充值状态：充值成功
		BigDecimal amount = customerAcctRecordsService.findCustomerAcctRecordsByDateYmdAndDataTypeAndRechargeStatus(beforeDate,1,1);
		
		CapitalReports cr = new CapitalReports();
		cr.setDataYmd(beforeDate);
		if(amount!=null){
			cr.setAmount(amount);
			capitalReportsService.save(cr);
		}
//		cr.setAmount(amount != null ? amount : new BigDecimal(0));
//		capitalReportsService.save(cr);
		
		logger.info("日终任务结束");
	}

}
