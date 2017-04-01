package com.whty.euicc.data.pojo;

public class EuiccScp03 {
    private String scp03Id;

    private String eid;

    private String isdPAid;

    private String id;

    private String version;

    private String data;

    public String getScp03Id() {
        return scp03Id;
    }

    public void setScp03Id(String scp03Id) {
        this.scp03Id = scp03Id == null ? null : scp03Id.trim();
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
    }

    public String getIsdPAid() {
        return isdPAid;
    }

    public void setIsdPAid(String isdPAid) {
        this.isdPAid = isdPAid == null ? null : isdPAid.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }
}