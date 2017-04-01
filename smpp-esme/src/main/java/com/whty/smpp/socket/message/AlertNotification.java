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
 * @ClassName AlertNotification
 * @author Administrator
 * @date 2017-3-10 下午1:47:42
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class AlertNotification extends Pdu {

    protected Address sourceAddress;
    protected Address esmeAddress;

    public AlertNotification(){
        super( SmppConstants.CMD_ID_ALERT_NOTIFICATION, "alert_notification", true );
    }

    public Address getSourceAddress() {
        return this.sourceAddress;
    }

    public void setSourceAddress(Address value) {
        this.sourceAddress = value;
    }

    public Address getEsmeAddress() {
        return this.esmeAddress;
    }

    public void setEsmeAddress(Address value) {
        this.esmeAddress = value;
    }

    @Override
    protected int calculateByteSizeOfBody(){
        int bodyLength = 0;
        bodyLength += PduUtil.calculateByteSizeOfAddress(this.sourceAddress);
        bodyLength += PduUtil.calculateByteSizeOfAddress(this.esmeAddress);
        return bodyLength;
    }

    @Override
    public void readBody( ChannelBuffer buffer ) throws UnrecoverablePduException, RecoverablePduException{
        this.sourceAddress = ChannelBufferUtil.readAddress(buffer);
        this.esmeAddress = ChannelBufferUtil.readAddress(buffer);
    }

    @Override
    public void writeBody( ChannelBuffer buffer ) throws UnrecoverablePduException, RecoverablePduException{
        ChannelBufferUtil.writeAddress(buffer, this.sourceAddress);
        ChannelBufferUtil.writeAddress(buffer, this.esmeAddress);
    }

    @Override
    protected void appendBodyToString( StringBuilder buffer ){
        buffer.append("( sourceAddr [");
        buffer.append(StringUtil.toStringWithNullAsEmpty(this.sourceAddress));
        buffer.append("] esmeAddr [");
        buffer.append(StringUtil.toStringWithNullAsEmpty(this.esmeAddress));
        buffer.append("])");
    }

}
