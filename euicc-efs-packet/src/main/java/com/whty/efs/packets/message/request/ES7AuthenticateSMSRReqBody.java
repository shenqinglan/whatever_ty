package com.whty.efs.packets.message.request;

import com.whty.efs.packets.message.MsgBody;
import com.whty.efs.packets.message.MsgType;

/**
 * 认证smsr请求
 * @author Administrator
 *
 */
@MsgType("authenticateSMSR")
public class ES7AuthenticateSMSRReqBody extends EuiccReqBody implements MsgBody{
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
