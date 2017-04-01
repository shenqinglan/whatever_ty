package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;
/**
 * 认证smsr请求
 * @author Administrator
 *
 */
@MsgType("authenticateSMSR")
public class AuthenticateSMSRReqBody extends EuiccReqBody{
	private String cert;
	private String certSrEcdsa;
	
	public String getCert() {
		return cert;
	}
	public void setCert(String cert) {
		this.cert = cert;
	}
	public String getCertSrEcdsa() {
		return certSrEcdsa;
	}
	public void setCertSrEcdsa(String certSrEcdsa) {
		this.certSrEcdsa = certSrEcdsa;
	}
	
}
