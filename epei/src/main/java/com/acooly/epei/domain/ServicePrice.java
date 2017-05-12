package com.acooly.epei.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * 医院 Entity
 *
 * @author Acooly Code Generator
 * Date: 2015-10-19 18:47:37
 */
@Entity
@Table(name = "EP_SERVICE_PRICE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServicePrice extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 服务等级 */
	private String serviceGrade;
	/**服务价格**/
	private Float price;
	/**服务类型***/
	private String serviceType;

	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_EP_SERVICE_PRICE") })
	public Long getId(){
		return this.id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public String getServiceGrade() {
		return serviceGrade;
	}

	public void setServiceGrade(String serviceGrade) {
		this.serviceGrade = serviceGrade;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
