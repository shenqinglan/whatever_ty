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

import com.logica.smpp.Data;
import com.logica.smpp.util.*;

/**
 * @author Logica Mobile Networks SMPP Open Source Team
 * @version 1.0, 11 Jun 2001
 */

public class GenericNack extends Response
{
    public GenericNack()
    {
        super(Data.GENERIC_NACK);
    }
    
    public GenericNack(int commandStatus, int sequenceNumber)
    {
        super(Data.GENERIC_NACK);
        setCommandStatus(commandStatus);
        setSequenceNumber(sequenceNumber);
    }
    
    public void setBody(ByteBuffer buffer)
    throws NotEnoughDataInByteBufferException,
           TerminatingZeroNotFoundException,
           PDUException
    {
    }
    
    public ByteBuffer getBody()
    {
        return null;
    }
    
    public String debugString()
    {
        String dbgs = "(genericnack: ";
        dbgs += super.debugString();
        dbgs += ")";
        return dbgs;
    }

}
