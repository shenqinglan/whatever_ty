package com.whty.smpp.netty.pdu;


import org.jboss.netty.buffer.ChannelBuffer;

import com.whty.smpp.netty.constants.SmppConstants;
import com.whty.smpp.netty.exception.RecoverablePduException;
import com.whty.smpp.netty.exception.UnrecoverablePduException;
/**
 * 
 * @ClassName GenericNack
 * @author Administrator
 * @date 2017-3-10 下午1:33:35
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class GenericNack extends PduResponse {
    
    public GenericNack() {
        super(SmppConstants.CMD_ID_GENERIC_NACK, "generic_nack");
    }

    @Override
    public void readBody(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        // no body
    }

    @Override
    public int calculateByteSizeOfBody() {
        return 0;   // no body
    }

    @Override
    public void writeBody(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        /// no body
    }

    @Override
    public void appendBodyToString(StringBuilder buffer) {
        // no body
    }
    
}