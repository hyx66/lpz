package com.acooly.epei.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.UserFocusHospital;

public interface UserFocusHospitalService extends EntityService<UserFocusHospital> {

	UserFocusHospital findByUserId(Long hospitalId);

}
