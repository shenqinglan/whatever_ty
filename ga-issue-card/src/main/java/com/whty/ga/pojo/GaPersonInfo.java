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
 * @ClassName GaPersonInfo
 * @author Administrator
 * @date 2017-3-3 上午9:51:36
 * @Description TODO(个人信息)
 */
@Entity
@Table(name="ga_person_info")
public class GaPersonInfo {

	@Id
	@GenericGenerator(name="generator",strategy="uuid")
	@GeneratedValue(generator="generator")
	@Column(name="id",unique=true,nullable=false)
	private String id;
	
	@Column(name="id_card_no")
	private String idCardNo;
	
	@Column(name="name")
	private String name;
	
	@Column(name="sex")
	private String sex;
	
	@Column(name="ethnic")
	private String ethnic;
	
	@Column(name="birthday")
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
	@Column(name="political_status")
	private String politicalStatus;
	
	@Column(name="education_degree")
	private String educationDegree;
	
	@Column(name="height")
	private String height;
	
	@Column(name="marital_status")
	private String maritalStatus;
	
	@Column(name="hukou_area_no")
	private String hukouAreaNo;
	
	@Column(name="hukou_address")
	private String hukouAddress;
	
	@Column(name="residence_address")
	private String residenceAddress;
	
	@Column(name="former_address")
	private String formerAddress;
	
	@Column(name="blood_type")
	private String bloodType;
	
	@Column(name="religion")
	private String religion;
	
	@Column(name="company")
	private String company;
	
	@Column(name="tel")
	private String tel;
	
	@Column(name="mobil")
	private String mobil;
	
	@Column(name="face")
	private String face;
	
	@Column(name="person_type_code")
	private String personTypeCode;
	
	@Column(name="issue_organ")
	private String issueOrgan;
	
	@Column(name="expiry_time")
	@Temporal(TemporalType.DATE)
	private Date expiryTime;
	
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
	 * @return the expiryTime
	 */
	public Date getExpiryTime() {
		return expiryTime;
	}

	/**
	 * @param expiryTime the expiryTime to set
	 */
	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	/**
	 * @return the ethnic
	 */
	public String getEthnic() {
		return ethnic;
	}

	/**
	 * @param ethnic the ethnic to set
	 */
	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the politicalStatus
	 */
	public String getPoliticalStatus() {
		return politicalStatus;
	}

	/**
	 * @param politicalStatus the politicalStatus to set
	 */
	public void setPoliticalStatus(String politicalStatus) {
		this.politicalStatus = politicalStatus;
	}

	/**
	 * @return the educationDegree
	 */
	public String getEducationDegree() {
		return educationDegree;
	}

	/**
	 * @param educationDegree the educationDegree to set
	 */
	public void setEducationDegree(String educationDegree) {
		this.educationDegree = educationDegree;
	}

	/**
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * @return the maritalStatus
	 */
	public String getMaritalStatus() {
		return maritalStatus;
	}

	/**
	 * @param maritalStatus the maritalStatus to set
	 */
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	/**
	 * @return the hukouAreaNo
	 */
	public String getHukouAreaNo() {
		return hukouAreaNo;
	}

	/**
	 * @param hukouAreaNo the hukouAreaNo to set
	 */
	public void setHukouAreaNo(String hukouAreaNo) {
		this.hukouAreaNo = hukouAreaNo;
	}

	/**
	 * @return the hukouAddress
	 */
	public String getHukouAddress() {
		return hukouAddress;
	}

	/**
	 * @param hukouAddress the hukouAddress to set
	 */
	public void setHukouAddress(String hukouAddress) {
		this.hukouAddress = hukouAddress;
	}

	/**
	 * @return the residenceAddress
	 */
	public String getResidenceAddress() {
		return residenceAddress;
	}

	/**
	 * @param residenceAddress the residenceAddress to set
	 */
	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	/**
	 * @return the formerAddress
	 */
	public String getFormerAddress() {
		return formerAddress;
	}

	/**
	 * @param formerAddress the formerAddress to set
	 */
	public void setFormerAddress(String formerAddress) {
		this.formerAddress = formerAddress;
	}

	/**
	 * @return the bloodType
	 */
	public String getBloodType() {
		return bloodType;
	}

	/**
	 * @param bloodType the bloodType to set
	 */
	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	/**
	 * @return the religion
	 */
	public String getReligion() {
		return religion;
	}

	/**
	 * @param religion the religion to set
	 */
	public void setReligion(String religion) {
		this.religion = religion;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the mobil
	 */
	public String getMobil() {
		return mobil;
	}

	/**
	 * @param mobil the mobil to set
	 */
	public void setMobil(String mobil) {
		this.mobil = mobil;
	}

	/**
	 * @return the face
	 */
	public String getFace() {
		return face;
	}

	/**
	 * @param face the face to set
	 */
	public void setFace(String face) {
		this.face = face;
	}

	/**
	 * @return the personTypeCode
	 */
	public String getPersonTypeCode() {
		return personTypeCode;
	}

	/**
	 * @param personTypeCode the personTypeCode to set
	 */
	public void setPersonTypeCode(String personTypeCode) {
		this.personTypeCode = personTypeCode;
	}

	/**
	 * @return the issueOrgan
	 */
	public String getIssueOrgan() {
		return issueOrgan;
	}

	/**
	 * @param issueOrgan the issueOrgan to set
	 */
	public void setIssueOrgan(String issueOrgan) {
		this.issueOrgan = issueOrgan;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
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
