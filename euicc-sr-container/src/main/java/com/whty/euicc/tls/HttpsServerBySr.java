package com.whty.euicc.tls;

import static com.whty.euicc.init.InitLoad.initHomePath;
import static com.whty.euicc.init.InitLoad.initLoadByStart;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ServerSocketFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.euicc.common.properties.EnvProperty;
import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.euicc.handler.SrHandler;
import com.whty.euicc.tls.server.AbstractShakeHandsServer;
import com.whty.euicc.tls.server.HttpsShakeHandsServerCard;
import com.whty.euicc.tls.server.HttsShakeHandsServer;
import com.whty.euicc.tls.server.STlsHankUtils;

/**
 * 启动sr tls
 * 
 * @author Administrator
 * 
 */
public class HttpsServerBySr{
	private final static Logger logger = LoggerFactory
			.getLogger(HttpsServerBySr.class);

	private static ExecutorService executorService = Executors
			.newFixedThreadPool(20);

	public static void main(String args[]) throws Exception {
		initHomePath();
		initLoadByStart();
		startSrServer();
	}

	public static void startSrServer() throws IOException, SocketException {
		AbstractShakeHandsServer shakeHandsServer = EnvProperty.isProduction() ? new  HttpsShakeHandsServerCard() : new  HttsShakeHandsServer();
		String euiccPortStr = StringUtils.defaultIfBlank(SpringPropertyPlaceholderConfigurer.getStringProperty("tls_port"), "8090");
		int	euiccPort = Integer.parseInt(euiccPortStr);
		ServerSocket ss = ServerSocketFactory.getDefault().createServerSocket(
				euiccPort);
		ss.setReceiveBufferSize(102400);
		ss.setReuseAddress(false);
		while (true) {
			try {
				System.out.println("等待Sr客户端连接");
				final Socket s = ss.accept();
				System.out.println("一个Sr客户端连接了");
				STlsHankUtils sTls = shakeHandsServer.doHttpsShakeHands(s);
				//System.out.println("sTls >>> "+sTls);
				if(sTls!=null){
					shakeHandsServer.doSocketTransport(s,new SrHandler(),sTls);
				}
				//httpHandsServer.finalize();
				/*tls.connect(s);
	            tls.doSocketTransport(s, new SrHandler());*/

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	
	

}
