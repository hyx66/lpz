package com.acooly.epei.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.Encodes;
import com.acooly.epei.domain.*;
import com.acooly.epei.service.CompanyService;
import com.acooly.epei.service.CustomerFamilyService;
import com.acooly.epei.service.CustomerNOService;
import com.acooly.module.security.domain.User;
import com.acooly.module.security.utils.Digests;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.CustomerService;
import com.acooly.epei.service.VipService;
import com.acooly.epei.service.VipCustomerDiscountService;
import com.acooly.epei.dao.CustomerDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.Assert;

@Service("customerService")
public class CustomerServiceImpl extends EntityServiceImpl<Customer, CustomerDao> implements CustomerService {

	private Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private CustomerNOService customerNOService;
	
	@Autowired
	private VipService vipService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private VipCustomerDiscountService vipCustomerDiscountService;
	
	@Autowired
	private CustomerFamilyService customerFamilyService;

	@Override public Customer findCustomerByUsername(String username) {
		return getEntityDao().findByUserName(username);
	}

	@Override public Customer getAndCheckCustomer(String username) {
		return null;
	}
	
	@Override public Customer findCustomerById(Long id) {
		return getEntityDao().findById(id);
	}

	@Override public Customer getCustomerByOpenid(String openid) {
		if(StringUtils.isNotBlank(openid)){
			Map<String, Object> params = new HashMap<>();
			params.put("EQ_openid", openid);	
			Map<String,Boolean> sortMap = new HashMap<String,Boolean>();
	    	sortMap.put("updateTime", false);
			List<Customer> customerList = query(params, sortMap);
			if(customerList.size()>0){
				return customerList.get(0);	
			}else{
				return null;
			}
		}
		return null;
	}

	@Override
	public Customer getCustomerByCusNo(Long cusNo) {
		if(StringUtils.isNotBlank(String.valueOf(cusNo))){
			return getEntityDao().findByCusNo(cusNo);
		}
		return null;
	}
	
	@Override
	@Transactional
	public void createCustomer(Customer customer) {
		checkUnique(customer);
		customer.setRegOrigin(RegOriginEnum.PC.code());
		customerNOService.genarateCusNo(customer);
		customer.setMobile(customer.getUserName());
		Assert.hasText(customer.getMobile(),"手机号不能为空");
		Assert.hasText(customer.getPassword(),"密码不能为空");
		entryptPassword(customer);
		super.save(customer);
	}

	@Override 
	public void updateCustomer(Customer customer) {
		checkUnique(customer);
		update(customer);
	}
	
	@Override
	public void updateCustomerHosIdById(Long HosId, Long id) {
		try {
			Customer customer = get(id);
			customer.setCustomerHosId(HosId);
			update(customer);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
    @Transactional
    public void changePassword(Customer user, String newPassword) throws BusinessException {
        user.setPassword(newPassword);
        entryptPassword(user);
        update(user);
	}

	@Override
	@Transactional
	public boolean validatePassword(Customer user, String plaintPassword) throws BusinessException {
		boolean success = false;
		success = entryptPassword(plaintPassword, user.getSalt()).equals(user.getPassword());
		logger.info("保存用户openid:"+user.toString());
		if(success && StringUtils.isNotBlank(user.getOpenid())){
		logger.info("保存用户openid:"+user.toString());
			update(user);
		}
		return  success;
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 *
	 * @param user
	 */
	private void entryptPassword(Customer customer) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		customer.setSalt(Encodes.encodeHex(salt));
		customer.setPassword(entryptPassword(customer.getPassword(), Encodes.encodeHex(salt)));
	}

	private String entryptPassword(String plainPassword, String salt) {
		return Encodes.encodeHex(Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), HASH_INTERATIONS));
	}

	public void checkUnique(Customer customer) {
		Customer exsit = getEntityDao().findByUserName(customer.getUserName());
		if (exsit != null && (!exsit.getId().equals(customer.getId()))) {
			throw new BusinessException("用户名:[" + customer.getUserName() + "]已经存在");
		}
	}

