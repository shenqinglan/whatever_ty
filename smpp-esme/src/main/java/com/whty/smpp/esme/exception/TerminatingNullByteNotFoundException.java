package com.whty.smpp.esme.exception;
/**
 * @ClassName TerminatingNullByteNotFoundException
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class TerminatingNullByteNotFoundException extends RecoverablePduException {
    static final long serialVersionUID = 1L;
    
    public TerminatingNullByteNotFoundException(String msg) {
        super(msg);
    }

    public TerminatingNullByteNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}