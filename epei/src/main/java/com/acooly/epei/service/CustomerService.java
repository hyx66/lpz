package com.acooly.epei.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.Customer;

/**
 * 会员 Service
 *
 * Date: 2015-10-19 18:47:37
 *
 * @author Acooly Code Generator
 *
 */
public interface CustomerService extends EntityService<Customer> {


	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;

	/**
	 * 根据登录名获取用户
	 *
	 * @param loginid
	 * @return
	 */
	Customer findCustomerByUsername(String username);

	Customer getAndCheckCustomer(String username);
	
	/**根据会员ID获取用户*/
	Customer findCustomerById(Long id);

	/**
	 * 根据微信的openid获取会员信息
	 * @param openid
	 * @return
	 */
	Customer getCustomerByOpenid(String openid);
	
	/**
	 * 根据会员的cusNo获取会员信息
	 * @param cusNo
	 * @return
	 */
	Customer getCustomerByCusNo(Long cusNo);

	/**
	 * 创建用户
	 *
	 * @param user
	 * @throws com.acooly.core.common.exception.BusinessException
	 */
	void createCustomer(Customer user) throws BusinessException;

	/**
	 * 修改用户 用户登录名称和密码不能通过该方法修改
	 *
	 * @param user
	 * @throws BusinessException
	 */
	void updateCustomer(Customer user) throws BusinessException;

	/**
	 * 修改密码
	 *
	 * @param user
	 * @param newPassword
	 * @throws BusinessException
	 */
	void changePassword(Customer user, String newPassword) throws BusinessException;

	/**
	 * 验证密码
	 *
	 * @param user
	 * @param plaintPassword
	 * @return
	 * @throws BusinessException
	 */
	boolean validatePassword(Customer user, String plaintPassword)
			throws BusinessException;

	void checkUnique(Customer customer);

	void updateCustomerHosIdById(Long HosNo, Long id);

	void updateCustomerAdminTypeById(Integer adminType, Long id);
	
	/**
	 * 根据客户姓名和手机号码，查询会员
	 * @param name
	 * @param mobile
	 * @return
	 */
	Customer findCustomerByNameAndMobile(String name, String mobile);

	int findCustomerCountByCreateTime(String beforeDate, String defaultEndDate);

	Customer findCustomerByOpenid(String openid);
	
	/**
	 * 普通会员转VIP会员
	 */
	void toBeVipCustomer(Customer customer);
	
	void cancelVipCustomer(Customer customer);

	void onSave(HttpServletRequest request, HttpServletResponse response, Model model, Customer entity, boolean isCreate);

	void vipIncludeSaves(List<Customer> entities);

	Customer findByCusNo(Long cusNo);

}
