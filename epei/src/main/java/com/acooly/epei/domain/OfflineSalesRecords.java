package com.acooly.epei.domain;

import java.math.BigDecimal;
import java.util.Date;

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
 * 线下消费流水表
 * @author Qimity
 * Date: 2016-09-01 15:53:09
 */
@Entity
@Table(name = "OFFLINE_SALES_RECORDS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OfflineSalesRecords extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/**客户ID*/
	private Long customerId;
	/**客户手机号*/
	private String customerMobile;
	/**客户姓名*/
	private String customerName;
	/**服务人员ID*/
	private Long servicePersonId;
	/**服务人员姓名*/
	private String servicePersonName;
	/**服务人员手机号*/
	private String servicePersonMobile;
	/**收款人ID*/
	private Long payeeId;
	/**收款人*/
	private String payeeName;
	/**服务项目ID*/
	private Long serviceItemsId;
	/**服务项目名称*/
	private String serviceItemsName;
	/**金额*/
	private BigDecimal amount;
	/**小/支票号*/
	private String ticketNo;
	/**所属医院*/
	private String hospital;
	/**所属医院ID*/
	private Long hospitalId;
	/**科室ID*/
	private Long departId;
	/**科室名称*/
	private String departName;
	/**服务开始日期*/
	private Date serviceYmd;
	/**服务结束日期*/
	private Date serviceEndYmd;
	/**数据类型  0:陪诊   1：陪护*/
	private int dataType;
	/**支付类型  0：现金     1：POS机      2：支票*/
	private int payType;
	/**数据状态*/
	private int status;
	/**账期*/
	private String dataYmd;
	/**外部单号*/
	private String outNo;
	/**备注*/
	private String memo;
	/**录入人员ID*/
	private Long createId;
	/**录入人员*/
	private String createName;
	/**修改人员ID*/
	private Long updateId;
	/**修改人员*/
	private String updateName;
	/**流水号*/
	private String recordsNo;
	/**服务总天数*/
	private int serviceDays;
	/**床位*/
	private String customerBed;
	/**年龄*/
	private Integer customerAge;
	/**性别*/
	private String customerSex;
	/**病症*/
	private String disease;
	/**信息来源    1：线下补录*/
	private String sources;

	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_EP_OFFLINE_SALES_RECORDS") })
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCustomerId(){
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getServicePersonId() {
		return servicePersonId;
	}

	public void setServicePersonId(Long servicePersonId) {
		this.servicePersonId = servicePersonId;
	}

	public String getServicePersonName() {
		return servicePersonName;
	}

	public void setServicePersonName(String servicePersonName) {
		this.servicePersonName = servicePersonName;
	}
	
	public String getServicePersonMobile() {
		return servicePersonMobile;
	}

	public void setServicePersonMobile(String servicePersonMobile) {
		this.servicePersonMobile = servicePersonMobile;
	}

	public Long getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(Long payeeId) {
		this.payeeId = payeeId;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public Long getServiceItemsId() {
		return serviceItemsId;
	}

	public void setServiceItemsId(Long serviceItemsId) {
		this.serviceItemsId = serviceItemsId;
	}

	public String getServiceItemsName() {
		return serviceItemsName;
	}

	public void setServiceItemsName(String serviceItemsName) {
		this.serviceItemsName = serviceItemsName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public Long getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public Date getServiceYmd() {
		return serviceYmd;
	}

	public void setServiceYmd(Date serviceYmd) {
		this.serviceYmd = serviceYmd;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDataYmd() {
		return dataYmd;
	}

	public void setDataYmd(String dataYmd) {
		this.dataYmd = dataYmd;
	}
	
	public Date getServiceEndYmd() {
		return serviceEndYmd;
	}

	public void setServiceEndYmd(Date serviceEndYmd) {
		this.serviceEndYmd = serviceEndYmd;
	}

	public String getOutNo() {
		return outNo;
	}

	public void setOutNo(String outNo) {
		this.outNo = outNo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getRecordsNo() {
		return recordsNo;
	}

	public void setRecordsNo(String recordsNo) {
		this.recordsNo = recordsNo;
	}
	
	public int getServiceDays() {
		return serviceDays;
	}

	public void setServiceDays(int serviceDays) {
		this.serviceDays = serviceDays;
	}

	public String getCustomerBed() {
		return customerBed;
	}

	public void setCustomerBed(String customerBed) {
		this.customerBed = customerBed;
	}

	public Integer getCustomerAge() {
		return customerAge;
	}

	public void setCustomerAge(Integer customerAge) {
		this.customerAge = customerAge;
	}

	public String getCustomerSex() {
		return customerSex;
	}

	public void setCustomerSex(String customerSex) {
		this.customerSex = customerSex;
	}

	
	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public String getSources() {
		return sources;
	}

	public void setSources(String sources) {
		this.sources = sources;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}