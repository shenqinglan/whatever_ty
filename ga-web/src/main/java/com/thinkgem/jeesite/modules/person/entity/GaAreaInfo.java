/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.person.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 小区信息Entity
 * @author liuwsh
 * @version 2017-02-28
 */
public class GaAreaInfo extends DataEntity<GaAreaInfo> {
	
	private static final long serialVersionUID = 1L;
	private String areaName;		// 小区名称
	private String cityNo;		// 城市编号
	private String districtNo;		// 区域编号
	private String areaNo;		// 小区编号
	private String areaAddress;		// 小区地址
	private String areaTypeCode;		// 小区类型
	
	public GaAreaInfo() {
		super();
	}

	public GaAreaInfo(String id){
		super(id);
	}

	@Length(min=1, max=64, message="小区名称长度必须介于 1 和 64 之间")
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	@Length(min=0, max=64, message="城市编号长度必须介于 0 和 64 之间")
	public String getCityNo() {
		return cityNo;
	}

	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}
	
	@Length(min=0, max=64, message="区域编号长度必须介于 0 和 64 之间")
	public String getDistrictNo() {
		return districtNo;
	}

	public void setDistrictNo(String districtNo) {
		this.districtNo = districtNo;
	}
	
	@Length(min=1, max=64, message="小区编号长度必须介于 1 和 64 之间")
	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}
	
	@Length(min=1, max=64, message="小区地址长度必须介于 1 和 64 之间")
	public String getAreaAddress() {
		return areaAddress;
	}

	public void setAreaAddress(String areaAddress) {
		this.areaAddress = areaAddress;
	}
	
	@Length(min=0, max=2, message="小区类型长度必须介于 0 和 2 之间")
	public String getAreaTypeCode() {
		return areaTypeCode;
	}

	public void setAreaTypeCode(String areaTypeCode) {
		this.areaTypeCode = areaTypeCode;
	}
	
}