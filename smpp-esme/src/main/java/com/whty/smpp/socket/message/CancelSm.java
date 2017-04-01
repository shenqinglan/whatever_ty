package com.whty.smpp.socket.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.cloudhopper.commons.util.StringUtil;
import com.whty.smpp.socket.constants.Address;
import com.whty.smpp.socket.constants.SmppConstants;
import com.whty.smpp.socket.exception.RecoverablePduException;
import com.whty.smpp.socket.exception.UnrecoverablePduException;
import com.whty.smpp.socket.util.ChannelBufferUtil;
import com.whty.smpp.socket.util.PduUtil;

/**
 * 
 * @ClassName CancelSm
 * @author Administrator
 * @date 2017-3-10 下午1:46:58
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class CancelSm extends PduRequest<CancelSmResp> {

    protected String serviceType;
    protected String messageId;
    protected Address sourceAddress;
    protected Address destAddress;

    public CancelSm() {
        super(SmppConstants.CMD_ID_CANCEL_SM, "cancel_sm");
    }

    public String getServiceType() {
        return this.serviceType;
    }

    public void setServiceType(String value) {
        this.serviceType = value;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String value) {
        this.messageId = value;
    }

    public Address getSourceAddress() {
        return this.sourceAddress;
    }

    public void setSourceAddress(Address value) {
        this.sourceAddress = value;
    }

    public Address getDestAddress() {
        return this.destAddress;
    }

    public void setDestAddress(Address value) {
        this.destAddress = value;
    }


    @Override
    public void readBody(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        this.serviceType = ChannelBufferUtil.readNullTerminatedString(buffer);
        this.messageId = ChannelBufferUtil.readNullTerminatedString(buffer);
        this.sourceAddress = ChannelBufferUtil.readAddress(buffer);
        this.destAddress = ChannelBufferUtil.readAddress(buffer);
    }

    @Override
    public int calculateByteSizeOfBody() {
        int bodyLength = 0;
        bodyLength += PduUtil.calculateByteSizeOfNullTerminatedString(this.serviceType);
        bodyLength += PduUtil.calculateByteSizeOfNullTerminatedString(this.messageId);
        bodyLength += PduUtil.calculateByteSizeOfAddress(this.sourceAddress);
        bodyLength += PduUtil.calculateByteSizeOfAddress(this.destAddress);
        return bodyLength;
    }

    @Override
    public void writeBody(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        ChannelBufferUtil.writeNullTerminatedString(buffer, this.serviceType);
        ChannelBufferUtil.writeNullTerminatedString(buffer, this.messageId);
        ChannelBufferUtil.writeAddress(buffer, this.sourceAddress);
        ChannelBufferUtil.writeAddress(buffer, this.destAddress);
    }

    @Override
    public void appendBodyToString(StringBuilder buffer) {
        buffer.append("(serviceType [");
        buffer.append(StringUtil.toStringWithNullAsEmpty(this.serviceType));
        buffer.append("] messageId [");
        buffer.append(StringUtil.toStringWithNullAsEmpty(this.messageId));
        buffer.append("] sourceAddr [");
        buffer.append(StringUtil.toStringWithNullAsEmpty(this.sourceAddress));
        buffer.append("] destAddr [");
        buffer.append(StringUtil.toStringWithNullAsEmpty(this.destAddress));
        buffer.append("])");

    }

    @Override
    public CancelSmResp createResponse() {
        CancelSmResp resp = new CancelSmResp();
        resp.setSequenceNumber(this.getSequenceNumber());
        return resp;
    }

    @Override
    public Class<CancelSmResp> getResponseClass() {
        return CancelSmResp.class;
    }

}
