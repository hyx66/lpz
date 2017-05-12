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
import com.acooly.epei.domain.PzService;

@ActiveProfiles("development")
@ContextConfiguration(locations = "classpath:applicationContext-test-main.xml")
@TransactionConfiguration(defaultRollback = true)
public class PzServiceDaoTest extends SpringTransactionalTests {

	private static final Logger logger = LoggerFactory.getLogger(PzServiceDaoTest.class);
	private static final String TABLE_NAME = "EP_PZ_SERVICE";
	
	@Resource
	PzServiceDao pzServiceDao;

	@Test
	public void testCreate() {
		PzService pzService = generateNewEntity();
		try {
			pzServiceDao.create(pzService);
		} catch (Exception e) {
			Assert.fail("testCreate fail. --> " + e.getMessage());
		}
		Assert.assertNotNull("testCreate fail. saved Id is null", pzService.getId());
		logger.info("test PzService Create Successful.");
	}

	@Test
	public void testUpdate() {
		PzService pzService = generateNewEntity();
		try {
			// 先创建一个对象，再修改
			pzServiceDao.create(pzService);
			pzServiceDao.update(pzService);
		} catch (Exception e) {
			Assert.fail("testUpdate fail. --> " + e.getMessage());
		}
		logger.info("test pzServiceDao Update Successful.");
	}

	@Test
	public void testDelete() {
		PzService pzService = generateNewEntity();
		try {
			pzServiceDao.save(pzService);
			Long savedId = pzService.getId();
			pzServiceDao.delete(savedId);
		} catch (Exception e) {
			Assert.fail("testDelete fail. --> " + e.getMessage());
		}
		logger.info("test pzServiceDao Delete Successful.");
	}

	@Test
	public void testGet() {
		PzService pzService = generateNewEntity();
		try {
			pzServiceDao.save(pzService);
			PzService getPzService = pzServiceDao.get(pzService.getId());
			Assert.assertNotNull(getPzService);
		} catch (Exception e) {
			Assert.fail("testGet fail. --> " + e.getMessage());
		}
		logger.info("test pzServiceDao Get Successful.");
	}

	

	@After
	public void clean() {
		deleteFromTables(TABLE_NAME);
	}

	private PzService generateNewEntity() {
		PzService pzService = new PzService();
		simpleFillEntity(pzService);
		pzService.setId(null);
		return pzService;
	}

}
