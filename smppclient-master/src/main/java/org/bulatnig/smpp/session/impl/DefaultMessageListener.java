package org.bulatnig.smpp.session.impl;

import org.bulatnig.smpp.pdu.Pdu;
import org.bulatnig.smpp.session.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Session default message listener implementation, just print received PDU's class names.
 *
 * @author Bulat Nigmatullin
 */
public class DefaultMessageListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(DefaultMessageListener.class);

    @Override
    public void received(Pdu pdu) {
        logger.debug("{} received, but no session listener set.", pdu.getClass().getName());
    }

}
