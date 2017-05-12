package com.acooly.epei.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.PzServiceService;
import com.acooly.epei.dao.PzServiceDao;
import com.acooly.epei.domain.PzService;

@Service("pzServiceService")
public class PzServiceServiceImpl extends EntityServiceImpl<PzService, PzServiceDao> implements PzServiceService {

}
