package com.whty.efs.data.pojo;

import java.util.Date;

public class EuiccCard {
    private String eid;

    private String eumId;

    private String smsrId;

    private String ecasdId;

    private String signatureId;

    private Date productionDate;

    private String platformType;

    private String platformVersion;

    private String remainingMemory;

    private String availablememoryforprofiles;

    private Integer count;

    private String phoneNo;

    private Date lastAuditDate;

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
    }

    public String getEumId() {
        return eumId;
    }

    public void setEumId(String eumId) {
        this.eumId = eumId == null ? null : eumId.trim();
    }

    public String getSmsrId() {
        return smsrId;
    }

    public void setSmsrId(String smsrId) {
        this.smsrId = smsrId == null ? null : smsrId.trim();
    }

    public String getEcasdId() {
        return ecasdId;
    }

    public void setEcasdId(String ecasdId) {
        this.ecasdId = ecasdId == null ? null : ecasdId.trim();
    }

    public String getSignatureId() {
        return signatureId;
    }

    public void setSignatureId(String signatureId) {
        this.signatureId = signatureId == null ? null : signatureId.trim();
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType == null ? null : platformType.trim();
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion == null ? null : platformVersion.trim();
    }

    public String getRemainingMemory() {
        return remainingMemory;
    }

    public void setRemainingMemory(String remainingMemory) {
        this.remainingMemory = remainingMemory == null ? null : remainingMemory.trim();
    }

    public String getAvailablememoryforprofiles() {
        return availablememoryforprofiles;
    }

    public void setAvailablememoryforprofiles(String availablememoryforprofiles) {
        this.availablememoryforprofiles = availablememoryforprofiles == null ? null : availablememoryforprofiles.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
    }

    public Date getLastAuditDate() {
        return lastAuditDate;
    }

    public void setLastAuditDate(Date lastAuditDate) {
        this.lastAuditDate = lastAuditDate;
    }
}