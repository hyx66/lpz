package com.acooly.epei.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.CompanyService;
import com.acooly.epei.dao.CompanyDao;
import com.acooly.epei.domain.Company;

@Service("CompanyService")
public class CompanyServiceImpl extends EntityServiceImpl<Company, CompanyDao> implements CompanyService {

	@Autowired
	CompanyDao companyDao;
	
	@Override
	public Company findByName(String name) {
		return companyDao.findByName(name);
	}

}
