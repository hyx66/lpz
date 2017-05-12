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
 * 会员注册报表
 * @author Qimity
 * Date: 2016-09-02 11:10:09
 */
@Entity
@Table(name = "CUSTOMER_REG_REPORTS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerRegReports extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/**数据日期*/
	private String dataYmd;
	/**统计类型*/
	private int type;
	/**会员数*/
	private int num;
	
	
	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_EP_CUSTOMER_REG_REPORTS") })
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}