package com.whty.efs.packets.message.request;


public class SrChangeKeyExchangeReqBody extends EuiccReqBody{
	private String isdr;
	private String cert;
	private String eskSr;
	private String epkSr;
	private String certSrEcdsa;
	private String epkSrEcka;
	
	public String getIsdr() {
		return isdr;
	}
	public void setIsdr(String isdr) {
		this.isdr = isdr;
	}
	public String getCert() {
		return cert;
	}
	public void setCert(String cert) {
		this.cert = cert;
	}
	public String getEskSr() {
		return eskSr;
	}
	public void setEskSr(String eskSr) {
		this.eskSr = eskSr;
	}
	public String getEpkSr() {
		return epkSr;
	}
	public void setEpkSr(String epkSr) {
		this.epkSr = epkSr;
	}
	public String getCertSrEcdsa() {
		return certSrEcdsa;
	}
	public void setCertSrEcdsa(String certSrEcdsa) {
		this.certSrEcdsa = certSrEcdsa;
	}
	public String getEpkSrEcka() {
		return epkSrEcka;
	}
	public void setEpkSrEcka(String epkSrEcka) {
		this.epkSrEcka = epkSrEcka;
	}
	
}

