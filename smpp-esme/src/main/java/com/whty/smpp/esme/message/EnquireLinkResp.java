package com.whty.smpp.esme.message;

import com.whty.smpp.esme.constants.SmppConstants;


/**
 * @ClassName EnquireLinkResp
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class EnquireLinkResp extends EmptyBodyResp {

    public EnquireLinkResp() {
        super(SmppConstants.CMD_ID_ENQUIRE_LINK_RESP, "enquire_link_resp");
    }
    
}