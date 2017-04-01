package com.whty.smpp.netty.pdu;

import com.whty.smpp.netty.constants.SmppConstants;


/**
 * 
 * @author joelauer (twitter: @jjlauer or <a href="http://twitter.com/jjlauer" target=window>http://twitter.com/jjlauer</a>)
 */
public class BindTransceiverResp extends BaseBindResp {

    public BindTransceiverResp() {
        super(SmppConstants.CMD_ID_BIND_TRANSCEIVER_RESP, "bind_transceiver_resp");
    }
    
}