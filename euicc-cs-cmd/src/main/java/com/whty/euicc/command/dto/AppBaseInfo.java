package com.whty.euicc.command.dto;

/**
 * 在原子任务中需要传递的应用基本信息
 * 
 * @author Ocea
 * 
 */
public class AppBaseInfo {

	/**
	 * 应用AID
	 */
	private String appAid;

	/**
	 * 应用版本
	 */
	private String appVersion;

	/**
	 * 应用所属安全域
	 */
	private String sdAid;

	/**
	 * 应用类型
	 */
	private String appType;
	
	/**
	 * 应用所在地
	 */
	private String appSite;
	
	/**
	 * 应用权限
	 */
	private String privilege;
	
	/**
	 * 个人化标识
	 */
	private String personalFlag;
	
	/**
	 * 应用提供方
	 */
	private String spCode;
	
	public String getPersonalFlag() {
		return personalFlag;
	}

	public void setPersonalFlag(String personalFlag) {
		this.personalFlag = personalFlag;
	}

	public String getAppAid() {
		return appAid;
	}

	public void setAppAid(String appAid) {
		this.appAid = appAid;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getSdAid() {
		return sdAid;
	}

	public void setSdAid(String sdAid) {
		this.sdAid = sdAid;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAppSite() {
		return appSite;
	}

	public void setAppSite(String appSite) {
		this.appSite = appSite;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getSpCode() {
		return spCode;
	}

	public void setSpCode(String spCode) {
		this.spCode = spCode;
	}
}
