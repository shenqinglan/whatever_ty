package com.whty.smpp.socket.message;

import com.whty.smpp.socket.constants.SmppConstants;
/**
 * 
 * @ClassName SubmitSm
 * @author Administrator
 * @date 2017-3-10 下午1:45:46
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SubmitSm extends BaseSm<SubmitSmResp> {

    public SubmitSm() {
        super(SmppConstants.CMD_ID_SUBMIT_SM, "submit_sm");
    }

    @Override
    public SubmitSmResp createResponse() {
        SubmitSmResp resp = new SubmitSmResp();
        resp.setSequenceNumber(this.getSequenceNumber());
        return resp;
    }

    @Override
    public Class<SubmitSmResp> getResponseClass() {
        return SubmitSmResp.class;
    }
    
}