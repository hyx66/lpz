package com.acooly.epei.dao;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.CustomerFocusHospital;

public interface CustomerFocusHospitalDao extends EntityJpaDao<CustomerFocusHospital, Long> {
	CustomerFocusHospital findByHospitalId(Long id);
	CustomerFocusHospital findByCustomerId(Long id);
}
