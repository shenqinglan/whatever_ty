package org.bulatnig.smpp.session.impl;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bulatnig.smpp.net.Connection;
import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.CommandStatus;
import org.bulatnig.smpp.pdu.Pdu;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.pdu.impl.BindReceiver;
import org.bulatnig.smpp.pdu.impl.DeliverSmResp;
import org.bulatnig.smpp.pdu.impl.EnquireLink;
import org.bulatnig.smpp.pdu.impl.EnquireLinkResp;
import org.bulatnig.smpp.pdu.impl.Outbind;
import org.bulatnig.smpp.pdu.impl.Unbind;
import org.bulatnig.smpp.session.MessageListener;
import org.bulatnig.smpp.session.Session;
import org.bulatnig.smpp.session.State;
import org.bulatnig.smpp.session.StateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Asynchronous session implementation.
 *
 * @author Bulat Nigmatullin
 */
public class BasicSession implements Session {

    private static final Logger logger = LoggerFactory.getLogger(BasicSession.class);

    private final Connection conn;
    private String bindType;

    private int smscResponseTimeout = DEFAULT_SMSC_RESPONSE_TIMEOUT;
    private int pingTimeout = DEFAULT_ENQUIRE_LINK_TIMEOUT;
    private int reconnectTimeout = DEFAULT_RECONNECT_TIMEOUT;
    private MessageListener messageListener = new DefaultMessageListener();
    private StateListener stateListener = new DefaultStateListener();
    private EnquireLinkThread enquireLinkThread;
    private ReadThread readThread;

    private Pdu bindPdu;
    private volatile long sequenceNumber = 0;
    private volatile long lastActivity;
    private volatile State state = State.DISCONNECTED;

    public BasicSession(Connection conn) {
    	this.conn = conn;
    }
    
    public BasicSession(Connection conn, String bindType) {
        this.conn = conn;
        this.bindType = bindType;
    }

    @Override
    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    @Override
    public void setStateListener(StateListener stateListener) {
        this.stateListener = stateListener;
    }

    @Override
    public void setSmscResponseTimeout(int timeout) {
        this.smscResponseTimeout = timeout;
    }

    @Override
    public void setEnquireLinkTimeout(int timeout) {
        this.pingTimeout = timeout;
    }

    @Override
    public void setReconnectTimeout(int timeout) {
        this.reconnectTimeout = timeout;
    }

    @Override
    public synchronized Pdu open(Pdu pdu) throws PduException, IOException {
        bindPdu = pdu;
        return open();
    }

    @Override
    public synchronized long nextSequenceNumber() {
        if (sequenceNumber == 2147483647L)
            sequenceNumber = 1;
        else
            sequenceNumber++;
        return sequenceNumber;
    }

    @Override
    public synchronized boolean send(Pdu pdu) throws PduException {
    	System.out.println(bindType+" send pdu: "+pdu.getCommandId());
        if (State.CONNECTED != state)
            return false;
        try {
            conn.write(pdu);
            return true;
        } catch (IOException e) {
            logger.debug("Send failed.", e);
            reconnect(e);
            return false;
        }
    }

    @Override
    public synchronized void close() {
        if (State.RECONNECTING == state || closeInternal(null))
            updateState(State.DISCONNECTED);
    }

    private synchronized Pdu open() throws PduException, IOException {
        System.out.println("Opening new session...");
        conn.open();
        System.out.println("TCP connection established. Sending bind request.");
        bindPdu.setSequenceNumber(nextSequenceNumber());
        conn.write(bindPdu);
        ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
        es.schedule(new Runnable() {
                    @Override
                    public void run() {
                        logger.warn("Bind response timed out.");
                        conn.close();
                    }
                }, smscResponseTimeout, TimeUnit.MILLISECONDS);
        System.out.println("Bind request sent. Waiting for bind response.");
        try {
            Pdu bindResp = conn.read();
            es.shutdownNow();
            System.out.println("Bind response command status: "+bindResp.getCommandStatus());
            if (CommandStatus.ESME_ROK == bindResp.getCommandStatus()) {
                updateLastActivity();
                enquireLinkThread = new EnquireLinkThread();
                enquireLinkThread.setName("EnquireLink");
                enquireLinkThread.start();
                readThread = new ReadThread();
                Thread t2 = new Thread(readThread);
                t2.setName("Read");
                t2.start();
                updateState(State.CONNECTED);
                System.out.println("Session successfully opened.");
            }
            return bindResp;
        } finally {
            if (!es.isShutdown())
                es.shutdownNow();
        }
    }

    /**
     * Actually close session.
     *
     * @param reason exception, caused session close, or null
     * @return session closed
     */
    private synchronized boolean closeInternal(Exception reason) {
        if (State.DISCONNECTED != state) {
            logger.trace("Closing session...");
            enquireLinkThread.stopAndInterrupt();
            enquireLinkThread = null;
            if (!(reason instanceof IOException) && readThread.run) {
                try {
                    synchronized (conn) {
                        Pdu unbind = new Unbind();
                        unbind.setSequenceNumber(nextSequenceNumber());
                        send(unbind);
                        conn.wait(smscResponseTimeout);
                    }
                } catch (Exception e) {
                	System.out.println("Unbind request send failed: "+ e);
                }
            }
            readThread.stop();
            readThread = null;
            conn.close();
            logger.trace("Session closed.");
            return true;
        } else {
            logger.trace("Session already closed.");
            return false;
        }
    }

    private void reconnect(Exception reason) {
        // only one thread should do reconnect
        boolean doReconnect = false;
        synchronized (state) {
            if (State.RECONNECTING != state) {
                doReconnect = true;
                state = State.RECONNECTING;
            }
        }
        if (doReconnect) {
            closeInternal(reason);
            new Thread(new ReconnectThread(reason)).start();
        }
    }


