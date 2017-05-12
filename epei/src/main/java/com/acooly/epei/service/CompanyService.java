package com.acooly.epei.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.Company;

public interface CompanyService extends EntityService<Company> {

	Company findByName(String name);

}
