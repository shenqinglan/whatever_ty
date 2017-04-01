package com.whty.smpp.netty.pdu;

import com.whty.smpp.netty.constants.SmppConstants;


/**
 * 
 * @ClassName EnquireLinkResp
 * @author Administrator
 * @date 2017-3-10 下午1:33:39
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class EnquireLinkResp extends EmptyBodyResp {

    public EnquireLinkResp() {
        super(SmppConstants.CMD_ID_ENQUIRE_LINK_RESP, "enquire_link_resp");
    }
    
}