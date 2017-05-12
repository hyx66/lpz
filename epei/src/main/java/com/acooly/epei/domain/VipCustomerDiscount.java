package com.acooly.epei.domain;

import javax.persistence.*;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * vip会员剩余优惠项目
 */
@Entity
@Table(name = "VIP_CUSTOMER_DISCOUNT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VipCustomerDiscount extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 会员ID */
	private Long customerId;
	/**会员账号*/
	private String customerUserName;
	/**VIP*/
	private Long vipId;
	/** 拥有的优惠项目    格式为：{服务项目代号：免费服务次数} */
	private String discount;

	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_VIP_CUSTOMER_DISCOUNT") })
	public Long getId(){
		return this.id;
	}

	public void setId(Long id){
		this.id = id;
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

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public Long getVipId() {
		return vipId;
	}

	public void setVipId(Long vipId) {
		this.vipId = vipId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
