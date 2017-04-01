package com.whty.euicc.data.pojo;

public class EuiccSmSr {
    private String smsrId;

    private String smsrName;

    private String smsrUrl;

    private String privateKey;

    private String cert;

    public String getSmsrId() {
        return smsrId;
    }

    public void setSmsrId(String smsrId) {
        this.smsrId = smsrId == null ? null : smsrId.trim();
    }

    public String getSmsrName() {
        return smsrName;
    }

    public void setSmsrName(String smsrName) {
        this.smsrName = smsrName == null ? null : smsrName.trim();
    }

    public String getSmsrUrl() {
        return smsrUrl;
    }

    public void setSmsrUrl(String smsrUrl) {
        this.smsrUrl = smsrUrl == null ? null : smsrUrl.trim();
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey == null ? null : privateKey.trim();
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert == null ? null : cert.trim();
    }
}