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
 * @version 1.1, 11 Oct 2001
 */

/*
  11-10-01 ticp@logica.com max address length configurable per instance
*/

public class Address extends ByteData
{
    private byte ton       = Data.DFLT_GSM_TON;
    private byte npi       = Data.DFLT_GSM_NPI;
    private String address = Data.DFLT_ADDR;
    private static int defaultMaxAddressLength = Data.SM_ADDR_LEN;
    private int maxAddressLength = defaultMaxAddressLength;
    
    public Address()
    {
        this(Data.getDefaultTon(), Data.getDefaultNpi(),
             defaultMaxAddressLength);
    }
    
    public Address(int maxAddressLength)
    {
        this(Data.getDefaultTon(), Data.getDefaultNpi(),
             maxAddressLength);
    }
    
    public Address(byte ton, byte npi, int maxAddressLength)
    {
        setTon(ton);
        setNpi(npi);
        try {
            setAddress(Data.DFLT_ADDR, maxAddressLength);
        } catch (WrongLengthOfStringException e) {
            throw new Error("Default address value was longer than default max address length.");
        }
    }
    
    public Address(String address)
    throws WrongLengthOfStringException
    {
        this(Data.getDefaultTon(), Data.getDefaultNpi(), address,
             defaultMaxAddressLength);
    }
    
    public Address(String address, int maxAddressLength)
    throws WrongLengthOfStringException
    {
        this(Data.getDefaultTon(), Data.getDefaultNpi(), address,
             maxAddressLength);
    }
    
    public Address(byte ton, byte npi, String address)
    throws WrongLengthOfStringException
    {
        this(ton, npi, address, defaultMaxAddressLength);
    }

    public Address(byte ton, byte npi, String address, int maxAddressLength)
    throws WrongLengthOfStringException
    {
        setTon(ton);
        setNpi(npi);
        setAddress(address, maxAddressLength);
    }
    
    public void setData(ByteBuffer buffer)
    throws NotEnoughDataInByteBufferException,
           TerminatingZeroNotFoundException,
           WrongLengthOfStringException
    {
        byte ton = buffer.removeByte();
        byte npi = buffer.removeByte();
        String address = buffer.removeCString();
        setAddress(address);
        setTon(ton);
        setNpi(npi);
    }
    
    public ByteBuffer getData()
    {
        ByteBuffer addressBuf = new ByteBuffer();
        addressBuf.appendByte(getTon());
        addressBuf.appendByte(getNpi());
        addressBuf.appendCString(getAddress());
        return addressBuf;
    }
    
    public void setTon(byte ton) { this.ton = ton; }
    public void setNpi(byte npi) { this.npi = npi; }
    public void setAddress(String address)
    throws WrongLengthOfStringException {
        setAddress(address,maxAddressLength);
    }
    public void setAddress(String address, int maxAddressLength)
    throws WrongLengthOfStringException {
        checkCString(address,maxAddressLength);
        this.maxAddressLength = maxAddressLength;
        this.address = address;
    }

    public byte getTon()       { return ton; }
    public byte getNpi()       { return npi; }
    public String getAddress() { return address; }
    
    public String debugString()
    {
        String dbgs = "(addr: ";
        dbgs += super.debugString();
        dbgs += Integer.toString(getTon()); dbgs += " ";
        dbgs += Integer.toString(getNpi()); dbgs += " ";
        dbgs += getAddress();
        dbgs += ") ";
        return dbgs;
    }
    
}
