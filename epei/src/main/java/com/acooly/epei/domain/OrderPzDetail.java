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

/**
 * 陪诊详情 Entity
 *
 * @author Acooly Code Generator
 * Date: 2015-10-26 21:28:11
 */
@Entity
@Table(name = "EP_ORDER_PZ_DETAIL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderPzDetail extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 陪诊服务 */
	private String serviceName;
	/** 服务id */
	private Long serviceId;
	/** 陪诊人员 */
	private String servicePerson;
	/** 陪诊人员id */
	private Long servicePersonId;
	/**
	 * 备注
	 */
	private String remark;

	/** 删除 */
	private int deleted;

	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @org.hibernate.annotations.Parameter(name = "sequence",
			value = "SEQ_EP_PZ_DETAIL") })
	public Long getId(){
		return this.id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	public String getServiceName(){
		return this.serviceName;
	}
	
	public void setServiceName(String serviceName){
		this.serviceName = serviceName;
	}
	public Long getServiceId(){
		return this.serviceId;
	}
	
	public void setServiceId(Long serviceId){
		this.serviceId = serviceId;
	}
	public String getServicePerson(){
		return this.servicePerson;
	}
	
	public void setServicePerson(String servicePerson){
		this.servicePerson = servicePerson;
	}
	public Long getServicePersonId(){
		return this.servicePersonId;
	}
	
	public void setServicePersonId(Long servicePersonId){
		this.servicePersonId = servicePersonId;
	}

	public int getDeleted(){
		return this.deleted;
	}
	
	public void setDeleted(int deleted){
		this.deleted = deleted;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
