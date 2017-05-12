package com.acooly.epei.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.ServiceItemService;
import com.acooly.epei.dao.ServiceItemDao;
import com.acooly.epei.domain.ServiceItem;

@Service("ServiceItemService")
public class ServiceItemServiceImpl extends EntityServiceImpl<ServiceItem, ServiceItemDao> implements ServiceItemService {

	@Autowired
	ServiceItemDao serviceItemDao;
	
	@Override
	public ServiceItem findByCode(String code) {
		return serviceItemDao.findByCode(code);
	}

}
