package org.bulatnig.smpp.testutil;

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
public class SimpleSmscStub implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SimpleSmscStub.class);

    public List<byte[]> input = new ArrayList<byte[]>();

    private final int port;
    private volatile ServerSocket server;
    private volatile OutputStream out;

    private volatile boolean run = true;

    public SimpleSmscStub(int port) {
        this.port = port;
    }

    public void start() throws IOException, InterruptedException {
        Thread listener = new Thread(this);
        listener.start();
        synchronized (this) {
            this.wait();
        }
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            synchronized (this) {
                this.notify();
            }
            Socket client = server.accept();
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
            } while (run);
            client.close();
            stop();
        } catch (IOException e) {
            if (run) {
                logger.error("SMSC execution failed.", e);
            }
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
        }
    }

}
