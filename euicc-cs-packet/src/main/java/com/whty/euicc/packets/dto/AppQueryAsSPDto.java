package com.whty.euicc.packets.dto;

/**
 * 第三方应用信息
 * 
 * @author Ocea
 *
 */
public class AppQueryAsSPDto {

	/**
	 * 应用AID
	 */
	private String appAID;

	/**
	 * 应用版本号
	 */
	private String appVersion;

	/**
	 * 合作方机构编码
	 */
	private String partnerOrgCode;

	/**
	 * 应用名称
	 */
	private String appName;

	/**
	 * 应用类型
	 */
	private String appType;

	/**
	 * 应用描述
	 */
	private String appDesc;

	/**
	 * 应用范围
	 */
	private String scope;

	/**
	 * 有效日期
	 */
	private String validate;

	/**
	 * 所需RAM空间
	 */
	private String ramSpace;

	/**
	 * 所需ROM空间
	 */
	private String nvmSpace;

	/**
	 * 应用权限
	 */
	private String appPermission;

	/**
	 * 应用安全等级
	 */
	private String appSecurityLevel;

	/**
	 * 检测机构代码
	 */
	private String chkOrgCode;

	/**
	 * 应用注册时间
	 */
	private String appRegisterTime;

	/**
	 * 应用ICO图标地址
	 */
	private String icoURL;

	/**
	 * 应用Logo图片地址
	 */
	private String logoURL;

	/**
	 * APK下载地址
	 */
	private String apkURL;
	
	/**
	 * 辅助安全域AID
	 */
	private String sdAID;
	
	public String getAppAID() {
		return appAID;
	}

	public void setAppAID(String appAID) {
		this.appAID = appAID;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getPartnerOrgCode() {
		return partnerOrgCode;
	}

	public void setPartnerOrgCode(String partnerOrgCode) {
		this.partnerOrgCode = partnerOrgCode;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public String getRamSpace() {
		return ramSpace;
	}

	public void setRamSpace(String ramSpace) {
		this.ramSpace = ramSpace;
	}

	public String getNvmSpace() {
		return nvmSpace;
	}

	public void setNvmSpace(String nvmSpace) {
		this.nvmSpace = nvmSpace;
	}

	public String getAppPermission() {
		return appPermission;
	}

	public void setAppPermission(String appPermission) {
		this.appPermission = appPermission;
	}

	public String getAppSecurityLevel() {
		return appSecurityLevel;
	}

	public void setAppSecurityLevel(String appSecurityLevel) {
		this.appSecurityLevel = appSecurityLevel;
	}

	public String getChkOrgCode() {
		return chkOrgCode;
	}

	public void setChkOrgCode(String chkOrgCode) {
		this.chkOrgCode = chkOrgCode;
	}

	public String getAppRegisterTime() {
		return appRegisterTime;
	}

	public void setAppRegisterTime(String appRegisterTime) {
		this.appRegisterTime = appRegisterTime;
	}

	public String getIcoURL() {
		return icoURL;
	}

	public void setIcoURL(String icoURL) {
		this.icoURL = icoURL;
	}

	public String getLogoURL() {
		return logoURL;
	}

	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}

	public String getApkURL() {
		return apkURL;
	}

	public void setApkURL(String apkURL) {
		this.apkURL = apkURL;
	}

	public String getSdAID() {
		return sdAID;
	}

	public void setSdAID(String sdAID) {
		this.sdAID = sdAID;
	}

}
