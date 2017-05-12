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
 * 病患 Entity
 *
 * @author Acooly Code Generator
 * Date: 2015-10-21 20:40:39
 */
@Entity
@Table(name = "EP_PATIENT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Patient extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 主键 */
	private Long id;
	/** 所属会员姓名 */
	private String cusName;
	/** 所属会员编号 */
	private Long cusNo;
	/** 所属会员id */
	private Long cusId;
	/** 姓名 */
	private String name;
	/** 手机号 */
	private String mobile;
	/** 身份证号 */
	private String idCard;
	/** 生日 */
	private String birthday;
	/** 性别 男 1 女 0 */
	private int gender = 1;
	/** 医保卡号 */
	private String medicareCard;
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
	public String getCusName(){
		return this.cusName;
	}
	
	public void setCusName(String cusName){
		this.cusName = cusName;
	}
	public Long getCusNo(){
		return this.cusNo;
	}
	
	public void setCusNo(Long cusNo){
		this.cusNo = cusNo;
	}
	public Long getCusId(){
		return this.cusId;
	}
	
	public void setCusId(Long cusId){
		this.cusId = cusId;
	}
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public String getMobile(){
		return this.mobile;
	}
	
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
	public String getIdCard(){
		return this.idCard;
	}
	
	public void setIdCard(String idCard){
		this.idCard = idCard;
	}
	public String getBirthday(){
		return this.birthday;
	}
	
	public void setBirthday(String birthday){
		this.birthday = birthday;
	}
	public int getGender(){
		return this.gender;
	}
	
	public void setGender(int gender){
		this.gender = gender;
	}
	public String getMedicareCard(){
		return this.medicareCard;
	}
	
	public void setMedicareCard(String medicareCard){
		this.medicareCard = medicareCard;
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
