package com.whty.euicc.data.pojo;

public class EuiccMno {
    private String mnoId;

    private String mnoName;

    private String mnoCode;

    private String cert;

    public String getMnoId() {
        return mnoId;
    }

    public void setMnoId(String mnoId) {
        this.mnoId = mnoId == null ? null : mnoId.trim();
    }

    public String getMnoName() {
        return mnoName;
    }

    public void setMnoName(String mnoName) {
        this.mnoName = mnoName == null ? null : mnoName.trim();
    }

    public String getMnoCode() {
        return mnoCode;
    }

    public void setMnoCode(String mnoCode) {
        this.mnoCode = mnoCode == null ? null : mnoCode.trim();
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert == null ? null : cert.trim();
    }
}