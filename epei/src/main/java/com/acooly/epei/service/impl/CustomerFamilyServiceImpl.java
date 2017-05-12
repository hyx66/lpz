package com.acooly.epei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acooly.core.common.exception.BusinessException;
import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.CustomerFamilyService;
import com.acooly.epei.dao.CustomerFamilyDao;
import com.acooly.epei.domain.Customer;
import com.acooly.epei.domain.CustomerFamily;

@Service("CustomerFamilyService")
public class CustomerFamilyServiceImpl extends EntityServiceImpl<CustomerFamily, CustomerFamilyDao> implements CustomerFamilyService {

	@Autowired
	private CustomerFamilyDao customerFamilyDao;
	@Override
	public List<CustomerFamily> findByCustomerUserName(String customerUserName) {
		return customerFamilyDao.findByCustomerUserName(customerUserName);
	}
	
	@Override
	public List<CustomerFamily> findByCustomerId(Long customerId) {
		return customerFamilyDao.findByCustomerId(customerId);
	}

	@Override
	public void customerFamilySave(CustomerFamily customerFamily) {
		CustomerFamily family = getEntityDao().findByIdCard(customerFamily.getIdCard());
		if(family!=null)throw new BusinessException("改家庭成员信息已存在");
		List<CustomerFamily> customerFamilyList =  getEntityDao().findByCustomerId(customerFamily.getCustomerId());
		if(customerFamilyList==null || customerFamilyList.size()<3){
			getEntityDao().save(customerFamily);
		}else{
			throw new BusinessException("同一个用户最多只能绑定三个家庭成员");
		}
	}

	@Override
	public CustomerFamily findByCustomerFamilyIdCard(String customerFamilyIdCard) {
		return customerFamilyDao.findByIdCard(customerFamilyIdCard);
	}

	@Override
	@Transactional
	public void createCustomerFamily(List<Customer> entities) {
		for(Customer customer : entities){
			if(customer.getVipId()!=null){
				addCustomerFamily(customer);
			}
		}	
	}

	@Override
	public void addCustomerFamily(Customer customer){
		System.out.println(getEntityDao().findByIdCard(customer.getIdCard()));
		if(getEntityDao().findByIdCard(customer.getIdCard())==null){
			CustomerFamily customerFamily = new CustomerFamily();
			customerFamily.setCustomerId(customer.getId());
			customerFamily.setCustomerUserName(customer.getUserName());
			customerFamily.setIdCard(customer.getIdCard());
			customerFamily.setName(customer.getName());
			customerFamily.setPhone(customer.getUserName());
			customerFamily.setRelationship("本人");
			save(customerFamily);
		}
	}
	
}