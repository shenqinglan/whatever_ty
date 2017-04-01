package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;

/**
 * 创建额外密钥组件请求
 * @author 11
 *
 */
@MsgType("createAdditionalKeySet")
public class CreateAdditionalKeySetReqBody extends EuiccReqBody{
	private String  ePK_SR_ECKA;

	public String getePK_SR_ECKA() {
		return ePK_SR_ECKA;
	}

	public void setePK_SR_ECKA(String ePK_SR_ECKA) {
		this.ePK_SR_ECKA = ePK_SR_ECKA;
	}
	

}
