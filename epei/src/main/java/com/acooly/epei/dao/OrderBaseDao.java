package com.acooly.epei.dao;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.OrderBase;

import java.util.Date;
import java.util.List;

/**
 * 订单基础信息 JPA Dao
 *
 * Date: 2015-10-26 21:28:11
 *
 * @author Acooly Code Generator
 *
 */
public interface OrderBaseDao extends EntityJpaDao<OrderBase, Long> {

	OrderBase findByOrderNo(String orderNo);


	List<OrderBase> findByStateInAndOrderTimeBetween(String[] states,Date start,Date end);
	
	
}
