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
 * 评论  Entity
 * @author xierui
 *
 */

@Entity
@Table(name = "EP_COMMENT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Comment  extends TimeEntity {
	/** serialVersionUID */   
	private static final long serialVersionUID = 1L;
	/*被评论的订单ID，关联的字段为EP_ORDER_BASE表中的ID字段。*/
	private Long orderId;//
	/*用户对评价的具体文字内容*/
	private String content;//
	/*用户给订单服务进行星级评定*/
	private int star;//
	/*进行评价的用户的会员ID，关联字段为EP_CUSTOMER表中的ID字段。*/
	private Long  customerId;
	/*进行评价的用户的会员账号，关联字段为EP_CUSTOMER表中的USER_NAME字段*/
	private String  CustomerUserName;
	/*操作人员id，表示帮客户填写的评价的服务人员ID */
	private Long  operatorId;
	//Comment[orderId=3531,content=ni shi fenger  wo我是沙,star=5,customerId=1543,
		//CustomerUserName=13212302823,operatorId=<null>,createTime=<null>,updateTime=<null>,id=<null>]
	
	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_EP_COMMENT") })
	public Long getId(){
		return this.id;
	}

	public void setId(Long id){
		this.id = id;
	}
	
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerUserName() {
		return CustomerUserName;
	}
	public void setCustomerUserName(String customerUserName) {
		CustomerUserName = customerUserName;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	
}
