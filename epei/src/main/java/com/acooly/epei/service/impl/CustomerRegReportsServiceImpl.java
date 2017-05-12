package com.acooly.epei.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.CustomerRegReportsService;
import com.acooly.epei.dao.CustomerRegReportsDao;
import com.acooly.epei.domain.CustomerRegReports;

@Service("CustomerRegReportsService")
public class CustomerRegReportsServiceImpl extends EntityServiceImpl<CustomerRegReports, CustomerRegReportsDao> implements CustomerRegReportsService {

}
