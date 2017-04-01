package com.whty.smpp.esme.session;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jboss.netty.buffer.ChannelBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.esme.Message;
import com.whty.smpp.esme.Writer;
import com.whty.smpp.esme.message.Pdu;
import com.whty.smpp.esme.message.PduRequest;
import com.whty.smpp.esme.message.PduResponse;
import com.whty.smpp.esme.transcoder.ISmppPduTranscoder;
import com.whty.smpp.esme.util.SequenceNumber;
/**
 * 
 * @ClassName ReceiverWriter
 * @author Administrator
 * @date 2017-3-10 下午1:40:06
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class ReceiverWriter implements Writer {

	private static Logger logger = LoggerFactory.getLogger(ReceiverWriter.class);

	protected DataOutputStream out;
	private final ISmppPduTranscoder transcoder;
	private final SequenceNumber sequenceNumber;

	public ReceiverWriter(OutputStream os, ISmppPduTranscoder transcoder,
			SequenceNumber sequenceNumber) {
		this.out = new DataOutputStream(os);
		this.transcoder = transcoder;
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @author Administrator
	 * @date 2017-2-8
	 * @param message
	 * @throws IOException
	 * @Description TODO(将message变成byte通过socket传给模拟器)
	 * @see com.netgao.sms.protocol.Writer#write(com.netgao.sms.protocol.Message)
	 */
	@Override
	public void write(Message message) throws IOException {
		if (message instanceof PduRequest) {
			Pdu pdu = (Pdu) message;
			if (!pdu.hasSequenceNumberAssigned()) {
				pdu.setSequenceNumber(sequenceNumber.next());
			}
			// pdu编码
			ChannelBuffer buffer = transcoder.encode(pdu);
			byte[] msg = buffer.array();
			writeBytes(msg);
		} else if (message instanceof PduResponse) {
			Pdu pdu = (Pdu) message;
			// pdu编码
			ChannelBuffer buffer = transcoder.encode(pdu);
			byte[] msg = buffer.array();
			writeBytes(msg);
		}
	}

	private void writeBytes(byte[] bytes) throws IOException {
		out.write(bytes);
		out.flush();
	}
}
