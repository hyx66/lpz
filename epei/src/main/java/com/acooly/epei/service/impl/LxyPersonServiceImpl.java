package com.acooly.epei.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.LxyPersonService;
import com.acooly.epei.dao.LxyPersonDao;
import com.acooly.epei.domain.LxyPerson;

@Service("lxyPersonService")
public class LxyPersonServiceImpl extends EntityServiceImpl<LxyPerson, LxyPersonDao> implements LxyPersonService {

}
