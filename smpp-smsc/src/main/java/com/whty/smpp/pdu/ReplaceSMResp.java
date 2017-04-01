package com.whty.smpp.pdu;
import com.whty.smpp.util.PduUtilities;
/**
 * @ClassName ReplaceSMResp
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class ReplaceSMResp extends Response implements Marshaller {

	public ReplaceSMResp(ReplaceSM requestMsg) {
		// message header fields except message length
		setCmd_id(PduConstants.REPLACE_SM_RESP);
		setCmd_status(PduConstants.ESME_ROK);
		setSeq_no(requestMsg.getSeq_no());
		// Set message length to zero since actual length will not be known until the object is
		// converted back to a message complete with null terminated strings
		setCmd_len(0);
		// message body
	}

	public byte[] marshall() throws Exception {
		out.reset();
		super.prepareHeaderForMarshalling();
		byte[] response = out.toByteArray();
		int l = response.length;
		response = PduUtilities.setPduLength(response, l);
		return response;
	}
		
	/**
	 * *returns String representation of PDU
	 */
	public String toString() {
		return super.toString();
	}

}