/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-3-1
 * Id: GaGateInfo.java,v 1.0 2017-3-1 下午1:47:24 Administrator
 */
package com.whty.ga.pojo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @ClassName GaGateInfo
 * @author Administrator
 * @date 2017-3-1 下午1:47:24
 * @Description TODO(门禁信息)
 */
@Entity
@Table(name="ga_gate_info")
public class GaGateInfo {

	@Id
	@GenericGenerator(name="generator",strategy="uuid")
	@GeneratedValue(generator="generator")
	@Column(name="id",unique=true,nullable=false)
	private String id;
	
	@Column(name="gate_code")
	private String gateCode;
	
	@Column(name="brand")
	private String brand;
	
	@Column(name="gate_type")
	private String gateType;
	
	@ManyToOne(cascade ={CascadeType.ALL},fetch = FetchType.LAZY)
	@JoinColumn(name="area_id")
	private GaAreaInfo area;
	
	@Column(name="building_no")
	private String buildingNo;
	
	@Column(name="unit_no")
	private String unitNo;
	
	@Column(name="install_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date installDate;
	
	@Column(name="last_maintain_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastMaintainDate;
	
	@Column(name="create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name="update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	
	@Column(name="del_flag")
	private String delFlag;
	
	@Column(name="remarks")
	private String remarks;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the gateCode
	 */
	public String getGateCode() {
		return gateCode;
	}

	/**
	 * @param gateCode the gateCode to set
	 */
	public void setGateCode(String gateCode) {
		this.gateCode = gateCode;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
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

	/**
	 * @return the area
	 */
	public GaAreaInfo getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(GaAreaInfo area) {
		this.area = area;
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
	 * @return the installDate
	 */
	public Date getInstallDate() {
		return installDate;
	}

	/**
	 * @param installDate the installDate to set
	 */
	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}

	/**
	 * @return the lastMaintainDate
	 */
	public Date getLastMaintainDate() {
		return lastMaintainDate;
	}

	/**
	 * @param lastMaintainDate the lastMaintainDate to set
	 */
	public void setLastMaintainDate(Date lastMaintainDate) {
		this.lastMaintainDate = lastMaintainDate;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the delFlag
	 */
	public String getDelFlag() {
		return delFlag;
	}

	/**
	 * @param delFlag the delFlag to set
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
