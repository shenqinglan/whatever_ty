package com.whty.euicc.data.pojo;

public class EuiccSmDp {
    private String smdpId;

    private String smdpName;

    private String smdpUrl;

    private String privateKey;

    private String cert;

    public String getSmdpId() {
        return smdpId;
    }

    public void setSmdpId(String smdpId) {
        this.smdpId = smdpId == null ? null : smdpId.trim();
    }

    public String getSmdpName() {
        return smdpName;
    }

    public void setSmdpName(String smdpName) {
        this.smdpName = smdpName == null ? null : smdpName.trim();
    }

    public String getSmdpUrl() {
        return smdpUrl;
    }

    public void setSmdpUrl(String smdpUrl) {
        this.smdpUrl = smdpUrl == null ? null : smdpUrl.trim();
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