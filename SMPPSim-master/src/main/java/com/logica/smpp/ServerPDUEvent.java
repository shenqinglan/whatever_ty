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
 * Instance of this class is created and passed to
 * the <code>ServerPDUEventListener</code> by the <code>Receiver</code>.
 *
 * @author Logica Mobile Networks SMPP Open Source Team
 * @version 1.0, 21 Aug 2001
 */
public class ServerPDUEvent extends ReceivedPDUEvent
{
    /**
     * Creates event for provided <code>Receiver</code> and
     * <code>Connection</code> with the received <code>PDU</code>.
     */
    public ServerPDUEvent(Receiver source,
                          Connection connection, PDU pdu)
    {
        super(source,connection,pdu);
    }

    /**
     * Returns the receiver thru which was received the PDU this
     * event relates to.
     */
    public Receiver getReceiver() { return (Receiver)getSource(); }

}
