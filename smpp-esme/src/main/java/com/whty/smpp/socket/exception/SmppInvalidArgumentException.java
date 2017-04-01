package com.whty.smpp.socket.exception;
/**
 * 
 * @ClassName SmppInvalidArgumentException
 * @author Administrator
 * @date 2017-3-10 下午1:47:52
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