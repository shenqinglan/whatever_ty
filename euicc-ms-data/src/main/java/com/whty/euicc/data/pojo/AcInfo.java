package com.whty.euicc.data.pojo;

public class AcInfo {
    private Integer id;

    private String hash;

    private String apdu;

    private String nfc;

    private String acAid;

    private String status;

    private String acIndex;

    private String appAid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash == null ? null : hash.trim();
    }

    public String getApdu() {
        return apdu;
    }

    public void setApdu(String apdu) {
        this.apdu = apdu == null ? null : apdu.trim();
    }

    public String getNfc() {
        return nfc;
    }

    public void setNfc(String nfc) {
        this.nfc = nfc == null ? null : nfc.trim();
    }

    public String getAcAid() {
        return acAid;
    }

    public void setAcAid(String acAid) {
        this.acAid = acAid == null ? null : acAid.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getAcIndex() {
        return acIndex;
    }

    public void setAcIndex(String acIndex) {
        this.acIndex = acIndex == null ? null : acIndex.trim();
    }

    public String getAppAid() {
        return appAid;
    }

    public void setAppAid(String appAid) {
        this.appAid = appAid == null ? null : appAid.trim();
    }
}