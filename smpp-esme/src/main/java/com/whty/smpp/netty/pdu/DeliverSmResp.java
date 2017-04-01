package com.whty.smpp.netty.pdu;

import com.whty.smpp.netty.constants.SmppConstants;
/**
 * 
 * @ClassName DeliverSmResp
 * @author Administrator
 * @date 2017-3-10 下午1:33:54
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class DeliverSmResp extends BaseSmResp {

    public DeliverSmResp() {
        super(SmppConstants.CMD_ID_DELIVER_SM_RESP, "deliver_sm_resp");
    }
    
}