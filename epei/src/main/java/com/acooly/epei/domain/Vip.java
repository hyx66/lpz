package com.acooly.epei.domain;

import javax.persistence.*;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * vip
 */
@Entity
@Table(name = "VIP")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Vip extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 名称 */
	private String name;
	/** 级别   1代表vip等级1，2代表vip等级2，以此类推，数值越大，vip级别越高 */
	private Integer grade;
	/** 包含的优惠        格式为：{服务项目代号：免费服务次数}*/
	private String discount;

	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_VIP") })
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

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
