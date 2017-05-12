package com.acooly.epei.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.DepartmentService;
import com.acooly.epei.dao.DepartmentDao;
import com.acooly.epei.domain.Department;

@Service("departmentService")
public class DepartmentServiceImpl extends EntityServiceImpl<Department, DepartmentDao> implements DepartmentService {
	
	@Autowired
	private DepartmentDao departmentDao;
	
	@Override
	public List<Department> getSamehosid(Long hosid) {
		Map<String, Object> params = new HashMap<>();
		params.put("EQ_hospitalId", hosid);
		List<Department> department = query(params, null);
		return department;
	}
	
	@Override
	public Department findDepartmentByNameAndHospitalId(String name, Long hospitalId) {
		return departmentDao.findDepartmentByNameAndHospitalId(name, hospitalId);
	}

	@Override
	public List<Department> queryDepartmentByNameAndHospitalId(String name,
			Long hospitalId) {
		Map<String, Object> params = new HashMap<>();
		params.put("EQ_name", name);
		params.put("EQ_hospitalId", hospitalId);
		List<Department> departments = query(params, null);
		return departments;
	}
}
