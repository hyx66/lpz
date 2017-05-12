package com.acooly.epei.service;

import java.math.BigDecimal;
import java.util.List;

import com.acooly.core.common.service.EntityService;
import com.acooly.epei.domain.CustomerAcctRecords;

public interface CustomerAcctRecordsService extends EntityService<CustomerAcctRecords> {

	CustomerAcctRecords getCustomerAcctRecordsByOutNo(String outNo);

	List<CustomerAcctRecords> queryByCustomerId(Long customerId);
	
	BigDecimal findCustomerAcctRecordsByDateYmdAndDataTypeAndSpendChannelAndSpendStatus(
			String beforeDate, int dataType, int spendChannel, int spendStatus);

	BigDecimal findCustomerAcctRecordsByDateYmdAndDataTypeAndRechargeStatus(
			String beforeDate, int dataType, int rechargeStatus);

	List<CustomerAcctRecords> queryForCount(String dateYmd, int spendStatus, int spendChannel, int spendType);
}
