package com.acooly.epei.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.Vip;

public interface VipService extends EntityService<Vip> {

	Vip findByName(String name);

}
