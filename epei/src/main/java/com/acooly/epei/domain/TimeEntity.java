package com.acooly.epei.domain;

import com.acooly.core.common.domain.AbstractEntity;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * 修订记录：
 * liubin 2015-10-19 18:02 创建
 */
@MappedSuperclass
public class TimeEntity  extends AbstractEntity {

	/** 创建时间 */
	protected Date createTime;
	/** 更新时间 */
	protected Date updateTime;

	public Date getCreateTime(){
		return this.createTime;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public Date getUpdateTime(){
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}

	@PrePersist
	private void setCreateTime(){
		this.createTime = new Date();
	}

	@PreUpdate
	private void setUpdateTime(){
		this.updateTime = new Date();
	}
}
