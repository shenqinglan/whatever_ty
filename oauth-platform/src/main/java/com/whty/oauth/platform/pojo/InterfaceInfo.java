/**
 * Copyright (c) 2015-2017.
 * All rights reserved.
 * 
 * Created on 2017-3-7
 * Id: TraceInfo.java,v 1.0 2017-3-7 下午4:03:31 Administrator
 */
package com.whty.oauth.platform.pojo;

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
 * @ClassName TraceInfo
 * @author Administrator
 * @date 2017-3-7 下午4:03:31
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Entity
@Table(name = "oauth_interface_info")
public class InterfaceInfo {

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name = "record_id", unique = true, nullable = false)
	private String recordId; // 记录ID

	@Column(name = "interface_type", length = 2)
	private String interfaceType; // 接口类型

	@Column(name = "ret_result")
	private String retResult; // 返回结果(大文本)

	@Column(name = "transactionId", length = 32)
	private String transactionId; // 交易编号

	@Column(name = "operator_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date operatorTime; // 操作时间

	@Column(name = "eid", length = 32)
	private String eid; // 卡标识

	@Column(name = "msisdn", length = 32)
	private String msisdn; // 电话号码

	@Column(name = "finish_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishTime; // 卡处理完成时间

	@Column(name = "cardmanufacturerid")
	private String cardManufacturerId; // 卡商标识
	
	@Column(name="authdata_in")
	private String authdataIn;
	
	@Column(name="authdata_out")
	private String authdataOut;
	
	@Column(name="down_record")
	private String downRecord;
	
	@Column(name="up_record")
	private String upRecord;

	/**
	 * @return the recordId
	 */
	public String getRecordId() {
		return recordId;
	}

	/**
	 * @param recordId
	 *            the recordId to set
	 */
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	/**
	 * @return the interfaceType
	 */
	public String getInterfaceType() {
		return interfaceType;
	}

	/**
	 * @param interfaceType
	 *            the interfaceType to set
	 */
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	/**
	 * @return the retResult
	 */
	public String getRetResult() {
		return retResult;
	}

	/**
	 * @param retResult
	 *            the retResult to set
	 */
	public void setRetResult(String retResult) {
		this.retResult = retResult;
	}

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId
	 *            the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the operatorTime
	 */
	public Date getOperatorTime() {
		return operatorTime;
	}

	/**
	 * @param operatorTime
	 *            the operatorTime to set
	 */
	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}

	/**
	 * @return the eid
	 */
	public String getEid() {
		return eid;
	}

	/**
	 * @param eid
	 *            the eid to set
	 */
	public void setEid(String eid) {
		this.eid = eid;
	}

	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}

	/**
	 * @param msisdn
	 *            the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/**
	 * @return the finishTime
	 */
	public Date getFinishTime() {
		return finishTime;
	}

	/**
	 * @param finishTime
	 *            the finishTime to set
	 */
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * @return the cardManufacturerId
	 */
	public String getCardManufacturerId() {
		return cardManufacturerId;
	}

	/**
	 * @param cardManufacturerId the cardManufacturerId to set
	 */
	public void setCardManufacturerId(String cardManufacturerId) {
		this.cardManufacturerId = cardManufacturerId;
	}

	public String getAuthdataIn() {
		return authdataIn;
	}

	public void setAuthdataIn(String authdataIn) {
		this.authdataIn = authdataIn;
	}

	public String getAuthdataOut() {
		return authdataOut;
	}

	public void setAuthdataOut(String authdataOut) {
		this.authdataOut = authdataOut;
	}

	/**
	 * @return the downRecord
	 */
	public String getDownRecord() {
		return downRecord;
	}

	/**
	 * @param downRecord the downRecord to set
	 */
	public void setDownRecord(String downRecord) {
		this.downRecord = downRecord;
	}

	/**
	 * @return the upRecord
	 */
	public String getUpRecord() {
		return upRecord;
	}

	/**
	 * @param upRecord the upRecord to set
	 */
	public void setUpRecord(String upRecord) {
		this.upRecord = upRecord;
	}
}
