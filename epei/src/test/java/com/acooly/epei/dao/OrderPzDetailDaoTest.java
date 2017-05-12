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
import com.acooly.epei.domain.OrderPzDetail;

@ActiveProfiles("development")
@ContextConfiguration(locations = "classpath:applicationContext-test-main.xml")
@TransactionConfiguration(defaultRollback = true)
public class OrderPzDetailDaoTest extends SpringTransactionalTests {

	private static final Logger logger = LoggerFactory.getLogger(OrderPzDetailDaoTest.class);
	private static final String TABLE_NAME = "EP_ORDER_PZ_DETAIL";
	
	@Resource
	OrderPzDetailDao orderPzDetailDao;

	@Test
	public void testCreate() {
		OrderPzDetail orderPzDetail = generateNewEntity();
		try {
			orderPzDetailDao.create(orderPzDetail);
		} catch (Exception e) {
			Assert.fail("testCreate fail. --> " + e.getMessage());
		}
		Assert.assertNotNull("testCreate fail. saved Id is null", orderPzDetail.getId());
		logger.info("test OrderPzDetail Create Successful.");
	}

	@Test
	public void testUpdate() {
		OrderPzDetail orderPzDetail = generateNewEntity();
		try {
			// 先创建一个对象，再修改
			orderPzDetailDao.create(orderPzDetail);
			orderPzDetailDao.update(orderPzDetail);
		} catch (Exception e) {
			Assert.fail("testUpdate fail. --> " + e.getMessage());
		}
		logger.info("test orderPzDetailDao Update Successful.");
	}

	@Test
	public void testDelete() {
		OrderPzDetail orderPzDetail = generateNewEntity();
		try {
			orderPzDetailDao.save(orderPzDetail);
			Long savedId = orderPzDetail.getId();
			orderPzDetailDao.delete(savedId);
		} catch (Exception e) {
			Assert.fail("testDelete fail. --> " + e.getMessage());
		}
		logger.info("test orderPzDetailDao Delete Successful.");
	}

	@Test
	public void testGet() {
		OrderPzDetail orderPzDetail = generateNewEntity();
		try {
			orderPzDetailDao.save(orderPzDetail);
			OrderPzDetail getOrderPzDetail = orderPzDetailDao.get(orderPzDetail.getId());
			Assert.assertNotNull(getOrderPzDetail);
		} catch (Exception e) {
			Assert.fail("testGet fail. --> " + e.getMessage());
		}
		logger.info("test orderPzDetailDao Get Successful.");
	}

	

	@After
	public void clean() {
		deleteFromTables(TABLE_NAME);
	}

	private OrderPzDetail generateNewEntity() {
		OrderPzDetail orderPzDetail = new OrderPzDetail();
		simpleFillEntity(orderPzDetail);
		orderPzDetail.setId(null);
		return orderPzDetail;
	}

}
