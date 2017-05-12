package com.acooly.epei.dao;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.ServiceItem;

public interface ServiceItemDao extends EntityJpaDao<ServiceItem, Long> {

	ServiceItem findByCode(String code);

}
