package com.whty.smpp.socket.message;

import com.whty.smpp.socket.constants.SmppConstants;
/**
 * 
 * @ClassName UnbindResp
 * @author Administrator
 * @date 2017-3-10 下午1:45:30
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class UnbindResp extends EmptyBodyResp {

    public UnbindResp() {
        super(SmppConstants.CMD_ID_UNBIND_RESP, "unbind_resp");
    }
    
}