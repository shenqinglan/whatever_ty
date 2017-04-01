package com.whty.efs.data.pojo;

public class EuiccIsdrKeyset {
    private String keysetId;

    private String rId;

    private String version;

    private String type;

    private String cntr;

    private String keyOrCertificate;

    public String getKeysetId() {
        return keysetId;
    }

    public void setKeysetId(String keysetId) {
        this.keysetId = keysetId == null ? null : keysetId.trim();
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId == null ? null : rId.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getCntr() {
        return cntr;
    }

    public void setCntr(String cntr) {
        this.cntr = cntr == null ? null : cntr.trim();
    }

    public String getKeyOrCertificate() {
        return keyOrCertificate;
    }

    public void setKeyOrCertificate(String keyOrCertificate) {
        this.keyOrCertificate = keyOrCertificate == null ? null : keyOrCertificate.trim();
    }
}