package com.acooly.epei.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.CustomerAcctService;
import com.acooly.epei.dao.CustomerAcctDao;
import com.acooly.epei.domain.CustomerAcct;

@Service("CustomerAcctService")
public class CustomerAcctServiceImpl extends EntityServiceImpl<CustomerAcct, CustomerAcctDao> implements CustomerAcctService {
	@Autowired
	CustomerAcctDao customerAcctDao;
	public CustomerAcct getCustomerAcctByCustomerId(Long customerId){
		return customerAcctDao.findByCustomerId(customerId);
	}
}
