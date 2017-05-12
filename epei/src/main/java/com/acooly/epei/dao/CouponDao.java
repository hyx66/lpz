package com.acooly.epei.dao;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.Coupon;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

/**
 * EP_COUPON JPA Dao
 *
 * Date: 2015-10-30 15:59:38
 *
 * @author Acooly Code Generator
 *
 */
public interface CouponDao extends EntityJpaDao<Coupon, Long> {


	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Coupon findByCustomerCusNoAndUsedAndCouponTypeAndOrderNoNull(long cusNo,int used,String couponType);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Coupon findByOrderNo(String orderNo);
}
