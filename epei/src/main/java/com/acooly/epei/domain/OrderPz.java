package com.acooly.epei.domain;

import javax.persistence.*;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

/**
 * 陪诊订单 Entity
 *
 * @author Acooly Code Generator
 * Date: 2015-10-26 21:28:11
 */
@Entity
@Table(name = "EP_ORDER_PZ")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DiscriminatorValue("PZ")
public class OrderPz extends OrderBase {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 陪诊模式 */
	private String pzMode;
	/** 就诊科室 */
	private String department;
	/** 接待地点 */
	private String receptionPosition;
	/**是否医保报销*/
	private boolean ybbx =false;
	/**服务人员*/
	private String servicePerson;
	/**挂号方式    1：专家                0：普通*/
	private String registrationType;
	/***陪诊服务详情*/
	private List<OrderPzDetail> details;
	
	public String getPzMode(){
		return this.pzMode;
	}
	
	public void setPzMode(String pzMode){
		this.pzMode = pzMode;
	}
	public String getDepartment(){
		return this.department;
	}
	
	public void setDepartment(String department){
		this.department = department;
	}
	public String getReceptionPosition(){
		return this.receptionPosition;
	}
	
	public void setReceptionPosition(String receptionPosition){
		this.receptionPosition = receptionPosition;
	}

	public boolean getYbbx() {
		return ybbx;
	}

	public void setYbbx(boolean ybbx) {
		this.ybbx = ybbx;
	}
	

	public String getServicePerson() {
		return servicePerson;
	}

	public void setServicePerson(String servicePerson) {
		this.servicePerson = servicePerson;
	}
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="ORDER_ID")
	public List<OrderPzDetail> getDetails() {
		return details;
	}

	public void setDetails(List<OrderPzDetail> details) {
		this.details = details;
	}
	

	public String getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
