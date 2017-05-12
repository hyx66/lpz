package com.acooly.epei.web.vo;

import com.acooly.epei.domain.ServicePerson;

/**
 * 修订记录：
 * liubin 2015-10-30 9:56 创建
 */
public class ServicePersonVo {

	public ServicePersonVo(){}

	public ServicePersonVo(ServicePerson person){
		this.id  = person.getId();
		this.name = person.getName();
	}

	/**s
	 * id
	 */
	private Long id;

	/**
	 * 护士名字
	 */
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
