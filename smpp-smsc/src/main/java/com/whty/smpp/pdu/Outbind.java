package com.whty.smpp.pdu;

import org.slf4j.LoggerFactory;

import com.whty.smpp.service.Smsc;
import com.whty.smpp.util.PduUtilities;
/**
 * @ClassName Outbind
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class Outbind extends Response implements Marshaller {

    
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(Outbind.class);

	private Smsc smsc = Smsc.getInstance();

	// PDU attributes

	private String system_id="";

	private String password="";

	public Outbind(String system_id,String password) {
		// message header fields except message length
		setCmd_id(PduConstants.OUTBIND);
		setCmd_status(PduConstants.ESME_ROK);
		setSeq_no(smsc.getNextSequence_No());
		// Set message length to zero since actual length will not be known until the object is
		// converted back to a message complete with null terminated strings
		setCmd_len(0);
		this.system_id = system_id;
		this.password = password;
	}
	
	public byte[] marshall() throws Exception {
		out.reset();
		super.prepareHeaderForMarshalling();
		logger.debug("Prepared header for marshalling");
		out.write(PduUtilities.stringToNullTerminatedByteArray(system_id));
		logger.debug("marshalled system_id");
		out.write(PduUtilities.stringToNullTerminatedByteArray(password));
		logger.debug("marshalled password");
		byte[] response = out.toByteArray();
		int l = response.length;
		response = PduUtilities.setPduLength(response, l);
		return response;
	}

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return
	 */
	public String getSystem_id() {
		return system_id;
	}

	/**
	 * @param string
	 */
	public void setPassword(String string) {
		password = string;
	}

	/**
	 * @param string
	 */
	public void setSystem_id(String string) {
		system_id = string;
	}

	/**
	 * *returns String representation of PDU
	 */
	public String toString() {
		return super.toString() + "," + "system_id=" + system_id + ","
				+ "password=" + password;
	}

}