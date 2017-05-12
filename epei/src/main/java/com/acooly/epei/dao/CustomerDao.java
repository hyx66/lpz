package com.acooly.epei.dao;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.Customer;

/**
 * 会员 JPA Dao
 *
 * Date: 2015-10-19 18:47:37
 *
 * @author Acooly Code Generator
 *
 */
public interface CustomerDao extends EntityJpaDao<Customer, Long> {

	Customer findByUserName(String userName);

	Customer findByOpenid(String openid);
	
	Customer findById(Long id);

	Customer findByOpenidAndDeleted(String openid,int deleted);

	Customer findByCusNo(Long cusNo);

	Customer findCustomerByNameAndMobile(String name, String mobile);

}
