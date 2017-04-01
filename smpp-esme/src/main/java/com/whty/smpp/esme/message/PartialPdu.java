package com.whty.smpp.esme.message;
/**
 * @ClassName PartialPdu
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
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