package com.whty.smpp.esme.exception;

import com.whty.smpp.esme.message.Pdu;
/**
 * @ClassName RecoverablePduException
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class RecoverablePduException extends Exception {
    static final long serialVersionUID = 1L;
    
    private Pdu partialPdu;

    public RecoverablePduException(String msg) {
        super(msg);
    }

    public RecoverablePduException(String msg, Throwable t) {
        super(msg, t);
    }

    public RecoverablePduException(Pdu partialPdu, String msg) {
        super(msg);
        this.partialPdu = partialPdu;
    }

    public RecoverablePduException(Pdu partialPdu, String msg, Throwable t) {
        super(msg, t);
        this.partialPdu = partialPdu;
    }

    public void setPartialPdu(Pdu pdu) {
        this.partialPdu = pdu;
    }

    public Pdu getPartialPdu() {
        return this.partialPdu;
    }
}