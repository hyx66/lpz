package com.acooly.epei.dao;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.acooly.core.common.test.SpringTransactionalTests;
import com.acooly.epei.domain.Coupon;

@ActiveProfiles("development")
@ContextConfiguration(locations = "classpath:applicationContext-test-main.xml")
@TransactionConfiguration(defaultRollback = true)
public class CouponDaoTest extends SpringTransactionalTests {

	private static final Logger logger = LoggerFactory.getLogger(CouponDaoTest.class);
	private static final String TABLE_NAME = "EP_COUPON";
	
	@Resource
	CouponDao couponDao;

	@Test
	public void testCreate() {
		Coupon coupon = generateNewEntity();
		try {
			couponDao.create(coupon);
		} catch (Exception e) {
			Assert.fail("testCreate fail. --> " + e.getMessage());
		}
		Assert.assertNotNull("testCreate fail. saved Id is null", coupon.getId());
		logger.info("test Coupon Create Successful.");
	}

	@Test
	public void testUpdate() {
		Coupon coupon = generateNewEntity();
		try {
			// 先创建一个对象，再修改
			couponDao.create(coupon);
			couponDao.update(coupon);
		} catch (Exception e) {
			Assert.fail("testUpdate fail. --> " + e.getMessage());
		}
		logger.info("test couponDao Update Successful.");
	}

	@Test
	public void testDelete() {
		Coupon coupon = generateNewEntity();
		try {
			couponDao.save(coupon);
			Long savedId = coupon.getId();
			couponDao.delete(savedId);
		} catch (Exception e) {
			Assert.fail("testDelete fail. --> " + e.getMessage());
		}
		logger.info("test couponDao Delete Successful.");
	}

	@Test
	public void testGet() {
		Coupon coupon = generateNewEntity();
		try {
			couponDao.save(coupon);
			Coupon getCoupon = couponDao.get(coupon.getId());
			Assert.assertNotNull(getCoupon);
		} catch (Exception e) {
			Assert.fail("testGet fail. --> " + e.getMessage());
		}
		logger.info("test couponDao Get Successful.");
	}

	

	@After
	public void clean() {
		deleteFromTables(TABLE_NAME);
	}

	private Coupon generateNewEntity() {
		Coupon coupon = new Coupon();
		simpleFillEntity(coupon);
		coupon.setId(null);
		return coupon;
	}

}
