package com.whty.smpp.pdu;

import com.whty.smpp.service.Smsc;
import com.whty.smpp.util.PduUtilities;
/**
 * @ClassName SubmitMultiResp
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SubmitMultiResp extends Response implements Marshaller {

	private Smsc smsc = Smsc.getInstance();

	private String message_id;
	private int no_unsuccess;
	private UnsuccessSME[] unsuccess_smes;

	public SubmitMultiResp(SubmitMulti requestMsg) {
		// message header fields except message length
		setCmd_id(PduConstants.SUBMIT_MULTI_RESP);
		setCmd_status(PduConstants.ESME_ROK);
		setSeq_no(requestMsg.getSeq_no());
		// Set message length to zero since actual length will not be known until the object is
		// converted back to a message complete with null terminated strings
		setCmd_len(0);

		// message body
		message_id = smsc.getMessageID();
		no_unsuccess = 0;
		// until we have message state simulator working		
	}

	public byte[] marshall() throws Exception {
		out.reset();
		UnsuccessSME u = new UnsuccessSME();
		super.prepareHeaderForMarshalling();
		
		out.write(PduUtilities.stringToNullTerminatedByteArray(message_id));
		out.write(PduUtilities.makeByteArrayFromInt(no_unsuccess, 1));
		for (int i=0;i<no_unsuccess;i++) {
			u = unsuccess_smes[i];
			out.write(PduUtilities.makeByteArrayFromInt(u.getDest_addr_ton(), 1));
			out.write(PduUtilities.makeByteArrayFromInt(u.getDest_addr_npi(), 1));
			out.write(PduUtilities.stringToNullTerminatedByteArray(u.getDestination_addr()));
			out.write(PduUtilities.makeByteArrayFromInt(u.getError_status_code(), 4));
		}
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
	 * @return
	 */
	public int getNo_unsuccess() {
		return no_unsuccess;
	}

	/**
	 * @return
	 */
	public UnsuccessSME[] getUnsuccess_smes() {
		return unsuccess_smes;
	}

	/**
	 * @param string
	 */
	public void setMessage_id(String string) {
		message_id = string;
	}

	/**
	 * @param unsuccessSMEs
	 */
	public void setUnsuccess_smes(UnsuccessSME[] unsuccessSMEs) {
		unsuccess_smes = unsuccessSMEs;
		no_unsuccess = unsuccess_smes.length;
	}

	public String toString() {
		String string = super.toString()+","+
		"message_id="+message_id+","+
		"unsuccess_sme array:";
		if (no_unsuccess > 0)
			string = string + unsuccessSmesToString(unsuccess_smes);
		else
			string = string + "<empty>";
		return string;
	}
	
	public String unsuccessSmesToString(UnsuccessSME[] unsuccess_smes) {
		int l=unsuccess_smes.length;
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<l;i++) {
			sb.append("["+i+"]");
			sb.append(unsuccess_smes[i].toString());
		}
		return sb.toString();
	}

}