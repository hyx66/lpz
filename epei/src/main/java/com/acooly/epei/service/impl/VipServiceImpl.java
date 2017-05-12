package com.acooly.epei.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.VipService;
import com.acooly.epei.dao.VipDao;
import com.acooly.epei.domain.Vip;

@Service("VipService")
public class VipServiceImpl extends EntityServiceImpl<Vip, VipDao> implements VipService {

	@Autowired
	VipDao vipDao;
	@Override
	public Vip findByName(String name) {
		return vipDao.findByName(name);
	}

}
