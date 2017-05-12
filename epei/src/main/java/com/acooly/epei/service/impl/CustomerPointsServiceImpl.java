package com.acooly.epei.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.CustomerPointsService;
import com.acooly.epei.dao.CustomerPointsDao;
import com.acooly.epei.domain.CustomerPoints;

@Service("CustomerPointsService")
public class CustomerPointsServiceImpl extends EntityServiceImpl<CustomerPoints, CustomerPointsDao> implements CustomerPointsService {
	@Autowired
	CustomerPointsDao customerPointsDao;
	@Override
	public CustomerPoints getCustomerPointByCustomerId(Long customerId) {
		return customerPointsDao.findByCustomerId(customerId);
	}
	
	@Override
	public CustomerPoints getCustomerPointsByCustomerId(Long customerId) {
		return customerPointsDao.findByCustomerId(customerId);
	}

}
