package com.acooly.epei.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.Hospital;

import java.util.List;

/**
 * 医院 Service
 *
 * Date: 2015-10-19 18:47:37
 *
 * @author Acooly Code Generator
 *
 */
public interface HospitalService extends EntityService<Hospital> {

	/**
	 * 获取所有未删除的医院信息
	 * @return
	 */
	List<Hospital> getAllNoDelByType(String serviceType);
	/**
	 * 根据医院名称查询医院信息
	 * @param name
	 * @return
	 */
	Hospital findHospitalByName(String name);
	Hospital findById(Long id);
	List<Hospital> getAllNoDel();
	List<Hospital> getNoDelByHospitalIdAndType(Long hospitalId,
			String serviceType);
}
