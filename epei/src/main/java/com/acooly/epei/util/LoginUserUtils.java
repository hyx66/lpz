package com.acooly.epei.util;

import com.acooly.epei.domain.Customer;

import javax.servlet.http.HttpSession;

/**
 * 修订记录：
 * liubin 2015-10-26 19:14 创建
 */
public class LoginUserUtils {

	/**
	 * 当前登录用户信息在session中的属性名
	 */
	public static final  String CURRENT_CUSTOMER_ATTR = "sessionCustomer";

	/**
	 * 获取当前登录会员信息
	 * @return
	 */
	public static Customer getCustomer(){
		HttpSession session = RequestContextHolderUtils.getSession();
		Customer customer = (Customer) session.getAttribute(CURRENT_CUSTOMER_ATTR);
		return customer;
	}

	public static Long getCusNo(){
		return getCustomer().getCusNo();
	}

	public static Long getCusId(){
		return getCustomer().getId();
	}
}
