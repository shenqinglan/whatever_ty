package com.whty.smpp.esme.message;

import com.whty.smpp.esme.constants.SmppConstants;
/**
 * @ClassName BindReceiverResp
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class BindReceiverResp extends BaseBindResp {

    public BindReceiverResp() {
        super(SmppConstants.CMD_ID_BIND_RECEIVER_RESP, "bind_receiver_resp");
    }
    
}