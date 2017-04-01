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
 * @ClassName QuerySm
 * @author Administrator
 * @date 2017-3-10 下午1:46:00
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class QuerySm extends PduRequest<QuerySmResp> {

    private String messageId;
    private Address sourceAddress;

    public QuerySm() {
        super(SmppConstants.CMD_ID_QUERY_SM, "query_sm");
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


    @Override
    public void readBody(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        this.messageId = ChannelBufferUtil.readNullTerminatedString(buffer);
        this.sourceAddress = ChannelBufferUtil.readAddress(buffer);
    }

    @Override
    public int calculateByteSizeOfBody() {
        int bodyLength = 0;
        bodyLength += PduUtil.calculateByteSizeOfNullTerminatedString(this.messageId);
        bodyLength += PduUtil.calculateByteSizeOfAddress(this.sourceAddress);
        return bodyLength;
    }

    @Override
    public void writeBody(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        ChannelBufferUtil.writeNullTerminatedString(buffer, this.messageId);
        ChannelBufferUtil.writeAddress(buffer, this.sourceAddress);
    }

    @Override
    public void appendBodyToString(StringBuilder buffer) {
        buffer.append("(messageId [");
        buffer.append(StringUtil.toStringWithNullAsEmpty(this.messageId));
        buffer.append("] sourceAddr [");
        buffer.append(StringUtil.toStringWithNullAsEmpty(this.sourceAddress));
        buffer.append("])");

    }

    @Override
    public QuerySmResp createResponse() {
        QuerySmResp resp = new QuerySmResp();
        resp.setSequenceNumber(this.getSequenceNumber());
        return resp;
    }

    @Override
    public Class<QuerySmResp> getResponseClass() {
        return QuerySmResp.class;
    }

}
