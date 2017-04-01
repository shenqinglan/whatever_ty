package com.whty.euicc.util;
/**
 * 加密类javabean
 * @author Administrator
 *
 */
public class CipherBean {
	private String cipherAlg;
	
	private String pcntr;
	
	
	public String getCipherAlg() {
		return cipherAlg;
	}
	
	public void setCipherAlg(String cipherAlg) {
		this.cipherAlg = cipherAlg;
	}
	
	public String getPcntr() {
		return pcntr;
	}
	
	public void setPcntr(String pcntr) {
		this.pcntr = pcntr;
	}

}
