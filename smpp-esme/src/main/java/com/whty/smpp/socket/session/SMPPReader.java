/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-2-7
 * Id: SMPPReader.java,v 1.0 2017-2-7 下午6:58:52 Administrator
 */
package com.whty.smpp.socket.session;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.socket.Message;
import com.whty.smpp.socket.Reader;
import com.whty.smpp.socket.message.Pdu;
import com.whty.smpp.socket.transcoder.ISmppPduTranscoder;
import com.whty.smpp.socket.util.PduUtil;

/**
 * @ClassName SMPPReader
 * @author Administrator
 * @date 2017-2-7 下午6:58:52
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SMPPReader implements Reader {
	
	private static Logger logger = LoggerFactory.getLogger(SMPPReader.class);

	protected DataInputStream is;
	private final ISmppPduTranscoder transcoder;

	public SMPPReader(InputStream is, ISmppPduTranscoder transcoder) {
		this.is = new DataInputStream(is);
		this.transcoder = transcoder;
	}

	@Override
	public Message read() throws IOException {
		logger.debug("starting readPacketInto");
		byte[] message;
		int len=0;
		int cmd=0;
		int status=0;
		byte[] packetLen = new byte[4];
		logger.debug("reading cmd_len");
		packetLen[0] = (byte) is.read();
		packetLen[1] = (byte) is.read();
		packetLen[2] = (byte) is.read();
		packetLen[3] = (byte) is.read();
		logger.debug("Got cmd_len");
		len = (PduUtil.getBytesAsInt(packetLen[0]) << 24)
				| (PduUtil.getBytesAsInt(packetLen[1]) << 16)
				| (PduUtil.getBytesAsInt(packetLen[2]) << 8)
				| (PduUtil.getBytesAsInt(packetLen[3]));
		if (packetLen[3] == -1) {
			logger.error("packetLen[3] == -1, throwing EOFException");
			throw new EOFException();
		}
		logger.debug("Reading " + len + " bytes");
		message = new byte[len];
		message[0] = packetLen[0];
		message[1] = packetLen[1];
		message[2] = packetLen[2];
		message[3] = packetLen[3];
		for (int i = 4; i < len; i++)
			message[i] = (byte) is.read();
		logger.debug("exiting readPacketInto");
		try {
			len = PduUtil.getIntegerValue(message, 0, 4);
	        cmd = PduUtil.getIntegerValue(message, 4, 4);
	        status = PduUtil.getIntegerValue(message, 8, 4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("read packet end message >>> len:{}, cmd:{}, status:{}", len,Integer.toHexString(cmd),status);
		PduUtil.hexDump("server response message:  ", message, len);
		
		ChannelBuffer buf = ChannelBuffers.wrappedBuffer(message);
		Pdu pdu = transcoder.decode(buf);
		return pdu;
	}
	
}
