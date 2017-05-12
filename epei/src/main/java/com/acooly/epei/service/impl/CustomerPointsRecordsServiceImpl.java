package com.acooly.epei.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.CustomerPointsRecordsService;
import com.acooly.epei.dao.CustomerPointsRecordsDao;
import com.acooly.epei.domain.CustomerPointsRecords;

@Service("CustomerPointsRecordsService")
public class CustomerPointsRecordsServiceImpl extends EntityServiceImpl<CustomerPointsRecords, CustomerPointsRecordsDao> implements CustomerPointsRecordsService {

	@Override
	public List<CustomerPointsRecords> queryByCustomerId(Long customerId) {
		Map<String, Object> params = new HashMap<>();
		params.put("EQ_customerId", customerId);	
		List<CustomerPointsRecords> customerPointsRecords = query(params, null);
		return customerPointsRecords;	
	}

}
