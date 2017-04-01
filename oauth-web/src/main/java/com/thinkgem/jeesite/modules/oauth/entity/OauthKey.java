/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oauth.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 密钥信息Entity
 * @author liuwsh
 * @version 2017-03-07
 */
public class OauthKey extends DataEntity<OauthKey> {
	
	private static final long serialVersionUID = 1L;
	private String eid;		// 卡id
	private String msisdn;		// 号码
	private String iccid;
	private String index;		// 密钥序号
	private String version;		// 加解密密钥
	private String macKey;		// 计算mac的密钥
	
	
	public OauthKey() {
		super();
	}

	public OauthKey(String id){
		super(id);
	}

	@Length(min=1, max=32, message="EID长度必须介于 1 和 32 之间")
	@ExcelField(title="EID", align=2, sort=1)
	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}
	
	/**
	 * @return the iccid
	 */
	@Length(min=1, max=32, message="ICCID长度必须介于 1 和 32 之间")
	@ExcelField(title="ICCID", align=2, sort=10)
	public String getIccid() {
		return iccid;
	}

	/**
	 * @param iccid the iccid to set
	 */
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	
	@Length(min=0, max=32, message="号码长度必须介于 0 和 32 之间")
	@ExcelField(title="手机号", align=2, sort=20)
	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	
	@Length(min=1, max=32, message="密钥序号长度必须介于 1和 32 之间")
	@ExcelField(title="密钥序号", align=2, sort=30)
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
	
	
	@Length(min=1, max=32, message="Version长度必须介于 1 和 32 之间")
	@ExcelField(title="Version", align=2, sort=40)
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	@Length(min=1, max=64, message="密钥长度必须介于 1 和 64之间")
	@ExcelField(title="密钥", align=2, sort=50)
	public String getMacKey() {
		return macKey;
	}

	public void setMacKey(String macKey) {
		this.macKey = macKey;
	}
	
	
	
}