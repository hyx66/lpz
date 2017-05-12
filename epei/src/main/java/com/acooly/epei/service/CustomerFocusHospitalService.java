package com.acooly.epei.service;

import java.util.List;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.CustomerFocusHospital;

public interface CustomerFocusHospitalService extends EntityService<CustomerFocusHospital> {

	List<CustomerFocusHospital> queryListByCustomerId(Long customerId);

}
