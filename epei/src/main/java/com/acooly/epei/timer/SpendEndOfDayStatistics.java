package com.acooly.epei.timer;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acooly.epei.domain.CustomerAcctRecords;
import com.acooly.epei.domain.CustomerSpendReports;
import com.acooly.epei.service.CustomerAcctRecordsService;
import com.acooly.epei.service.CustomerSpendReportsService;

/**
 * 会员消费日终统计
 * @author Administrator
 */
@Component("spendEndOfDayStatistics")
public class SpendEndOfDayStatistics {

	private static Logger logger  = LoggerFactory.getLogger(SpendEndOfDayStatistics.class);
	
	@Autowired
	private CustomerAcctRecordsService customerAcctRecordsService;
	@Autowired
	private CustomerSpendReportsService customerSpendReportsService;
	
	public void main(String[] args){
		logger.info("平台会员每日线上消费统计");
		Date dNow = new Date();						//当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);						//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);	//设置为前一天
		dBefore = calendar.getTime();				//得到前一天的时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");	//设置时间格式
		String acctYmd = sdf.format(dBefore);						//格式化前一天
		
		//按照不同医院的不同科室进行统计,目前只统计了陪护订单在线付款后完成的消费
		List<CustomerAcctRecords> csrl = customerAcctRecordsService.queryForCount(acctYmd,1,1,1);//账期，消费状态，消费渠道，消费类型
		BigDecimal total = new BigDecimal("0");
		for(CustomerAcctRecords c : csrl){
			total = total.add(c.getSpendAmount());
		}
		CustomerSpendReports customerSpendReports = new CustomerSpendReports();
		customerSpendReports.setAmount(total);
		customerSpendReports.setDataYmd(acctYmd);
		customerSpendReports.setSpendType(1);//陪护
		if(customerSpendReports.getAmount().compareTo(new BigDecimal("0"))==1){
			customerSpendReportsService.save(customerSpendReports);
		}
		logger.info("平台会员每日线上消费统计结束");
	}
	
}
