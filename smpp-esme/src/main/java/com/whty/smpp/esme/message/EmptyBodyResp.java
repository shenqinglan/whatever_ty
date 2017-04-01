package com.whty.smpp.esme.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.whty.smpp.esme.exception.RecoverablePduException;
import com.whty.smpp.esme.exception.UnrecoverablePduException;
/**
 * @ClassName EmptyBodyResp
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public abstract class EmptyBodyResp extends PduResponse {

    public EmptyBodyResp(int commandId, String name) {
        super(commandId, name);
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
        // no body
    }

    @Override
    public void appendBodyToString(StringBuilder buffer) {
        // no body
    }
    
}