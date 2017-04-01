package com.whty.euicc.tls.server;

import java.io.IOException;
import java.net.Socket;

import com.whty.euicc.handler.base.AbstractHandler;

public abstract class AbstractShakeHandsServer {
	public abstract STlsHankUtils doHttpsShakeHands(Socket s) throws Exception;
	public abstract void doSocketTransport(Socket s,AbstractHandler handler,STlsHankUtils sTls) throws IOException;
	public abstract void doSocketTransportForRsp(Socket s,AbstractHandler handler,STlsHankUtils sTls) throws IOException;
	public abstract void releaseStream();
}
