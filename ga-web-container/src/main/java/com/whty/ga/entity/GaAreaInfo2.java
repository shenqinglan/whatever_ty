/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.whty.ga.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 小区信息Entity
 * @author liuwsh
 * @version 2017-02-17
 */
public class GaAreaInfo2 extends DataEntity<GaAreaInfo2> {
	
	private static final long serialVersionUID = 1L;
	private String areaId;		// 小区
    private String area;
	private String personType;
	private String typeName;
	private String name;
	private String idCardNo;
	private String house;
	
	public GaAreaInfo2() {
		super();
	}

	public GaAreaInfo2(String id){
		super(id);
	}

	/**
	 * @return the areaId
	 */
	public String getAreaId() {
		return areaId;
	}

	/**
	 * @param areaId the areaId to set
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	/**
	 * @return the personType
	 */
	public String getPersonType() {
		return personType;
	}

	/**
	 * @param personType the personType to set
	 */
	public void setPersonType(String personType) {
		this.personType = personType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the idCardNo
	 */
	public String getIdCardNo() {
		return idCardNo;
	}

	/**
	 * @param idCardNo the idCardNo to set
	 */
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	/**
	 * @return the house
	 */
	public String getHouse() {
		return house;
	}

	/**
	 * @param house the house to set
	 */
	public void setHouse(String house) {
		this.house = house;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	
	
}