package com.whty.euicc.data.pojo;

public class EuiccScp80 {
	    private String scp80Id;

	    private String eid;

	    private String id;

	    private String version;

	    private String data;

	    public String getScp80Id() {
	        return scp80Id;
	    }

	    public void setScp80Id(String scp80Id) {
	        this.scp80Id = scp80Id == null ? null : scp80Id.trim();
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


