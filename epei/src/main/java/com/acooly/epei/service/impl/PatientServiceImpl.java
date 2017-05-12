package com.acooly.epei.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.utils.IdCards;
import com.acooly.epei.domain.Customer;
import com.acooly.epei.util.LoginUserUtils;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.PatientService;
import com.acooly.epei.dao.PatientDao;
import com.acooly.epei.domain.Patient;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Session;

@Service("patientService")
public class PatientServiceImpl extends EntityServiceImpl<Patient, PatientDao> implements PatientService {

	@Override public void createPatient(Patient patient) {
		checkExists(patient);
		try{
			//会员相关信息
			Customer currLoginCus = LoginUserUtils.getCustomer();
			patient.setCusNo(currLoginCus.getCusNo());
			patient.setCusId(currLoginCus.getId());
			patient.setCusName(currLoginCus.getName());

			//解析身份证信息
			fillIdCardInfo(patient);

			super.save(patient);
		}
		catch ( Exception e){
			throw new BusinessException(e.getMessage());
		}
	}

	@Override public void updatePatient(Patient patient) {
		checkExists(patient);
		try{
			Patient persistedPatient = get(patient.getId());
			persistedPatient.setMobile(patient.getMobile());

			//解析身份证信息
			fillIdCardInfo(patient);
			super.update(persistedPatient);
		}
		catch (Exception e){
			throw  new BusinessException(e.getMessage());
		}
	}

	@Override
	@Transactional
	public Patient noExistsSave(Patient patient) {
		Patient exists = getEntityDao().findByCusNoAndName(patient.getCusNo(), patient.getName());
		if(exists != null){
			exists.setMobile(patient.getMobile());
			exists.setMedicareCard(patient.getMedicareCard());
			exists.setBirthday(patient.getBirthday());
			exists.setGender(patient.getGender());
			getEntityDao().update(exists);
			return exists;
		}else{
			getEntityDao().save(patient);
			return patient;
		}
	}

	@Transactional
	@Override 
	public Patient noExistsUpdate(Patient patient) {
		Patient exists = getEntityDao().findByCusNoAndName(patient.getCusNo(), patient.getName());
		if(exists!=null){
			exists.setMobile(patient.getMobile());
			exists.setMedicareCard(patient.getMedicareCard());
			exists.setBirthday(patient.getBirthday());
			getEntityDao().update(exists);
			return exists;
		}else{
			getEntityDao().update(patient);
			return patient;
		}
	}

	@Override public List<Patient> findByCusNo(Long cusNo) {
		Map<String,Object> params = new HashMap<>();
		params.put("EQ_cusNo",cusNo);
		params.put("EQ_deleted",0);

		return getEntityDao().query(params,null);
	}

	private void fillIdCardInfo(Patient patient){
		IdCards.IdCardInfo idCardInfo = IdCards.parseIdCard(patient.getIdCard());
		patient.setBirthday(idCardInfo.getBirthday());
		patient.setGender("M".equals(idCardInfo.getGender())?1:0);
	}

	private void checkExists(Patient patient){
		Patient exists = getEntityDao().findByCusNoAndIdCard(patient.getCusNo(),patient.getIdCard());
		if(exists != null && (!exists.getId().equals(patient.getId()))){
			throw new BusinessException("需服务人员:[" + patient.getName() + "]已经存在");
		}
	}
	
	@Override
	public Patient findPatientByNameAndMobile(String name, String mobile) {
		Map<String, Object> params = new HashMap<>();
		params.put("EQ_name", name);
		params.put("EQ_mobile", mobile);
		List<Patient> patient = query(params, null);
		if(patient.size()>0){
			return patient.get(0);
		}else{
			return null;
		}
		
	}

	@Override
	public List<Patient> queryPatientByNameAndMobile(String name, String mobile) {
		Map<String, Object> params = new HashMap<>();
		params.put("EQ_name", name);
		params.put("EQ_mobile", mobile);
		List<Patient> patients = query(params, null);
		return patients;
	}

	@Override
	public Patient findByCusNoAndName(Long cusNo, String name) {
		return getEntityDao().findByCusNoAndName(cusNo, name);
	}
	
}
