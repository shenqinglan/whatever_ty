package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;

/**
 * Https下SM-SR的更新SMSR地址参数请求
 * @author Administrator
 *
 */
@MsgType("updateSrAddressParaByHttps") //卡片通知
public class UpdateSrAddressParaByHttpsReqBody extends EuiccReqBody{
	private String rId;
	private String isdRAid;
	private String srAddressPara;
	public String getrId() {
		return rId;
	}
	public void setrId(String rId) {
		this.rId = rId;
	}
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
