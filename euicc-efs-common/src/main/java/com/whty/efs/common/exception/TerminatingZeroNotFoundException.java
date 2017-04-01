// Copyright (C) 2012 WHTY
package com.whty.efs.common.exception;

public class TerminatingZeroNotFoundException extends EuiccBaseException {
    private static final long serialVersionUID = 7028315742573472677L;

    public TerminatingZeroNotFoundException() {
        super("Terminating zero not found in buffer.");
    }

    public TerminatingZeroNotFoundException(String s) {
        super(s);
    }

	@Override
	public String getCode() {
		return "000005";
	}
}
