package com.whty.euicc.tls.client;

import java.io.IOException;
import java.net.Socket;

public abstract class AbstractCTlsHank {
	public abstract int shakeHands(Socket s) throws Exception;
	public abstract void sendEncryptData(Socket s,byte[] dataByte) throws IOException;
	public abstract void sendEncryptDataByRsp(Socket s,byte[] dataByte) throws IOException;
	public abstract byte[] receiveServerData(Socket s) throws IOException;
	public abstract byte[] receiveServerDataByRsp(Socket s) throws IOException;
}
