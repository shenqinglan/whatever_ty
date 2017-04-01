package com.whty.rsp.data.pojo;

public class RspProfileWithBLOBs extends RspProfile {
    private String icon;

    private String asnFile;

    private String derFile;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getAsnFile() {
        return asnFile;
    }

    public void setAsnFile(String asnFile) {
        this.asnFile = asnFile == null ? null : asnFile.trim();
    }

    public String getDerFile() {
        return derFile;
    }

    public void setDerFile(String derFile) {
        this.derFile = derFile == null ? null : derFile.trim();
    }
}