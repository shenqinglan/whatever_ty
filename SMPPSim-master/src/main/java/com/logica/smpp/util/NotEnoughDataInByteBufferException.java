/*
 * Copyright (c) 1996-2001
 * Logica Mobile Networks Limited
 * All rights reserved.
 *
 * This software is distributed under Logica Open Source License Version 1.0
 * ("Licence Agreement"). You shall use it and distribute only in accordance
 * with the terms of the License Agreement.
 *
 */
package com.logica.smpp.util;

import com.logica.smpp.SmppException;

/**
 * @author Logica Mobile Networks SMPP Open Source Team
 * @version 1.0, 11 Jun 2001
 */

public class NotEnoughDataInByteBufferException extends SmppException
{
    private int available;
    private int expected;
    
    public NotEnoughDataInByteBufferException(int p_available, int p_expected)
    {
	    super("Not enough data in byte buffer. " + 
	        "Expected " + p_expected + 
	        ", available: "+p_available + ".");
        available = p_available;
        expected = p_expected;
    }
    
    public NotEnoughDataInByteBufferException(String s)
    {
	    super(s);
        available = 0;
        expected = 0;
    }
    
    public int getAvailable()
    {
        return available;
    }
    
    public int getExpected()
    {
        return expected;
    }
}