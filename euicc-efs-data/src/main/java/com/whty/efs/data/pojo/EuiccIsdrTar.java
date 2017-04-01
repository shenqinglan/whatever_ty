package com.whty.efs.data.pojo;

public class EuiccIsdrTar {
    private String tarId;

    private String rId;

    private String tar;

    public String getTarId() {
        return tarId;
    }

    public void setTarId(String tarId) {
        this.tarId = tarId == null ? null : tarId.trim();
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId == null ? null : rId.trim();
    }

    public String getTar() {
        return tar;
    }

    public void setTar(String tar) {
        this.tar = tar == null ? null : tar.trim();
    }
}