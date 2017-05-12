package com.acooly.epei.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.Patient;

import java.util.List;

/**
 * 病患 Service
 *
 * Date: 2015-10-21 20:40:39
 *
 * @author Acooly Code Generator
 *
 */
public interface PatientService extends EntityService<Patient> {

	/**
	 * 创建病患
	 * @param patient
	 * @return
	 */
	void createPatient(Patient patient);

	/**
	 * 更新病患
	 * @param patient
	 */
	void updatePatient(Patient patient);

	/**
	 * 如果不存在该病患的话则保存 存在的话返回已存在的
	 * @param patient
	 * @return
	 */
	Patient noExistsSave(Patient patient);

	/**
	 * 更新患者信息
	 * @param patient
	 * @return
	 */
	Patient noExistsUpdate(Patient patient);

	/**
	 * 根据会员编号获取病患信息
	 * @param cusNo
	 * @return
	 */
	List<Patient> findByCusNo(Long cusNo);
	
	/**
	 * 根据病患姓名和手机号查询病患是否存在
	 * @param name
	 * @param mobile
	 * @return
	 */
	Patient findPatientByNameAndMobile(String name, String mobile);

	List<Patient> queryPatientByNameAndMobile(String name, String mobile);

	Patient findByCusNoAndName(Long cusNo, String name);
}
