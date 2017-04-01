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
package com.logica.smpp.pdu;

/**
 * @author Logica Mobile Networks SMPP Open Source Team
 * @version 1.0, 11 Jun 2001
 */

public class IntegerOutOfRangeException extends PDUException
{
    public IntegerOutOfRangeException()
    {
        super("The integer is lower or greater than required.");
    }
    
    public IntegerOutOfRangeException(int min, int max, int val)
    {
        super("The integer is lower or greater than required: " +
            " min=" + min +
            " max=" + max +
            " actual=" + val+
            ".");
    }
}