/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oauth.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 卡信息Entity
 * @author liuwsh
 * @version 2017-03-07
 */
public class OauthCard extends DataEntity<OauthCard> {
	
	private static final long serialVersionUID = 1L;
	private String imsi;		// 国际移动用户标识
	private String msisdn;		// 手机号
	private String count;		// 卡片计数器
	private String cardmanufacturerid;		// 卡商标识
	private String eid;		// 卡id
	private String cardState;		// 卡片注册状态
	private String remarks;
	private String iccid;
	
	public OauthCard() {
		super();
	}

	public OauthCard(String id){
		super(id);
	}

	@Length(min=0, max=32, message="国际移动用户标识长度必须介于 0 和 32 之间")
	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	
	@Length(min=0, max=32, message="手机号长度必须介于 0 和 32 之间")
	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	
	@Length(min=0, max=11, message="卡片计数器长度必须介于 0 和 11 之间")
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
	@Length(min=0, max=2, message="卡商标识长度必须介于 0 和 2 之间")
	public String getCardmanufacturerid() {
		return cardmanufacturerid;
	}

	public void setCardmanufacturerid(String cardmanufacturerid) {
		this.cardmanufacturerid = cardmanufacturerid;
	}
	
	@Length(min=0, max=32, message="卡id长度必须介于 0 和 32 之间")
	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}
	
	@Length(min=0, max=2, message="卡片注册状态长度必须介于 0 和 2 之间")
	public String getCardState() {
		return cardState;
	}

	public void setCardState(String cardState) {
		this.cardState = cardState;
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

	/**
	 * @return the iccid
	 */
	public String getIccid() {
		return iccid;
	}

	/**
	 * @param iccid the iccid to set
	 */
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	
	
	
}