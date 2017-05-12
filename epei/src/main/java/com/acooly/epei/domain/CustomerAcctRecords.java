package com.acooly.epei.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 账务明细表
 * @author Qimity
 * Date: 2016-09-01 17:28:09
 */
@Entity
@Table(name = "CUSTOMER_ACCT_RECORDS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerAcctRecords extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/**客户ID*/
	private Long customerId;
	/**客户手机号*/
	private String customerMobile;
	/**客户姓名*/
	private String customerName;
	/**标题     在线充值、线下充值、线下补录*/
	private String title;
	/**流水号*/
	private String recordsNo;
	/**外部订单号*/
	private String outNo;
	/**充值金额*/
	private BigDecimal rechargeAmount;
	/**数据类型   1：充值/余额增加      2：消费/余额减少*/
	private int dataType;
	/**充值渠道   1:在线充值     2：线下充值      0：无效（消费记录不需要这条信息，用0占位）*/
	private int rechargeChannle;
	/**充值状态     1:充值成功     2:充值失败       0：无效（消费记录不需要这条信息，用0占位）*/
	private int rechargeStatus; 
	/**消费状态   1：消费成功     2：消费失败      0：无效（充值记录不需要这条信息，用0占位）*/
	private int spendStatus;
	/**消费渠道     1：在线支付    2：线下支付      0：无效（充值记录不需要这条信息，用0占位）*/
	private int spendChannel;
	/**消费类型       1：陪护        2：陪诊        3：提现                   0：无效（充值记录不需要这条信息，用0占位） */
	private int spendType;
	/**消费金额*/
	private BigDecimal spendAmount;
	/**余额*/
	private BigDecimal balance;
	/**管理员ID*/
	private Long userId;
	/**管理员*/
	private String userName;
	/**备注*/
	private String memo;
	/**DAC*/
	private String dac = "dac";
	/**账期*/
	private String dateYmd;
	
	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_EP_CUSTOMER_ACCT_RECORDS") })
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCustomerId(){
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRecordsNo() {
		return recordsNo;
	}

	public void setRecordsNo(String recordsNo) {
		this.recordsNo = recordsNo;
	}

	public String getOutNo() {
		return outNo;
	}

	public void setOutNo(String outNo) {
		this.outNo = outNo;
	}

	public BigDecimal getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(BigDecimal rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public int getRechargeChannle() {
		return rechargeChannle;
	}

	public void setRechargeChannle(int rechargeChannle) {
		this.rechargeChannle = rechargeChannle;
	}

	public int getRechargeStatus() {
		return rechargeStatus;
	}

	public void setRechargeStatus(int rechargeStatus) {
		this.rechargeStatus = rechargeStatus;
	}

	public BigDecimal getSpendAmount() {
		return spendAmount;
	}

	public void setSpendAmount(BigDecimal spendAmount) {
		this.spendAmount = spendAmount;
	}

	public int getSpendType() {
		return spendType;
	}

	public void setSpendType(int spendType) {
		this.spendType = spendType;
	}

	public int getSpendChannel() {
		return spendChannel;
	}

	public void setSpendChannel(int spendChannel) {
		this.spendChannel = spendChannel;
	}

	public int getSpendStatus() {
		return spendStatus;
	}

	public void setSpendStatus(int spendStatus) {
		this.spendStatus = spendStatus;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getDac() {
		return dac;
	}

	public void setDac(String dac) {
		this.dac = dac;
	}

	public String getDateYmd() {
		return dateYmd;
	}

	public void setDateYmd(String dateYmd) {
		this.dateYmd = dateYmd;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}