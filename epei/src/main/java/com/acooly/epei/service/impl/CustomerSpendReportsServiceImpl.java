package com.acooly.epei.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.CustomerSpendReportsService;
import com.acooly.epei.dao.CustomerSpendReportsDao;
import com.acooly.epei.domain.CustomerSpendReports;

@Service("CustomerSpendReportsService")
public class CustomerSpendReportsServiceImpl extends EntityServiceImpl<CustomerSpendReports, CustomerSpendReportsDao> implements CustomerSpendReportsService {

}
