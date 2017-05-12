package com.acooly.epei.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.VipCustomerDiscountService;
import com.acooly.epei.dao.VipCustomerDiscountDao;
import com.acooly.epei.domain.VipCustomerDiscount;

@Service("VipCustomerDiscountService")
public class VipCustomerDiscountServiceImpl extends EntityServiceImpl<VipCustomerDiscount, VipCustomerDiscountDao> implements VipCustomerDiscountService {

	@Autowired
	VipCustomerDiscountDao vipCustomerDiscountDao;
	
	@Override
	public VipCustomerDiscount findByCustomerId(Long customerId) {
		return vipCustomerDiscountDao.findByCustomerId(customerId);
	}

}
