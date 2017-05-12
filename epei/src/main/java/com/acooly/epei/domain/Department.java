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
 * 医院科室 Entity
 *
 * @author Acooly Code Generator
 * Date: 2015-10-19 18:43:56
 */
@Entity
@Table(name = "EP_DEPARTMENT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Department extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 主键 */
	private Long id;
	/**医院ID*/
	private Long hospitalId;
	/**医院名称*/
	private String hospitalName;
	/** 科室 */
	private String name;
	/** 删除 */
	private int deleted;
	/**陪护价格*/
	private BigDecimal phServicePrice;

	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_EP_DEPARTMENT") })
	public Long getId(){
		return this.id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public Long getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public int getDeleted(){
		return this.deleted;
	}
	
	public void setDeleted(int deleted){
		this.deleted = deleted;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public BigDecimal getPhServicePrice() {
		return phServicePrice;
	}

	public void setPhServicePrice(BigDecimal phServicePrice) {
		this.phServicePrice = phServicePrice;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
