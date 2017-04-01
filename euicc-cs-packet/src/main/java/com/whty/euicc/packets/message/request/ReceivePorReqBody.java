package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;


@MsgType("receivePor")
public class ReceivePorReqBody extends PorReqBody{
	private String poR;
	private String tpud; 

	public String getTpud() {
		return tpud;
	}

	public void setTpud(String tpud) {
		this.tpud = tpud;
	}

	public String getPoR() {
		return poR;
	}

	public void setPoR(String poR) {
		this.poR = poR;
	}
	

}
