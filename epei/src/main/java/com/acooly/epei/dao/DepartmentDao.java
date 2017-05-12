package com.acooly.epei.dao;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.Department;

/**
 * 医院科室 JPA Dao
 *
 * Date: 2015-10-19 18:43:56
 *
 * @author Acooly Code Generator
 *
 */
public interface DepartmentDao extends EntityJpaDao<Department, Long> {
	Department findDepartmentByNameAndHospitalId(String name, Long hospitalId);
}
