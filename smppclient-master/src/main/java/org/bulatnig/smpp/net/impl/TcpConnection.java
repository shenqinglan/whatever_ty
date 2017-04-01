package org.bulatnig.smpp.net.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;

import org.bulatnig.smpp.net.Connection;
import org.bulatnig.smpp.pdu.Pdu;
import org.bulatnig.smpp.pdu.PduException;
import org.bulatnig.smpp.pdu.PduParser;
import org.bulatnig.smpp.pdu.impl.DefaultPduParser;
import org.bulatnig.smpp.util.ByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TCP/IP connection. Additionally logs on debug level all read and sent PDU's.
 * <p/>
 * Not thread safe. Weak against buffer overflow problem.
 *
 * @author Bulat Nigmatullin
 */
public class TcpConnection implements Connection {

    private static final Logger logger = LoggerFactory.getLogger(TcpConnection.class);

    private final SocketAddress socketAddress;
    private final byte[] bytes = new byte[250];
    private PduParser parser = new DefaultPduParser();

    private Socket socket;
    private InputStream in;
    private OutputStream out;

    private ByteBuffer bb;

    public TcpConnection(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    @Override
    public void setParser(PduParser parser) {
        this.parser = parser;
    }

    @Override
    public void open() throws IOException {
    	System.out.println("Create a new socket");
        socket = new Socket();
        socket.connect(socketAddress);
        socket.setSoTimeout(0); // block read forever
        in = socket.getInputStream();
        out = socket.getOutputStream();
        bb = new ByteBuffer();
    }

//    @Override
//    public Pdu read() throws PduException, IOException {
//        synchronized (in) {
//            Pdu pdu = tryToReadBuffer(); // there may be two PDU's read previous time, but only one parsed
//            while (pdu == null) {
//                int read = in.read(bytes);
//                if (read < 0)
//                    throw new IOException("Connection closed by SMSC");
//                bb.appendBytes(bytes, read);
//                pdu = tryToReadBuffer();
//            }
//            logger.info("<<< {}", pdu.buffer().hexDump());
//            return pdu;
//        }
//    }
    
//    @Override
//    public Pdu read() throws PduException, IOException {
//        synchronized (in) {
//            Pdu pdu = tryToReadBuffer(); // there may be two PDU's read previous time, but only one parsed
//            while (pdu == null) {
//            	int r=0;
//           	    byte[] data=new byte[4];
//           	    while(r<4){
//           	        r+=in.read(data,r,4-r);
//           	    }
//           	    if (r <= 0)
//                 throw new IOException("Connection closed by SMSC");
//           	
//           		String strTmpBuf = hexByteToString(data);
//           		int len = Integer.parseInt(strTmpBuf, 16) - 4;
//           		
//           	    byte[] data2=new byte[len];
//           	    int i= 0;
//           	    while(i<len){
//           	    	i+=in.read(data2,i,len-i);
//           	    }
//           		
//           	    String strTmpBuf2 = hexByteToString(data2);
//           	    byte[] bytes = hexToBuffer(strTmpBuf+strTmpBuf2);
//                
//                bb.appendBytes(bytes, len+4);
//                pdu = tryToReadBuffer();
//            }
//            logger.info("<<< {}", pdu.buffer().hexDump());
//            return pdu;
//        }
//    }
    
    @Override
    public Pdu read() throws PduException, IOException {
        synchronized (in) {
            Pdu pdu = tryToReadBuffer(); // there may be two PDU's read previous time, but only one parsed
            while (pdu == null) {
            	byte[] header = new byte[4];
                byte[] cmdBytes = null;
                
           	    in.read(header,0,4);
           	 
           	    int cmdLen = byte2int(header, 0);
	           	if (cmdLen > 8096 || cmdLen < 4) {
	                 throw new IOException("read stream error,cmdLen="+ cmdLen + ",close connection");
	            }
	            cmdBytes = new byte[cmdLen];
	            System.arraycopy(header, 0, cmdBytes, 0, 4);
           	
           	    in.read(cmdBytes,4,cmdLen-4);
           		                
                bb.appendBytes(cmdBytes, cmdLen);
                pdu = tryToReadBuffer();
            }
            logger.info("<<< {}", pdu.buffer().hexDump());
            return pdu;
        }
    }

    @Override
    public void write(Pdu pdu) throws PduException, IOException {
        synchronized (out) {
            out.write(pdu.buffer().array());
            out.flush();
            logger.debug(">>> {}", pdu.buffer().hexDump());
        }
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            logger.debug("Connection closing error.", e);
        }
        socket = null;
        in = null;
        out = null;
        bb = null;
    }

    private Pdu tryToReadBuffer() throws PduException {
        if (bb.length() >= Pdu.HEADER_LENGTH) {
            long commandLength = bb.readInt();
            if (bb.length() >= commandLength)
                return parser.parse(new ByteBuffer(bb.removeBytes((int) commandLength)));
        }
        return null;
    }
    
    public static int byte2int(byte b[], int offset) {
		return b[offset + 3] & 0xff | (b[offset + 2] & 0xff) << 8 | (b[offset + 1] & 0xff) << 16
				| (b[offset] & 0xff) << 24;
	}
  
    private String hexByteToString (byte[] bytes) {
	    String ret = "";
	    for(int i = 0, len = bytes.length; i < len; i ++) {
	    	String hex =  Integer.toHexString(bytes[i] & 0xff).toUpperCase();
	    	ret += hex.length()  == 1 ? "0" + hex : hex;
	    }
	    return ret;
	}
    
    private byte[] hexToBuffer(String hex) throws IOException{
    	if ((hex == null) || ("".equals(hex))) {
    		return new byte[0];
    	}
    	if (hex.length() % 2 != 0) {
    		throw new RuntimeException("字符串的长度不为偶数");
    	}
    	int len = hex.length() / 2;
    	byte[] byteArray = new byte[len];
    	int index = 0;
    	for (int i = 0; i < len; i++) {
    		byteArray[i] = ((byte)Integer.parseInt(
    		hex.substring(index, index + 2), 16));
    		index += 2;
    	}
    	
    	return byteArray;
	}

}
