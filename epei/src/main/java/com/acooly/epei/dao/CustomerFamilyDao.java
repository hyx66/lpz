package com.acooly.epei.dao;

import java.util.List;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.CustomerFamily;

public interface CustomerFamilyDao extends EntityJpaDao<CustomerFamily, Long> {

	List<CustomerFamily> findByCustomerUserName(String customerUserName);

	List<CustomerFamily> findByCustomerId(Long customerId);

	CustomerFamily findByIdCard(String idCard);

}
