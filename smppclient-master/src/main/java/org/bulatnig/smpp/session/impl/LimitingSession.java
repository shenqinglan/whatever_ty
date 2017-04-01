package org.bulatnig.smpp.session.impl;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.Pdu;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.session.Session;
import org.bulatnig.smpp.session.MessageListener;
import org.bulatnig.smpp.session.StateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Session, limiting the number of outgoing requests per second.
 *
 * @author Bulat Nigmatullin
 */
public class LimitingSession implements Session {

    private static final Logger logger = LoggerFactory.getLogger(LimitingSession.class);

    /**
     * Limit message count per this amount of time.
     */
    private static final int TIMEOUT = 1010;

    private final Session session;

    /**
     * Holds the times when the last messages was sent.
     */
    private final BlockingQueue<Long> sentTimes;

    public LimitingSession(Session session, int maxMessagesPerSecond) {
        this.session = session;
        sentTimes = new LinkedBlockingQueue<Long>(maxMessagesPerSecond);
        for (int i = 0; i < maxMessagesPerSecond; i++)
            sentTimes.add(0L);
    }

    @Override
    public void setMessageListener(MessageListener messageListener) {
        session.setMessageListener(messageListener);
    }

    @Override
    public void setStateListener(StateListener stateListener) {
        session.setStateListener(stateListener);
    }

    @Override
    public void setSmscResponseTimeout(int timeout) {
        session.setSmscResponseTimeout(timeout);
    }

    @Override
    public void setEnquireLinkTimeout(int timeout) {
        session.setEnquireLinkTimeout(timeout);
    }

    @Override
    public void setReconnectTimeout(int timeout) {
        session.setReconnectTimeout(timeout);
    }

    @Override
    public Pdu open(Pdu pdu) throws PduException, IOException {
        return session.open(pdu);
    }

    @Override
    public long nextSequenceNumber() {
        return session.nextSequenceNumber();
    }

    @Override
    public boolean send(Pdu pdu) throws PduException {
        if (CommandId.SUBMIT_SM != pdu.getCommandId()) {
            return session.send(pdu);
        } else {
            try {
                long timeToSleep = sentTimes.poll() + TIMEOUT - System.currentTimeMillis();
                logger.trace("Wait before send: {} ms.", timeToSleep);
                if (timeToSleep > 0) {
                    logger.trace("Going to sleep {} ms.", timeToSleep);
                    Thread.sleep(timeToSleep);
                }
                return session.send(pdu);
            } catch (InterruptedException e) {
                logger.warn("Send interrupted.", e);
                return false;
            } finally {
                sentTimes.add(System.currentTimeMillis());
            }
        }
    }

    @Override
    public void close() {
        session.close();
    }
}
