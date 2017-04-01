package com.whty.ga.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @ClassName GaAreaInfo
 * @author Administrator
 * @date 2017-2-24 下午2:59:29
 * @Description TODO(小区信息)
 */
@Entity
@Table(name="ga_area_info")
public class GaAreaInfo {

	@Id
	@GenericGenerator(name="generator",strategy="uuid")
	@GeneratedValue(generator="generator")
	@Column(name="id",unique=true,nullable=false)
	private String id;
	
	@Column(name = "area_name")
	private String areaName;
	
	@Column(name = "city_no")
	private String cityNo;
	
	@Column(name = "district_no")
	private String districtNo;
	
	@Column(name = "area_no")
	private String areaNo;
	
	@Column(name = "area_address")
	private String areaAddress;
	
	@Column(name = "area_type_code")
	private String areaTypeCode;
	
	@Column(name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name = "update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	
	@Column(name = "del_flag")
	private String delFlag;
	
	@Column(name = "remarks")
	private String remarks;

	/**
	 * @return the areaId
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param areaId the areaId to set
	 */
	public void setAreaId(String id) {
		this.id = id;
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
	 * @return the cityNo
	 */
	public String getCityNo() {
		return cityNo;
	}

	/**
	 * @param cityNo the cityNo to set
	 */
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	/**
	 * @return the districtNo
	 */
	public String getDistrictNo() {
		return districtNo;
	}

	/**
	 * @param districtNo the districtNo to set
	 */
	public void setDistrictNo(String districtNo) {
		this.districtNo = districtNo;
	}

	/**
	 * @return the areaNo
	 */
	public String getAreaNo() {
		return areaNo;
	}

	/**
	 * @param areaNo the areaNo to set
	 */
	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	/**
	 * @return the areaAddress
	 */
	public String getAreaAddress() {
		return areaAddress;
	}

	/**
	 * @param areaAddress the areaAddress to set
	 */
	public void setAreaAddress(String areaAddress) {
		this.areaAddress = areaAddress;
	}

	/**
	 * @return the areaTypeCode
	 */
	public String getAreaTypeCode() {
		return areaTypeCode;
	}

	/**
	 * @param areaTypeCode the areaTypeCode to set
	 */
	public void setAreaTypeCode(String areaTypeCode) {
		this.areaTypeCode = areaTypeCode;
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
