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
import com.acooly.epei.domain.ServicePerson;

@ActiveProfiles("development")
@ContextConfiguration(locations = "classpath:applicationContext-test-main.xml")
@TransactionConfiguration(defaultRollback = true)
public class ServicePersonDaoTest extends SpringTransactionalTests {

	private static final Logger logger = LoggerFactory.getLogger(ServicePersonDaoTest.class);
	private static final String TABLE_NAME = "EP_SERVICE_PERSON";
	
	@Resource
	ServicePersonDao servicePersonDao;

	@Test
	public void testCreate() {
		ServicePerson servicePerson = generateNewEntity();
		try {
			servicePersonDao.create(servicePerson);
		} catch (Exception e) {
			Assert.fail("testCreate fail. --> " + e.getMessage());
		}
		Assert.assertNotNull("testCreate fail. saved Id is null", servicePerson.getId());
		logger.info("test ServicePerson Create Successful.");
	}

	@Test
	public void testUpdate() {
		ServicePerson servicePerson = generateNewEntity();
		try {
			// 先创建一个对象，再修改
			servicePersonDao.create(servicePerson);
			servicePersonDao.update(servicePerson);
		} catch (Exception e) {
			Assert.fail("testUpdate fail. --> " + e.getMessage());
		}
		logger.info("test servicePersonDao Update Successful.");
	}

	@Test
	public void testDelete() {
		ServicePerson servicePerson = generateNewEntity();
		try {
			servicePersonDao.save(servicePerson);
			Long savedId = servicePerson.getId();
			servicePersonDao.delete(savedId);
		} catch (Exception e) {
			Assert.fail("testDelete fail. --> " + e.getMessage());
		}
		logger.info("test servicePersonDao Delete Successful.");
	}

	@Test
	public void testGet() {
		ServicePerson servicePerson = generateNewEntity();
		try {
			servicePersonDao.save(servicePerson);
			ServicePerson getServicePerson = servicePersonDao.get(servicePerson.getId());
			Assert.assertNotNull(getServicePerson);
		} catch (Exception e) {
			Assert.fail("testGet fail. --> " + e.getMessage());
		}
		logger.info("test servicePersonDao Get Successful.");
	}

	

	@After
	public void clean() {
		deleteFromTables(TABLE_NAME);
	}

	private ServicePerson generateNewEntity() {
		ServicePerson servicePerson = new ServicePerson();
		simpleFillEntity(servicePerson);
		servicePerson.setId(null);
		return servicePerson;
	}

}
