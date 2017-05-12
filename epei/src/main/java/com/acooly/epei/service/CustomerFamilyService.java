package com.acooly.epei.service;

import java.util.List;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.Customer;
import com.acooly.epei.domain.CustomerFamily;

public interface CustomerFamilyService extends EntityService<CustomerFamily> {

	List<CustomerFamily> findByCustomerUserName(String customerUserName);

	List<CustomerFamily> findByCustomerId(Long customerId);

	void customerFamilySave(CustomerFamily customerFamily);

	CustomerFamily findByCustomerFamilyIdCard(String customerFamilyIdCard);

	void createCustomerFamily(List<Customer> entities);

	void addCustomerFamily(Customer customer);

}
