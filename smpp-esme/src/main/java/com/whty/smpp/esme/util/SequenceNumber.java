package com.whty.smpp.esme.util;

import com.whty.smpp.esme.exception.InvalidSequenceNumberException;
/**
 * 
 * @ClassName SequenceNumber
 * @author Administrator
 * @date 2017-3-10 下午1:39:31
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SequenceNumber {

    public static final int MIN_VALUE = 0x00000000;
    public static final int DEFAULT_VALUE = 0x00000001;
    public static final int MAX_VALUE = 0x7FFFFFFF;

    private int value;

    public SequenceNumber() {
        this.value = DEFAULT_VALUE;
    }

    public SequenceNumber(int initialValue) throws InvalidSequenceNumberException {
        assertValid(initialValue);
        this.value = initialValue;
    }

    /**
     * Get the next number in this sequence's scheme. This method is synchronized
     * so its safe for multiple threads to call.
     */
    synchronized public int next() {
        // the next value is the current value
        int nextValue = this.value;

        if (this.value == MAX_VALUE) {
            // wrap this around back to 1
            this.value = DEFAULT_VALUE;
        } else {
            this.value++;
        }

        return nextValue;
    }

    synchronized public int peek() {
        return this.value;
    }

    synchronized public void reset() {
        this.value = DEFAULT_VALUE;
    }

    static public void assertValid(int sequenceNumber) throws InvalidSequenceNumberException {
        /**
        if (sequenceNumber < MIN_VALUE || sequenceNumber > MAX_VALUE) {
            throw new InvalidSequenceNumberException("Sequence number [" + sequenceNumber + "] is not in range from " + MIN_VALUE + " to " + MAX_VALUE);
        }
         */
    }
}

