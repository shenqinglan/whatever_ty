package com.whty.smpp.esme.message;


import org.jboss.netty.buffer.ChannelBuffer;

import com.cloudhopper.commons.util.HexUtil;
import com.cloudhopper.commons.util.StringUtil;
import com.whty.smpp.esme.constants.SmppConstants;
import com.whty.smpp.esme.exception.RecoverablePduException;
import com.whty.smpp.esme.exception.UnrecoverablePduException;
import com.whty.smpp.esme.util.ChannelBufferUtil;
import com.whty.smpp.esme.util.PduUtil;
/**
 * @ClassName QuerySmResp
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class QuerySmResp extends PduResponse {

    private String messageId;
    private String finalDate;
    private byte messageState;
    private byte errorCode;

    public QuerySmResp() {
        super(SmppConstants.CMD_ID_QUERY_SM_RESP, "query_sm_resp");
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(final String iMessageId) {
        messageId = iMessageId;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(final String iFinalDate) {
        finalDate = iFinalDate;
    }

    public byte getMessageState() {
        return messageState;
    }

    public void setMessageState(final byte iMessageState) {
        messageState = iMessageState;
    }

    public byte getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(final byte iErrorCode) {
        errorCode = iErrorCode;
    }

    @Override
    public void readBody(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        this.messageId = ChannelBufferUtil.readNullTerminatedString(buffer);
        this.finalDate = ChannelBufferUtil.readNullTerminatedString(buffer);
        this.messageState = buffer.readByte();
        this.errorCode = buffer.readByte();
    }

    @Override
    public int calculateByteSizeOfBody() {
        int bodyLength = 0;
        bodyLength += PduUtil.calculateByteSizeOfNullTerminatedString(this.messageId);
        bodyLength += PduUtil.calculateByteSizeOfNullTerminatedString(this.finalDate);
        bodyLength += 2;    // messageState, errorCode
        return bodyLength;
    }

    @Override
    public void writeBody(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        ChannelBufferUtil.writeNullTerminatedString(buffer, this.messageId);
        ChannelBufferUtil.writeNullTerminatedString(buffer, this.finalDate);
        buffer.writeByte(this.messageState);
        buffer.writeByte(this.errorCode);
    }

    @Override
    public void appendBodyToString(StringBuilder buffer) {
        buffer.append("(messageId [");
        buffer.append(StringUtil.toStringWithNullAsEmpty(this.messageId));
        buffer.append("] finalDate [");
        buffer.append(StringUtil.toStringWithNullAsEmpty(this.finalDate));
        buffer.append("] messageState [0x");
        buffer.append(HexUtil.toHexString(this.messageState));
        buffer.append("] errorCode [0x");
        buffer.append(HexUtil.toHexString(this.errorCode));
        buffer.append("])");
    }
}
