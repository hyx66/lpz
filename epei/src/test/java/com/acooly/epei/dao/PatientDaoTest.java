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
import com.acooly.epei.domain.Patient;

@ActiveProfiles("development")
@ContextConfiguration(locations = "classpath:applicationContext-test-main.xml")
@TransactionConfiguration(defaultRollback = true)
public class PatientDaoTest extends SpringTransactionalTests {

	private static final Logger logger = LoggerFactory.getLogger(PatientDaoTest.class);
	private static final String TABLE_NAME = "EP_PATIENT";
	
	@Resource
	PatientDao patientDao;

	@Test
	public void testCreate() {
		Patient patient = generateNewEntity();
		try {
			patientDao.create(patient);
		} catch (Exception e) {
			Assert.fail("testCreate fail. --> " + e.getMessage());
		}
		Assert.assertNotNull("testCreate fail. saved Id is null", patient.getId());
		logger.info("test Patient Create Successful.");
	}

	@Test
	public void testUpdate() {
		Patient patient = generateNewEntity();
		try {
			// 先创建一个对象，再修改
			patientDao.create(patient);
			patientDao.update(patient);
		} catch (Exception e) {
			Assert.fail("testUpdate fail. --> " + e.getMessage());
		}
		logger.info("test patientDao Update Successful.");
	}

	@Test
	public void testDelete() {
		Patient patient = generateNewEntity();
		try {
			patientDao.save(patient);
			Long savedId = patient.getId();
			patientDao.delete(savedId);
		} catch (Exception e) {
			Assert.fail("testDelete fail. --> " + e.getMessage());
		}
		logger.info("test patientDao Delete Successful.");
	}

	@Test
	public void testGet() {
		Patient patient = generateNewEntity();
		try {
			patientDao.save(patient);
			Patient getPatient = patientDao.get(patient.getId());
			Assert.assertNotNull(getPatient);
		} catch (Exception e) {
			Assert.fail("testGet fail. --> " + e.getMessage());
		}
		logger.info("test patientDao Get Successful.");
	}

	

	@After
	public void clean() {
		deleteFromTables(TABLE_NAME);
	}

	private Patient generateNewEntity() {
		Patient patient = new Patient();
		simpleFillEntity(patient);
		patient.setId(null);
		return patient;
	}

}
