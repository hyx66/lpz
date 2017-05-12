package com.acooly.epei.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.ServicePersonService;
import com.acooly.epei.dao.ServicePersonDao;
import com.acooly.epei.domain.ServicePerson;

@Service("servicePersonService")
public class ServicePersonServiceImpl extends EntityServiceImpl<ServicePerson, ServicePersonDao> implements ServicePersonService {
	
	@Autowired
	private ServicePersonDao servicePersonDao;
	
	@Override
	public List<ServicePerson> getAllNoServicePersonAndServiceType(
			String orderType, Long hosid) {
		Map<String, Object> params = new HashMap<>();
		params.put("EQ_deleted", 0);				//0:是否被禁用
		params.put("EQ_serviceState", 0);			//0:
		params.put("EQ_serviceType", orderType);	//orderType:1.服务类型
		params.put("EQ_hospitalId", hosid);			//hosid:医院id
		List<ServicePerson> servicePerson = query(params, null);
		return servicePerson;
	}
	
	@Override
	public List<ServicePerson> getAllPersonAndServiceType(
			String orderType, Long hosid) {
		Map<String, Object> params = new HashMap<>();
		params.put("EQ_deleted", 0);				//0:是否被禁用
		params.put("EQ_serviceType", orderType);	//orderType:1.服务类型
		params.put("EQ_hospitalId", hosid);			//hosid:医院id
		List<ServicePerson> servicePerson = query(params, null);
		return servicePerson;
	}

	@Override
	public List<ServicePerson> getAllNoServicePerson(String type) {
		Map<String, Object> params = new HashMap<>();
		String TYPE = StringUtils.upperCase(type);
		params.put("EQ_deleted", 0);	//0:是否被禁用
		if(TYPE.equals("PH"))params.put("EQ_serviceState", 0);//0:是否正在服务
		params.put("EQ_serviceType", TYPE);	//服务类型：PH-陪护          |   PZ-陪诊
		List<ServicePerson> servicePerson = query(params, null);
		return servicePerson;
	}
	
	@Override
	public ServicePerson findServicePersonByNameAndMobileAndHospitalIdAndServiceType(String name,
			Long mobile, Long hospitalId, String serviceType) {
		return servicePersonDao.findServicePersonByNameAndMobileAndHospitalIdAndServiceType(name,mobile,hospitalId,serviceType);
	}

	@Override
	public ServicePerson findServicePersonByMobile(String mobile) {
		return servicePersonDao.findByMobile(Long.parseLong(mobile));
	}

	@Override
	public List<ServicePerson> findServicePersonByHospitalIdAndServiceType(
		Long hospitalId, String serviceType) {
		Map<String, Object> params = new HashMap<>();
		params.put("EQ_deleted", 0);				//0:是否被禁用
		params.put("EQ_serviceType", serviceType);	//orderType:1.服务类型
		params.put("EQ_hospitalId", hospitalId);			//hosid:医院id
		List<ServicePerson> servicePersons = query(params, null);
		return servicePersons;
	}
}
