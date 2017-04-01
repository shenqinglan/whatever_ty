package com.whty.efs.packets.message.request;

import com.whty.efs.packets.message.MsgBody;
import com.whty.efs.packets.message.MsgType;

/**
 * 个人化ISD-P请求
 * @author Administrator
 *
 */
@MsgType("personalISDP")
public class ES3PersonalISDPReqBody extends EuiccReqBody implements MsgBody{
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

