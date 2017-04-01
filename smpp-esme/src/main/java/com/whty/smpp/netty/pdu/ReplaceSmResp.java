package com.whty.smpp.netty.pdu;

import org.jboss.netty.buffer.ChannelBuffer;

import com.whty.smpp.netty.constants.SmppConstants;
import com.whty.smpp.netty.exception.RecoverablePduException;
import com.whty.smpp.netty.exception.UnrecoverablePduException;
/**
 * @ClassName ReplaceSmResp
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class ReplaceSmResp extends PduResponse {

    public ReplaceSmResp() {
        super(SmppConstants.CMD_ID_REPLACE_SM_RESP, "replace_sm_resp");
    }
    
    @Override
    public void readBody(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        // nothing
    }

    @Override
    public int calculateByteSizeOfBody() {
        return 0;
    }

    @Override
    public void writeBody(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        // do nothing
    }

    @Override
    public void appendBodyToString(StringBuilder buffer) {
    }

}