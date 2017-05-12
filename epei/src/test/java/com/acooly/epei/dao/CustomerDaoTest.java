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
import com.acooly.epei.domain.Customer;

@ActiveProfiles("development")
@ContextConfiguration(locations = "classpath:applicationContext-test-main.xml")
@TransactionConfiguration(defaultRollback = true)
public class CustomerDaoTest extends SpringTransactionalTests {

	private static final Logger logger = LoggerFactory.getLogger(CustomerDaoTest.class);
	private static final String TABLE_NAME = "EP_CUSTOMER";
	
	@Resource
	CustomerDao customerDao;

	@Test
	public void testCreate() {
		Customer customer = generateNewEntity();
		try {
			customerDao.create(customer);
		} catch (Exception e) {
			Assert.fail("testCreate fail. --> " + e.getMessage());
		}
		Assert.assertNotNull("testCreate fail. saved Id is null", customer.getId());
		logger.info("test Customer Create Successful.");
	}

	@Test
	public void testUpdate() {
		Customer customer = generateNewEntity();
		try {
			// 先创建一个对象，再修改
			customerDao.create(customer);
			customerDao.update(customer);
		} catch (Exception e) {
			Assert.fail("testUpdate fail. --> " + e.getMessage());
		}
		logger.info("test customerDao Update Successful.");
	}

	@Test
	public void testDelete() {
		Customer customer = generateNewEntity();
		try {
			customerDao.save(customer);
			Long savedId = customer.getId();
			customerDao.delete(savedId);
		} catch (Exception e) {
			Assert.fail("testDelete fail. --> " + e.getMessage());
		}
		logger.info("test customerDao Delete Successful.");
	}

	@Test
	public void testGet() {
		Customer customer = generateNewEntity();
		try {
			customerDao.save(customer);
			Customer getCustomer = customerDao.get(customer.getId());
			Assert.assertNotNull(getCustomer);
		} catch (Exception e) {
			Assert.fail("testGet fail. --> " + e.getMessage());
		}
		logger.info("test customerDao Get Successful.");
	}

	

	@After
	public void clean() {
		deleteFromTables(TABLE_NAME);
	}

	private Customer generateNewEntity() {
		Customer customer = new Customer();
		simpleFillEntity(customer);
		customer.setId(null);
		return customer;
	}

}
