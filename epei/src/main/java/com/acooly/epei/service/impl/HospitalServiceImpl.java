package com.acooly.epei.service.impl;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.epei.dao.ImageDao;
import com.acooly.epei.domain.Customer;
import com.acooly.epei.domain.Image;
import com.acooly.epei.domain.ServiceTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.CustomerService;
import com.acooly.epei.service.HospitalService;
import com.acooly.epei.dao.HospitalDao;
import com.acooly.epei.domain.Hospital;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("hospitalService")
public class HospitalServiceImpl extends EntityServiceImpl<Hospital, HospitalDao> implements HospitalService {

	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private CustomerService customerService;
	
	@Override
	@Transactional
	public void save(Hospital o) throws BusinessException {
		try {
			//保存logo
			Image image = o.getHospitalLogo();
			if(image != null){
				imageDao.create(image);
				o.setLogoImageId(image.getId());
			}
			
			if(o.getPzCusNoOld() != null){
				//根据会员编号查询会员信息
				Customer pzCustomerOld = customerService.getCustomerByCusNo(o.getPzCusNoOld());
				pzCustomerOld.setCustomerHosId(Long.parseLong("-1"));
				pzCustomerOld.setAdminType(null);
				customerService.update(pzCustomerOld);
			}
			if(o.getPhCusNoOld() != null){
				//根据会员编号查询会员信息
				Customer phCustomerOld = customerService.getCustomerByCusNo(o.getPhCusNoOld());
				phCustomerOld.setCustomerHosId(Long.parseLong("-1"));
				phCustomerOld.setAdminType(null);
				customerService.update(phCustomerOld);
			}
			
			if(o.getPzCusNo() != null){
				//根据会员编号查询会员信息
				Customer pzCustomer = customerService.getCustomerByCusNo(o.getPzCusNo());
				o.setPzPrincipalName(pzCustomer.getName());			//陪诊负责人姓名
				o.setPzPrincipalMobile(pzCustomer.getMobile());		//陪诊负责人电话
				customerService.updateCustomerAdminTypeById(0,pzCustomer.getId());//0：陪诊管理员
				customerService.updateCustomerHosIdById(o.getId(),pzCustomer.getId());
			} 
			if(o.getPhCusNo() != null){
				//根据会员编号查询会员信息
				Customer phCustomer = customerService.getCustomerByCusNo(o.getPhCusNo());
				o.setPhPrincipalName(phCustomer.getName());			//陪护负责人姓名
				o.setPhPrincipalMobile(phCustomer.getMobile());		//陪护负责人电话
				customerService.updateCustomerAdminTypeById(1,phCustomer.getId());//1：陪护管理员
				customerService.updateCustomerHosIdById(o.getId(),phCustomer.getId());
			}
			getEntityDao().create(o);
		} catch (DataAccessException e) {
			throw new BusinessException(e.getMessage());
		}
	}

	@Override public List<Hospital> getAllNoDelByType(String serviceType) {
		serviceType = StringUtils.upperCase(serviceType);

		Map<String,Object> params = new HashMap<>();
		params.put("EQ_deleted",0);

		String[] serviceTypes = new String[2];
		serviceTypes[0] = serviceType;
		//如果查询的服务类型不是ALL 则需要将ALL包含进去
		if(!ServiceTypeEnum.ALL.code().equals(serviceType)){
			serviceTypes[1] = "ALL";
		}
		params.put("IN_serviceType",serviceTypes);

		List<Hospital> hospitals = query(params,null);

		return hospitals;
	}
	
	@Override
	public Hospital findHospitalByName(String name) {
		return getEntityDao().findByName(name);
	}

	@Override
	public List<Hospital> getAllNoDel() {
		Map<String,Object> params = new HashMap<>();
		params.put("EQ_deleted",0);
		List<Hospital> hospitals = query(params,null);
		return hospitals;
	}
	
	@Override
	public List<Hospital> getNoDelByHospitalIdAndType(Long hospitalId,String serviceType){
		serviceType = StringUtils.upperCase(serviceType);
		Map<String,Object> params = new HashMap<>();
		String[] serviceTypes = new String[2];
		serviceTypes[0] = serviceType;
		//如果查询的服务类型不是ALL 则需要将ALL包含进去
		if(!ServiceTypeEnum.ALL.code().equals(serviceType)){
			serviceTypes[1] = "ALL";
		}
		params.put("IN_serviceType",serviceTypes);
		params.put("EQ_deleted",0);
		params.put("EQ_id",hospitalId);
		List<Hospital> hospitals = query(params,null);
		return hospitals;
	}

	@Override
	public Hospital findById(Long id) {
		// TODO Auto-generated method stub
	 return getEntityDao().findById(id);
	}
	
}
