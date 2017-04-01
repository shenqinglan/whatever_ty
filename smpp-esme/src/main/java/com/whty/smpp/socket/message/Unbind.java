package com.whty.smpp.socket.message;

import com.whty.smpp.socket.constants.SmppConstants;
/**
 * 
 * @ClassName Unbind
 * @author Administrator
 * @date 2017-3-10 下午1:45:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class Unbind extends EmptyBody<UnbindResp> {
    
    public Unbind() {
        super(SmppConstants.CMD_ID_UNBIND, "unbind");
    }

    @Override
    public UnbindResp createResponse() {
        UnbindResp resp = new UnbindResp();
        resp.setSequenceNumber(this.getSequenceNumber());
        return resp;
    }

    @Override
    public Class<UnbindResp> getResponseClass() {
        return UnbindResp.class;
    }
    
}