package com.whty.smpp.esme.exception;
/**
 * @ClassName SmppInvalidArgumentException
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SmppInvalidArgumentException extends RecoverablePduException {
    static final long serialVersionUID = 1L;

    public SmppInvalidArgumentException(String msg) {
        super(null, msg);
    }

    public SmppInvalidArgumentException(String msg, Throwable t) {
        super(null, msg, t);
    }
}