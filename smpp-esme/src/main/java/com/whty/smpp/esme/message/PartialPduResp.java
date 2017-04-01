package com.whty.smpp.esme.message;
/**
 * @ClassName PartialPduResp
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class PartialPduResp extends EmptyBodyResp {
    
    public PartialPduResp(int commandId) {
        super(commandId, "partial_pdu_resp");
    }
    
}