package com.whty.smpp.esme.message;


import org.jboss.netty.buffer.ChannelBuffer;

import com.cloudhopper.commons.util.StringUtil;
import com.whty.smpp.esme.exception.RecoverablePduException;
import com.whty.smpp.esme.exception.UnrecoverablePduException;
import com.whty.smpp.esme.util.ChannelBufferUtil;
import com.whty.smpp.esme.util.PduUtil;
/**
 * @ClassName BaseSmResp
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public abstract class BaseSmResp extends PduResponse {

    private String messageId;

    public BaseSmResp(int commandId, String name) {
        super(commandId, name);
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String value) {
        this.messageId = value;
    }

    @Override
    public void readBody(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        // the body may or may not contain a messageId -- the helper utility
        // method will take care of returning null if there aren't any readable bytes
        this.messageId = ChannelBufferUtil.readNullTerminatedString(buffer);
    }

    @Override
    public int calculateByteSizeOfBody() {
        int bodyLength = 0;
        bodyLength += PduUtil.calculateByteSizeOfNullTerminatedString(this.messageId);
        return bodyLength;
    }

    @Override
    public void writeBody(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        // when this PDU was parsed, it's possible it was missing the messageId instead
        // of having a NULL messageId. If that's the case, the commandLength will be just
        // enough for the headers (and theoretically any optional TLVs). Don't try to
        // write the NULL byte for that case.
        // See special note in 4.4.2 of SMPP 3.4 spec
        if (!((buffer.writableBytes() == 0) && (this.messageId == null))) {
            ChannelBufferUtil.writeNullTerminatedString(buffer, this.messageId);
        }
    }

    @Override
    public void appendBodyToString(StringBuilder buffer) {
        buffer.append("(messageId [");
        buffer.append(StringUtil.toStringWithNullAsEmpty(this.messageId));
        buffer.append("])");
    }
}