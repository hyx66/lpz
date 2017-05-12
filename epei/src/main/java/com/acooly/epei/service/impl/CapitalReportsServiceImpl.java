package com.acooly.epei.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.CapitalReportsService;
import com.acooly.epei.dao.CapitalReportsDao;
import com.acooly.epei.domain.CapitalReports;

@Service("CapitalReportsService")
public class CapitalReportsServiceImpl extends EntityServiceImpl<CapitalReports, CapitalReportsDao> implements CapitalReportsService {

}
