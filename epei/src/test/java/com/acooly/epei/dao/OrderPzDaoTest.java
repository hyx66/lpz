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
import com.acooly.epei.domain.OrderPz;

@ActiveProfiles("development")
@ContextConfiguration(locations = "classpath:applicationContext-test-main.xml")
@TransactionConfiguration(defaultRollback = true)
public class OrderPzDaoTest extends SpringTransactionalTests {

	private static final Logger logger = LoggerFactory.getLogger(OrderPzDaoTest.class);
	private static final String TABLE_NAME = "EP_ORDER_PZ";
	
	@Resource
	OrderPzDao orderPzDao;

	@Test
	public void testCreate() {
		OrderPz orderPz = generateNewEntity();
		try {
			orderPzDao.create(orderPz);
		} catch (Exception e) {
			Assert.fail("testCreate fail. --> " + e.getMessage());
		}
		Assert.assertNotNull("testCreate fail. saved Id is null", orderPz.getId());
		logger.info("test OrderPz Create Successful.");
	}

	@Test
	public void testUpdate() {
		OrderPz orderPz = generateNewEntity();
		try {
			// 先创建一个对象，再修改
			orderPzDao.create(orderPz);
			orderPzDao.update(orderPz);
		} catch (Exception e) {
			Assert.fail("testUpdate fail. --> " + e.getMessage());
		}
		logger.info("test orderPzDao Update Successful.");
	}

	@Test
	public void testDelete() {
		OrderPz orderPz = generateNewEntity();
		try {
			orderPzDao.save(orderPz);
			Long savedId = orderPz.getId();
			orderPzDao.delete(savedId);
		} catch (Exception e) {
			Assert.fail("testDelete fail. --> " + e.getMessage());
		}
		logger.info("test orderPzDao Delete Successful.");
	}

	@Test
	public void testGet() {
		OrderPz orderPz = generateNewEntity();
		try {
			orderPzDao.save(orderPz);
			OrderPz getOrderPz = orderPzDao.get(orderPz.getId());
			Assert.assertNotNull(getOrderPz);
		} catch (Exception e) {
			Assert.fail("testGet fail. --> " + e.getMessage());
		}
		logger.info("test orderPzDao Get Successful.");
	}

	

	@After
	public void clean() {
		deleteFromTables(TABLE_NAME);
	}

	private OrderPz generateNewEntity() {
		OrderPz orderPz = new OrderPz();
		simpleFillEntity(orderPz);
		orderPz.setId(null);
		return orderPz;
	}

}
