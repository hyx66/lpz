package com.acooly.epei.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.*;

import org.hibernate.annotations.Cache;

/**
 * 订单基础信息 Entity
 *
 * @author Acooly Code Generator Date: 2015-10-26 21:28:11
 */
@Entity
@Table(name = "EP_ORDER_BASE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "ORDER_TYPE", discriminatorType = DiscriminatorType.STRING)
public class OrderBase extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 订单编号 */
	private String orderNo;
	/** 病患姓名 */
	private String patientName;
	/** 病患联系电话 */
	private String patientMobile;
	/** 病患身份证号 */
	private String patientIdCard;
	/** 会员编号 */
	private Long cusNo;
	/** 就诊医院名 */
	private String hospitalName;
	/** 预约时间 */
	private Date orderTime;
	/** 订单状态 */
	private String state = OrderState.ORDERED.name();
	/** 预约来源 */
	private String orign;
	/** 病患 */
	private Patient patient;
	/** 预约医院 */
	private Hospital hospital;
	/** 服务人员ID */
	private Long servicePersonId;
	/** 服务人员姓名 */
	private String servicePersonName;
	/** 服务人员电话 */
	private String servicePersonMobile;
	/** 科室 */
	private String departmentName;
	/** 床位 */
	private String bed;
	/** 管理员姓名(微信医院管理员、后台管理员录入该订单的管理员姓名) */
	private String adminName;
	/** 录入人员ID(微信医院管理员、后台管理员录入该订单的管理员ID) */
	private Long adminId;
	/** 预约方式 0:电话预约 1:主动预约 */
	private Integer appointmentType;
	/** 选择的收费标准 */
	private ServicePrice servicePrice;
	/** 付款方式 */
	private String payMode = PayMode.OFFLINE.code();
	/** 医院ID */
	private Long hospitalId;
	/** 价格 */
	private Float price;
	/** 服务价格ID */
	private Long servicePriceId;
	/** 服务级别 */
	private String serviceGrade;
	/** 备注 */
	private String note;
	/** 记录一天中的时间段 AM:上午 PM：下午 */
	private String timeInOneDay;
	/** 会员ID */
	private Long customerId;
	/** 收款人ID */
	private Long payeeId;
	/** 收款人姓名 */
	private String payeeName;
	/** 金额  应支付的费用*/
	private BigDecimal amount;
	/** 小票号 */
	private String ticketNo;
	/** 科室ID */
	private Long departmentId;
	/** 数据类型 0默认值 2:陪诊 1：陪护 */
	private int dataType;
	/** 支付类型 5：微信扫码支付 4:微信-在线支付（特别注意：此支付方式指的是在平台调用微信接口付款，不包括线下的微信转账、付款） 3:现金 2：支票 1：POS机*/
	private int payType;
	/** 支付状态 0：未支付 1：已支付 */
	private Integer payStatus;
	/** 账期-订单完成的时间 */
	private String dataYmd;
	/** 外部单号 */
	private String outNo;
	/** 修改人员ID */
	private Long updateId;
	/** 修改人员 */
	private String updateName;
	/** 患者年龄 */
	private int customerAge;
	/** 患者性别 */
	private String customerSex;
	/** 病症 */
	private String disease;
	/** 陪护服务价格 */
	private BigDecimal phServicePrice;
	/** 定价方式 1：系统定价（根据医院设置的收费标准来确定） 2：工作人员根据患者病情确认价格 */
	private Integer pricingType;
	/** 续单单号每个订单只能对应其创建一条有效的续单记录 原订单默认值为空，续单的记录填写此记录 */
	private Long originalOrderId;
	/** 原订单记录中保存此信息，创建的续单记录上的服务结束时间 */
	private Date finallyEndDate;
	/** 结算金额，找零，退款金额 */
	private BigDecimal refundAmount;
	/**结算方式（退款方式） 1：线下结算（现金退款） 2：系统结算（除去已消费部分，用户订单剩余金额即为账户冻结金额减少数值，可用金额增加相应数值）*/
	private Integer refundType;
	/** 实际服务天数 */
	private Integer actualDuration;
	/** 订单结算后剩余费用（用户在这个订单上实际花费的钱） */
	private BigDecimal actualAmount;
	/** 订单客服受理状态{1:未受理; 2:受理中   3：受理结束} */
	private Integer acceptance = 1;
	/**受理时间*/
	private Date acceptTime;
	/**受理人ID*/
	private Long acceptUserId;
	/**受理人姓名*/
	private String acceptUserName;
	/**是否使用VIP免费服务项目特权     0未使用      1：使用*/
	private Integer freeItem;
	/**使用vip会员的家庭成员成员身份证号*/
	private String customerFamilyIdCard;

	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = {
			@org.hibernate.annotations.Parameter(name = "sequence", value = "SEQ_EP_ORDER") })
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPatientName() {
		return this.patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientIdCard() {
		return this.patientIdCard;
	}

	public void setPatientIdCard(String patientIdCard) {
		this.patientIdCard = patientIdCard;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PATIENT_ID")
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Long getCusNo() {
		return this.cusNo;
	}

	public void setCusNo(Long cusNo) {
		this.cusNo = cusNo;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	@ManyToOne
	@JoinColumn(name = "HOSPITAL_ID")
	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOrign() {
		return orign;
	}

	public void setOrign(String orign) {
		this.orign = orign;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "service_price_id")
	public ServicePrice getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(ServicePrice servicePrice) {
		this.servicePrice = servicePrice;
	}

	@Transient
	public Long getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}

	@Transient
	public Long getServicePriceId() {
		return servicePriceId;
	}

	public void setServicePriceId(Long servicePriceId) {
		this.servicePriceId = servicePriceId;
	}

	public void copyPatientProp() {
		if (this.patient != null) {
			this.patientIdCard = patient.getIdCard();
			this.patientName = patient.getName();
			this.patientMobile = patient.getMobile();
		}
	}

	public String getPatientMobile() {
		return patientMobile;
	}

	public void setPatientMobile(String patientMobile) {
		this.patientMobile = patientMobile;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getServiceGrade() {
		return serviceGrade;
	}

	public void setServiceGrade(String serviceGrade) {
		this.serviceGrade = serviceGrade;
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

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getBed() {
		return bed;
	}

	public void setBed(String bed) {
		this.bed = bed;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public Integer getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(Integer appointmentType) {
		this.appointmentType = appointmentType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTimeInOneDay() {
		return timeInOneDay;
	}

	public void setTimeInOneDay(String timeInOneDay) {
		this.timeInOneDay = timeInOneDay;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
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

	public String getDataYmd() {
		return dataYmd;
	}

	public void setDataYmd(String dataYmd) {
		this.dataYmd = dataYmd;
	}

	public String getOutNo() {
		return outNo;
	}

	public void setOutNo(String outNo) {
		this.outNo = outNo;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
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

	public int getCustomerAge() {
		return customerAge;
	}

	public void setCustomerAge(int customerAge) {
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

	public BigDecimal getPhServicePrice() {
		return phServicePrice;
	}

	public void setPhServicePrice(BigDecimal phServicePrice) {
		this.phServicePrice = phServicePrice;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getPricingType() {
		return pricingType;
	}

	public void setPricingType(Integer pricingType) {
		this.pricingType = pricingType;
	}

	public Long getOriginalOrderId() {
		return originalOrderId;
	}

	public void setOriginalOrderId(Long originalOrderId) {
		this.originalOrderId = originalOrderId;
	}

	public Date getFinallyEndDate() {
		return finallyEndDate;
	}

	public void setFinallyEndDate(Date finallyEndDate) {
		this.finallyEndDate = finallyEndDate;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Integer getRefundType() {
		return refundType;
	}

	public void setRefundType(Integer refundType) {
		this.refundType = refundType;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public Integer getActualDuration() {
		return actualDuration;
	}

	public void setActualDuration(Integer actualDuration) {
		this.actualDuration = actualDuration;
	}

	public Integer getAcceptance() {
		return acceptance;
	}

	public void setAcceptance(Integer acceptance) {
		this.acceptance = acceptance;
	}

	public Integer getFreeItem() {
		return freeItem;
	}

	public void setFreeItem(Integer freeItem) {
		this.freeItem = freeItem;
	}

	public Date getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	public Long getAcceptUserId() {
		return acceptUserId;
	}

	public void setAcceptUserId(Long acceptUserId) {
		this.acceptUserId = acceptUserId;
	}

	public String getAcceptUserName() {
		return acceptUserName;
	}

	public void setAcceptUserName(String acceptUserName) {
		this.acceptUserName = acceptUserName;
	}

	public String getCustomerFamilyIdCard() {
		return customerFamilyIdCard;
	}

	public void setCustomerFamilyIdCard(String customerFamilyIdCard) {
		this.customerFamilyIdCard = customerFamilyIdCard;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
