package com.whty.smpp.esme.message;

import com.whty.smpp.esme.constants.SmppConstants;

/**
 * @ClassName BindTransmitter
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class BindTransmitter extends BaseBind<BindTransmitterResp> {

    public BindTransmitter() {
        super(SmppConstants.CMD_ID_BIND_TRANSMITTER, "bind_transmitter");
    }

    @Override
    public BindTransmitterResp createResponse() {
        BindTransmitterResp resp = new BindTransmitterResp();
        resp.setSequenceNumber(this.getSequenceNumber());
        return resp;
    }

    @Override
    public Class<BindTransmitterResp> getResponseClass() {
        return BindTransmitterResp.class;
    }
    
}