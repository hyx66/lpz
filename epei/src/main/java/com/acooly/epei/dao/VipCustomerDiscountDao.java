package com.acooly.epei.dao;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.VipCustomerDiscount;

public interface VipCustomerDiscountDao extends EntityJpaDao<VipCustomerDiscount, Long> {

	VipCustomerDiscount findByCustomerId(Long customerId);

}
