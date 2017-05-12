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
import com.acooly.epei.domain.OrderBase;

@ActiveProfiles("development")
@ContextConfiguration(locations = "classpath:applicationContext-test-main.xml")
@TransactionConfiguration(defaultRollback = true)
public class OrderBaseDaoTest extends SpringTransactionalTests {

	private static final Logger logger = LoggerFactory.getLogger(OrderBaseDaoTest.class);
	private static final String TABLE_NAME = "EP_ORDER_BASE";
	
	@Resource
	OrderBaseDao orderBaseDao;

	@Test
	public void testCreate() {
		OrderBase orderBase = generateNewEntity();
		try {
			orderBaseDao.create(orderBase);
		} catch (Exception e) {
			Assert.fail("testCreate fail. --> " + e.getMessage());
		}
		Assert.assertNotNull("testCreate fail. saved Id is null", orderBase.getId());
		logger.info("test OrderBase Create Successful.");
	}

	@Test
	public void testUpdate() {
		OrderBase orderBase = generateNewEntity();
		try {
			// 先创建一个对象，再修改
			orderBaseDao.create(orderBase);
			orderBaseDao.update(orderBase);
		} catch (Exception e) {
			Assert.fail("testUpdate fail. --> " + e.getMessage());
		}
		logger.info("test orderBaseDao Update Successful.");
	}

	@Test
	public void testDelete() {
		OrderBase orderBase = generateNewEntity();
		try {
			orderBaseDao.save(orderBase);
			Long savedId = orderBase.getId();
			orderBaseDao.delete(savedId);
		} catch (Exception e) {
			Assert.fail("testDelete fail. --> " + e.getMessage());
		}
		logger.info("test orderBaseDao Delete Successful.");
	}

	@Test
	public void testGet() {
		OrderBase orderBase = generateNewEntity();
		try {
			orderBaseDao.save(orderBase);
			OrderBase getOrderBase = orderBaseDao.get(orderBase.getId());
			Assert.assertNotNull(getOrderBase);
		} catch (Exception e) {
			Assert.fail("testGet fail. --> " + e.getMessage());
		}
		logger.info("test orderBaseDao Get Successful.");
	}

	

	@After
	public void clean() {
		deleteFromTables(TABLE_NAME);
	}

	private OrderBase generateNewEntity() {
		OrderBase orderBase = new OrderBase();
		simpleFillEntity(orderBase);
		orderBase.setId(null);
		return orderBase;
	}

}
