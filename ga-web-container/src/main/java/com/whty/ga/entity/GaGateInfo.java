/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.whty.ga.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 门禁信息Entity
 * @author liuwsh
 * @version 2017-02-28
 */
public class GaGateInfo extends DataEntity<GaGateInfo> {
	
	private static final long serialVersionUID = 1L;
	private String gateCode;		// 门禁编码
	private String gateType;
	private String brand;		// 品牌型号
	private String areaId;		// 所属小区
	private String areaName;
	private String buildingNo;
	private String unitNo;		// 所属门栋
	private Date installDate;		// 安装时间
	private Date lastMaintainDate;		// 上次维护时间
	
	public GaGateInfo() {
		super();
	}

	public GaGateInfo(String id){
		super(id);
	}

	@Length(min=1, max=64, message="门禁编码长度必须介于 1 和 64 之间")
	public String getGateCode() {
		return gateCode;
	}

	public void setGateCode(String gateCode) {
		this.gateCode = gateCode;
	}
	
	@Length(min=0, max=64, message="品牌型号长度必须介于 0 和 64 之间")
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	@Length(min=1, max=32, message="所属小区长度必须介于 1 和 32 之间")
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getInstallDate() {
		return installDate;
	}

	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastMaintainDate() {
		return lastMaintainDate;
	}

	public void setLastMaintainDate(Date lastMaintainDate) {
		this.lastMaintainDate = lastMaintainDate;
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

	/**
	 * @return the gateType
	 */
	public String getGateType() {
		return gateType;
	}

	/**
	 * @param gateType the gateType to set
	 */
	public void setGateType(String gateType) {
		this.gateType = gateType;
	}
	
	
	
}