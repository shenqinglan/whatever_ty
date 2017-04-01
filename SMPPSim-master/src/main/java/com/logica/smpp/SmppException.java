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
package com.logica.smpp;

/**
 * Class <code>SmppException</code> is the root of all SMPP Library
 * exceptions. Every exception defined in the library <code>SmppException</code>
 * as a superclass -- this way class <code>SmppException</code>
 * provides single class for <code>catch</code> clause.
 * 
 * @author Logica Mobile Networks SMPP Open Source Team
 * @version 1.0, 11 Jun 2001
 */

public class SmppException extends Exception
{
    /**
     * Constructs a <code>SmppException</code> with no specified detail
     * message. 
     */
    public SmppException()
    {
        super();
    }
    
    /**
     * Constructs a <code>SmppException</code> with the specified detail
     * message. 
     *
     * @param   s   the detail message.
     */
    public SmppException(String s)
    {
        super(s);
    }
}