package com.whty.smpp.socket.message;

import com.whty.smpp.socket.constants.SmppConstants;
/**
 * 
 * @ClassName DataSmResp
 * @author Administrator
 * @date 2017-3-10 下午1:46:47
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class DataSmResp extends BaseSmResp {

    public DataSmResp() {
        super(SmppConstants.CMD_ID_DATA_SM_RESP, "data_sm_resp");
    }
    
}