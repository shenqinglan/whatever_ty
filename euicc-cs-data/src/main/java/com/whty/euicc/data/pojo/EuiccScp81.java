package com.whty.euicc.data.pojo;

public class EuiccScp81 {
    private String scp81Id;

    private String eid;

    private String id;

    private String version;

    private String data;

    public String getScp81Id() {
        return scp81Id;
    }

    public void setScp81Id(String scp81Id) {
        this.scp81Id = scp81Id == null ? null : scp81Id.trim();
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
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