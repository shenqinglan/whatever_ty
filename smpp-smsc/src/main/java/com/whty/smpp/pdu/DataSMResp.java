package com.whty.smpp.pdu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.whty.smpp.service.Smsc;
import com.whty.smpp.util.PduUtilities;
/**
 * @ClassName DataSMResp
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */

public class DataSMResp extends Response implements Marshaller, Demarshaller {

	private Smsc smsc = Smsc.getInstance();
	String message_id;
  
  // Optional PDU attributes
  private HashMap<Short, Tlv> optionalsByTag = new HashMap<Short, Tlv>();

  public DataSMResp() {
  }
  
	public DataSMResp(DataSM requestMsg) {
		// message header fields except message length
		setCmd_id(PduConstants.DATA_SM_RESP);
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
    
    for (Iterator<Tlv> it = optionalsByTag.values().iterator(); it.hasNext(); ) {
      Tlv opt = it.next();
      out.write(PduUtilities.makeByteArrayFromInt(opt.getTag(), 2));
      out.write(PduUtilities.makeByteArrayFromInt(opt.getLen(), 2));
      out.write(opt.getValue());
    }
    
		byte[] response = out.toByteArray();
		int l = response.length;
		response = PduUtilities.setPduLength(response, l);
		return response;
	}
  

  public void demarshall(byte[] request) throws Exception {

    // demarshall the header
    int inx = 0;
    setCmd_len(PduUtilities.getIntegerValue(request, inx, 4));
    inx = inx+4;      
    setCmd_id(PduUtilities.getIntegerValue(request, inx, 4));
    inx = inx+4;      
    setCmd_status(PduUtilities.getIntegerValue(request, inx, 4));
    inx = inx+4;      
    setSeq_no(PduUtilities.getIntegerValue(request, inx, 4));
    
    // now set mandatory attributes of this specific PDU type
    inx = inx+4;
    message_id = PduUtilities.getStringValueNullTerminated(request, inx, 65, PduConstants.C_OCTET_STRING_TYPE);
  }
  
	/**
	 * @return
	 */
	public String getMessage_id() {
		return message_id;
	}
  
  public boolean hasOptionnal(short aTag) {
    return optionalsByTag.containsKey(new Short(aTag));
  }
  
  public Tlv getOptionnal(short aTag) {
    return optionalsByTag.get(new Short(aTag));
  }
  
  public List<Short> getOptionnalTags() {
    return new ArrayList<Short>(optionalsByTag.keySet());
  }

	/**
	 * @param string
	 */
	public void setMessage_id(String string) {
		message_id = string;
	}
  
  public void setOptionnal(Tlv opt) {
    optionalsByTag.put(new Short(opt.getTag()), opt);
  }
	
	public String toString() {
    StringBuffer sb = new StringBuffer();
		sb.append(super.toString()).
       append(",message_id=").append(message_id);
    
    if (optionalsByTag.size() > 0) {
      for (Iterator<Tlv> it = optionalsByTag.values().iterator(); it.hasNext(); ) {
        sb.append(",").append(it.next().toString());
      }
    }
    return sb.toString();
	}

}