package com.acooly.epei.dao;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.GlobalVariable;

public interface GlobalVariableDao extends EntityJpaDao<GlobalVariable, Long> {

	GlobalVariable findByParam(String param);

}
