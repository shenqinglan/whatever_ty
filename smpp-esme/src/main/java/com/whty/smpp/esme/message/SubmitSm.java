package com.whty.smpp.esme.message;

import com.whty.smpp.esme.constants.SmppConstants;
/**
 * @ClassName SubmitSm
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
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