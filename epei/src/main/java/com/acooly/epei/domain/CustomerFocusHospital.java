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
 * 会员医院记录
 * @author Qimity
 * Date: 2016-09-02 11:38:09
 */
@Entity
@Table(name = "CUSTOMER_FOCUS_HOSPITAL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerFocusHospital extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/**会员ID*/
	private Long customerId;
	/**会员电话*/
	private String customerMobile;
	/**医院ID*/
	private Long hospitalId;
	/**医院名称*/
	private String hospitalName;
	/**科室ID*/
	private Long departmentId;
	/**科室名称*/
	private String departmentName;
	
	
	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_EP_CUSTOMER_FOCUS_HOSPITAL") })
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

	public Long getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}
	
	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}