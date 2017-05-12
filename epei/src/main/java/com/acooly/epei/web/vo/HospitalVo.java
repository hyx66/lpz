package com.acooly.epei.web.vo;

import com.acooly.epei.domain.Hospital;

/**
 * 修订记录：
 * liubin 2015-10-26 22:28 创建
 */
public class HospitalVo {

	/**
	 * id
	 */
	private  Long id;

	/**
	 * 医院
	 */

	private  String name;

	public HospitalVo(){}

	public  HospitalVo(Hospital hospital){
		this.id = hospital.getId();
		this.name = hospital.getName();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
