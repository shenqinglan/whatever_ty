package com.whty.smpp.netty.pdu;

import com.whty.smpp.netty.constants.SmppConstants;


/**
 * 
 * @author joelauer (twitter: @jjlauer or <a href="http://twitter.com/jjlauer" target=window>http://twitter.com/jjlauer</a>)
 */
public class BindReceiverResp extends BaseBindResp {

    public BindReceiverResp() {
        super(SmppConstants.CMD_ID_BIND_RECEIVER_RESP, "bind_receiver_resp");
    }
    
}