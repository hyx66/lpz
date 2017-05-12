package com.acooly.epei.service;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.Customer;
import com.acooly.epei.domain.CustomerNO;

/**
 * 会员 Service
 *
 * Date: 2015-10-19 18:47:37
 *
 * @author Acooly Code Generator
 *
 */
public interface CustomerNOService extends EntityService<CustomerNO> {

    /**
     * 生成会员编号
     * @param customer
     * @return
     */
    String genarateCusNo(Customer customer);

    
}
