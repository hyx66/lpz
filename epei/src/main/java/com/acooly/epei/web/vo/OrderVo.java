package com.acooly.epei.web.vo;

import com.acooly.epei.domain.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 修订记录：
 * liubin 2015-11-05 15:36 创建
 */
public class OrderVo {


	public OrderVo(){}

	public OrderVo(OrderBase order){
		BeanUtils.copyProperties(order,this);
		this.state  = OrderState.getMsgByCode(order.getState());
		this.orign = OrderOriginalEnum.getMsgByCode(order.getOrign());
		this.hospitalId = order.getHospital().getId();
		this.hospitalReception = order.getHospital().getReceptionPosition();
		this.amount =order.getAmount();
		this.timeInOneDay = order.getTimeInOneDay();
		this.payStatus = order.getPayStatus();
		this.pricingType = order.getPricingType();
		this.originalOrderId = order.getOriginalOrderId();
		this.setFinallyEndDate(order.getFinallyEndDate());
		this.setServicePerson(order.getServicePersonName());
		this.setId(order.getId());
		this.setOrderNo(order.getOrderNo());
		this.setPatientGender(order.getPatient().getGender());
		this.setFreeItem(order.getFreeItem());
		this.setAcceptance(order.getAcceptance());
		this.setPhServicePrice(order.getPhServicePrice());
	}
	
	protected Long id;
	/** 订单编号 */
	private String orderNo;
	/** 病患姓名 */
	private String patientName;
	/**病患联系电话*/
	private String patientMobile;
	/** 病患身份证号 */
	private String patientIdCard;
	/**患者性别*/
	private Integer patientGender;
	/**病患医保卡号*/
	private String patientMedicareCard;
	/** 会员编号 */
	private Long cusNo;
	/** 就诊医院名 */
	private String hospitalName;
	/**接待地点**/
	private String hospitalReception;
	/** 预约时间 */
	private Date orderTime;
	/** 订单状态 */
	private String state ;
	/** 预约来源 */
	private String orign;
	/**医院id**/
	private  Long hospitalId;
	/**价格***/
	private Float price;
	private String orderType;
	/**病患生日*/
	private String patientBirthday;
	/**床位*/
	private String bed;
	/**科室*/
	private String departmentName;
	/** 开始时间 */
	private Date startTime;
	/** 结束时间 */
	private Date endTime;
	/** 持续时长 */
	private int duration;
	/** 陪护人 */
	private String servicePerson;
	/***/
	private BigDecimal amount;
	/**是否可以修改 陪诊单预约时间前3小时不能修改*/
	private boolean editable = true;
	/**上午或下午*/
	private String timeInOneDay;
	/**患者性别*/
	private String customerSex;
	/**支付状态*/
	private Integer payStatus;
	/**计费方式*/
	private Integer pricingType;
	/**续单类型*/
	private Long originalOrderId;
	/***/
	private Date finallyEndDate;
	/**评论*/
	private String comment;
	/**星级评价*/
	private Integer star;
	/**是否使用免费服务项目*/
	private Integer freeItem;
	/** 订单客服受理状态{1:未受理; 2:受理中   3：受理结束} */
	private Integer acceptance;
	/**陪护价格*天*/
	private BigDecimal phServicePrice;

	public Long getId() {
		return id;
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
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientMobile() {
		return patientMobile;
	}

	public void setPatientMobile(String patientMobile) {
		this.patientMobile = patientMobile;
	}

	public String getPatientIdCard() {
		return patientIdCard;
	}

	public void setPatientIdCard(String patientIdCard) {
		this.patientIdCard = patientIdCard;
	}

	public Long getCusNo() {
		return cusNo;
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

	public String getHospitalReception() {
		return hospitalReception;
	}

	public void setHospitalReception(String hospitalReception) {
		this.hospitalReception = hospitalReception;
	}

	public Date getOrderTime() {
		return orderTime;
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

	public Long getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPatientBirthday() {
		return patientBirthday;
	}
	public void setPatientBirthday(String patientBirthday) {
		this.patientBirthday = patientBirthday;
	}
	
	public String getBed() {
		return bed;
	}
	public void setBed(String bed) {
		this.bed = bed;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	public boolean getEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getServicePerson() {
		return servicePerson;
	}

	public void setServicePerson(String servicePerson) {
		this.servicePerson = servicePerson;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTimeInOneDay() {
		return timeInOneDay;
	}

	public void setTimeInOneDay(String timeInOneDay) {
		this.timeInOneDay = timeInOneDay;
	}

	public String getCustomerSex() {
		return customerSex;
	}

	public void setCustomerSex(String customerSex) {
		this.customerSex = customerSex;
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

	public String getPatientMedicareCard() {
		return patientMedicareCard;
	}

	public void setPatientMedicareCard(String patientMedicareCard) {
		this.patientMedicareCard = patientMedicareCard;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Integer getPatientGender() {
		return patientGender;
	}

	public void setPatientGender(Integer patientGender) {
		this.patientGender = patientGender;
	}

	public Integer getFreeItem() {
		return freeItem;
	}

	public void setFreeItem(Integer freeItem) {
		this.freeItem = freeItem;
	}

	public Integer getAcceptance() {
		return acceptance;
	}

	public void setAcceptance(Integer acceptance) {
		this.acceptance = acceptance;
	}

	public BigDecimal getPhServicePrice() {
		return phServicePrice;
	}

	public void setPhServicePrice(BigDecimal phServicePrice) {
		this.phServicePrice = phServicePrice;
	}

}
