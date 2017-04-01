// Copyright (C) 2012 WHTY
package com.whty.euicc.packets.dto;

import java.util.Date;

/**
 * 应用信息表
 */
public class AppQueryDto  {


    /**
     * 应用名称
     */
    private String appAID;
    
    private String appVersion;
    
    private String seID;
    
    private String appName;
    
    private String appType;
    
    private String appDesc;
    
    private String providerID;
    
    private String providerName;
    
    private Date onlineDate;
    
    private String icoUrl;
    
    private String logoUrl;
    
    private String appStatus;

    private String apkUrl;
    
    private String personalFlag;

    private String appPackageName;

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
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


	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public Date getOnlineDate() {
		return onlineDate;
	}

	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}

	public String getIcoUrl() {
		return icoUrl;
	}

	public void setIcoUrl(String icoUrl) {
		this.icoUrl = icoUrl;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	
	public String getAppAID() {
		return appAID ;
	}

	
	public void setAppAID(String appAID) {
		this.appAID = appAID ;
	}

	
	public String getSeID() {
		return seID ;
	}

	
	public void setSeID(String seID) {
		this.seID = seID ;
	}

	
	public String getProviderID() {
		return providerID ;
	}

	
	public void setProviderID(String providerID) {
		this.providerID = providerID ;
	}

	
	public String getAppStatus() {
		return appStatus ;
	}

	
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus ;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public String getPersonalFlag() {
		return personalFlag;
	}

	public void setPersonalFlag(String personalFlag) {
		this.personalFlag = personalFlag;
	}

	public String getAppPackageName() {
		return appPackageName;
	}

	public void setAppPackageName(String appPackageName) {
		this.appPackageName = appPackageName;
	}
}
