package org.bulatnig.smpp.pdu;

/**
 * Numbric Plan Indicator (NPI).
 *
 * @author Bulat Nigmatullin
 */
public enum Npi {
    INSTANCE;

    /**
     * Unknown.
     */
    public static final int UNKNOWN = 0;
    /**
     * ISDN (E163/E164).
     */
    public static final int ISDN = 1;
    /**
     * Data (X.121).
     */
    public static final int DATA = 3;
    /**
     * Telex (F.69).
     */
    public static final int TELEX = 4;
    /**
     * Land Mobile (E.212).
     */
    public static final int LAND_MOBILE = 6;
    /**
     * National.
     */
    public static final int NATIONAL = 8;
    /**
     * Private.
     */
    public static final int PRIVATE = 9;
    /**
     * ERMES
     */
    public static final int ERMES = 10;
    /**
     * Internet (IP).
     */
    public static final int INTERNET = 14;
    /**
     * WAP Client Id (to be defined by WAP Forum).
     */
    public static final int WAP_CLIENT_ID = 18;
}
