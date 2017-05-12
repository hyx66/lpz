package com.acooly.epei.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.acooly.core.common.service.EntityServiceImpl;
import com.acooly.epei.service.CustomerAcctRecordsService;
import com.acooly.epei.dao.CustomerAcctRecordsDao;
import com.acooly.epei.domain.CustomerAcctRecords;

@Service("CustomerAcctRecordsService")
public class CustomerAcctRecordsServiceImpl extends EntityServiceImpl<CustomerAcctRecords, CustomerAcctRecordsDao> implements CustomerAcctRecordsService {
	@Autowired
	CustomerAcctRecordsDao customerAcctRecordsDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public CustomerAcctRecords getCustomerAcctRecordsByOutNo(String outNo){
		return customerAcctRecordsDao.findByOutNo(outNo);
	}
	
	@Override
	public List<CustomerAcctRecords> queryByCustomerId(Long customerId) {
		Map<String, Object> params = new HashMap<>();
		params.put("EQ_customerId", customerId);	
		List<CustomerAcctRecords> customerAcctRecordsList = query(params, null);
		return customerAcctRecordsList;	
	}
	
	@Override
	public BigDecimal findCustomerAcctRecordsByDateYmdAndDataTypeAndSpendChannelAndSpendStatus(
			String beforeDate, int dataType, int spendChannel, int spendStatus) {
		String sql = " select sum(recharge_amount) from CUSTOMER_ACCT_RECORDS where date_ymd='20160914' and data_type=2 and spend_channel=1 and spend_status=1 ";
		BigDecimal amount = jdbcTemplate.queryForObject(sql, BigDecimal.class);
		return amount;
	}

	@Override
	public BigDecimal findCustomerAcctRecordsByDateYmdAndDataTypeAndRechargeStatus(
			String beforeDate, int dataType, int rechargeStatus) {
		String sql = " select sum(recharge_amount) from CUSTOMER_ACCT_RECORDS where " +
				"date_ymd='"+beforeDate+"' and data_type="+dataType+" and recharge_status="+rechargeStatus+" ";
		BigDecimal amount = jdbcTemplate.queryForObject(sql, BigDecimal.class);
		return amount;
	}

	@Override
	public List<CustomerAcctRecords> queryForCount(String dateYmd, int spendStatus, int spendChannel, int spendType) {
		Map<String, Object> params = new HashMap<>();
		params.put("EQ_dateYmd", dateYmd);	
		params.put("EQ_spendStatus", spendStatus);	
		params.put("EQ_spendChannel", spendChannel);	
		params.put("EQ_spendType", spendType);	
		List<CustomerAcctRecords> customerAcctRecordsList = query(params, null);
		return customerAcctRecordsList;
	}
}
