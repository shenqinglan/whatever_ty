package com.whty.smpp.pdu;
import java.io.ByteArrayOutputStream;

import com.whty.smpp.util.PduUtilities;
/**
 * @ClassName Response
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
abstract public class Response extends Pdu implements Marshaller {

	transient ByteArrayOutputStream out = new ByteArrayOutputStream();

	public void prepareHeaderForMarshalling() throws Exception {
		out.reset();
		out.write(PduUtilities.makeByteArrayFromInt(getCmd_len(),4));
		out.write(PduUtilities.makeByteArrayFromInt(getCmd_id(),4));
		out.write(PduUtilities.makeByteArrayFromInt(getCmd_status(),4));
		out.write(PduUtilities.makeByteArrayFromInt(getSeq_no(),4));
	}
	
	public byte [] errorResponse(int cmd_id, int cmd_status, int seq_no) throws Exception {
		out.reset();
		setCmd_len(16);
		setCmd_id(cmd_id);
		setCmd_status(cmd_status);
		setSeq_no(seq_no);
		prepareHeaderForMarshalling();
		byte [] response = out.toByteArray();
		return response;
	}
	
	public String toString() {
		return super.toString();
	}

}