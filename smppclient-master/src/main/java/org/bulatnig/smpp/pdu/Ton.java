package org.bulatnig.smpp.pdu;

/**
 * Type of Number (TON).
 *
 * @author Bulat Nigmatullin
 */
public enum Ton {
    INSTANCE;

    /**
     * Unknown.
     */
    public static final int UNKNOWN = 0;
    /**
     * International.
     */
    public static final int INTERNATIONAL = 1;
    /**
     * National.
     */
    public static final int NATIONAL = 2;
    /**
     * Network Specific.
     */
    public static final int NETWORK_SPECIFIC = 3;
    /**
     * Subscriber number.
     */
    public static final int SUBSCRIBER_NUMBER = 4;
    /**
     * Alphanumeric.
     */
    public static final int ALPHANUMERIC = 5;
    /**
     * Abbreviated.
     */
    public static final int ABBREVIATED = 6;
}