    public void updateLastActivity() {
        lastActivity = System.currentTimeMillis();
    }

    private void updateState(State newState) {
        updateState(newState, null);
    }

    private void updateState(State newState, Exception e) {
        this.state = newState;
        stateListener.changed(newState, e);
    }

    private class EnquireLinkThread extends Thread {

        private volatile boolean run = true;

        @Override
        public void run() {
            logger.trace(bindType + " EnquireLink thread started.");
            try {
                while (run) {
                	System.out.println(bindType + " Checking last activity.");
                    try {
                        Thread.sleep(pingTimeout);
                        if (pingTimeout < (System.currentTimeMillis() - lastActivity)) {
                            long prevLastActivity = lastActivity;
                            Pdu enquireLink = new EnquireLink();
                            enquireLink.setSequenceNumber(nextSequenceNumber());
                            send(enquireLink);
                            System.out.println("send "+bindType+" EnquireLink");
                            synchronized (conn) {
                                conn.wait(smscResponseTimeout);
                            }
                            if (run && lastActivity == prevLastActivity) {
                                reconnect(new IOException(bindType + " Enquire link response not received. Session closed."));
                            }
                        }
                    } catch (InterruptedException e) {
                    	System.out.println(bindType + " Ping thread interrupted.");
                    }
                }
            } catch (PduException e) {
                if (run) {
                	System.out.println(bindType + " EnquireLink request failed: "+e);
                    run = false;
                    reconnect(e);
                }
            } finally {
            	System.out.println(bindType + " Ping thread stopped.");
            }
        }

        void stopAndInterrupt() {
            run = false;
            interrupt();
        }

    }

    private class ReadThread implements Runnable {

        private volatile boolean run = true;

        @Override
        public void run() {
        	System.out.println(bindType + " Read thread started.");
            try {
                while (run) {
                    Pdu request = conn.read();
                    updateLastActivity();
                    Pdu response;
                    if (CommandId.ENQUIRE_LINK == request.getCommandId()) {
                    	System.out.println("receive "+bindType+" ENQUIRE_LINK");
                        response = new EnquireLinkResp();
                        response.setSequenceNumber(request.getSequenceNumber());
                        send(response);
                    } else if (CommandId.ENQUIRE_LINK_RESP == request.getCommandId()) {
                    	System.out.println("receive "+bindType+" ENQUIRE_LINK_RESP");
                        synchronized (conn) {
                            conn.notifyAll();
                        }
                    } else if (CommandId.UNBIND == request.getCommandId()){
                    	System.out.println("receive "+bindType+" UNBIND");
                    	synchronized (conn) {
                            conn.notifyAll();
                        }
                    } else if (CommandId.UNBIND_RESP == request.getCommandId()) {
                    	System.out.println("receive "+bindType+" UNBIND_RESP");
                        synchronized (conn) {
                            conn.notifyAll();
                        }
                        stop();
                    } else {
                        messageListener.received(request);
                        if (CommandId.DELIVER_SM == request.getCommandId()) {
                        	response  = new DeliverSmResp();
                        	response.setSequenceNumber(request.getSequenceNumber());
                        	send(response);
                        	System.out.println(bindType+" send DELIVER_SM_RESP");
                        } else if (CommandId.OUTBIND == request.getCommandId()) {
                        	System.out.println(bindType+" receive OUTBIND");
                        	Outbind outbind = (Outbind) request;
                        	BindReceiver bindReceiver = new BindReceiver();
                        	bindReceiver.setSequenceNumber(outbind.getSequenceNumber());
                        	bindReceiver.setSystemId(outbind.getSystemId());
                        	bindReceiver.setPassword(outbind.getPassword());
                        	bindReceiver.setInterfaceVersion(0x34);
                    		bindReceiver.setAddrNpi(0x00);
                    		bindReceiver.setAddrTon(0x00);
                    		bindReceiver.setAddressRange(null);
                    		send(bindReceiver);
                    		System.out.println(bindType+" send bindReceiver");
                        }
                    }
                }
            } catch (PduException e) {
                if (run) {
                	System.out.println(bindType+" Incoming message parsing failed: "+e);
                    run = false;
                    reconnect(e);
                }
            } catch (IOException e) {
                if (run) {
                	System.out.println(bindType+" Reading IO failure: "+e);
                    run = false;
                    reconnect(e);
                }
            } finally {
            	System.out.println(bindType+" Read thread stopped.");
            }
        }

        void stop() {
            run = false;
        }

    }

    private class ReconnectThread implements Runnable {

        private final Exception reason;

        private ReconnectThread(Exception reason) {
            this.reason = reason;
        }

        @Override
        public void run() {
        	System.out.println(bindType+" Reconnect started.");
            stateListener.changed(state, reason);
            boolean reconnectSuccessful = false;
            while (!reconnectSuccessful && state == State.RECONNECTING) {
            	System.out.println("Reconnecting...");
                try {
                    Pdu bindResponse = open();
                    if (CommandStatus.ESME_ROK == bindResponse.getCommandStatus()) {
                        reconnectSuccessful = true;
                    } else {
                    	System.out.println(bindType+" Reconnect failed. Bind response error code: "+
                                bindResponse.getCommandStatus());
                    }
                } catch (Exception e) {
                	System.out.println(bindType+" Reconnect failed: "+ e);
                    try {
                        Thread.sleep(reconnectTimeout);
                    } catch (InterruptedException e1) {
                    	System.out.println(bindType+" Reconnect sleep interrupted: "+e1);
                    }
                }
            }
            if (reconnectSuccessful)
                state = State.CONNECTED;
            System.out.println(bindType+" Reconnect done.");
        }
    }

}
