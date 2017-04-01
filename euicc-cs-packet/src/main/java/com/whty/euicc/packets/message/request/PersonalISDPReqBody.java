package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;
/**
 * 个人化ISD-P请求
 * @author Administrator
 *
 */
@MsgType("personalISDP")
public class PersonalISDPReqBody extends EuiccReqBody{
	private String certDpEcdsa;
	
	private String eskDp;
	
	private String epkDp;
	
    private String isdPAid;//从数据库查询得到
    
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
	public String getIsdPAid() {
		return isdPAid;
	}

	public void setIsdPAid(String isdPAid) {
		this.isdPAid = isdPAid;
	}
}
