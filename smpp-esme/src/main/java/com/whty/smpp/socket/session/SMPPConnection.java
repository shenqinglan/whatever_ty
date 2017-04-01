/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-2-7
 * Id: SMPPConnection.java,v 1.0 2017-2-7 下午6:57:51 Administrator
 */
package com.whty.smpp.socket.session;

import java.io.InputStream;
import java.io.OutputStream;

import com.whty.smpp.socket.Connection;
import com.whty.smpp.socket.Deliver;
import com.whty.smpp.socket.Reader;
import com.whty.smpp.socket.Session;
import com.whty.smpp.socket.Writer;
import com.whty.smpp.socket.constants.Address;
import com.whty.smpp.socket.constants.Configuration;
import com.whty.smpp.socket.constants.SmppBindType;
import com.whty.smpp.socket.session.SMPPSession;
import com.whty.smpp.socket.transcoder.ISmppPduTranscoder;
import com.whty.smpp.socket.transcoder.SmppPduTranscoderContextImpl;
import com.whty.smpp.socket.transcoder.SmppPduTranscoderImpl;
import com.whty.smpp.socket.util.SequenceNumber;

/**
 * @ClassName SMPPConnection
 * @author Administrator
 * @date 2017-2-7 下午6:57:51
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SMPPConnection extends Connection {
	
	private final ISmppPduTranscoder transcoder;
	private final SequenceNumber sequenceNumber;
	private final Configuration config;
	private final Deliver deliver;

	private SmppBindType bindType;
	private String systemId;
	private String password;
	private String systemType;
	private byte interfaceVersion;
	private Address addressRange;

	public SMPPConnection(Configuration config, Deliver deliver) {
		super();
		this.transcoder = new SmppPduTranscoderImpl(new SmppPduTranscoderContextImpl());
		this.sequenceNumber = new SequenceNumber();
		this.config = config;
		this.deliver = deliver;
	}
	
	public SmppBindType getBindType() {
		return bindType;
	}

	public void setBindType(SmppBindType bindType) {
		this.bindType = bindType;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	public byte getInterfaceVersion() {
		return interfaceVersion;
	}

	public void setInterfaceVersion(byte interfaceVersion) {
		this.interfaceVersion = interfaceVersion;
	}
	
	public Address getAddressRange() {
		return addressRange;
	}

	public void setAddressRange(Address addressRange) {
		this.addressRange = addressRange;
	}

	@Override
	protected Session createSession() {
		return new SMPPSession(this, false, config, deliver);
	}

	@Override
	protected Writer createWriter(OutputStream output) {
		return new SMPPWriter(output, transcoder, sequenceNumber);
	}

	@Override
	protected Reader createReader(InputStream input) {
		return new SMPPReader(input,transcoder);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("smpp:[systemId=").append(systemId).append(",")
				.append("host=").append(getHost()).append(",").append("port=")
				.append(getPort()).append(",").append("password=")
				.append(password).append(",").append("bindType=")
				.append(bindType).append("]");
		return buffer.toString();
	}
}
