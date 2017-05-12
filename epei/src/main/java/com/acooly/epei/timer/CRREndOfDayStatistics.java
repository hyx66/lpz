package com.acooly.epei.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acooly.epei.domain.CustomerRegReports;
import com.acooly.epei.service.CustomerRegReportsService;
import com.acooly.epei.service.CustomerService;

/**
 * 
 * @author Administrator
 */
@Component("cRREndOfDayStatistics")
public class CRREndOfDayStatistics {

	private static Logger logger  = LoggerFactory.getLogger(CRREndOfDayStatistics.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerRegReportsService customerRegReportsService;

	public void main(String[] args){
		System.out.println("定时任务执行了！");
		logger.info("日终任务开始");

		Date dNow = new Date();						//当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);						//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);	//设置为前一天
		dBefore = calendar.getTime();				//得到前一天的时间

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");	//设置时间格式
		String beforeDate = sdf.format(dBefore);					//格式化前一天
		String defaultEndDate = sdf.format(dNow);//格式化当前时间

		int customerCount = customerService.findCustomerCountByCreateTime(beforeDate,defaultEndDate);
		if(customerCount>0){
			CustomerRegReports crr = new CustomerRegReports();
			crr.setDataYmd(beforeDate);
			crr.setNum(customerCount);
			crr.setType(0);
			customerRegReportsService.save(crr);
		}
		logger.info("日终任务结束");
	}

}
