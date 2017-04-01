package com.whty.smpp.esme.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.whty.smpp.esme.constants.SmppConstants;
import com.whty.smpp.esme.exception.RecoverablePduException;
import com.whty.smpp.esme.exception.UnrecoverablePduException;
/**
 * @ClassName CancelSmResp
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class CancelSmResp extends PduResponse {

    public CancelSmResp() {
        super(SmppConstants.CMD_ID_CANCEL_SM_RESP, "cancel_sm_resp");
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
