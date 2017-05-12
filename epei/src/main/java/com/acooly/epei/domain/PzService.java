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
 * 陪诊服务 Entity
 *
 * @author Acooly Code Generator
 * Date: 2015-10-21 20:20:44
 */
@Entity
@Table(name = "EP_PZ_SERVICE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PzService extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 主键 */
	private Long id;
	/** 服务项 */
	private String service;
	/** 删除 */
	private int deleted;

	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_EP_PZ_SERVICE") })
	public Long getId(){
		return this.id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	public String getService(){
		return this.service;
	}
	
	public void setService(String service){
		this.service = service;
	}
	public int getDeleted(){
		return this.deleted;
	}
	
	public void setDeleted(int deleted){
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
