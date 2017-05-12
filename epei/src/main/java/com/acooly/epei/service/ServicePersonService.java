package com.acooly.epei.service;

import java.util.List;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.ServicePerson;

/**
 * 陪护陪诊人员 Service
 *
 * Date: 2015-10-21 20:33:30
 *
 * @author Acooly Code Generator
 *
 */
public interface ServicePersonService extends EntityService<ServicePerson> {

	List<ServicePerson> getAllNoServicePersonAndServiceType(String orderType, Long hosid);
	List<ServicePerson> getAllPersonAndServiceType(String orderType, Long hosid);
	List<ServicePerson> getAllNoServicePerson(String type);
	ServicePerson findServicePersonByNameAndMobileAndHospitalIdAndServiceType(String name, Long mobile, Long hospitalId, String serviceType);
	ServicePerson findServicePersonByMobile(String mobile);
	List<ServicePerson> findServicePersonByHospitalIdAndServiceType(
			Long hospitalId, String serviceType);

}
