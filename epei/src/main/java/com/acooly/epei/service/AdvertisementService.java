package com.acooly.epei.service;

import java.util.List;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.Advertisement;
 /** 
 * Date： 2017-3-13 下午6:16:10
 * @author acooly code Generator
 */
public interface AdvertisementService extends EntityService<Advertisement>{
	List<Advertisement> findByDeleted(int deleted);

	Advertisement findById(Long id);

	List<Advertisement> findByDeletedReorderByPriority();
}