	@Override
	public void updateCustomerAdminTypeById(Integer adminType, Long id) {
		try {
			Customer customer = get(id);
			customer.setAdminType(adminType);
			update(customer);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	@Override
	public Customer findCustomerByNameAndMobile(String name, String mobile) {
		return getEntityDao().findCustomerByNameAndMobile(name, mobile);
	}

	@Override
	public int findCustomerCountByCreateTime(String beforeDate, String defaultEndDate) {
		String sql = " select count(id) as count from ep_customer " +
				" where to_char(create_time,'yyyy/MM/dd') >= '"+beforeDate+"' " +
				" and   to_char(create_time,'yyyy/MM/dd') <  '"+defaultEndDate+"' " +
				" and deleted = 0 ";
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		return count;
	}

	@Override
	public Customer findCustomerByOpenid(String openid) {
		return getEntityDao().findByOpenid(openid);
	}

	@Override
	@Transactional
	public void toBeVipCustomer(Customer customer) {
		Vip vip = vipService.get(customer.getVipId());
		if(customer.getVipCompanyId() != null){
			Company company = companyService.get(customer.getVipCompanyId());
			if(company == null) throw new BusinessException("没有找到相应的企业信息");
		}
		VipCustomerDiscount vipCustomerDiscount = vipCustomerDiscountService.findByCustomerId(customer.getId());
		if(vipCustomerDiscount != null) {
			if(vipCustomerDiscount.getVipId() != customer.getVipId()) {
				vipCustomerDiscount.setDiscount(vip.getDiscount());
				vipCustomerDiscount.setVipId(customer.getVipId());
				vipCustomerDiscountService.update(vipCustomerDiscount);
			} else {
				return;
			}
		} else {
			vipCustomerDiscount = new VipCustomerDiscount();
			vipCustomerDiscount.setCustomerId(customer.getId());
			vipCustomerDiscount.setCustomerUserName(customer.getUserName());
			vipCustomerDiscount.setDiscount(vip.getDiscount());
			vipCustomerDiscount.setVipId(customer.getVipId());
			vipCustomerDiscountService.save(vipCustomerDiscount);
		}
		User loginUser= (User) SecurityUtils.getSubject().getPrincipal();
		customer.setVipCreateUserId(loginUser.getId());
		customer.setVipActivated(1);
		customer.setVipCreateUserName(loginUser.getUsername());
		customer.setVipGrade(vip.getGrade());
		customer.setVipTime(new Date());
	}

	@Override
	@Transactional
	public void cancelVipCustomer(Customer customer){
		VipCustomerDiscount vipCustomerDiscount = vipCustomerDiscountService.findByCustomerId(customer.getId());
		if(vipCustomerDiscount != null){
			vipCustomerDiscountService.remove(vipCustomerDiscount);
		}
		customer.setVipCreateUserId(null);
		customer.setVipActivated(null);
		customer.setVipCreateUserName(null);
		customer.setVipGrade(null);
		customer.setVipTime(null);
		customer.setVipCompanyId(null);
	}

	@Override
	@Transactional
	public void onSave(HttpServletRequest request, HttpServletResponse response, Model model, Customer entity,
			boolean isCreate) {
		if (isCreate) {
			createCustomer(entity);
		} else {
			updateCustomer(entity);
		}
		if (entity.getVipId() != null) {
			toBeVipCustomer(entity);
			customerFamilyService.addCustomerFamily(entity);
		} else {
			cancelVipCustomer(entity);
		}
	}

	@Override
	@Transactional
	public void vipIncludeSaves(List<Customer> entities) {
		for(Customer customer : entities){
			super.save(customer);
			if(customer.getVipId()!=null){
				toBeVipCustomer(customer);
			}
		}
	}

	@Override
	public Customer findByCusNo(Long cusNo) {
		return getEntityDao().findByCusNo(cusNo);
	}
	
}
