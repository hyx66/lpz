package com.acooly.epei.dao;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.LxyOrderBase;

/**
 * LXY_ORDER_BASE JPA Dao
 *
 * Date: 2017-04-13 15:07:59
 *
 * @author Acooly Code Generator
 *
 */
public interface LxyOrderBaseDao extends EntityJpaDao<LxyOrderBase, Long> {

	LxyOrderBase findByOrderId(String orderId);

}
