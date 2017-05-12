package com.acooly.epei.service;

import java.util.List;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.CustomerPointsRecords;

public interface CustomerPointsRecordsService extends EntityService<CustomerPointsRecords> {

	List<CustomerPointsRecords> queryByCustomerId(Long customerId);

}
