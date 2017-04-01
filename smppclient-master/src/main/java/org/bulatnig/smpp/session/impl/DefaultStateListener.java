package org.bulatnig.smpp.session.impl;

import org.bulatnig.smpp.session.State;
import org.bulatnig.smpp.session.StateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Session default state listener implementation. Just print new session state.
 *
 * @author Bulat Nigmatullin
 */
public class DefaultStateListener implements StateListener {

    private static final Logger logger = LoggerFactory.getLogger(DefaultStateListener.class);

    @Override
    public void changed(State state, Exception e) {
        logger.debug("Session state changed to {}.", state, e);
    }
}
