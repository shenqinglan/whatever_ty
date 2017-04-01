package com.whty.smpp.pdu;

import com.whty.smpp.util.PduUtilities;
/**
 * @ClassName BindReceiverResp
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class BindReceiverResp
	extends Response
	implements Marshaller {

	String system_id;

	public BindReceiverResp() {
	}

	public BindReceiverResp(
		BindReceiver requestMsg,
		String sysid) {
		// message header fields except message length
		setCmd_id(PduConstants.BIND_RECEIVER_RESP);
		setCmd_status(PduConstants.ESME_ROK);
		setSeq_no(requestMsg.getSeq_no());
		// Set message length to zero since actual length will not be known until the object is
		// converted back to a message complete with null terminated strings
		setCmd_len(0);

		// message body
		system_id = sysid;
	}

	public byte [] marshall() throws Exception {
		out.reset();
		super.prepareHeaderForMarshalling();
		out.write(PduUtilities.stringToNullTerminatedByteArray(system_id)); 
		byte [] response = out.toByteArray();
		int l = response.length;
		response = PduUtilities.setPduLength(response,l);
		return response;
	}
	/**
	 * *returns String representation of PDU
	 */
	public String toString() {
		return 	super.toString()+","+
				"system_id="+system_id;
	}

}