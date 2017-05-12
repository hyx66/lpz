package com.acooly.epei.web.front;

import com.acooly.epei.domain.Coupon;
import com.acooly.epei.domain.CouponType;
import com.acooly.epei.service.CouponService;
import com.acooly.epei.util.LoginUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 修订记录：
 * liubin 2015-11-07 12:40 创建
 */
@Controller
@RequestMapping("coupon")
public class CouponController {

	@Autowired
	private CouponService couponService;

	@RequestMapping("list")
	public String list(Model model){
		Long customerId = LoginUserUtils.getCusId();

		Map<String,Object> params = new HashMap<>();
		params.put("EQ_customer.id",customerId);
		params.put("EQ_used","0");

		List<Coupon> coupons = couponService.query(params,null);

		for(Coupon coupon : coupons){
			coupon.setCouponType(CouponType.getMsgByCode(coupon.getCouponType()));
		}

		model.addAttribute("coupons",coupons);

		return "/front/mobile/coupons";
	}
}
