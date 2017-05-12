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
import com.acooly.epei.domain.Image;

@ActiveProfiles("development")
@ContextConfiguration(locations = "classpath:applicationContext-test-main.xml")
@TransactionConfiguration(defaultRollback = true)
public class ImageDaoTest extends SpringTransactionalTests {

	private static final Logger logger = LoggerFactory.getLogger(ImageDaoTest.class);
	private static final String TABLE_NAME = "EPEI_IMAGE";
	
	@Resource
	ImageDao imageDao;

	@Test
	public void testCreate() {
		Image image = generateNewEntity();
		try {
			imageDao.create(image);
		} catch (Exception e) {
			Assert.fail("testCreate fail. --> " + e.getMessage());
		}
		Assert.assertNotNull("testCreate fail. saved Id is null", image.getId());
		logger.info("test Image Create Successful.");
	}

	@Test
	public void testUpdate() {
		Image image = generateNewEntity();
		try {
			// 先创建一个对象，再修改
			imageDao.create(image);
			imageDao.update(image);
		} catch (Exception e) {
			Assert.fail("testUpdate fail. --> " + e.getMessage());
		}
		logger.info("test imageDao Update Successful.");
	}

	@Test
	public void testDelete() {
		Image image = generateNewEntity();
		try {
			imageDao.save(image);
			Long savedId = image.getId();
			imageDao.delete(savedId);
		} catch (Exception e) {
			Assert.fail("testDelete fail. --> " + e.getMessage());
		}
		logger.info("test imageDao Delete Successful.");
	}

	@Test
	public void testGet() {
		Image image = generateNewEntity();
		try {
			imageDao.save(image);
			Image getImage = imageDao.get(image.getId());
			Assert.assertNotNull(getImage);
		} catch (Exception e) {
			Assert.fail("testGet fail. --> " + e.getMessage());
		}
		logger.info("test imageDao Get Successful.");
	}

	

	@After
	public void clean() {
		deleteFromTables(TABLE_NAME);
	}

	private Image generateNewEntity() {
		Image image = new Image();
		simpleFillEntity(image);
		image.setId(null);
		return image;
	}

}
