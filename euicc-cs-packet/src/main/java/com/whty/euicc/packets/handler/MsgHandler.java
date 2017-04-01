package com.whty.euicc.packets.handler;

import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.response.ResponseMsgBody;
import com.whty.euicc.packets.thirdpartymsg.F17Msg;

public interface MsgHandler {
	
	public EuiccMsg<ResponseMsgBody> handler(F17Msg f17Msg);
	
	public EuiccMsg<ResponseMsgBody> handler(byte[] jsonMsg);
	
	public byte[] encode(EuiccMsg<ResponseMsgBody> respBody)throws Exception;
}
