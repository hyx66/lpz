package com.acooly.epei.service;

import java.util.List;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.Department;

/**
 * 医院科室 Service
 *
 * Date: 2015-10-19 18:43:56
 *
 * @author Acooly Code Generator
 *
 */
public interface DepartmentService extends EntityService<Department> {
	public List<Department> getSamehosid(Long hosid);
	public Department findDepartmentByNameAndHospitalId(String name, Long hospitalId);
	public List<Department> queryDepartmentByNameAndHospitalId(String name,
			Long hospitalId);
}
