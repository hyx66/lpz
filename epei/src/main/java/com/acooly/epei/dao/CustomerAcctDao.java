package com.acooly.epei.dao;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.CustomerAcct;

public interface CustomerAcctDao extends EntityJpaDao<CustomerAcct, Long> {
	CustomerAcct findByCustomerId(Long customerId);
}
