package com.acooly.epei.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * 陪护陪诊人员 Entity
 *
 * @author Acooly Code Generator
 * Date: 2015-10-21 20:33:30
 */
@Entity
@Table(name = "EP_SERVICE_PERSON")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServicePerson extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 主键 */
	private Long id;
	/** 姓名 */
	private String name;
	/** 手机号 */
	private Long mobile;
	/** 身份证号 */
	private String idCard;
	/** 所属医院 */
	private String hospital;
	/** 医院id */
	private Long hospitalId;
	/** 服务类型 */
	private String serviceType;
	/** 员工编号*/
	private String empNo;
	/** 删除 */
	private int deleted;
	/**服务状态	0:未服务	1:服务中*/
	private int serviceState;
	/**入职时间*/
	private Date hireDate;

	@Id	
	@GeneratedValue
	public Long getId(){
		return this.id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public Long getMobile(){
		return this.mobile;
	}
	
	public void setMobile(Long mobile){
		this.mobile = mobile;
	}
	public String getIdCard(){
		return this.idCard;
	}
	
	public void setIdCard(String idCard){
		this.idCard = idCard;
	}
	public String getHospital(){
		return this.hospital;
	}
	
	public void setHospital(String hospital){
		this.hospital = hospital;
	}
	public Long getHospitalId(){
		return this.hospitalId;
	}
	
	public void setHospitalId(Long hospitalId){
		this.hospitalId = hospitalId;
	}
	public String getServiceType(){
		return this.serviceType;
	}
	
	public void setServiceType(String serviceType){
		this.serviceType = serviceType;
	}
	public int getDeleted(){
		return this.deleted;
	}
	
	public void setDeleted(int deleted){
		this.deleted = deleted;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public int getServiceState() {
		return serviceState;
	}
	public void setServiceState(int serviceState) {
		this.serviceState = serviceState;
	}
	
	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
