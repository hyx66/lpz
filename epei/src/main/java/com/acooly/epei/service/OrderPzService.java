package com.acooly.epei.service;

import java.util.List;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.OrderBase;
import com.acooly.epei.domain.OrderPz;

/**
 * 陪诊订单 Service
 *
 * Date: 2015-10-26 21:28:11
 *
 * @author Acooly Code Generator
 *
 */
public interface OrderPzService extends EntityService<OrderPz> {

	/**
	 * 创建陪诊单
	 * @param order
	 */
	void createPzOrder(OrderPz order);

	/**
	 * 更新陪诊单
	 * @param order
	 */
	void updatePzOrder(OrderPz order);



	void finishedPzOrder(OrderPz order);

	void confirmPzOrder(OrderPz orderPz);

	List<OrderPz> getOrderByAcceptance(Integer acceptance);

	void createSpendRecords(OrderBase order);

}
