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
import com.acooly.epei.domain.Department;

@ActiveProfiles("development")
@ContextConfiguration(locations = "classpath:applicationContext-test-main.xml")
@TransactionConfiguration(defaultRollback = true)
public class DepartmentDaoTest extends SpringTransactionalTests {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentDaoTest.class);
	private static final String TABLE_NAME = "EP_DEPARTMENT";
	
	@Resource
	DepartmentDao departmentDao;

	@Test
	public void testCreate() {
		Department department = generateNewEntity();
		try {
			departmentDao.create(department);
		} catch (Exception e) {
			Assert.fail("testCreate fail. --> " + e.getMessage());
		}
		Assert.assertNotNull("testCreate fail. saved Id is null", department.getId());
		logger.info("test Department Create Successful.");
	}

	@Test
	public void testUpdate() {
		Department department = generateNewEntity();
		try {
			// 先创建一个对象，再修改
			departmentDao.create(department);
			departmentDao.update(department);
		} catch (Exception e) {
			Assert.fail("testUpdate fail. --> " + e.getMessage());
		}
		logger.info("test departmentDao Update Successful.");
	}

	@Test
	public void testDelete() {
		Department department = generateNewEntity();
		try {
			departmentDao.save(department);
			Long savedId = department.getId();
			departmentDao.delete(savedId);
		} catch (Exception e) {
			Assert.fail("testDelete fail. --> " + e.getMessage());
		}
		logger.info("test departmentDao Delete Successful.");
	}

	@Test
	public void testGet() {
		Department department = generateNewEntity();
		try {
			departmentDao.save(department);
			Department getDepartment = departmentDao.get(department.getId());
			Assert.assertNotNull(getDepartment);
		} catch (Exception e) {
			Assert.fail("testGet fail. --> " + e.getMessage());
		}
		logger.info("test departmentDao Get Successful.");
	}

	

	@After
	public void clean() {
		deleteFromTables(TABLE_NAME);
	}

	private Department generateNewEntity() {
		Department department = new Department();
		simpleFillEntity(department);
		department.setId(null);
		return department;
	}

}
