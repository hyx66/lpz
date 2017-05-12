package com.acooly.epei.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.VipCustomerDiscount;

public interface VipCustomerDiscountService extends EntityService<VipCustomerDiscount> {

	VipCustomerDiscount findByCustomerId(Long customerId);

}
