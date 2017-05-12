package com.acooly.epei.service;

import java.util.Date;
import java.util.List;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.OrderPh;

/**
 * 陪护订单 Service
 *
 * Date: 2015-10-26 21:28:11
 *
 * @author Acooly Code Generator
 *
 */
public interface OrderPhService extends EntityService<OrderPh> {

	/**
	 * 创建陪护单
	 * @param order
	 */
	void createPhOrder(OrderPh order);

	/**
	 * 更新陪护单
	 * @param order
	 */
	void updatePhOrder(OrderPh order);

	/**
	 * 确认陪护单
	 * @param order
	 */
	void confirmPhOrder(OrderPh order);

	/**
	 * 完成陪护单
	 * @param order
	 */
	void finishedPhOrder(OrderPh order);
	
	/**
	 * 根据账期查询陪护订单
	 * @param dataYmd
	 * @return
	 */
	List<OrderPh> queryOrderPhByDataYmd(String dataYmd);
	
	int getAge(Date birthDay);

	void calculateAndSetAmount(OrderPh orderPh);

	void createSpendRecords(OrderPh order);

	List<OrderPh> getOrderByAcceptance(Integer acceptance);
	
}
