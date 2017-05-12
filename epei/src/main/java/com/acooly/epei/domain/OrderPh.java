package com.acooly.epei.domain;

import java.util.Date;
import javax.persistence.*;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 陪护订单 Entity
 *
 * @author Acooly Code Generator
 * Date: 2015-10-26 21:28:11
 */
@Entity
@Table(name = "EP_ORDER_PH")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DiscriminatorValue("PH")
public class OrderPh extends OrderBase  {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 陪护人id */
	private Long servicePersonId;
	/** 开始时间 */
	private Date startTime;
	/** 结束时间 */
	private Date endTime;
	/** 持续时长 */
	private int duration;
	/** 陪护人 */
	private String servicePerson;
	
	public Long getServicePersonId(){
		return this.servicePersonId;
	}
	
	public void setServicePersonId(Long servicePersonId){
		this.servicePersonId = servicePersonId;
	}

	public Date getStartTime(){
		return this.startTime;
	}
	
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	public Date getEndTime(){
		return this.endTime;
	}
	
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}
	public int getDuration(){
		return this.duration;
	}
	
	public void setDuration(int duration){
		this.duration = duration;
	}
	public String getServicePerson(){
		return this.servicePerson;
	}
	
	public void setServicePerson(String servicePerson){
		this.servicePerson = servicePerson;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
