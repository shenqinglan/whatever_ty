package com.whty.smpp.pdu;

import org.slf4j.LoggerFactory;

import com.whty.smpp.util.PduUtilities;
/**
 * @ClassName QuerySM
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class QuerySM extends Request implements Demarshaller {

       private static org.slf4j.Logger logger = LoggerFactory.getLogger(QuerySM.class);
	// PDU attributes

	private String original_message_id;

	private int originating_ton;

	private int originating_npi;

	private String originating_addr;

	public void demarshall(byte[] request) throws Exception {

		// demarshall the header
		super.demarshall(request);
		// now set atributes of this specific PDU type
		int inx = 16;
		try {
			original_message_id = PduUtilities.getStringValueNullTerminated(
					request, inx, 65, PduConstants.C_OCTET_STRING_TYPE);
		} catch (Exception e) {
			logger
					.debug("QUERY_SM PDU is malformed. original_message_id is incorrect");
			throw (e);
		}
		inx = inx + original_message_id.length() + 1;
		try {
			originating_ton = PduUtilities.getIntegerValue(request, inx, 1);
		} catch (Exception e) {
			logger
					.debug("QUERY_SM PDU is malformed. originating_ton is incorrect");
			throw (e);
		}
		inx = inx + 1;
		try {
			originating_npi = PduUtilities.getIntegerValue(request, inx, 1);
		} catch (Exception e) {
			logger
					.debug("QUERY_SM PDU is malformed. originating_npi is incorrect");
			throw (e);
		}
		inx = inx + 1;
		try {
			originating_addr = PduUtilities.getStringValueNullTerminated(
					request, inx, 21, PduConstants.C_OCTET_STRING_TYPE);
		} catch (Exception e) {
			logger
					.debug("QUERY_SM PDU is malformed. originating_npi is incorrect");
			throw (e);
		}
		inx = inx + originating_addr.length() + 1;
	}

	/**
	 * @return
	 */
	public String getOriginal_message_id() {
		return original_message_id;
	}

	/**
	 * @return
	 */
	public String getOriginating_addr() {
		return originating_addr;
	}

	/**
	 * @return
	 */
	public int getOriginating_npi() {
		return originating_npi;
	}

	/**
	 * @return
	 */
	public int getOriginating_ton() {
		return originating_ton;
	}

	/**
	 * @param string
	 */
	public void setOriginal_message_id(String string) {
		original_message_id = string;
	}

	/**
	 * @param string
	 */
	public void setOriginating_addr(String string) {
		originating_addr = string;
	}

	/**
	 * @param i
	 */
	public void setOriginating_npi(int i) {
		originating_npi = i;
	}

	/**
	 * @param i
	 */
	public void setOriginating_ton(int i) {
		originating_ton = i;
	}

	/**
	 * *returns String representation of PDU
	 */
	public String toString() {
		return super.toString() + "," + "original_message_id="
				+ original_message_id + "," + "originating_ton="
				+ originating_ton + "," + "originating_npi=" + originating_npi
				+ "," + "originating_addr=" + originating_addr;
	}

}