package com.whty.smpp.netty.pdu;

import com.whty.smpp.netty.constants.SmppConstants;
/**
 * @ClassName SubmitSmResp
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SubmitSmResp extends BaseSmResp {

    public SubmitSmResp() {
        super(SmppConstants.CMD_ID_SUBMIT_SM_RESP, "submit_sm_resp");
    }
    
}