package com.acooly.epei.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.CustomerPoints;

public interface CustomerPointsService extends EntityService<CustomerPoints> {

	CustomerPoints getCustomerPointByCustomerId(Long customerId);

	CustomerPoints getCustomerPointsByCustomerId(Long customerId);

}
