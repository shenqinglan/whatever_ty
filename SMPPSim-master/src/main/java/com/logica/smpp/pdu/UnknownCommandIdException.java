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
 * @version 1.1, 10 Oct 2001
 */

/*
  10-10-01 ticp@logica.com pdu header carried by the exception made transient
                           (it is not serializable)
*/

public class UnknownCommandIdException extends PDUException
{
    private transient PDUHeader header = null;
    
    public UnknownCommandIdException()
    {
    }

    public UnknownCommandIdException(PDUHeader header)
    {
        this.header = header;
    }
    
    private void checkHeader()
    {
        if (header == null) {
            header = new PDUHeader();
        }
    }
    
    public int getCommandLength()
    {
        return header == null ? 0 : header.getCommandLength();
    }

    public int getCommandId()
    {
        return header == null ? 0 : header.getCommandId();
    }

    public int getCommandStatus()
    {
        return header == null ? 0 : header.getCommandStatus();
    }

    public int getSequenceNumber()
    {
        return header == null ? 0 : header.getSequenceNumber();
    }

}
