package org.bulatnig.smpp.testutil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generate unique port for each test.
 *
 * @author Bulat Nigmatullin
 */
public enum UniquePortGenerator {
    INSTANCE;

    /**
     * Values through 1 to 1024 reserved. So we are starting from 1025.
     */
    private static final AtomicInteger port = new AtomicInteger(1025);

    public static int generate() {
        return port.getAndIncrement();
    }

}
