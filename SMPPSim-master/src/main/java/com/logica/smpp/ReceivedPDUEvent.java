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

import java.util.EventObject;
import com.logica.smpp.pdu.PDU;

/**
 * The base class for events representing receiving a pdu by
 * receiver.
 *
 * @author Logica Mobile Networks SMPP Open Source Team
 * @version 1.0, 21 Aug 2001
 */

/*
  10-10-01 ticp@logica.com connection and pdu carried by the event made transient
                           (they are non-serializable)
*/
public class ReceivedPDUEvent extends EventObject
{
    /**
     * The connection over which was the pdu received.
     */
    private transient Connection connection = null;

    /**
     * The received pdu.
     */
    private transient PDU pdu = null;
    
    /**
     * Construct event for pdu received over connection belonging
     * to the receiver.
     */
    public ReceivedPDUEvent(ReceiverBase source,
                            Connection connection, PDU pdu)
    {
        super(source);
        this.connection = connection;
        this.pdu = pdu;
    }        

    /**
     * Return the connection over which the pdu was received.
     */
    public Connection getConnection() { return connection; }
    
    /**
     * Return the received pdu.
     */
    public PDU getPDU() { return pdu; }

}
