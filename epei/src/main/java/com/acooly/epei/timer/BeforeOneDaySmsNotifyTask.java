package com.acooly.epei.timer;

import com.acooly.epei.service.OrderBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 提前一天短信通知用户
 * 修订记录：
 * liubin 2015-11-25 13:38 创建
 */
@Component("beforeOneDaySmsNotifyTask")
public class BeforeOneDaySmsNotifyTask {

	private Logger logger  = LoggerFactory.getLogger(BeforeOneDaySmsNotifyTask.class);

	@Autowired
	private OrderBaseService orderBaseService;


	public void doIt(){
		logger.info("开始执行提前一天提醒用户陪诊/陪护信息定时任务......");
		try{
			orderBaseService.notifyPatients();
		}
		catch ( Exception e){
			logger.error("定时任务提醒用户陪诊/陪护信息发生异常,",e);
		}

		logger.info("提前一天提醒用户陪诊/陪护信息定时任务完成.");
	}

}
