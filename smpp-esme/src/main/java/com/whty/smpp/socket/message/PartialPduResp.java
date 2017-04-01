package com.whty.smpp.socket.message;
/**
 * 
 * @ClassName PartialPduResp
 * @author Administrator
 * @date 2017-3-10 下午1:46:20
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class PartialPduResp extends EmptyBodyResp {
    
    public PartialPduResp(int commandId) {
        super(commandId, "partial_pdu_resp");
    }
    
}