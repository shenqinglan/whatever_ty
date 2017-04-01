package com.whty.smpp.pdu;

import com.whty.smpp.service.Smsc;
import com.whty.smpp.util.PduUtilities;

/**
 * @ClassName SubmitSMResp
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SubmitSMResp extends Response implements Marshaller {
	private Smsc smsc = Smsc.getInstance();
	
	String message_id;

	public SubmitSMResp(SubmitSM requestMsg) {
		// message header fields except message length
		setCmd_id(PduConstants.SUBMIT_SM_RESP);
		setCmd_status(PduConstants.ESME_ROK);
		setSeq_no(requestMsg.getSeq_no());
		// Set message length to zero since actual length will not be known until the object is
		// converted back to a message complete with null terminated strings
		setCmd_len(0);

		// message body
		message_id = smsc.getMessageID();		
	}

	public byte[] marshall() throws Exception {
		out.reset();
		super.prepareHeaderForMarshalling();
		out.write(PduUtilities.stringToNullTerminatedByteArray(message_id));
		byte[] response = out.toByteArray();
		int l = response.length;
		response = PduUtilities.setPduLength(response, l);
		return response;
	}
	/**
	 * @return
	 */
	public String getMessage_id() {
		return message_id;
	}

	/**
	 * @param string
	 */
	public void setMessage_id(String string) {
		message_id = string;
	}
	
	public String toString() {
		return super.toString()+","+
		"message_id="+message_id;		
	}

}