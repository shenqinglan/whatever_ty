package com.whty.efs.data.pojo;

import java.util.Date;

public class EuiccAuditTrailRecord {
    private String auditId;

    private String eid;

    private String iccid;

    private String isdPAid;

    private Date operationDate;

    private String operationType;

    private String requesterId;

    private String imei;

    private String meid;

    private String smsrId;

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId == null ? null : auditId.trim();
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
    }

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

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType == null ? null : operationType.trim();
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId == null ? null : requesterId.trim();
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getMeid() {
        return meid;
    }

    public void setMeid(String meid) {
        this.meid = meid == null ? null : meid.trim();
    }

    public String getSmsrId() {
        return smsrId;
    }

    public void setSmsrId(String smsrId) {
        this.smsrId = smsrId == null ? null : smsrId.trim();
    }
}