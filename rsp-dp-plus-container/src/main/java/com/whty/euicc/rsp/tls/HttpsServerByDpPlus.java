package com.whty.euicc.rsp.tls;

import static com.whty.euicc.rsp.init.InitLoad.initHomePath;
import static com.whty.euicc.rsp.init.InitLoad.initLoadByStart;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ServerSocketFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.euicc.rsp.handler.DpPlusHandler;
import com.whty.euicc.tls.demo.HttpsServer;
import com.whty.euicc.tls.server.AbstractShakeHandsServer;
import com.whty.euicc.tls.server.HttpsShakeHandsServerCard;
import com.whty.euicc.tls.server.STlsHankUtils;
import com.whty.tls.server.ServerConnection;
import com.whty.tls.server.ShakeHandsServer;
import com.whty.tls.server.ecc.EccCardShakeHandsServer;

/**
 * dp plus tls启动类
 * @author Administrator
 *
 */
public class HttpsServerByDpPlus extends HttpsServer {  
	private final static Logger logger = LoggerFactory.getLogger(HttpsServerByDpPlus.class);
  
	private static ExecutorService executorService= Executors.newFixedThreadPool(20);  
	
    public static void main(String args[]) throws Exception{  
    	initHomePath();
    	initLoadByStart();
    	startDpPlusServer();  
    }
    

	public static void startDpPlusServer() throws IOException,
			SocketException {
		//AbstractShakeHandsServer shakeHandsServer = new  HttsShakeHandsServer();
		//AbstractShakeHandsServer shakeHandsServer = new  HttpsShakeHandsServerCard();
		ShakeHandsServer shakeHandsServer = new EccCardShakeHandsServer();
		String euiccPortStr = SpringPropertyPlaceholderConfigurer.getStringProperty("dp_plus_tls_port");
		int	euiccPort = Integer.parseInt(euiccPortStr);
		ServerSocket ss= ServerSocketFactory.getDefault().createServerSocket(euiccPort);  
        ss.setReceiveBufferSize(102400);  
        ss.setReuseAddress(false);  
        while(true){  
            try {  
            	System.out.println("等待LPA客户端连接:"+euiccPortStr);
                final Socket s = ss.accept();  
                System.out.println("一个LPA客户端连接了");
                /*doHttpsShakeHands(s);  
                executorService.execute(new Runnable() {  
                    @Override  
                    public void run() {  
                    	doSocketTransportByDp(s);  
                    }  
                });  */
                ServerConnection sTls = shakeHandsServer.doHttpsShakeHands(s);
                shakeHandsServer.doSocketTransport(s,new DpPlusHandler(),sTls);
                
                /*STlsHankUtils sTls = shakeHandsServer.doHttpsShakeHands(s);
                shakeHandsServer.doSocketTransportForRsp(s,new DpPlusHandler(),sTls);*/
  
            }catch (Exception e){  
                e.printStackTrace();  
            }  
        }
	}

  
}
