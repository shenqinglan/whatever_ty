package com.whty.euicc.data.pojo;

public class EuiccCapabilities {
    private String capabilitieId;

    private String eid;

    private String catTpSupport;

    private String catTpVersion;

    private String httpSupport;

    private String httpVersion;

    private String securePacketVersion;

    private String remoteProvisioningVersion;

    public String getCapabilitieId() {
        return capabilitieId;
    }

    public void setCapabilitieId(String capabilitieId) {
        this.capabilitieId = capabilitieId == null ? null : capabilitieId.trim();
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
    }

    public String getCatTpSupport() {
        return catTpSupport;
    }

    public void setCatTpSupport(String catTpSupport) {
        this.catTpSupport = catTpSupport == null ? null : catTpSupport.trim();
    }

    public String getCatTpVersion() {
        return catTpVersion;
    }

    public void setCatTpVersion(String catTpVersion) {
        this.catTpVersion = catTpVersion == null ? null : catTpVersion.trim();
    }

    public String getHttpSupport() {
        return httpSupport;
    }

    public void setHttpSupport(String httpSupport) {
        this.httpSupport = httpSupport == null ? null : httpSupport.trim();
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion == null ? null : httpVersion.trim();
    }

    public String getSecurePacketVersion() {
        return securePacketVersion;
    }

    public void setSecurePacketVersion(String securePacketVersion) {
        this.securePacketVersion = securePacketVersion == null ? null : securePacketVersion.trim();
    }

    public String getRemoteProvisioningVersion() {
        return remoteProvisioningVersion;
    }

    public void setRemoteProvisioningVersion(String remoteProvisioningVersion) {
        this.remoteProvisioningVersion = remoteProvisioningVersion == null ? null : remoteProvisioningVersion.trim();
    }
}