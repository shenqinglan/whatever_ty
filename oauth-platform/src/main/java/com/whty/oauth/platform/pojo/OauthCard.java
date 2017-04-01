/**
 * Copyright (c) 2015-2017.
 * All rights reserved.
 * 
 * Created on 2017-3-7
 * Id: EuiccCard.java,v 1.0 2017-3-7 下午4:01:44 Administrator
 */
package com.whty.oauth.platform.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @ClassName EuiccCard
 * @author Administrator
 * @date 2017-3-7 下午4:01:44
 * @Description TODO(卡片pojo)
 */
@Entity
@Table(name = "oauth_card")
public class OauthCard {

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	private String id;

	@Column(name = "imsi", length = 32)
	private String imsi; // 国际移动用户标识

	@Column(name = "msisdn", length = 32)
	private String msisdn; // 手机号

	@Column(name = "count", length = 10)
	private Integer count; // 计数器

	@Column(name = "cardmanufacturerid")
	private String cardManufacturerId; // 卡商标识

	@Column(name = "eid", length = 32)
	private String eid; // 卡ID

	@Column(name = "card_state", length = 2)
	private String cardState; // 卡片注册状态

	@Column(name = "iccid", length = 32)
	private String iccid; // 卡片iccid

	@Column(name = "cardid", length = 24)
	private String cardid; // 空卡序列号
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @return the cardManufacturerId
	 */
	public String getCardManufacturerId() {
		return cardManufacturerId;
	}

	/**
	 * @param cardManufacturerId
	 *            the cardManufacturerId to set
	 */
	public void setCardManufacturerId(String cardManufacturerId) {
		this.cardManufacturerId = cardManufacturerId;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getCardState() {
		return cardState;
	}

	public void setCardState(String cardState) {
		this.cardState = cardState;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
}
