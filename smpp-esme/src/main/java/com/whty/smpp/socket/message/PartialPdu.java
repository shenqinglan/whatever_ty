package com.whty.smpp.socket.message;
/**
 * 
 * @ClassName PartialPdu
 * @author Administrator
 * @date 2017-3-10 下午1:46:23
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class PartialPdu extends EmptyBody<GenericNack> {
    
    public PartialPdu(int commandId) {
        super(commandId, "partial_pdu");
    }

    @Override
    public GenericNack createResponse() {
        GenericNack resp = new GenericNack();
        resp.setSequenceNumber(this.getSequenceNumber());
        return resp;
    }

    @Override
    public Class<GenericNack> getResponseClass() {
        return GenericNack.class;
    }
    
}