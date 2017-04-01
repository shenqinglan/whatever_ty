package com.whty.smpp.pdu;
/**
 * @ClassName Unbind
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class Unbind extends Request implements Demarshaller {

	// PDU attributes

	public void demarshall(byte[] request) throws Exception {
		// demarshall the header
		super.demarshall(request);
	}
	
	/**
	 * *returns String representation of PDU
	 */
	public String toString() {
		return super.toString();
	}


}