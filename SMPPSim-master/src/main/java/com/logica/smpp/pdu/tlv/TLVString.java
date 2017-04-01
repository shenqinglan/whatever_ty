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
package com.logica.smpp.pdu.tlv;

import com.logica.smpp.util.ByteBuffer;
import com.logica.smpp.util.NotEnoughDataInByteBufferException;
import com.logica.smpp.util.TerminatingZeroNotFoundException;
import com.logica.smpp.pdu.ValueNotSetException;

/**
 * @author Logica Mobile Networks SMPP Open Source Team
 * @version 1.1, 20 Nov 2001
 */

/*
  20-11-01 ticp@logica.com setValue() now sets flag that the value was set
*/

public class TLVString extends TLV
{
    private String value;

    public TLVString()
    {
        super();
    }
    
    public TLVString(short tag)
    {
        super(tag);
    }
    
    public TLVString(short tag, int min, int max)
    {
        super(tag,min,max);
    }
    
    public TLVString(short tag, String value)
    throws TLVException
    {
        super(tag);
        setValue(value);
    }
    
    public TLVString(short tag, int min, int max, String value)
    throws TLVException
    {
        super(tag, min, max);
        setValue(value);
    }
    
    public void setValueData(ByteBuffer buffer)
    throws TLVException
    {
        checkLength(buffer);
        if (buffer != null) {
            try {
                value = buffer.removeCString();
            } catch (NotEnoughDataInByteBufferException e) {
                throw new TLVException("Not enough data for string in the buffer.");
            } catch (TerminatingZeroNotFoundException e) {
                throw new TLVException("String terminating zero not found in the buffer.");
            }
        } else {
            value = new String("");
        }
        markValueSet();
    }
    
    public ByteBuffer getValueData()
    throws ValueNotSetException
    {
        ByteBuffer valueBuf = new ByteBuffer();
        valueBuf.appendCString(getValue());
        return valueBuf;
    }
    
    public void setValue(String value)
    throws WrongLengthException
    {
        checkLength(value.length() + 1);
        this.value = value;
        markValueSet();
    }
    
    public String getValue()
    throws ValueNotSetException
    {
        if (hasValue()) {
            return value;
        } else {
            throw new ValueNotSetException();
        }
    }
    
    public String debugString()
    {        
        String dbgs = "(str: ";
        dbgs += super.debugString();
        dbgs += value;
        dbgs += ") ";
        return dbgs;
    }

}
