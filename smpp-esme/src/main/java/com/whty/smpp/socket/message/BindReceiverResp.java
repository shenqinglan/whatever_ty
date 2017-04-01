package com.whty.smpp.socket.message;

import com.whty.smpp.socket.constants.SmppConstants;
/**
 * 
 * @ClassName BindReceiverResp
 * @author Administrator
 * @date 2017-3-10 下午1:47:17
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class BindReceiverResp extends BaseBindResp {

    public BindReceiverResp() {
        super(SmppConstants.CMD_ID_BIND_RECEIVER_RESP, "bind_receiver_resp");
    }
    
}