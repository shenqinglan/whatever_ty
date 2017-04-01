package com.whty.efs.packets.message.response;

/**
 * 执行指令C-APDU
 * @author gaofeng
 *
 */
public class Capdu {
	private String apdu;
	private String compsta;

	public String getCompsta() {
		return compsta;
	}

	public void setCompsta(String compsta) {
		this.compsta = compsta;
	}

	public String getApdu() {
		return apdu;
	}

	public void setApdu(String apdu) {
		this.apdu = apdu;
	}

}
