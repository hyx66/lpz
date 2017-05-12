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
 * 线下消费入账统计
 * @author Qimity
 * Date: 2016-09-02 10:09:09
 */
@Entity
@Table(name = "OFFLINE_SALES_ACCT_STA")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OfflineSalesAcctSta extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/**账期*/
	private String acctYmd;
	/**入账总金额*/
	private BigDecimal offlineAmount;
	/**实账总金额*/
	private BigDecimal acctAmount;
	/**相差金额*/
	private BigDecimal diffAmount;
	/**确认总金额*/
	private BigDecimal amount;
	/**状态-"0":"初始","1":"未对账","2":"已对账"*/
	private int status;
	/**录入人员ID 0：系统日终统计*/
	private Long createId;
	/**录入人员      默认：系统日终统计*/
	private String createName;
	/**录入备注*/
	private String createMemo;
	/**对账人员ID*/
	private Long acctId;
	/**对账人员*/
	private String acctName;
	/**对账备注*/
	private String acctMemo;
	/**对账日期*/
	private Date acctTime;
	/**医院名称*/
	private String hospitalName;
	/**统计记录所属医院ID*/
	private Long hospitalId;
	/**科室名称*/
	private String departmentName;
	/**统计记录所属科室*/
	private Long departmentId;
	/**统计数据条数总和*/
	private int dataCount;
	/**统计范围  1:对整个系统进行统计   2：对每个医院进行统计   3：对每个医院下的每个科室进行统计*/
	private int countScope; 
	/**统计的订单类型：1：陪诊      2：陪护      3:全部*/
	private int orderType;
	
	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_EP_OFFLINE_SALES_ACCT_STA") })
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getAcctYmd() {
		return acctYmd;
	}

	public void setAcctYmd(String acctYmd) {
		this.acctYmd = acctYmd;
	}

	public BigDecimal getOfflineAmount() {
		return offlineAmount;
	}

	public void setOfflineAmount(BigDecimal offlineAmount) {
		this.offlineAmount = offlineAmount;
	}

	public BigDecimal getAcctAmount() {
		return acctAmount;
	}

	public void setAcctAmount(BigDecimal acctAmount) {
		this.acctAmount = acctAmount;
	}

	public BigDecimal getDiffAmount() {
		return diffAmount;
	}

	public void setDiffAmount(BigDecimal diffAmount) {
		this.diffAmount = diffAmount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getCreateMemo() {
		return createMemo;
	}

	public void setCreateMemo(String createMemo) {
		this.createMemo = createMemo;
	}

	public Long getAcctId() {
		return acctId;
	}

	public void setAcctId(Long acctId) {
		this.acctId = acctId;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getAcctMemo() {
		return acctMemo;
	}

	public void setAcctMemo(String acctMemo) {
		this.acctMemo = acctMemo;
	}

	public Date getAcctTime() {
		return acctTime;
	}

	public void setAcctTime(Date acctTime) {
		this.acctTime = acctTime;
	}

	public Long getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}
	
	public int getDataCount() {
		return dataCount;
	}

	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
	}

	public int getCountScope() {
		return countScope;
	}

	public void setCountScope(int countScope) {
		this.countScope = countScope;
	}
	
	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	
	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	
	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}