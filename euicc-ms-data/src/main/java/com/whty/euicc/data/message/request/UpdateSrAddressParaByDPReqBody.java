package com.whty.euicc.data.message.request;

import com.whty.euicc.data.message.MsgType;

/**
 * Https下SM-DP的更新SMSR地址参数请求
 * @author Administrator
 *
 */
@MsgType("updateSrAddressParaByDP") //卡片通知
public class UpdateSrAddressParaByDPReqBody  extends EuiccReqBody{
	private String isdRAid;
	private String srAddressPara;
	public String getIsdRAid() {
		return isdRAid;
	}
	public void setIsdRAid(String isdRAid) {
		this.isdRAid = isdRAid;
	}
	public String getSrAddressPara() {
		return srAddressPara;
	}
	public void setSrAddressPara(String srAddressPara) {
		this.srAddressPara = srAddressPara;
	}

}
