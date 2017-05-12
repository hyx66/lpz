package com.acooly.epei.dao;

import java.util.List;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.OrderPh;

/**
 * 陪护订单 JPA Dao
 *
 * Date: 2015-10-26 21:28:11
 *
 * @author Acooly Code Generator
 *
 */
public interface OrderPhDao extends EntityJpaDao<OrderPh, Long> {

	List<OrderPh> findByAcceptance(Integer acceptance);

}
