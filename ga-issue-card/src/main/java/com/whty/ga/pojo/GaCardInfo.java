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
 * @ClassName GaCardInfo
 * @author Administrator
 * @date 2017-3-3 上午9:51:00
 * @Description TODO(卡信息)
 */
@Entity
@Table(name="ga_card_info")
public class GaCardInfo {

	@Id
	@GenericGenerator(name="generator",strategy="uuid")
	@GeneratedValue(generator="generator")
	@Column(name="id",unique=true,nullable=false)
	private String id;
	
	@Column(name="card_no")
	private String cardNo;
	
	@ManyToOne(cascade ={CascadeType.ALL},fetch = FetchType.LAZY)
	@JoinColumn(name="person_id")
	private GaPersonInfo person;
	
	@ManyToOne(cascade ={CascadeType.ALL},fetch = FetchType.LAZY)
	@JoinColumn(name="house_id")
	private GaHouseInfo house;
	
	@Column(name="relation")
	private String relation;
	
	@Column(name="card_type_code")
	private String cardTypeCode;
	
	@Column(name="expiry_time")
	@Temporal(TemporalType.DATE)
	private Date expiryTime;
	
	@Column(name="black_list_flag")
	private String blackListFlag;
	
	@Column(name="issuer")
	private String issuer;
	
	@Column(name="issue_date")
	@Temporal(TemporalType.DATE)
	private Date issueDate;
	
	@Column(name="in_limit_count")
	private String inLimitCount;
	
	@Column(name="out_limit_count")
	private String outLimitCount;
	
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
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	

	/**
	 * @return the person
	 */
	public GaPersonInfo getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(GaPersonInfo person) {
		this.person = person;
	}

	/**
	 * @return the house
	 */
	public GaHouseInfo getHouse() {
		return house;
	}

	/**
	 * @param house the house to set
	 */
	public void setHouse(GaHouseInfo house) {
		this.house = house;
	}

	/**
	 * @return the relation
	 */
	public String getRelation() {
		return relation;
	}

	/**
	 * @param relation the relation to set
	 */
	public void setRelation(String relation) {
		this.relation = relation;
	}

	/**
	 * @return the cardTypeCode
	 */
	public String getCardTypeCode() {
		return cardTypeCode;
	}

	/**
	 * @param cardTypeCode the cardTypeCode to set
	 */
	public void setCardTypeCode(String cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
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
	 * @return the blackListFlag
	 */
	public String getBlackListFlag() {
		return blackListFlag;
	}

	/**
	 * @param blackListFlag the blackListFlag to set
	 */
	public void setBlackListFlag(String blackListFlag) {
		this.blackListFlag = blackListFlag;
	}

	/**
	 * @return the issuer
	 */
	public String getIssuer() {
		return issuer;
	}

	/**
	 * @param issuer the issuer to set
	 */
	public void setIssuer(String issuer) {
		this.issuer = issuer;
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
	 * @return the inLimitCount
	 */
	public String getInLimitCount() {
		return inLimitCount;
	}

	/**
	 * @param inLimitCount the inLimitCount to set
	 */
	public void setInLimitCount(String inLimitCount) {
		this.inLimitCount = inLimitCount;
	}

	/**
	 * @return the outLimitCount
	 */
	public String getOutLimitCount() {
		return outLimitCount;
	}

	/**
	 * @param outLimitCount the outLimitCount to set
	 */
	public void setOutLimitCount(String outLimitCount) {
		this.outLimitCount = outLimitCount;
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
