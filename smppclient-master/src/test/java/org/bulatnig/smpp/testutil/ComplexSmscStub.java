package org.bulatnig.smpp.testutil;

import org.bulatnig.smpp.pdu.CommandId;
import org.bulatnig.smpp.pdu.impl.BindTransceiverResp;
import org.bulatnig.smpp.pdu.impl.EnquireLinkResp;
import org.bulatnig.smpp.pdu.impl.SubmitSmResp;
import org.bulatnig.smpp.pdu.impl.UnbindResp;
import org.bulatnig.smpp.util.ByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * SMSC stub implementation.
 *
 * @author Bulat Nigmatullin
 */
public class ComplexSmscStub implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ComplexSmscStub.class);

    public List<byte[]> input = new ArrayList<byte[]>();

    private final int port;
    private volatile ServerSocket server;
    private volatile OutputStream out;

    private Thread listener;
    private Socket client;
    private volatile boolean run = true;

    public ComplexSmscStub(int port) {
        this.port = port;
    }

    public void start() throws IOException, InterruptedException {
        listener = new Thread(this);
        listener.start();
        synchronized (this) {
            wait();
        }
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            synchronized (this) {
                notify();
            }
            client = server.accept();
            client.setSoTimeout(0);
            InputStream in = client.getInputStream();
            out = client.getOutputStream();
            byte[] bytes = new byte[1024];
            do {
                int read = in.read(bytes);
                if (read < 0)
                    break;
                byte[] pdu = new byte[read];
                System.arraycopy(bytes, 0, pdu, 0, read);
                input.add(pdu);
                ByteBuffer bb = new ByteBuffer().appendBytes(bytes, read);
                long commandId = bb.readInt(4);
                if (commandId < CommandId.GENERIC_NACK) {
                    long seqNum = bb.readInt(12);
                    if (CommandId.BIND_TRANSCEIVER == commandId) {
                        BindTransceiverResp bindResp = new BindTransceiverResp();
                        bindResp.setSystemId(Long.toString(System.currentTimeMillis()));
                        bindResp.setSequenceNumber(seqNum);
                        out.write(bindResp.buffer().array());
                    } else if (CommandId.ENQUIRE_LINK == commandId) {
                        EnquireLinkResp enquireLinkResp = new EnquireLinkResp();
                        enquireLinkResp.setSequenceNumber(seqNum);
                        out.write(enquireLinkResp.buffer().array());
                    } else if (CommandId.SUBMIT_SM == commandId) {
                        SubmitSmResp submitSmResp = new SubmitSmResp();
                        submitSmResp.setMessageId(Long.toString(System.currentTimeMillis()));
                        submitSmResp.setSequenceNumber(seqNum);
                        out.write(submitSmResp.buffer().array());
                    } else if (CommandId.UNBIND == commandId) {
                        UnbindResp unbindResp = new UnbindResp();
                        unbindResp.setSequenceNumber(seqNum);
                        out.write(unbindResp.buffer().array());
                    }
                }
            } while (run);
            client.close();
        } catch (Exception e) {
            if (run) {
                logger.error("SMSC execution failed.", e);
            }
        } finally {
            stop();
        }

    }

    public void write(byte[] bytes) throws IOException, InterruptedException {
        while (out == null)
            Thread.sleep(10);
        out.write(bytes);
    }

    public synchronized void stop() {
        if (run) {
            run = false;
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (client != null) {
                try {
                    client.close();
                } catch (IOException ignore) {
                    // omit it
                }
            }
            listener.interrupt();
        }
    }

}
