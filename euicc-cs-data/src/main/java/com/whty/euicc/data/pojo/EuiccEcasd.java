package com.whty.euicc.data.pojo;

public class EuiccEcasd {
    private String ecasdId;

    private String certEcasdEcka;

    public String getEcasdId() {
        return ecasdId;
    }

    public void setEcasdId(String ecasdId) {
        this.ecasdId = ecasdId == null ? null : ecasdId.trim();
    }

    public String getCertEcasdEcka() {
        return certEcasdEcka;
    }

    public void setCertEcasdEcka(String certEcasdEcka) {
        this.certEcasdEcka = certEcasdEcka == null ? null : certEcasdEcka.trim();
    }
}