package com.acooly.epei.dao;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.ServicePerson;

/**
 * 陪护陪诊人员 JPA Dao
 *
 * Date: 2015-10-21 20:33:30
 *
 * @author Acooly Code Generator
 *
 */
public interface ServicePersonDao extends EntityJpaDao<ServicePerson, Long> {
	ServicePerson findServicePersonByNameAndMobileAndHospitalIdAndServiceType(String name, Long mobile, Long hospitalId, String serviceType);

	ServicePerson findByMobile(Long mobile);
}
