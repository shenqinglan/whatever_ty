package com.whty.smpp.socket.message;
/**
 * 
 * @ClassName PduRequest
 * @author Administrator
 * @date 2017-3-10 下午1:46:13
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public abstract class PduRequest<R extends PduResponse> extends Pdu {

    public PduRequest(int commandId, String name) {
        super(commandId, name, true);
    }

    abstract public R createResponse();

    abstract public Class<R> getResponseClass();

    public GenericNack createGenericNack(int commandStatus) {
        GenericNack nack = new GenericNack();
        nack.setCommandStatus(commandStatus);
        nack.setSequenceNumber(this.getSequenceNumber());
        return nack;
    }

}