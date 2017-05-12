package com.acooly.epei.dao;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.Hospital;

/**
 * 医院 JPA Dao
 *
 * Date: 2015-10-19 18:47:37
 *
 * @author Acooly Code Generator
 *
 */
public interface HospitalDao extends EntityJpaDao<Hospital, Long> {
	Hospital findByName(String name);
	Hospital findById(Long id);
}
