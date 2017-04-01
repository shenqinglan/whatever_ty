// Copyright (C) 2012 WHTY
package com.whty.efs.common.exception;

public class NotEnoughDataException extends EuiccBaseException {
    private static final long serialVersionUID = -3720107899765064964L;
    private int available;
    private int expected;

    public NotEnoughDataException(int pAvailable, int pExpected) {
        super("Not enough data in byte buffer. " + "Expected " + pExpected
                + ", available: " + pAvailable + ".");
        available = pAvailable;
        expected = pExpected;
    }

    public NotEnoughDataException(String s) {
        super(s);
        available = 0;
        expected = 0;
    }

    public int getAvailable() {
        return available;
    }

    public int getExpected() {
        return expected;
    }

	@Override
	public String getCode() {
		return "000002";
	}
}
