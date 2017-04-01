package com.whty.smpp.esme.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.whty.smpp.esme.constants.SmppConstants;
import com.whty.smpp.esme.exception.RecoverablePduException;
import com.whty.smpp.esme.exception.UnrecoverablePduException;
import com.whty.smpp.esme.util.ChannelBufferUtil;
import com.whty.smpp.esme.util.PduUtil;
/**
 * @ClassName DataSm
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class DataSm extends BaseSm<DataSmResp> {

    public DataSm() {
        super(SmppConstants.CMD_ID_DATA_SM, "data_sm");
    }

    @Override
    public DataSmResp createResponse() {
        DataSmResp resp = new DataSmResp();
        resp.setSequenceNumber(this.getSequenceNumber());
        return resp;
    }

    @Override
    public Class<DataSmResp> getResponseClass() {
        return DataSmResp.class;
    }
    
    @Override
    public void readBody(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        this.serviceType = ChannelBufferUtil.readNullTerminatedString(buffer);
        this.sourceAddress = ChannelBufferUtil.readAddress(buffer);
        this.destAddress = ChannelBufferUtil.readAddress(buffer);
        this.esmClass = buffer.readByte();
        this.registeredDelivery = buffer.readByte();
        this.dataCoding = buffer.readByte();
    }

    @Override
    public int calculateByteSizeOfBody() {
        int bodyLength = 0;
        bodyLength += PduUtil.calculateByteSizeOfNullTerminatedString(this.serviceType);
        bodyLength += PduUtil.calculateByteSizeOfAddress(this.sourceAddress);
        bodyLength += PduUtil.calculateByteSizeOfAddress(this.destAddress);
        bodyLength += 3;    // esmClass, regDelivery, dataCoding bytes
        return bodyLength;
    }

    @Override
    public void writeBody(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        ChannelBufferUtil.writeNullTerminatedString(buffer, this.serviceType);
        ChannelBufferUtil.writeAddress(buffer, this.sourceAddress);
        ChannelBufferUtil.writeAddress(buffer, this.destAddress);
        buffer.writeByte(this.esmClass);
        buffer.writeByte(this.registeredDelivery);
        buffer.writeByte(this.dataCoding);
    }
    
}