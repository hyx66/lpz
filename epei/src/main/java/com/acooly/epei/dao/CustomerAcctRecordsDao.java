package com.acooly.epei.dao;

import com.acooly.core.common.dao.jpa.EntityJpaDao;
import com.acooly.epei.domain.CustomerAcctRecords;

public interface CustomerAcctRecordsDao extends EntityJpaDao<CustomerAcctRecords, Long> {

	CustomerAcctRecords findByOutNo(String outNo);

}
