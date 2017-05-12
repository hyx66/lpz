package com.acooly.epei.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.ServiceItem;

public interface ServiceItemService extends EntityService<ServiceItem> {

	ServiceItem findByCode(String code);

}
