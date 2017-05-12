package com.acooly.epei.dao;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.Vip;

public interface VipDao extends EntityJpaDao<Vip, Long> {

	Vip findByName(String name);

}
