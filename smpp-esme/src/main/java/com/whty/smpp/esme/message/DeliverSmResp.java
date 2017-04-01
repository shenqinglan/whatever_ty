package com.whty.smpp.esme.message;

import com.whty.smpp.esme.constants.SmppConstants;
/**
 * @ClassName DeliverSmResp
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class DeliverSmResp extends BaseSmResp {

    public DeliverSmResp() {
        super(SmppConstants.CMD_ID_DELIVER_SM_RESP, "deliver_sm_resp");
    }
    
}