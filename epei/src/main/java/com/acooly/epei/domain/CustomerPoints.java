package com.acooly.epei.domain;

import java.util.Date;

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
 * 会员积分表
 * @author Qimity
 * Date: 2016-09-01 10:50:09
 */
@Entity
@Table(name = "CUSTOMER_POINTS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerPoints extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/**客户ID*/
	private Long customerId;
	/**能力值*/
	private int capacityValue;
	/**积分数*/
	private String points;
	/**变更时间*/
	private Date modifyTime;
	/**DAC*/
	private String dac;
	/**状态     0：启用       1：禁用*/
	private int status;
	
	
	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_EP_CUSTOMER_POINTS") })
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

	public int getCapacityValue() {
		return capacityValue;
	}

	public void setCapacityValue(int capacityValue) {
		this.capacityValue = capacityValue;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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