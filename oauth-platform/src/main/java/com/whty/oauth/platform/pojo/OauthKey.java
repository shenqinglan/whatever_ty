/**
 * Copyright (c) 2015-2017.
 * All rights reserved.
 * 
 * Created on 2017-3-13
 * Id: OauthKey.java,v 1.0 2017-3-13 下午4:33:04 Administrator
 */
package com.whty.oauth.platform.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @ClassName OauthKey
 * @author Administrator
 * @date 2017-3-13 下午4:33:04
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@Entity
@Table(name="oauth_key")
public class OauthKey {

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false)
	private String id;
	
	@Column(name="key_index")
	private String keyIndex;
	
	@Column(name="version")
	private String version;
	
	@Column(name="mac_key",length=255)
	private String macKey;
	
	@Column(name="eid")
	private String eid;
	
	@Column(name="msisdn")
	private String msisdn;
	
	@Column(name="iccid")
	private String iccid;

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
	 * @return the keyIndex
	 */
	public String getKeyIndex() {
		return keyIndex;
	}

	/**
	 * @param keyIndex the keyIndex to set
	 */
	public void setKeyIndex(String keyIndex) {
		this.keyIndex = keyIndex;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the macKey
	 */
	public String getMacKey() {
		return macKey;
	}

	/**
	 * @param macKey the macKey to set
	 */
	public void setMacKey(String macKey) {
		this.macKey = macKey;
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
