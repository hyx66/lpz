package com.acooly.epei.domain;

import java.math.BigDecimal;

import javax.persistence.*;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 微信支付日志
 */
@Entity
@Table(name = "WECHAT_PAYMENT_LOG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WechatPaymentLog extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/**标题*/
	private String title;
	/**编号1-记录外部单号*/
	private String dataOne;
	/**编号2-流水号*/
	private String dataTwo;
	/**金额*/
	private BigDecimal amount;
	/**内容*/
	private String content;
	/**操作员ID*/
	private Long userId;
	/**操作员姓名*/
	private String userName;
	/**操作员类型      1：管理员         2：会员           3：系统*/
	private int userType;

	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_EP_WECHAT_PAYMENT_LOG") })
	public Long getId(){
		return this.id;
	}

	public void setId(Long id){
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDataOne() {
		return dataOne;
	}

	public void setDataOne(String dataOne) {
		this.dataOne = dataOne;
	}

	public String getDataTwo() {
		return dataTwo;
	}

	public void setDataTwo(String dataTwo) {
		this.dataTwo = dataTwo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}