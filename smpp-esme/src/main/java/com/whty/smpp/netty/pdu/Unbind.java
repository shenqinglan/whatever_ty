package com.whty.smpp.netty.pdu;

import com.whty.smpp.netty.constants.SmppConstants;
/**
 * @ClassName Unbind
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
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