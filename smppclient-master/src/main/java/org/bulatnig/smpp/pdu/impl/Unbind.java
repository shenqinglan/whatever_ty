package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.util.ByteBuffer;

/**
 * The purpose of the SMPP unbind operation is to deregister an instance of an
 * ESME from the SMSC and inform the SMSC that the ESME no longer wishes to use
 * this network connection for the submission or delivery of messages.<br/>
 * <p/>
 * Thus, the unbind operation may be viewed as a form of SMSC logoff request to
 * close the current SMPP session.
 *
 * @author Bulat Nigmatullin
 */
public class Unbind extends AbstractPdu {

    public Unbind() {
        super(CommandId.UNBIND);
    }

    Unbind(ByteBuffer bb) throws PduException {
        super(bb);
    }

    @Override
    protected ByteBuffer body() {
        return null;
    }
}
