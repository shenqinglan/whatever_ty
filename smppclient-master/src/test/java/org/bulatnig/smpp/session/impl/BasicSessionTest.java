package org.bulatnig.smpp.session.impl;

import org.bulatnig.smpp.net.impl.TcpConnection;
import org.bulatnig.smpp.pdu.CommandStatus;
import org.bulatnig.smpp.pdu.Pdu;
import org.bulatnig.smpp.pdu.impl.*;
import org.bulatnig.smpp.session.MessageListener;
import org.bulatnig.smpp.session.Session;
import org.bulatnig.smpp.testutil.ComplexSmscStub;
import org.bulatnig.smpp.testutil.SimpleSmscStub;
import org.bulatnig.smpp.testutil.UniquePortGenerator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * BasicSession test.
 *
 * @author Bulat Nigmatullin
 */
public class BasicSessionTest {

    private static final Logger logger = LoggerFactory.getLogger(BasicSessionTest.class);

    @Test
    public void openAndClose() throws Exception {
        final int port = UniquePortGenerator.generate();
        final Pdu request = new BindTransceiver();
        final ComplexSmscStub smsc = new ComplexSmscStub(port);
        smsc.start();
        Pdu bindResp = null;
        try {

            Session session = session(port, null);
            bindResp = session.open(request);
            session.close();

        } finally {
            smsc.stop();
        }
        assertNotNull(bindResp);
        assertEquals(CommandStatus.ESME_ROK, bindResp.getCommandStatus());
        assertArrayEquals(request.buffer().array(), smsc.input.get(0));
    }

    @Test(expected = IOException.class)
    public void openFailedPortClosed() throws Exception {
        final int port = UniquePortGenerator.generate();
        final Pdu request = new BindTransceiver();

        Session session = new BasicSession(new TcpConnection(new InetSocketAddress("localhost", port)));
        session.setSmscResponseTimeout(100);
        session.open(request);
    }

    @Test(expected = IOException.class)
    public void openFailedBindResponseNotReceived() throws Exception {
        final int port = UniquePortGenerator.generate();
        final Pdu request = new BindTransceiver();
        SimpleSmscStub smsc = new SimpleSmscStub(port);
        smsc.start();
        try {

            Session session = new BasicSession(new TcpConnection(new InetSocketAddress("localhost", port)));
            session.setSmscResponseTimeout(50);
            session.open(request);

        } finally {
            smsc.stop();
        }
    }

    @Test(timeout = 2000)
    public void read() throws Exception {
        final int port = UniquePortGenerator.generate();
        final Pdu request = new BindTransceiver();
        final ComplexSmscStub smsc = new ComplexSmscStub(port);
        smsc.start();
        final MessageListenerImpl listener = new MessageListenerImpl();
        final byte[] incoming = new DeliverSm().buffer().array();
        try {

            Session session = new BasicSession(new TcpConnection(new InetSocketAddress("localhost", port)));
            session.setMessageListener(listener);
            session.setSmscResponseTimeout(500);
            session.setEnquireLinkTimeout(300);
            session.open(request);

            smsc.write(incoming);

            while (smsc.input.size() < 3)
                Thread.sleep(10);

            smsc.write(incoming);

            session.close();

        } finally {
            smsc.stop();
        }
        assertEquals(2, listener.pdus.size());
        assertArrayEquals(incoming, listener.pdus.get(0).buffer().array());
        assertArrayEquals(incoming, listener.pdus.get(1).buffer().array());
        assertEquals(4, smsc.input.size());
        assertArrayEquals(request.buffer().array(), smsc.input.get(0));
        Pdu ping = new EnquireLink();
        ping.setSequenceNumber(2);
        assertArrayEquals(ping.buffer().array(), smsc.input.get(1));
        Pdu ping2 = new EnquireLink();
        ping2.setSequenceNumber(3);
        assertArrayEquals(ping2.buffer().array(), smsc.input.get(2));
    }

