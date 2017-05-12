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
 * 会员积分流水表
 * @author Qimity
 * Date: 2016-09-02 10:16:09
 */
@Entity
@Table(name = "CUSTOMER_POINTS_RECORDS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerPointsRecords extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/**会员ID*/
	private Long customerId;
	/**会员名字*/
	private String customerName;
	/**会员电话*/
	private String customerMobile;
	/**标题*/
	private String title;
	/**流水号*/
	private String recordsNo;
	/**外部订单号*/
	private String outNo;
	/**积分数*/
	private int points;
	/**消费类型    1：收益      2：支出*/
	private int spendType;
	/**数据类型*/
	private int dataType;
	/**备注*/
	private String memo;
	/**DAC*/
	private String dac;
	
	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_EP_CUSTOMER_POINTS_RECORDS") })
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
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

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getSpendType() {
		return spendType;
	}

	public void setSpendType(int spendType) {
		this.spendType = spendType;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
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

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}