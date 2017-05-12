package com.acooly.epei.service.impl;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.dao.CustomerNODao;
import com.acooly.epei.domain.Customer;
import com.acooly.epei.domain.CustomerNO;
import com.acooly.epei.domain.RegOriginEnum;
import com.acooly.epei.service.CustomerNOService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 会员 Service
 *
 * Date: 2015-10-19 18:47:37
 *
 * @author Acooly Code Generator
 *
 */
@Service
public class CustomerNOServiceImpl extends EntityServiceImpl<CustomerNO, CustomerNODao> implements CustomerNOService{


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String genarateCusNo(Customer customer) {

        String finalCusNo =  RegOriginEnum.getByCode(customer.getRegOrigin()).ordinal()+1+"";

        finalCusNo +=DateFormatUtils.format(new Date(),"yyMMdd");

        CustomerNO cusNO = new CustomerNO();
        save(cusNO);

        Long cusNOId = cusNO.getId();
        String cusNOIdStr  = String.valueOf(cusNOId);

        finalCusNo += cusNOIdStr.substring(cusNOIdStr.length() - 5);

        customer.setCusNo(Long.parseLong(finalCusNo));

        return finalCusNo;
    }
}
