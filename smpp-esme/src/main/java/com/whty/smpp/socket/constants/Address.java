package com.whty.smpp.socket.constants;

import org.jboss.netty.buffer.ChannelBuffer;

import com.cloudhopper.commons.util.HexUtil;
import com.cloudhopper.commons.util.StringUtil;
import com.whty.smpp.socket.exception.RecoverablePduException;
import com.whty.smpp.socket.exception.UnrecoverablePduException;
import com.whty.smpp.socket.util.ChannelBufferUtil;
import com.whty.smpp.socket.util.PduUtil;
/**
 * 
 * @ClassName Address
 * @author Administrator
 * @date 2017-3-10 下午1:48:13
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class Address {

    private byte ton;
    private byte npi;
    private String address;

    public Address() {
        this((byte)0, (byte)0, (String)null);
    }

    public Address(byte ton, byte npi, String address) {
        this.ton = ton;
        this.npi = npi;
        this.address = address;
    }

    public byte getTon() {
        return this.ton;
    }

    public void setTon(byte value) {
        this.ton = value;
    }

    public byte getNpi() {
        return this.npi;
    }

    public void setNpi(byte value) {
        this.npi = value;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String value) {
        this.address = value;
    }

    public int calculateByteSize() {
        return 2 + PduUtil.calculateByteSizeOfNullTerminatedString(this.address);
    }

    public void read(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        this.ton = buffer.readByte();
        this.npi = buffer.readByte();
        this.address = ChannelBufferUtil.readNullTerminatedString(buffer);
    }

    public void write(ChannelBuffer buffer) throws UnrecoverablePduException, RecoverablePduException {
        buffer.writeByte(this.ton);
        buffer.writeByte(this.npi);
        ChannelBufferUtil.writeNullTerminatedString(buffer, this.address);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder(40);
        buffer.append("0x");
        buffer.append(HexUtil.toHexString(this.ton));
        buffer.append(" 0x");
        buffer.append(HexUtil.toHexString(this.npi));
        buffer.append(" [");
        buffer.append(StringUtil.toStringWithNullAsEmpty(this.address));
        buffer.append("]");
        return buffer.toString();
    }
    
}
