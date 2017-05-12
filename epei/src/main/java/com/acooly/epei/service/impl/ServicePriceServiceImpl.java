package com.acooly.epei.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.dao.HospitalDao;
import com.acooly.epei.dao.ServicePriceDao;
import com.acooly.epei.domain.Hospital;
import com.acooly.epei.domain.ServicePrice;
import com.acooly.epei.service.ServicePriceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("servicePriceService")
public class ServicePriceServiceImpl extends EntityServiceImpl<ServicePrice,
		ServicePriceDao> implements ServicePriceService{

	@Autowired
	private HospitalDao hospitalDao;

	@Override
	public List<ServicePrice> getAllServiceByHospitalAndType(Long hospitalId, String type) {
		List<ServicePrice> prices = new ArrayList<>();
		Hospital  hospital = hospitalDao.get(hospitalId);
		if(hospital != null){
			for(ServicePrice price : hospital.getPrices()){
				if(StringUtils.upperCase(type).equals(price.getServiceType())){
					prices.add(price);
				}
			}
		}

		return prices;
	}
	
	@Override
	public ServicePrice findServicePriceByHospitalIdAndServiceGradeAndServiceType(
			Long hospitalId, String serviceGrade, String serviceType) {
		ServicePrice sp = new ServicePrice();
		Hospital  hospital = hospitalDao.get(hospitalId);
		if(hospital != null){
			for(ServicePrice price : hospital.getPrices()){
				if(StringUtils.upperCase(serviceType).equals(price.getServiceType())
						&& serviceGrade.equals(serviceGrade)){
					sp = price;
				}
			}
		}
		return sp;
	}
}
