package com.whty.smpp.socket.message;

import com.whty.smpp.socket.constants.SmppConstants;
/**
 * 
 * @ClassName BindTransceiverResp
 * @author Administrator
 * @date 2017-3-10 下午1:47:10
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class BindTransceiverResp extends BaseBindResp {

    public BindTransceiverResp() {
        super(SmppConstants.CMD_ID_BIND_TRANSCEIVER_RESP, "bind_transceiver_resp");
    }
    
}