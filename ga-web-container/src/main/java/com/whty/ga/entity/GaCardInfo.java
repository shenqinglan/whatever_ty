/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.whty.ga.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 卡信息Entity
 * @author liuwsh
 * @version 2017-02-28
 */
public class GaCardInfo extends DataEntity<GaCardInfo> {
	
	private static final long serialVersionUID = 1L;
	private String cardNo;		// 卡片号
	private String personId;		// 个人信息id
	private String houseId;		// 房屋id
	private String person;
	private String idCardNo;
	private String area;
	private String house;
	private String cardTypeCode;		// 卡类别
	private Date expiryTime;		// 有效期至
	private String blackListFlag;		// 是否在黑名单中
	private String faceInfoFlag;		// 是否需要人脸信息
	private String issuer;		// 发行单位
	private Date issueDate;		// 发行时间
	private String inLimitCount;		// 进入次数限制
	private String outLimitCount;		// 出去次数限制
	private String relation;		// 与绑定人员（户主）关系
	
	public GaCardInfo() {
		super();
	}

	public GaCardInfo(String id){
		super(id);
	}

	@Length(min=1, max=64, message="卡片号长度必须介于 1 和 64 之间")
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	@Length(min=1, max=32, message="个人信息id长度必须介于 1 和 32 之间")
	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	@Length(min=1, max=32, message="房屋id长度必须介于 1 和 32 之间")
	public String getHouseId() {
		return houseId;
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}
	
	@Length(min=1, max=64, message="卡类别长度必须介于 1 和 64 之间")
	public String getCardTypeCode() {
		return cardTypeCode;
	}

	public void setCardTypeCode(String cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}
	
	@Length(min=0, max=1, message="是否在黑名单中长度必须介于 0 和 1 之间")
	public String getBlackListFlag() {
		return blackListFlag;
	}

	public void setBlackListFlag(String blackListFlag) {
		this.blackListFlag = blackListFlag;
	}
	
	@Length(min=0, max=1, message="是否需要人脸信息长度必须介于 0 和 1 之间")
	public String getFaceInfoFlag() {
		return faceInfoFlag;
	}

	public void setFaceInfoFlag(String faceInfoFlag) {
		this.faceInfoFlag = faceInfoFlag;
	}
	
	@Length(min=0, max=64, message="发行单位长度必须介于 0 和 64 之间")
	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	
	@Length(min=0, max=10, message="进入次数限制长度必须介于 0 和 10 之间")
	public String getInLimitCount() {
		return inLimitCount;
	}

	public void setInLimitCount(String inLimitCount) {
		this.inLimitCount = inLimitCount;
	}
	
	@Length(min=0, max=10, message="出去次数限制长度必须介于 0 和 10 之间")
	public String getOutLimitCount() {
		return outLimitCount;
	}

	public void setOutLimitCount(String outLimitCount) {
		this.outLimitCount = outLimitCount;
	}
	
	@Length(min=0, max=64, message="与绑定人员（户主）关系长度必须介于 0 和 64 之间")
	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	/**
	 * @return the person
	 */
	public String getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(String person) {
		this.person = person;
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
	
	
	
}