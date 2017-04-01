package com.whty.smpp.esme.message;

import com.whty.smpp.esme.constants.SmppConstants;
/**
 * @ClassName BindTransmitterResp
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class BindTransmitterResp extends BaseBindResp {

    public BindTransmitterResp() {
        super(SmppConstants.CMD_ID_BIND_TRANSMITTER_RESP, "bind_transmitter_resp");
    }
    
}