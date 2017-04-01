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

public class DistributionList extends ByteData
{
    private String dlName = Data.DFLT_DL_NAME;
    
    public DistributionList()
    {
    }
    
    public DistributionList(String dlName)
    throws WrongLengthOfStringException
    {
        setDlName(dlName);
    }
    
    public void setData(ByteBuffer buffer)
    throws NotEnoughDataInByteBufferException,
           TerminatingZeroNotFoundException,
           WrongLengthOfStringException
    {
        setDlName(buffer.removeCString());
    }
    
    public ByteBuffer getData()
    {
        ByteBuffer buffer = new ByteBuffer();
        buffer.appendCString(getDlName());
        return buffer;
    }
    
    public void setDlName(String dln)
    throws WrongLengthOfStringException {
        checkCString(dln,Data.SM_DL_NAME_LEN);
        dlName = dln;
    }
    
    public String getDlName() { return dlName; }
    
    public String debugString()
    {
        String dbgs = "(dl: ";
        dbgs += super.debugString();
        dbgs += getDlName();
        dbgs += ") ";
        return dbgs;
    }
    
}