package com.whty.euicc.data.pojo;

public class EuiccIsdR {
    private String rId;

    private String eid;

    private String pol1Id;

    private String isdRAid;

    private String srAddressPara;
    
    public String getSrAddressPara() {
		return srAddressPara;
	}

	public void setSrAddressPara(String srAddressPara) {
		this.srAddressPara = srAddressPara;
	}

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
}