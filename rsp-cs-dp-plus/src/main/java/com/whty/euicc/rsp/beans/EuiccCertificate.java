package com.whty.euicc.rsp.beans;

public class EuiccCertificate {
	private String eid;
	private String pkEuiccEcdsa;
	public String getEid() {
		return eid;
	}
	public void setEid(String eid) {
		this.eid = eid;
	}
	public String getPkEuiccEcdsa() {
		return pkEuiccEcdsa;
	}
	public void setPkEuiccEcdsa(String pkEuiccEcdsa) {
		this.pkEuiccEcdsa = pkEuiccEcdsa;
	}

}
