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
import com.acooly.epei.domain.OrderPh;

@ActiveProfiles("development")
@ContextConfiguration(locations = "classpath:applicationContext-test-main.xml")
@TransactionConfiguration(defaultRollback = true)
public class OrderPhDaoTest extends SpringTransactionalTests {

	private static final Logger logger = LoggerFactory.getLogger(OrderPhDaoTest.class);
	private static final String TABLE_NAME = "EP_ORDER_PH";
	
	@Resource
	OrderPhDao orderPhDao;

	@Test
	public void testCreate() {
		OrderPh orderPh = generateNewEntity();
		try {
			orderPhDao.create(orderPh);
		} catch (Exception e) {
			Assert.fail("testCreate fail. --> " + e.getMessage());
		}
		Assert.assertNotNull("testCreate fail. saved Id is null", orderPh.getId());
		logger.info("test OrderPh Create Successful.");
	}

	@Test
	public void testUpdate() {
		OrderPh orderPh = generateNewEntity();
		try {
			// 先创建一个对象，再修改
			orderPhDao.create(orderPh);
			orderPhDao.update(orderPh);
		} catch (Exception e) {
			Assert.fail("testUpdate fail. --> " + e.getMessage());
		}
		logger.info("test orderPhDao Update Successful.");
	}

	@Test
	public void testDelete() {
		OrderPh orderPh = generateNewEntity();
		try {
			orderPhDao.save(orderPh);
			Long savedId = orderPh.getId();
			orderPhDao.delete(savedId);
		} catch (Exception e) {
			Assert.fail("testDelete fail. --> " + e.getMessage());
		}
		logger.info("test orderPhDao Delete Successful.");
	}

	@Test
	public void testGet() {
		OrderPh orderPh = generateNewEntity();
		try {
			orderPhDao.save(orderPh);
			OrderPh getOrderPh = orderPhDao.get(orderPh.getId());
			Assert.assertNotNull(getOrderPh);
		} catch (Exception e) {
			Assert.fail("testGet fail. --> " + e.getMessage());
		}
		logger.info("test orderPhDao Get Successful.");
	}

	

	@After
	public void clean() {
		deleteFromTables(TABLE_NAME);
	}

	private OrderPh generateNewEntity() {
		OrderPh orderPh = new OrderPh();
		simpleFillEntity(orderPh);
		orderPh.setId(null);
		return orderPh;
	}

}
