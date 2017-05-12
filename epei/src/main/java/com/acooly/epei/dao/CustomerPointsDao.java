package com.acooly.epei.dao;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.CustomerPoints;

public interface CustomerPointsDao extends EntityJpaDao<CustomerPoints, Long> {

	CustomerPoints findByCustomerId(Long customerId);

}
