package com.whty.euicc.rsp.beans;

public class EuiccInfo1 {
	private String svn;
	private String euiccCiPKIdListForVerification;
	private String euiccCiPKIdListForSigning;
	
	public String getSvn() {
		return svn;
	}
	public void setSvn(String svn) {
		this.svn = svn;
	}
	public String getEuiccCiPKIdListForVerification() {
		return euiccCiPKIdListForVerification;
	}
	public void setEuiccCiPKIdListForVerification(
			String euiccCiPKIdListForVerification) {
		this.euiccCiPKIdListForVerification = euiccCiPKIdListForVerification;
	}
	public String getEuiccCiPKIdListForSigning() {
		return euiccCiPKIdListForSigning;
	}
	public void setEuiccCiPKIdListForSigning(String euiccCiPKIdListForSigning) {
		this.euiccCiPKIdListForSigning = euiccCiPKIdListForSigning;
	}
	
	

}
