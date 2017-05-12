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
import com.acooly.epei.domain.Hospital;

@ActiveProfiles("development")
@ContextConfiguration(locations = "classpath:applicationContext-test-main.xml")
@TransactionConfiguration(defaultRollback = true)
public class HospitalDaoTest extends SpringTransactionalTests {

	private static final Logger logger = LoggerFactory.getLogger(HospitalDaoTest.class);
	private static final String TABLE_NAME = "EP_HOSPITAL";
	
	@Resource
	HospitalDao hospitalDao;

	@Test
	public void testCreate() {
		Hospital hospital = generateNewEntity();
		try {
			hospitalDao.create(hospital);
		} catch (Exception e) {
			Assert.fail("testCreate fail. --> " + e.getMessage());
		}
		Assert.assertNotNull("testCreate fail. saved Id is null", hospital.getId());
		logger.info("test Hospital Create Successful.");
	}

	@Test
	public void testUpdate() {
		Hospital hospital = generateNewEntity();
		try {
			// 先创建一个对象，再修改
			hospitalDao.create(hospital);
			hospitalDao.update(hospital);
		} catch (Exception e) {
			Assert.fail("testUpdate fail. --> " + e.getMessage());
		}
		logger.info("test hospitalDao Update Successful.");
	}

	@Test
	public void testDelete() {
		Hospital hospital = generateNewEntity();
		try {
			hospitalDao.save(hospital);
			Long savedId = hospital.getId();
			hospitalDao.delete(savedId);
		} catch (Exception e) {
			Assert.fail("testDelete fail. --> " + e.getMessage());
		}
		logger.info("test hospitalDao Delete Successful.");
	}

	@Test
	public void testGet() {
		Hospital hospital = generateNewEntity();
		try {
			hospitalDao.save(hospital);
			Hospital getHospital = hospitalDao.get(hospital.getId());
			Assert.assertNotNull(getHospital);
		} catch (Exception e) {
			Assert.fail("testGet fail. --> " + e.getMessage());
		}
		logger.info("test hospitalDao Get Successful.");
	}

	

	@After
	public void clean() {
		deleteFromTables(TABLE_NAME);
	}

	private Hospital generateNewEntity() {
		Hospital hospital = new Hospital();
		simpleFillEntity(hospital);
		hospital.setId(null);
		return hospital;
	}

}
