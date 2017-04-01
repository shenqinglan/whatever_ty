package org.bulatnig.smpp.session;

/**
 * Track session connects and disconnects.
 *
 * @author Bulat Nigmatullin
 */
public interface StateListener {

    /**
     * Session state changed to new state.
     *
     * @param state new state
     * @param e failure reason, may be null
     */
    void changed(State state, Exception e);

}
