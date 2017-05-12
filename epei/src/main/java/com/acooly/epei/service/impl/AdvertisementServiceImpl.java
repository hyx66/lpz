package com.acooly.epei.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.dao.AdvertisementDao;
import com.acooly.epei.domain.Advertisement;
import com.acooly.epei.service.AdvertisementService;

/**
 * Date： 2017-3-13 下午6:24:20
 * 
 * @author acooly code Generator
 */
@Service("advertisementService")
public class AdvertisementServiceImpl extends
		EntityServiceImpl<Advertisement, AdvertisementDao> implements AdvertisementService {
	
	@Override
	public List<Advertisement> findByDeleted(int deleted) {
		return getEntityDao().findByDeleted(deleted);
	}

	@Override
	public Advertisement findById(Long id) {
		return getEntityDao().findById(id);
	}

	@Override
	public List<Advertisement> findByDeletedReorderByPriority() {
		Map<String, Object> params = new HashMap<>();
		params.put("EQ_deleted", 0);	
		Map<String,Boolean> sortMap = new HashMap<String,Boolean>();
    	sortMap.put("priority", false);
		List<Advertisement> advertisementList = query(params, sortMap);
		return advertisementList;
	}

}
