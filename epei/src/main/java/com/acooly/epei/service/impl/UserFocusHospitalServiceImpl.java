package com.acooly.epei.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.UserFocusHospitalService;
import com.acooly.epei.dao.UserFocusHospitalDao;
import com.acooly.epei.domain.UserFocusHospital;

@Service("UserFocusHospitalService")
public class UserFocusHospitalServiceImpl extends EntityServiceImpl<UserFocusHospital, UserFocusHospitalDao> implements UserFocusHospitalService {

	@Autowired
	private UserFocusHospitalDao userFocusHospitalDao;
	
	@Override
	public UserFocusHospital findByUserId(Long hospitalId) {
		return userFocusHospitalDao.findByUserId(hospitalId);
	}
	
}
