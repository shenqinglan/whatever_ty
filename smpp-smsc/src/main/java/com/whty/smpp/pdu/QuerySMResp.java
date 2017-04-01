package com.whty.smpp.pdu;

import java.util.Date;

import com.whty.smpp.util.PduUtilities;
/**
 * @ClassName QuerySMResp
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class QuerySMResp extends Response implements Marshaller {

	private String original_message_id;
	private String final_date;
	private int message_state;
	private int error_code;

	public QuerySMResp(QuerySM requestMsg) {
		// message header fields except message length
		setCmd_id(PduConstants.QUERY_SM_RESP);
		setCmd_status(PduConstants.ESME_ROK);
		setSeq_no(requestMsg.getSeq_no());
		// Set message length to zero since actual length will not be known until the object is
		// converted back to a message complete with null terminated strings
		setCmd_len(0);
		// message body
		original_message_id = requestMsg.getOriginal_message_id();
		Date now = new Date();
		final_date = SmppTime.dateToSMPPString(now);
		message_state = PduConstants.DELIVERED;
		error_code = PduConstants.ESME_ROK;

	}

	public byte[] marshall() throws Exception {
		out.reset();
		super.prepareHeaderForMarshalling();
		out.write(PduUtilities.stringToNullTerminatedByteArray(original_message_id));
		out.write(PduUtilities.stringToNullTerminatedByteArray(final_date));
		out.write(PduUtilities.makeByteArrayFromInt(message_state,1));
		out.write(PduUtilities.makeByteArrayFromInt(error_code,1));
		byte[] response = out.toByteArray();
		int l = response.length;
		response = PduUtilities.setPduLength(response, l);
		return response;
	}
	/**
	 * @return
	 */
	public int getError_code() {
		return error_code;
	}

	/**
	 * @return
	 */
	public String getFinal_date() {
		return final_date;
	}

	/**
	 * @return
	 */
	public int getMessage_state() {
		return message_state;
	}

	/**
	 * @return
	 */
	public String getOriginal_message_id() {
		return original_message_id;
	}

	/**
	 * @param i
	 */
	public void setError_code(int i) {
		error_code = i;
	}

	/**
	 * @param string
	 */
	public void setFinal_date(String string) {
		final_date = string;
	}

	/**
	 * @param i
	 */
	public void setMessage_state(int i) {
		message_state = i;
	}

	/**
	 * @param string
	 */
	public void setOriginal_message_id(String string) {
		original_message_id = string;
	}

	/**
	 * *returns String representation of PDU
	 */
	public String toString() {
		return 	super.toString()+","+
				"original_message_id="+original_message_id+","+
				"final_date="+final_date+","+
				"message_state="+message_state+","+
				"error_code="+error_code;
	}

}