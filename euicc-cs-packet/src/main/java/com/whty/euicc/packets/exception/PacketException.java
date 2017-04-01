package com.whty.euicc.packets.exception;

import com.whty.euicc.common.exception.EuiccBaseException;

public class PacketException extends EuiccBaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3059126062450123042L;

	@Override
	public String getCode() {
		return "P0000";
	}

	public PacketException() {
		super();
	}

	public PacketException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public PacketException(String arg0) {
		super(arg0);
	}

	public PacketException(Throwable arg0) {
		super(arg0);
	}

}
