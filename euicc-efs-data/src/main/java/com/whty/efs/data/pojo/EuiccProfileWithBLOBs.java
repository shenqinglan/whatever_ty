package com.whty.efs.data.pojo;

public class EuiccProfileWithBLOBs extends EuiccProfile {
    private String asnFile;

    private String derFile;

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