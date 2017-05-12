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
 * 会员消费报表
 * @author Qimity
 * Date: 2016-09-02 11:24:09
 */
@Entity
@Table(name = "CUSTOMER_SPEND_REPORTS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerSpendReports extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/**消费类型    1：陪护*/
	private int spendType;
	/**账期   所统计的消费记录上面的日期，而不是产生此对象的日期*/
	private String dataYmd;
	/**金额*/
	private BigDecimal amount;
	
	
	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_EP_CUSTOMER_SPEND_REPORTS") })
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getDataYmd() {
		return dataYmd;
	}

	public void setDataYmd(String dataYmd) {
		this.dataYmd = dataYmd;
	}

	public int getSpendType() {
		return spendType;
	}

	public void setSpendType(int spendType) {
		this.spendType = spendType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}