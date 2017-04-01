/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.person.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 出入信息Entity
 * @author liuwsh
 * @version 2017-02-28
 */
public class GaEntranceInfo extends DataEntity<GaEntranceInfo> {
	
	private static final long serialVersionUID = 1L;
	private String areaId;
	private String areaName;
	private String gateId;
	private String building;
	private String door;	
	private String house;
	private String name;
	private String idCardNo;
	private String cardId;		// 卡片id
	private Date swipeTime;		// 刷卡时间
	private Date startTime;
	private Date endTime;
	private String inOrOut;		// 出还是入
	private String face;		// 人脸信息
	
	public GaEntranceInfo() {
		super();
	}

	public GaEntranceInfo(String id){
		super(id);
	}
	
	@Length(min=1, max=32, message="卡片id长度必须介于 1 和 32 之间")
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="刷卡时间不能为空")
	public Date getSwipeTime() {
		return swipeTime;
	}

	public void setSwipeTime(Date swipeTime) {
		this.swipeTime = swipeTime;
	}
	
	@Length(min=0, max=1, message="出还是入长度必须介于 0 和 1 之间")
	public String getInOrOut() {
		return inOrOut;
	}

	public void setInOrOut(String inOrOut) {
		this.inOrOut = inOrOut;
	}
	
	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
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
	 * @return the door
	 */
	public String getDoor() {
		return door;
	}

	/**
	 * @param door the door to set
	 */
	public void setDoor(String door) {
		this.door = door;
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
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the building
	 */
	public String getBuilding() {
		return building;
	}

	/**
	 * @param building the building to set
	 */
	public void setBuilding(String building) {
		this.building = building;
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
	
	
	
}