/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oauth.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 状态信息Entity
 * @author liuwsh
 * @version 2017-03-08
 */
public class OauthInterfaceInfo extends DataEntity<OauthInterfaceInfo> {
	
	private static final long serialVersionUID = 1L;
	private String recordId;		// record_id
	private String eid;		// eid
	private String interfaceType;		// interface_type
	private String msisdn;		// msisdn
	private Date operatorTime;		// operator_time
	private String retResult;		// ret_result
	private String transactionId;		// transaction_id
	private String finishTime;
	
	public OauthInterfaceInfo() {
		super();
	}

	public OauthInterfaceInfo(String id){
		super(id);
	}

	@Length(min=1, max=255, message="record_id长度必须介于 1 和 255 之间")
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	@Length(min=0, max=32, message="eid长度必须介于 0 和 32 之间")
	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}
	
	@Length(min=0, max=2, message="interface_type长度必须介于 0 和 2 之间")
	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	
	@Length(min=0, max=32, message="msisdn长度必须介于 0 和 32 之间")
	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}
	
	@Length(min=0, max=255, message="ret_result长度必须介于 0 和 255 之间")
	public String getRetResult() {
		return retResult;
	}

	public void setRetResult(String retResult) {
		this.retResult = retResult;
	}
	
	@Length(min=0, max=32, message="transaction_id长度必须介于 0 和 32 之间")
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the finishTime
	 */
	public String getFinishTime() {
		return finishTime;
	}

	/**
	 * @param finishTime the finishTime to set
	 */
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	
	
	
}