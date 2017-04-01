package com.thinkgem.jeesite.modules.oauth.entity;

import java.io.Serializable;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 测试数据Entity
 * @author liuwsh
 * @version 2017-03-07
 */
public class OutputEntity extends DataEntity<OutputEntity> {
	
	private static final long serialVersionUID = 1L;
	private String respcode;
	private String respdesc;
	private String accepttime;
	private String transactionId;
	private String eid;		// eid
	private String interfaceType;		// interface_type
	private String msisdn;		// msisdn
	private String operatorTime;		// operator_time
	private String retResult;		// ret_result
	private String finishTime;
	/**
	 * @return the respcode
	 */
	public String getRespcode() {
		return respcode;
	}
	/**
	 * @param respcode the respcode to set
	 */
	public void setRespcode(String respcode) {
		this.respcode = respcode;
	}
	/**
	 * @return the respdesc
	 */
	public String getRespdesc() {
		return respdesc;
	}
	/**
	 * @param respdesc the respdesc to set
	 */
	public void setRespdesc(String respdesc) {
		this.respdesc = respdesc;
	}
	/**
	 * @return the accepttime
	 */
	public String getAccepttime() {
		return accepttime;
	}
	/**
	 * @param accepttime the accepttime to set
	 */
	public void setAccepttime(String accepttime) {
		this.accepttime = accepttime;
	}
	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}
	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	/**
	 * @return the eid
	 */
	public String getEid() {
		return eid;
	}
	/**
	 * @param eid the eid to set
	 */
	public void setEid(String eid) {
		this.eid = eid;
	}
	/**
	 * @return the interfaceType
	 */
	public String getInterfaceType() {
		return interfaceType;
	}
	/**
	 * @param interfaceType the interfaceType to set
	 */
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}
	/**
	 * @param msisdn the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	/**
	 * @return the operatorTime
	 */
	public String getOperatorTime() {
		return operatorTime;
	}
	/**
	 * @param operatorTime the operatorTime to set
	 */
	public void setOperatorTime(String operatorTime) {
		this.operatorTime = operatorTime;
	}
	/**
	 * @return the retResult
	 */
	public String getRetResult() {
		return retResult;
	}
	/**
	 * @param retResult the retResult to set
	 */
	public void setRetResult(String retResult) {
		this.retResult = retResult;
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
