package com.whty.smpp.netty.pdu;

import com.whty.smpp.netty.constants.SmppConstants;

/**
 * 
 * @ClassName DeliverSm
 * @author Administrator
 * @date 2017-3-10 下午1:35:25
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class DeliverSm extends BaseSm<DeliverSmResp> {

    public DeliverSm() {
        super(SmppConstants.CMD_ID_DELIVER_SM, "deliver_sm");
    }

    @Override
    public DeliverSmResp createResponse() {
        DeliverSmResp resp = new DeliverSmResp();
        resp.setSequenceNumber(this.getSequenceNumber());
        return resp;
    }

    @Override
    public Class<DeliverSmResp> getResponseClass() {
        return DeliverSmResp.class;
    }
    
}