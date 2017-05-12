package com.acooly.epei.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.acooly.core.common.web.AbstractJQueryEntityController;
import com.acooly.epei.domain.Coupon;
import com.acooly.epei.service.CouponService;

@Controller
@RequestMapping(value = "/manage/epei/coupon")
public class CouponManagerController extends AbstractJQueryEntityController<Coupon, CouponService> {


	@Autowired
	private CouponService couponService;

	

}
