package com.acooly.epei.dao;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.Company;

public interface CompanyDao extends EntityJpaDao<Company, Long> {

	Company findByName(String name);

}
