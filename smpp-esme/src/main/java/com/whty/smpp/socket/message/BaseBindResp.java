package com.whty.smpp.socket.message;


import org.jboss.netty.buffer.ChannelBuffer;

import com.cloudhopper.commons.util.StringUtil;
import com.whty.smpp.socket.exception.RecoverablePduException;
import com.whty.smpp.socket.exception.UnrecoverablePduException;
import com.whty.smpp.socket.util.ChannelBufferUtil;
import com.whty.smpp.socket.util.PduUtil;
/**
 * 
 * @ClassName BaseBindResp
 * @author Administrator
 * @date 2017-3-10 下午1:47:35
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public abstract class BaseBindResp extends PduResponse {

    private String systemId;

    public BaseBindResp(int commandId, String name) {
        super(commandId, name);
    }

    public void setSystemId(String value) {
        this.systemId = value;
    }

    public String getSystemId() {
        return this.systemId;
    }

    @Override
    public void readBody(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        // the body may or may not contain a systemId -- the helper utility
        // method will take care of returning null if there aren't any readable bytes
        this.systemId = ChannelBufferUtil.readNullTerminatedString(buffer);
    }

    @Override
    public int calculateByteSizeOfBody() {
        int bodyLength = 0;
        bodyLength += PduUtil.calculateByteSizeOfNullTerminatedString(this.systemId);
        return bodyLength;
    }

    @Override
    public void writeBody(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        ChannelBufferUtil.writeNullTerminatedString(buffer, this.systemId);
    }

    @Override
    public void appendBodyToString(StringBuilder buffer) {
        buffer.append("systemId [");
        buffer.append(StringUtil.toStringWithNullAsEmpty(this.systemId));
        buffer.append("]");
    }
}