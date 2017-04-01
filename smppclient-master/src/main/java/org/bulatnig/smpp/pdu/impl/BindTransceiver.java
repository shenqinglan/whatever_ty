package org.bulatnig.smpp.pdu.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.util.ByteBuffer;
import org.bulatnig.smpp.util.TerminatingNullNotFoundException;

/**
 * An ESME bound as a Transceiver is allowed to send messages to the SMSC and
 * receive messages from the SMSC over a single SMPP session.
 *
 * @author Bulat Nigmatullin
 */
public class BindTransceiver extends AbstractPdu {

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
    /**
     * Identifies the type of ESME system requesting to bind as a receiver with
     * the SMSC.
     */
    private String systemType;
    /**
     * Identifies the version of the SMPP protocol supported by the ESME.
     */
    private int interfaceVersion;
    /**
     * Type of Number (TON) for ESME address(es) served via this SMPP receiver
     * session. Set to NULL if not known.
     */
    private int addrTon;
    /**
     * Numbering Plan Indicator (NPI) for ESME address(es) served via this SMPP
     * receiver session. Set to NULL if not known.
     */
    private int addrNpi;
    /**
     * A single ESME address or a range of ESME addresses served via this SMPP
     * receiver session. The parameter value is represented in UNIX regular
     * expression format (see Appendix A). Set to NULL if not known.
     */
    private String addressRange;

    public BindTransceiver() {
        super(CommandId.BIND_TRANSCEIVER);
    }

    BindTransceiver(ByteBuffer bb) throws PduException {
        super(bb);
        try {
            systemId = bb.removeCString();
        } catch (TerminatingNullNotFoundException e) {
            throw new PduException("System id parsing failed.", e);
        }
        try {
            password = bb.removeCString();
        } catch (TerminatingNullNotFoundException e) {
            throw new PduException("Password parsing failed.", e);
        }
        try {
            systemType= bb.removeCString();
        } catch (TerminatingNullNotFoundException e) {
            throw new PduException("System type parsing failed.", e);
        }
        interfaceVersion = bb.removeByte();
        addrTon = bb.removeByte();
        addrNpi = bb.removeByte();
        try {
            addressRange = bb.removeCString();
        } catch (TerminatingNullNotFoundException e) {
            throw new PduException("Address range parsing failed.", e);
        }
    }

    @Override
    protected ByteBuffer body() {
        ByteBuffer bb = new ByteBuffer();
        bb.appendCString(systemId);
        bb.appendCString(password);
        bb.appendCString(systemType);
        bb.appendByte(interfaceVersion);
        bb.appendByte(addrTon);
        bb.appendByte(addrNpi);
        bb.appendCString(addressRange);
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

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public int getInterfaceVersion() {
        return interfaceVersion;
    }

    public void setInterfaceVersion(int interfaceVersion) {
        this.interfaceVersion = interfaceVersion;
    }

    public int getAddrTon() {
        return addrTon;
    }

    public void setAddrTon(int addrTon) {
        this.addrTon = addrTon;
    }

    public int getAddrNpi() {
        return addrNpi;
    }

    public void setAddrNpi(int addrNpi) {
        this.addrNpi = addrNpi;
    }

    public String getAddressRange() {
        return addressRange;
    }

    public void setAddressRange(String addressRange) {
        this.addressRange = addressRange;
    }
}
