package com.thinkgem.jeesite.modules.oauth.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 测试数据Entity
 * @author liuwsh
 * @version 2017-03-07
 */
public class InputEntity extends DataEntity<InputEntity> {
	private String type;
	private String phoneNo;
	private String format;
	private String authType;
	private String authData;
	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}
	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}
	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	/**
	 * @return the authType
	 */
	public String getAuthType() {
		return authType;
	}
	/**
	 * @param authType the authType to set
	 */
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	/**
	 * @return the authData
	 */
	public String getAuthData() {
		return authData;
	}
	/**
	 * @param authData the authData to set
	 */
	public void setAuthData(String authData) {
		this.authData = authData;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
}
