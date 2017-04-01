package com.whty.smpp.socket.message;

import com.whty.smpp.socket.constants.SmppConstants;
/**
 * 
 * @ClassName EnquireLink
 * @author Administrator
 * @date 2017-3-10 下午1:46:34
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class EnquireLink extends EmptyBody<EnquireLinkResp> {
    
    public EnquireLink() {
        super(SmppConstants.CMD_ID_ENQUIRE_LINK, "enquire_link");
    }

    @Override
    public EnquireLinkResp createResponse() {
        EnquireLinkResp resp = new EnquireLinkResp();
        resp.setSequenceNumber(this.getSequenceNumber());
        return resp;
    }

    @Override
    public Class<EnquireLinkResp> getResponseClass() {
        return EnquireLinkResp.class;
    }
    
}