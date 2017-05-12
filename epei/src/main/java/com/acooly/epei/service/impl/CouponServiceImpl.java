package com.acooly.epei.service.impl;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.CouponService;
import com.acooly.epei.dao.CouponDao;
import com.acooly.epei.domain.Coupon;

@Service("couponService")
public class CouponServiceImpl extends EntityServiceImpl<Coupon, CouponDao> implements CouponService {

}
