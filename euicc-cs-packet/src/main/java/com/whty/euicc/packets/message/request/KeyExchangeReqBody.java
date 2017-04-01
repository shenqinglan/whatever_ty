package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;

/**
 * 交易密钥请求
 * @author dengzm
 *
 */
@MsgType("phone.005.001.01")
public class KeyExchangeReqBody extends RequestMsgBody{
	private String tKey;

	public String gettKey() {
		return tKey;
	}

	public void settKey(String tKey) {
		this.tKey = tKey;
	}
}
