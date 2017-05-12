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
 *申请提现Entity
 */
@Entity
@Table(name = "ENCHASHMENT_APPLY")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EnchashmentApply extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 主键 */
	private Long id;
	/**申请人ID*/
	private Long customerId;
	/**申请人电话*/
	private String customerUserName;
	/**openid,用户微信号与公众号相对应的唯一ID标识，必须提醒用过在提交申请时是在自己的微信上登录*/
	private String openId;
	/**提现金额*/
	private BigDecimal amount;
	/**审批人ID  系统操作员ID*/
	private Long auditorId;
	/**审批人姓名    系统操作员姓名*/
	private String auditorName;
	/**审批人签名     操作员填写自己的名字*/
	private String AuditorSign;
	/**申请状态   1：初始，默认      2：成功    3：失败     4：取消*/
	private Integer status;
	/**提现说明*/
	private String explain;
	/**提现时间   提现成功时间*/
	private Date endTime;
	/**备注*/
	private String memo;
	/**流水号*/
	private String applyNo;
	

	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_ENCHASHMENT_APPLY") })
	public Long getId(){
		return this.id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerUserName() {
		return customerUserName;
	}

	public void setCustomerUserName(String customerUserName) {
		this.customerUserName = customerUserName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(Long auditorId) {
		this.auditorId = auditorId;
	}

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	public String getAuditorSign() {
		return AuditorSign;
	}

	public void setAuditorSign(String auditorSign) {
		AuditorSign = auditorSign;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}