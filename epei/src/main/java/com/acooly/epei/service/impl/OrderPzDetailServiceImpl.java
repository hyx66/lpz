package com.acooly.epei.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.OrderPzDetailService;
import com.acooly.epei.dao.OrderPzDetailDao;
import com.acooly.epei.domain.OrderPzDetail;

@Service("orderPzDetailService")
public class OrderPzDetailServiceImpl extends EntityServiceImpl<OrderPzDetail, OrderPzDetailDao> implements OrderPzDetailService {

}
