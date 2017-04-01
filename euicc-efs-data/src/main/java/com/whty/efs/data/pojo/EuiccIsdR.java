package com.whty.efs.data.pojo;

public class EuiccIsdR {
    private String rId;

    private String eid;

    private String pol1Id;

    private String isdRAid;

    private String aid;

    private String sin;

    private String sdin;

    private String role;

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId == null ? null : rId.trim();
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid == null ? null : eid.trim();
    }

    public String getPol1Id() {
        return pol1Id;
    }

    public void setPol1Id(String pol1Id) {
        this.pol1Id = pol1Id == null ? null : pol1Id.trim();
    }

    public String getIsdRAid() {
        return isdRAid;
    }

    public void setIsdRAid(String isdRAid) {
        this.isdRAid = isdRAid == null ? null : isdRAid.trim();
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid == null ? null : aid.trim();
    }

    public String getSin() {
        return sin;
    }

    public void setSin(String sin) {
        this.sin = sin == null ? null : sin.trim();
    }

    public String getSdin() {
        return sdin;
    }

    public void setSdin(String sdin) {
        this.sdin = sdin == null ? null : sdin.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }
}