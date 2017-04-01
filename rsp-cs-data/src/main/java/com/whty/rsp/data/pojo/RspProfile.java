package com.whty.rsp.data.pojo;

public class RspProfile {
    private String iccid;

    private String isdPAid;

    private String eid;

    private String profileState;

    private String profileNickName;

    private String serviceProviderName;

    private String profileName;

    private String iconType;

    private String profileClass;

    private String notifEvent;

    private String notifAddress;

    private String profileOwner;

    private String smdpProprietaryData;

    private String ppr;

    private String profileType;
    
    private String hashCc;

    private String matchingId;

    private String msisdn;

    private String lockVersion;

	public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getIsdPAid() {
        return isdPAid;
    }

    public void setIsdPAid(String isdPAid) {
        this.isdPAid = isdPAid == null ? null : isdPAid.trim();
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
    }

    public String getProfileState() {
        return profileState;
    }

    public void setProfileState(String profileState) {
        this.profileState = profileState == null ? null : profileState.trim();
    }

    public String getProfileNickName() {
        return profileNickName;
    }

    public void setProfileNickName(String profileNickName) {
        this.profileNickName = profileNickName == null ? null : profileNickName.trim();
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName == null ? null : serviceProviderName.trim();
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName == null ? null : profileName.trim();
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType == null ? null : iconType.trim();
    }

    public String getProfileClass() {
        return profileClass;
    }

    public void setProfileClass(String profileClass) {
        this.profileClass = profileClass == null ? null : profileClass.trim();
    }

    public String getNotifEvent() {
        return notifEvent;
    }

    public void setNotifEvent(String notifEvent) {
        this.notifEvent = notifEvent == null ? null : notifEvent.trim();
    }

    public String getNotifAddress() {
        return notifAddress;
    }

    public void setNotifAddress(String notifAddress) {
        this.notifAddress = notifAddress == null ? null : notifAddress.trim();
    }

    public String getProfileOwner() {
        return profileOwner;
    }

    public void setProfileOwner(String profileOwner) {
        this.profileOwner = profileOwner == null ? null : profileOwner.trim();
    }

    public String getSmdpProprietaryData() {
        return smdpProprietaryData;
    }

    public void setSmdpProprietaryData(String smdpProprietaryData) {
        this.smdpProprietaryData = smdpProprietaryData == null ? null : smdpProprietaryData.trim();
    }

    public String getPpr() {
        return ppr;
    }

    public void setPpr(String ppr) {
        this.ppr = ppr == null ? null : ppr.trim();
    }

    public String getProfileType() {
        return profileType;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType == null ? null : profileType.trim();
    }

	public String getHashCc() {
		return hashCc;
	}

    public void setHashCc(String hashCc) {
        this.hashCc = hashCc == null ? null : hashCc.trim();
    }

    public String getMatchingId() {
        return matchingId;
    }

    public void setMatchingId(String matchingId) {
        this.matchingId = matchingId == null ? null : matchingId.trim();
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn == null ? null : msisdn.trim();
    }

    public String getLockVersion() {
        return lockVersion;
    }

    public void setLockVersion(String lockVersion) {
        this.lockVersion = lockVersion == null ? null : lockVersion.trim();
    }
}