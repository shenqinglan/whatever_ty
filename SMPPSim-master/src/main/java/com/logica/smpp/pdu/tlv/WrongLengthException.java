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
package com.logica.smpp.pdu.tlv;

/**
 * @author Logica Mobile Networks SMPP Open Source Team
 * @version 1.0, 11 Jun 2001
 */

public class WrongLengthException extends TLVException
{
    public WrongLengthException()
    {
        super("The TLV is shorter or longer than allowed.");
    }
    
    public WrongLengthException(int min, int max, int actual)
    {
        super("The TLV is shorter or longer than allowed: " +
            " min=" + min +
            " max=" + max +
            " actual=" + actual+
            ".");
    }
}