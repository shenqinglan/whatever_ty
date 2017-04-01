package com.whty.tls.client;

import java.io.IOException;
import java.net.Socket;

public abstract class ShakeHandsClient {
	/***
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public abstract ClientConnection doHttpsShakeHands(Socket s) throws Exception;

	/***
	 * @param s
	 * @param conn
	 * @throws IOException
	 */
	public abstract void doSocketTransport(Socket s,ClientConnection conn) throws IOException;
	
	/***
	 * @param s
	 * @param conn
	 * @param dataByte
	 * @throws IOException
	 */
	public abstract void sendEncryptData(Socket s, ClientConnection conn, byte[] dataByte) throws IOException;
	
	/***
	 * @param s
	 * @param conn
	 * @return
	 * @throws IOException
	 */
	public abstract byte[] receiveServerData(Socket s, ClientConnection conn) throws IOException;
}
