package com.acooly.epei.domain;

import java.util.Date;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.*;

import org.hibernate.annotations.Cache;

/**
 * EP_COUPON Entity
 *
 * @author Acooly Code Generator
 * Date: 2015-10-30 15:59:38
 */
@Entity
@Table(name = "EP_COUPON")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties({"customer"})
public class Coupon extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** ID */
	private Long id;
	/** 优惠券类型 */
	private String couponType;
	/** 优惠券使用目标 */
	private String useTarget;
	/** 是否已使用 */
	private int used = 0;
	/** 使用时间 */
	private Date useTime;
	/** 面额 */
	private int denomination;
	/** 到期日 */
	private Date expiryDate;
    /**
     * 所属会员
     */
    private Customer customer;

	/**
	 * 使用该优惠券的订单编号
	 */
	private String orderNo;


    private String customerUserName;

	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence",
			value = "SEQ_EP_COUPON") })
	public Long getId(){
		return this.id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	public String getCouponType(){
		return this.couponType;
	}
	
	public void setCouponType(String couponType){
		this.couponType = couponType;
	}
	public String getUseTarget(){
		return this.useTarget;
	}
	
	public void setUseTarget(String useTarget){
		this.useTarget = useTarget;
	}

	public int getUsed(){
		return this.used;
	}
	
	public void setUsed(int used){
		this.used = used;
	}
	public Date getUseTime(){
		return this.useTime;
	}
	
	public void setUseTime(Date useTime){
		this.useTime = useTime;
	}
	public int getDenomination(){
		return this.denomination;
	}
	
	public void setDenomination(int denomination){
		this.denomination = denomination;
	}
	public Date getExpiryDate(){
		return this.expiryDate;
	}
	
	public void setExpiryDate(Date expiryDate){
		this.expiryDate = expiryDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_ID")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
