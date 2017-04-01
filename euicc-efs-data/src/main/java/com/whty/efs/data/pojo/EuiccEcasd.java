package com.whty.efs.data.pojo;

public class EuiccEcasd {
    private String ecasdId;

    private String aid;

    private String sin;

    private String sdin;

    private String role;

    private String certEcasdEcka;

    public String getEcasdId() {
        return ecasdId;
    }

    public void setEcasdId(String ecasdId) {
        this.ecasdId = ecasdId == null ? null : ecasdId.trim();
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid == null ? null : aid.trim();
    }

    public String getSin() {
        return sin;
    }

    public void setSin(String sin) {
        this.sin = sin == null ? null : sin.trim();
    }

    public String getSdin() {
        return sdin;
    }

    public void setSdin(String sdin) {
        this.sdin = sdin == null ? null : sdin.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getCertEcasdEcka() {
        return certEcasdEcka;
    }

    public void setCertEcasdEcka(String certEcasdEcka) {
        this.certEcasdEcka = certEcasdEcka == null ? null : certEcasdEcka.trim();
    }
}