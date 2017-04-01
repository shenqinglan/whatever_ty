package com.whty.euicc.data.message.request;

import com.whty.euicc.data.message.MsgType;

/**
 * 个人化ISD-P请求
 * @author Administrator
 *
 */
@MsgType("personalAllISDP")
public class PersonalISDPReqBody extends EuiccReqBody{
private String certDpEcdsa;
	
	private String eskDp;
	
	private String epkDp;

	public String getCertDpEcdsa() {
		return certDpEcdsa;
	}

	public void setCertDpEcdsa(String certDpEcdsa) {
		this.certDpEcdsa = certDpEcdsa;
	}

	public String getEskDp() {
		return eskDp;
	}

	public void setEskDp(String eskDp) {
		this.eskDp = eskDp;
	}

	public String getEpkDp() {
		return epkDp;
	}

	public void setEpkDp(String epkDp) {
		this.epkDp = epkDp;
	}
}
