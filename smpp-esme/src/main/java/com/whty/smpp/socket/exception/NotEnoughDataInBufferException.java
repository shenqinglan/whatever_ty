package com.whty.smpp.socket.exception;
/**
 * 
 * @ClassName NotEnoughDataInBufferException
 * @author Administrator
 * @date 2017-3-10 下午1:47:59
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class NotEnoughDataInBufferException extends RecoverablePduException {
    static final long serialVersionUID = 1L;
    
    private int available;
    private int expected;

    /**
     * Constructs an instance of <code>AtNotEnoughDataInBufferException</code>
     * with the specified detail message and estimated number of bytes required.
     * An estimate of -1 represents an unknown amount.
     * @param msg the detail message.
     * @param available Number of bytes that were available
     * @param expected Number of bytes expected or -1 if unknown
     */
    public NotEnoughDataInBufferException(int available, int expected) {
        this(null, available, expected);
    }

    /**
     * Constructs an instance of <code>AtNotEnoughDataInBufferException</code>
     * with the specified detail message and estimated number of bytes required.
     * An estimate of -1 represents an unknown amount.
     * @param msg the detail message.
     * @param available Number of bytes that were available
     * @param expected Number of bytes expected or -1 if unknown
     */
    public NotEnoughDataInBufferException(String msg, int available, int expected) {
        super("Not enough data in byte buffer to complete encoding/decoding [expected: " + expected + ", available: " + available + "]" + (msg == null ? "" : ": " + msg));
        this.available = available;
        this.expected = expected;
    }

    public int getAvailable() {
        return available;
    }

    public int getExpected() {
        return expected;
    }
}