package com.acooly.epei.timer;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acooly.epei.service.LxyOrderBaseService;

/**
 * 乐小优微店订单日终同步更新
 */
@Component("lxyOrderBaseRefresh")
public class LxyOrderBaseRefresh {

	@Autowired
	LxyOrderBaseService lxyOrderBaseService;
	
	private static Logger logger  = LoggerFactory.getLogger(LxyOrderBaseRefresh.class);
	
	public void main(String[] args){
		logger.info("乐小优微店订单日终同步更新开始……");
		Date dNow = new Date();						//当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);						//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);	//设置为前一天
		dBefore = calendar.getTime();				//得到前一天的时间
		lxyOrderBaseService.dataSynchronization(dBefore);
		logger.info("乐小优微店订单日终同步更新结束！");
	}
	
}
