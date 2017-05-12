package com.acooly.epei.domain;

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
 * 会员账务表
 * @author Qimity
 * Date: 2016-09-01 10:40:09
 */
@Entity
@Table(name = "CUSTOMER_ACCT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerAcct extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/**客户ID*/
	private Long customerId;
	/**充值总额 ：每次有钱进入用户账户时此参数就增加相应的金额，这个参数是用来记录用户历史入账总和*/
	private String totalAmount;
	/**财务余额*/
	private String balance;
	/**可用金额*/
	private String availableAmount;
	/**冻结金额*/
	private String freezAmount;
	/**DAC*/
	private String dac = "dac";
	/**状态   0：启用    1：禁用*/
	private int status;
	
	
	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_EP_CUSTOMER_ACCT") })
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
	
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(String availableAmount) {
		this.availableAmount = availableAmount;
	}

	public String getFreezAmount() {
		return freezAmount;
	}

	public void setFreezAmount(String freezAmount) {
		this.freezAmount = freezAmount;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDac() {
		return dac;
	}

	public void setDac(String dac) {
		this.dac = dac;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}