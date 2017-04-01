package com.whty.smpp.pdu;
/**
 * @ClassName UnsuccessSME
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class UnsuccessSME {

	int dest_addr_ton;
	int dest_addr_npi;
	String destination_addr;
	int error_status_code;

	public UnsuccessSME() {
	}

	public UnsuccessSME(
		int dest_addr_ton,
		int dest_addr_npi,
		String destination_addr,
		int error_status_code) {
		this.dest_addr_ton = dest_addr_ton;
		this.dest_addr_npi = dest_addr_npi;
		this.destination_addr = destination_addr;
		this.error_status_code = error_status_code;
	}

	/**
	 * @return
	 */
	public int getDest_addr_npi() {
		return dest_addr_npi;
	}

	/**
	 * @return
	 */
	public int getDest_addr_ton() {
		return dest_addr_ton;
	}

	/**
	 * @return
	 */
	public String getDestination_addr() {
		return destination_addr;
	}

	/**
	 * @return
	 */
	public int getError_status_code() {
		return error_status_code;
	}

	/**
	 * @param i
	 */
	public void setDest_addr_npi(int i) {
		dest_addr_npi = i;
	}

	/**
	 * @param i
	 */
	public void setDest_addr_ton(int i) {
		dest_addr_ton = i;
	}

	/**
	 * @param string
	 */
	public void setDestination_addr(String string) {
		destination_addr = string;
	}

	/**
	 * @param i
	 */
	public void setError_status_code(int i) {
		error_status_code = i;
	}

	public String toString() {
		return "dest_addr_ton="+dest_addr_ton+","+
		"dest_addr_npi="+dest_addr_npi+","+
		"destination_addr="+destination_addr+","+
		"error_status_code="+error_status_code;
	}

}