package com.acooly.epei.domain;

import javax.persistence.*;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * vip会员家庭成员
 */
@Entity
@Table(name = "CUSTOMER_FAMILY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerFamily extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 姓名 */
	private String name;
	/** 电话 */
	private String phone;
	/** 身份证号    验证家庭成员唯一性的唯一标识 */
	private String idCard;
	/**与持卡人的关系*/
	private String relationship;
	/**会员ID*/
	private Long customerId;
	/**会员账号*/
	private String customerUserName;
	

	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_CUSTOMER_FAMILY") })
	public Long getId(){
		return this.id;
	}

	public void setId(Long id){
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerUserName() {
		return customerUserName;
	}

	public void setCustomerUserName(String customerUserName) {
		this.customerUserName = customerUserName;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
