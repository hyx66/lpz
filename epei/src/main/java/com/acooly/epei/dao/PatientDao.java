package com.acooly.epei.dao;

import java.util.List;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.Patient;

/**
 * 病患 JPA Dao
 *
 * Date: 2015-10-21 20:40:39
 *
 * @author Acooly Code Generator
 *
 */
public interface PatientDao extends EntityJpaDao<Patient, Long> {

	Patient findByCusNoAndIdCard(Long cusNo,String idCard);

	Patient findByCusNoAndIdCardAndName(Long cusNo,String idCard,String name);
	
	Patient findByCusNoAndName(Long cusNo,String name);
	
	Patient findPatientByNameAndMobile(String name, String mobile);

	Patient findByCusNoAndNameAndIdNot(Long cusNo, String name, Long id);
}
