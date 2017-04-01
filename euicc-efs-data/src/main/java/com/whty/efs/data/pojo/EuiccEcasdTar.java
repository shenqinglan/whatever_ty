package com.whty.efs.data.pojo;

public class EuiccEcasdTar {
    private String tarId;

    private String ecasdId;

    private String tar;

    public String getTarId() {
        return tarId;
    }

    public void setTarId(String tarId) {
        this.tarId = tarId == null ? null : tarId.trim();
    }

    public String getEcasdId() {
        return ecasdId;
    }

    public void setEcasdId(String ecasdId) {
        this.ecasdId = ecasdId == null ? null : ecasdId.trim();
    }

    public String getTar() {
        return tar;
    }

    public void setTar(String tar) {
        this.tar = tar == null ? null : tar.trim();
    }
}