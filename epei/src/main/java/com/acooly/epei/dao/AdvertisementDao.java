package com.acooly.epei.dao;

import java.util.List;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.Advertisement;
 /** 
 * Date： 2017-3-13 下午6:11:18
 * @author acooly code Generator
 */

public interface AdvertisementDao extends EntityJpaDao<Advertisement, Long>{
	List<Advertisement> findByDeleted(int deleted);

	Advertisement findById(Long id);
}
