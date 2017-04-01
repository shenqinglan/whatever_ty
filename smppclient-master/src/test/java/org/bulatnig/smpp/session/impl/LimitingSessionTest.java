package org.bulatnig.smpp.session.impl;

import org.bulatnig.smpp.net.impl.TcpConnection;
import org.bulatnig.smpp.pdu.impl.BindTransceiver;
import org.bulatnig.smpp.pdu.impl.BindTransceiverResp;
import org.bulatnig.smpp.pdu.impl.EnquireLink;
import org.bulatnig.smpp.pdu.impl.SubmitSm;
import org.bulatnig.smpp.session.Session;
import org.bulatnig.smpp.testutil.ComplexSmscStub;
import org.bulatnig.smpp.testutil.UniquePortGenerator;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * LimitingSession test.
 *
 * @author Bulat Nigmatullin
 */
public class LimitingSessionTest {

    @Test
    public void limit() throws Exception {
        final int port = UniquePortGenerator.generate();
        final int limit = 10;
        final int count = 20;
        final ComplexSmscStub smsc = new ComplexSmscStub(port);
        smsc.start();

        ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
        es.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    smsc.write(new BindTransceiverResp().buffer().array());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 100, TimeUnit.MILLISECONDS);

        long started = System.currentTimeMillis();
        try {
            Session session = new LimitingSession(
                    new BasicSession(new TcpConnection(new InetSocketAddress("localhost", port))),
                            limit);
            session.setSmscResponseTimeout(200);
            session.open(new BindTransceiver());

            for (int i = 0; i < count; i++) {
                session.send(new SubmitSm());
            }

            session.close();
        } finally {
            es.shutdownNow();
            smsc.stop();
        }
        long done = System.currentTimeMillis();
        long executionTime = done - started;
        if (executionTime < 1000 || executionTime > 1500)
            throw new Exception("Execution time between 1 and 1.5 seconds expected, but " + executionTime);
    }

    @Test
    public void limitOnlySubmitSm() throws Exception {
        final int port = UniquePortGenerator.generate();
        final int limit = 1;
        final int count = 20;
        final ComplexSmscStub smsc = new ComplexSmscStub(port);
        smsc.start();

        ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
        es.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    smsc.write(new BindTransceiverResp().buffer().array());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 100, TimeUnit.MILLISECONDS);

        long started = System.currentTimeMillis();
        try {
            Session session = new LimitingSession(
                    new BasicSession(new TcpConnection(new InetSocketAddress("localhost", port))),
                            limit);
            session.setSmscResponseTimeout(200);
            session.open(new BindTransceiver());

            for (int i = 0; i < count; i++) {
                session.send(new EnquireLink());
            }

            session.close();
        } finally {
            es.shutdownNow();
            smsc.stop();
        }
        long done = System.currentTimeMillis();
        long executionTime = done - started;
        if (executionTime > 1000)
            throw new Exception("Execution time less 1 second expected, but " + executionTime);
    }

}
