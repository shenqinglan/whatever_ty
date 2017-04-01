package com.whty.smpp.socket.message;

import com.whty.smpp.socket.constants.SmppConstants;

/**
 * 
 * @ClassName BindTransmitterResp
 * @author Administrator
 * @date 2017-3-10 下午1:47:02
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class BindTransmitterResp extends BaseBindResp {

    public BindTransmitterResp() {
        super(SmppConstants.CMD_ID_BIND_TRANSMITTER_RESP, "bind_transmitter_resp");
    }
    
}