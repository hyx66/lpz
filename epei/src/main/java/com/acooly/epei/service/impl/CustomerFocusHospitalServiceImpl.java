package com.acooly.epei.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.CustomerFocusHospitalService;
import com.acooly.epei.dao.CustomerFocusHospitalDao;
import com.acooly.epei.domain.CustomerFocusHospital;

@Service("CustomerFocusHospitalService")
public class CustomerFocusHospitalServiceImpl extends EntityServiceImpl<CustomerFocusHospital, CustomerFocusHospitalDao> implements CustomerFocusHospitalService {
	
	@Override
	public List<CustomerFocusHospital> queryListByCustomerId(Long customerId) {
		Map<String, Object> params = new HashMap<>();
		params.put("EQ_customerId", customerId);
		List<CustomerFocusHospital> customerFocusHospital = query(params, null);
		return customerFocusHospital;
	}
	
}
