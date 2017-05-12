package com.acooly.epei.dao;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.UserFocusHospital;

public interface UserFocusHospitalDao extends EntityJpaDao<UserFocusHospital, Long> {

	UserFocusHospital findByUserId(Long hospitalId);
}