    @Test
    public void send() throws Exception {
        final int port = UniquePortGenerator.generate();
        final ComplexSmscStub smsc = new ComplexSmscStub(port);
        smsc.start();
        final MessageListenerImpl listener = new MessageListenerImpl();
        final Pdu request = new SubmitSm();
        boolean sendResult = false;

        try {
            Session session = session(port, listener);
            session.open(new BindTransceiver());
            sendResult = session.send(request);

            Thread.sleep(20);

            session.close();
        } finally {
            smsc.stop();
        }

        assertTrue(sendResult);

        assertEquals(1, listener.pdus.size());

        assertEquals(3, smsc.input.size());
        assertArrayEquals(request.buffer().array(), smsc.input.get(1));
    }

    @Test(timeout = 1000)
    public void closeWithoutUnbindResp() throws Exception {
        final Pdu response = new BindTransceiverResp();
        response.setSequenceNumber(1);
        final int port = UniquePortGenerator.generate();
        final SimpleSmscStub smsc = new SimpleSmscStub(port);
        smsc.start();

        ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
        es.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    smsc.write(response.buffer().array());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 100, TimeUnit.MILLISECONDS);

        try {
            Session session = session(port, null);
            session.open(new BindTransceiver());

            session.close();
        } finally {
            smsc.stop();
            es.shutdownNow();
        }
    }

    @Test(expected = IOException.class)
    public void closeWhileOpen() throws Exception {
        final Pdu response = new BindTransceiverResp();
        response.setSequenceNumber(1);
        final int port = UniquePortGenerator.generate();
        final SimpleSmscStub smsc = new SimpleSmscStub(port);
        smsc.start();

        ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();

        try {
            final Session session = session(port, null);
            session.setSmscResponseTimeout(50);

            es.schedule(new Runnable() {
                @Override
                public void run() {
                    session.close();
                }
            }, 20, TimeUnit.MILLISECONDS);

            session.open(new BindTransceiver());
        } finally {
            smsc.stop();
            es.shutdownNow();
        }
    }

    @Test(timeout = 500)
    public void closeWhileSend() throws Exception {
        BindTransceiver bind = new BindTransceiver();
        SubmitSm submitSm = new SubmitSm();
        Unbind unbind = new Unbind();
        unbind.setSequenceNumber(3);
        final Pdu bindResp = new BindTransceiverResp();
        bindResp.setSequenceNumber(1);
        final Pdu unbindResp = new UnbindResp();
        unbindResp.setSequenceNumber(3);
        final int port = UniquePortGenerator.generate();
        final SimpleSmscStub smsc = new SimpleSmscStub(port);
        smsc.start();

        ScheduledExecutorService es = Executors.newScheduledThreadPool(2);
        es.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    smsc.write(bindResp.buffer().array());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 100, TimeUnit.MILLISECONDS);
        es.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    smsc.write(unbindResp.buffer().array());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 200, TimeUnit.MILLISECONDS);

        try {
            final Session session = session(port, null);
            session.open(bind);

            es.schedule(new Runnable() {
                @Override
                public void run() {
                    session.close();
                }
            }, 50, TimeUnit.MILLISECONDS);

            submitSm.setSequenceNumber(session.nextSequenceNumber());
            session.send(submitSm);

            Thread.sleep(200);
        } finally {
            smsc.stop();
            es.shutdownNow();
        }
        assertEquals(3, smsc.input.size());
        assertArrayEquals(bind.buffer().array(), smsc.input.get(0));
        assertArrayEquals(submitSm.buffer().array(), smsc.input.get(1));
        assertArrayEquals(unbind.buffer().array(), smsc.input.get(2));
    }

    @Test
    public void closeWhilePing() throws Exception {
        final Pdu request = new BindTransceiver();
        final Pdu bindResp = new BindTransceiverResp();
        bindResp.setSequenceNumber(1);
        final Pdu unbindResp = new UnbindResp();
        unbindResp.setSequenceNumber(3);
        final int port = UniquePortGenerator.generate();
        final SimpleSmscStub smsc = new SimpleSmscStub(port);
        smsc.start();

        ScheduledExecutorService es = Executors.newScheduledThreadPool(2);
        es.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    smsc.write(bindResp.buffer().array());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 50, TimeUnit.MILLISECONDS);
        try {
            Session session = session(port, null);
            session.setEnquireLinkTimeout(100);
            session.open(request);

            while (smsc.input.size() < 2)
                Thread.sleep(10);

            es.schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        smsc.write(unbindResp.buffer().array());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 50, TimeUnit.MILLISECONDS);

            session.close();
        } finally {
            smsc.stop();
        }
    }

    @Test
    public void reconnect() throws Exception {
        final int port = UniquePortGenerator.generate();
        ComplexSmscStub smsc = new ComplexSmscStub(port);
        smsc.start();

        try {
            Session session = new BasicSession(new TcpConnection(new InetSocketAddress("localhost", port)));
            session.setSmscResponseTimeout(100);
            session.setEnquireLinkTimeout(100);
            session.open(new BindTransceiver());

            smsc.stop();

            Thread.sleep(1000);

            smsc = new ComplexSmscStub(port);
            smsc.start();

            Thread.sleep(100);

            session.close();
        } finally {
            smsc.stop();
        }
    }

    @Test
    public void sendWhileReconnecting() throws Exception {
        final int port = UniquePortGenerator.generate();
        ComplexSmscStub smsc = new ComplexSmscStub(port);
        smsc.start();
        boolean sendResult = true;

        try {
            Session session = new BasicSession(new TcpConnection(new InetSocketAddress("localhost", port)));
            session.setSmscResponseTimeout(100);
            session.setEnquireLinkTimeout(100);
            session.open(new BindTransceiver());

            smsc.stop();

            Thread.sleep(500);
            sendResult = session.send(new SubmitSm());
            Thread.sleep(500);

            smsc = new ComplexSmscStub(port);
            smsc.start();

            Thread.sleep(100);

            session.close();
        } finally {
            smsc.stop();
        }
        assertFalse(sendResult);
    }

    @Test
    public void closeWhileReconnect() throws Exception {
        final int port = UniquePortGenerator.generate();
        ComplexSmscStub smsc = new ComplexSmscStub(port);
        smsc.start();

        Session session = new BasicSession(new TcpConnection(new InetSocketAddress("localhost", port)));
        session.setSmscResponseTimeout(1000);
        session.setEnquireLinkTimeout(100);
        session.open(new BindTransceiver());

        smsc.stop();

        Thread.sleep(50);

        session.close();
        smsc = new ComplexSmscStub(port);
        smsc.start();

        Thread.sleep(1100);

        smsc.stop();

        assertTrue(smsc.input.isEmpty());
    }

    @Test
    public void reconnectWhilePing() throws Exception {
        final Pdu request = new BindTransceiver();
        final Pdu bindResp = new BindTransceiverResp();
        bindResp.setSequenceNumber(1);
        final Pdu unbindResp = new UnbindResp();
        unbindResp.setSequenceNumber(3);
        final int port = UniquePortGenerator.generate();
        final SimpleSmscStub smsc = new SimpleSmscStub(port);
        final SimpleSmscStub smsc2 = new SimpleSmscStub(port);
        smsc.start();

        ScheduledExecutorService es = Executors.newScheduledThreadPool(2);
        es.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    smsc.write(bindResp.buffer().array());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 50, TimeUnit.MILLISECONDS);
        try {
            Session session = session(port, null);
            session.setSmscResponseTimeout(200);
            session.setEnquireLinkTimeout(100);
            session.open(request);

            while (smsc.input.size() < 2)
                Thread.sleep(10);

            smsc.stop();

            Thread.sleep(250);

            smsc2.start();

            while (smsc2.input.size() < 1)
                Thread.sleep(10);

            smsc2.write(bindResp.buffer().array());

            es.schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (smsc2.input.size() < 2)
                            Thread.sleep(10);
                        smsc2.write(unbindResp.buffer().array());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 0, TimeUnit.MILLISECONDS);

            session.close();
        } finally {
            smsc.stop();
            smsc2.stop();
            es.shutdownNow();
        }
    }

    protected Session session(int port, MessageListener listener) {
        Session session = new BasicSession(new TcpConnection(new InetSocketAddress("localhost", port)));
        session.setSmscResponseTimeout(500);
        if (listener != null)
            session.setMessageListener(listener);
        return session;
    }

    private class MessageListenerImpl implements MessageListener {

        private final List<Pdu> pdus = new ArrayList<Pdu>();

        @Override
        public void received(Pdu pdu) {
            pdus.add(pdu);
        }
    }

}
