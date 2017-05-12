package com.acooly.epei.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.CustomerAcct;

public interface CustomerAcctService extends EntityService<CustomerAcct> {
	CustomerAcct getCustomerAcctByCustomerId(Long customerId);
}
