/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.whty.ga.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 房屋信息Entity
 * @author liuwsh
 * @version 2017-02-28
 */
public class GaHouseInfo extends DataEntity<GaHouseInfo> {
	
	private static final long serialVersionUID = 1L;
	private String areaId;		// 小区id
	private String areaName;
	private String standardAddress;		// 标准地址
	private String commonAddress;		// 通俗地址
	private String gateId;
	private String buildingNo;
	private String unitNo;
	private String roomNo;		// 房间号
	private String houseTypeCode;		// 房屋类型
	private String size;		// 面积
	private Date issueDate;		// 交付时间
	
	public GaHouseInfo() {
		super();
	}

	public GaHouseInfo(String id){
		super(id);
	}

	@Length(min=1, max=32, message="小区id长度必须介于 1 和 32 之间")
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	@Length(min=1, max=64, message="标准地址长度必须介于 1 和 64 之间")
	public String getStandardAddress() {
		return standardAddress;
	}

	public void setStandardAddress(String standardAddress) {
		this.standardAddress = standardAddress;
	}
	
	@Length(min=0, max=64, message="通俗地址长度必须介于 0 和 64 之间")
	public String getCommonAddress() {
		return commonAddress;
	}

	public void setCommonAddress(String commonAddress) {
		this.commonAddress = commonAddress;
	}

	@Length(min=0, max=32, message="房间号长度必须介于 0 和 32 之间")
	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	
	@Length(min=0, max=2, message="房屋类型长度必须介于 0 和 2 之间")
	public String getHouseTypeCode() {
		return houseTypeCode;
	}

	public void setHouseTypeCode(String houseTypeCode) {
		this.houseTypeCode = houseTypeCode;
	}
	
	@Length(min=0, max=10, message="面积长度必须介于 0 和 10 之间")
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return the gateId
	 */
	public String getGateId() {
		return gateId;
	}

	/**
	 * @param gateId the gateId to set
	 */
	public void setGateId(String gateId) {
		this.gateId = gateId;
	}

	/**
	 * @return the buildingNo
	 */
	public String getBuildingNo() {
		return buildingNo;
	}

	/**
	 * @param buildingNo the buildingNo to set
	 */
	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}

	/**
	 * @return the unitNo
	 */
	public String getUnitNo() {
		return unitNo;
	}

	/**
	 * @param unitNo the unitNo to set
	 */
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
	
	
	
}