package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.util.ByteBuffer;
import org.bulatnig.smpp.util.TerminatingNullNotFoundException;

/**
 * @ClassName Outbind
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class Outbind extends AbstractPdu {


	/**
     * Identifies the ESME system requesting to bind as a receiver with the
     * SMSC.
     */
    private String systemId;
    /**
     * The password may be used by the SMSC for security reasons to authenticate
     * the ESME requesting to bind.
     */
    private String password;

    public Outbind() {
        super(CommandId.OUTBIND);
    }

    protected Outbind(ByteBuffer bb) throws PduException {
        super(bb);
        try {
        	systemId = bb.removeCString();
        } catch (TerminatingNullNotFoundException e) {
            throw new PduException("systemId parsing failed.", e);
        }
        try {
        	password = bb.removeCString();
        } catch (TerminatingNullNotFoundException e) {
            throw new PduException("password parsing failed.", e);
        }
    }

    @Override
    protected ByteBuffer body() {
        ByteBuffer bb = new ByteBuffer();
        bb.appendCString(systemId);
        bb.appendCString(password);
        return bb;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
