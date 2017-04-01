package com.whty.security.scp03t.scp03t.bean;
/**
 * 命令报文结果体
 * @author Administrator
 *
 */
public class CmdApduBean {
	private String apdu;
	private String counter;
	private String perMac;
	
	public CmdApduBean(String apdu, String counter, String perMac) {
		super();
		this.counter = counter;
		this.apdu = apdu;
		this.perMac = perMac;
	}
	public String getApdu() {
		return apdu;
	}
	public void setApdu(String apdu) {
		this.apdu = apdu;
	}
	public String getPerMac() {
		return perMac;
	}
	public void setPerMac(String perMac) {
		this.perMac = perMac;
	}

	public String getCounter() {
		return counter;
	}

	public void setCounter(String counter) {
		this.counter = counter;
	}
}
