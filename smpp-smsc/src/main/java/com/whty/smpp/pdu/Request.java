package com.whty.smpp.pdu;

import org.slf4j.LoggerFactory;

import com.whty.smpp.util.PduUtilities;
/**
 * @ClassName Request
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
abstract public class Request extends Pdu implements Demarshaller {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(Request.class);
    
	public void demarshall(byte[] request) throws Exception {
		int inx = 0;
		try {
			setCmd_len(PduUtilities.getIntegerValue(request, inx, 4));
		} catch (Exception e) {
			logger.debug("SMPP header PDU is malformed. cmd_len is incorrect");
			throw (e);
		}
		inx = inx + 4;
		try {
			setCmd_id(PduUtilities.getIntegerValue(request, inx, 4));
		} catch (Exception e) {
			logger.debug("SMPP header PDU is malformed. cmd_id is incorrect");
			throw (e);
		}
		inx = inx + 4;
		try {
			setCmd_status(PduUtilities.getIntegerValue(request, inx, 4));
		} catch (Exception e) {
			logger
					.debug("SMPP header PDU is malformed. cmd_status is incorrect");
			throw (e);
		}
		inx = inx + 4;
		try {
			setSeq_no(PduUtilities.getIntegerValue(request, inx, 4));
		} catch (Exception e) {
			logger.debug("SMPP header PDU is malformed. seq_no is incorrect");
			throw (e);
		}
		inx = inx + 4;
	}

	public String toString() {
		return super.toString();
	}
}