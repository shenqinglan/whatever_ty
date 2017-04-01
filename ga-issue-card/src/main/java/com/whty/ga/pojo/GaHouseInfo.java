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
 * @ClassName GaHouseInfo
 * @author Administrator
 * @date 2017-3-3 上午9:51:21
 * @Description TODO(房屋信息)
 */
@Entity
@Table(name="ga_house_info")
public class GaHouseInfo {

	@Id
	@GenericGenerator(name="generator",strategy="uuid")
	@GeneratedValue(generator="generator")
	@Column(name="id",unique=true,nullable=false)
	private String id;
	
	@ManyToOne(cascade ={CascadeType.ALL},fetch = FetchType.LAZY)
	@JoinColumn(name="area_id")
	private GaAreaInfo area;
	
	@Column(name="building_no")
	private String buildingNo;
	
	@Column(name="unit_no")
	private String unitNo;
	
	@Column(name="room_no")
	private String roomNo;
	
	@ManyToOne(cascade ={CascadeType.ALL},fetch = FetchType.LAZY)
	@JoinColumn(name="gate_id")
	private GaGateInfo gate;
	
	@Column(name="standard_address")
	private String standardAddress;
	
	@Column(name="common_address")
	private String commonAddress;
	
	@Column(name="building_info")
	private String buildingInfo;
	
	@Column(name="house_type_code")
	private String houseTypeCode;
	
	@Column(name="size")
	private String size;
	
	@Column(name="issue_date")
	@Temporal(TemporalType.DATE)
	private Date issueDate;
	
	@Column(name="create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name="update_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	
	@Column(name="del_flag")
	private String delDlag;
	
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
	 * @return the roomNo
	 */
	public String getRoomNo() {
		return roomNo;
	}

	/**
	 * @param roomNo the roomNo to set
	 */
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	/**
	 * @return the gate
	 */
	public GaGateInfo getGate() {
		return gate;
	}

	/**
	 * @param gate the gate to set
	 */
	public void setGate(GaGateInfo gate) {
		this.gate = gate;
	}

	/**
	 * @return the standardAddress
	 */
	public String getStandardAddress() {
		return standardAddress;
	}

	/**
	 * @param standardAddress the standardAddress to set
	 */
	public void setStandardAddress(String standardAddress) {
		this.standardAddress = standardAddress;
	}

	/**
	 * @return the commonAddress
	 */
	public String getCommonAddress() {
		return commonAddress;
	}

	/**
	 * @param commonAddress the commonAddress to set
	 */
	public void setCommonAddress(String commonAddress) {
		this.commonAddress = commonAddress;
	}

	/**
	 * @return the buildingInfo
	 */
	public String getBuildingInfo() {
		return buildingInfo;
	}

	/**
	 * @param buildingInfo the buildingInfo to set
	 */
	public void setBuildingInfo(String buildingInfo) {
		this.buildingInfo = buildingInfo;
	}

	/**
	 * @return the houseTypeCode
	 */
	public String getHouseTypeCode() {
		return houseTypeCode;
	}

	/**
	 * @param houseTypeCode the houseTypeCode to set
	 */
	public void setHouseTypeCode(String houseTypeCode) {
		this.houseTypeCode = houseTypeCode;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @return the issueDate
	 */
	public Date getIssueDate() {
		return issueDate;
	}

	/**
	 * @param issueDate the issueDate to set
	 */
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
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
	 * @return the delDlag
	 */
	public String getDelDlag() {
		return delDlag;
	}

	/**
	 * @param delDlag the delDlag to set
	 */
	public void setDelDlag(String delDlag) {
		this.delDlag = delDlag;
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
