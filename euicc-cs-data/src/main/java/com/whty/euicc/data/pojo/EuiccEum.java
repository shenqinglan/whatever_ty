package com.whty.euicc.data.pojo;

public class EuiccEum {
    private String eumId;

    private String eumName;

    private String eumCode;

    private String cert;

    public String getEumId() {
        return eumId;
    }

    public void setEumId(String eumId) {
        this.eumId = eumId == null ? null : eumId.trim();
    }

    public String getEumName() {
        return eumName;
    }

    public void setEumName(String eumName) {
        this.eumName = eumName == null ? null : eumName.trim();
    }

    public String getEumCode() {
        return eumCode;
    }

    public void setEumCode(String eumCode) {
        this.eumCode = eumCode == null ? null : eumCode.trim();
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert == null ? null : cert.trim();
    }
}