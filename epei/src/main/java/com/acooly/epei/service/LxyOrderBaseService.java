package com.acooly.epei.service;

import java.util.Date;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.LxyOrderBase;

/**
 * LXY_ORDER_BASE Service
 *
 * Date: 2017-04-13 15:07:59
 *
 * @author Acooly Code Generator
 *
 */
public interface LxyOrderBaseService extends EntityService<LxyOrderBase> {

	//查询微店订单信息，如果本地数据库没有该订单，则录入该订单到系统中
	void dataSynchronization(Date date);

	String getWeidianAccessToken();

	void setEntityTheme(LxyOrderBase entity);
	
}
