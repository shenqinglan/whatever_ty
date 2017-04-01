package com.whty.smpp.socket.message;

import com.whty.smpp.socket.constants.SmppConstants;
/**
 * 
 * @ClassName SubmitSmResp
 * @author Administrator
 * @date 2017-3-10 下午1:45:42
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SubmitSmResp extends BaseSmResp {

    public SubmitSmResp() {
        super(SmppConstants.CMD_ID_SUBMIT_SM_RESP, "submit_sm_resp");
    }
    
}