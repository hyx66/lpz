package com.acooly.epei.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.ServicePrice;

import java.util.List;

/**
 * 医院 Service
 *
 * Date: 2015-10-19 18:47:37
 *
 * @author Acooly Code Generator
 *
 */
public interface ServicePriceService extends EntityService<ServicePrice> {

	/**
	 * 获取指定医院下 指定类型的服务价目
	 * @param hospitalId 医院
	 * @param type
	 * @return
	 */
	List<ServicePrice> getAllServiceByHospitalAndType(Long hospitalId,String type);
	
	/**
	 * 根据医院ID，服务名称，服务类型查看服务信息
	 * @param hosid
	 * @param serviceGrade
	 * @param serviceType
	 * @return
	 */
	ServicePrice findServicePriceByHospitalIdAndServiceGradeAndServiceType(
			Long hospitalId, String serviceGrade, String serviceType);

}
