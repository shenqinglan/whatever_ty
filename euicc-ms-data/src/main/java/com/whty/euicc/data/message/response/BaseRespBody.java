package com.whty.euicc.data.message.response;

import com.whty.euicc.data.message.response.attr.AttrResponseMsg;

public class BaseRespBody extends ResponseMsgBody implements AttrResponseMsg {

	public BaseRespBody() {
		rspnMsg = new RspnMsg();
	}

	public BaseRespBody(RspnMsg rspnMsg) {
		this.rspnMsg = rspnMsg;
	}

	public RspnMsg getRspnMsg() {
		return rspnMsg;
	}

	public void setRspnMsg(RspnMsg rspnMsg) {
		this.rspnMsg = rspnMsg;
	}

	/** 业务应答信息 */
	private RspnMsg rspnMsg;

}
