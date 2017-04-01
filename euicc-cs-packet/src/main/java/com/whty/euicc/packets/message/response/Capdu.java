package com.whty.euicc.packets.message.response;

import java.io.Serializable ;

public class Capdu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8821743270144713478L ;
	private String apdu;
	private String compsta;
	
	public Capdu(){
		
	}
	
	public Capdu(String apdu, String compsta){
		this.apdu = apdu;
		this.compsta = compsta;
	}

	/**
	 * @return the apdu
	 */
	public String getApdu() {
		return apdu;
	}

	/**
	 * @param apdu
	 *            the apdu to set
	 */
	public void setApdu(String apdu) {
		this.apdu = apdu;
	}

	/**
	 * @return the compsta
	 */
	public String getCompsta() {
		return compsta;
	}

	/**
	 * @param compsta
	 *            the compsta to set
	 */
	public void setCompsta(String compsta) {
		this.compsta = compsta;
	}
}
