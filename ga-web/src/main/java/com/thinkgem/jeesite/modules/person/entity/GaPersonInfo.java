/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.person.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 个人信息Entity
 * @author liuwsh
 * @version 2017-02-28
 */
public class GaPersonInfo extends DataEntity<GaPersonInfo> {
	
	private static final long serialVersionUID = 1L;
	private String idCardNo;		// 身份证号
	private Date expiryTime;		// 身份证有效期至
	private String issueOrgan;		// 发卡机关
	private String name;		// 姓名
	private String sex;		// 性别
	private String ethnic;		// 民族
	private Date birthday;		// 出生年月日
	private String politicalStatus;		// 政治面貌
	private String educationDegree;		// 教育程度
	private String height;		// 身高
	private String maritalStatus;		// 婚姻状况
	private String spouseName;		// 配偶姓名
	private String spouseIdCardNo;		// 配偶身份证号
	private String personTypeCode;		// 人口类型
	private String hukouAreaNo;		// 户籍地编码
	private String hukouAddress;		// 户籍地址
	private String residenceAddress;		// 居住地址
	private String formerAddress;		// 曾住地址
	private String bloodType;		// 血型
	private String religion;		// 宗教信仰
	private String company;		// 工作单位
	private String tel;		// 电话
	private String mobil;		// 手机号码
	private String face;		// 人脸信息
	
	public GaPersonInfo() {
		super();
	}

	public GaPersonInfo(String id){
		super(id);
	}

	@Length(min=1, max=64, message="身份证号长度必须介于 1 和 64 之间")
	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}
	
	@Length(min=0, max=64, message="发卡机关长度必须介于 0 和 64 之间")
	public String getIssueOrgan() {
		return issueOrgan;
	}

	public void setIssueOrgan(String issueOrgan) {
		this.issueOrgan = issueOrgan;
	}
	
	@Length(min=1, max=64, message="姓名长度必须介于 1 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1, message="性别长度必须介于 0 和 1 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=64, message="民族长度必须介于 0 和 64 之间")
	public String getEthnic() {
		return ethnic;
	}

	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Length(min=0, max=64, message="政治面貌长度必须介于 0 和 64 之间")
	public String getPoliticalStatus() {
		return politicalStatus;
	}

	public void setPoliticalStatus(String politicalStatus) {
		this.politicalStatus = politicalStatus;
	}
	
	@Length(min=0, max=64, message="教育程度长度必须介于 0 和 64 之间")
	public String getEducationDegree() {
		return educationDegree;
	}

	public void setEducationDegree(String educationDegree) {
		this.educationDegree = educationDegree;
	}
	
	@Length(min=0, max=64, message="身高长度必须介于 0 和 64 之间")
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	
	@Length(min=0, max=1, message="婚姻状况长度必须介于 0 和 1 之间")
	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	@Length(min=0, max=64, message="配偶姓名长度必须介于 0 和 64 之间")
	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}
	
	@Length(min=0, max=64, message="配偶身份证号长度必须介于 0 和 64 之间")
	public String getSpouseIdCardNo() {
		return spouseIdCardNo;
	}

	public void setSpouseIdCardNo(String spouseIdCardNo) {
		this.spouseIdCardNo = spouseIdCardNo;
	}
	
	@Length(min=0, max=2, message="人口类型长度必须介于 0 和 2 之间")
	public String getPersonTypeCode() {
		return personTypeCode;
	}

	public void setPersonTypeCode(String personTypeCode) {
		this.personTypeCode = personTypeCode;
	}
	
	@Length(min=0, max=64, message="户籍地编码长度必须介于 0 和 64 之间")
	public String getHukouAreaNo() {
		return hukouAreaNo;
	}

	public void setHukouAreaNo(String hukouAreaNo) {
		this.hukouAreaNo = hukouAreaNo;
	}
	
	@Length(min=0, max=64, message="户籍地址长度必须介于 0 和 64 之间")
	public String getHukouAddress() {
		return hukouAddress;
	}

	public void setHukouAddress(String hukouAddress) {
		this.hukouAddress = hukouAddress;
	}
	
	@Length(min=0, max=64, message="居住地址长度必须介于 0 和 64 之间")
	public String getResidenceAddress() {
		return residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}
	
	@Length(min=0, max=64, message="曾住地址长度必须介于 0 和 64 之间")
	public String getFormerAddress() {
		return formerAddress;
	}

	public void setFormerAddress(String formerAddress) {
		this.formerAddress = formerAddress;
	}
	
	@Length(min=0, max=1, message="血型长度必须介于 0 和 1 之间")
	public String getBloodType() {
		return bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}
	
	@Length(min=0, max=20, message="宗教信仰长度必须介于 0 和 20 之间")
	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}
	
	@Length(min=0, max=64, message="工作单位长度必须介于 0 和 64 之间")
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	@Length(min=0, max=64, message="电话长度必须介于 0 和 64 之间")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Length(min=0, max=64, message="手机号码长度必须介于 0 和 64 之间")
	public String getMobil() {
		return mobil;
	}

	public void setMobil(String mobil) {
		this.mobil = mobil;
	}
	
	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}
	
}