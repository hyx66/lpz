package com.acooly.epei.domain;
import javax.persistence.*;


import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 系统全局变量
 * 说明：项目运行中调用的某些接口需要动态的获取一些参数，由于获取参数的次数有限制，不可能每一次都去获取，因此需要将获取到的参数值保存起来。
 */
@Entity
@Table(name = "GLOBAL_VARIABLE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GlobalVariable extends TimeEntity {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/**参数名称*/
	private String param;
	/**参数值*/
	private String value;
	/**备注说明*/
	private String memo;

	@Id
	@GeneratedValue(generator = "sequence")
	@GenericGenerator(name = "sequence", strategy = "sequence", parameters = { @Parameter(name = "sequence",
			value = "SEQ_GLOBAL_VARIABLE") })
	public Long getId(){
		return this.id;
	}

	public void setId(Long id){
		this.id = id;
	}
	
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
