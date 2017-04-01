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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.euicc.common.properties.EnvProperty;
import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.euicc.rsp.handler.DsHandler;
import com.whty.euicc.tls.demo.HttpsServer;
import com.whty.euicc.tls.server.AbstractShakeHandsServer;
import com.whty.euicc.tls.server.HttpsShakeHandsServerCard;
import com.whty.euicc.tls.server.HttsShakeHandsServer;
import com.whty.euicc.tls.server.STlsHankUtils;

/**
 * dp tls启动类
 * @author Administrator
 *
 */
public class HttpsServerByDs extends HttpsServer {  
	private final static Logger logger = LoggerFactory.getLogger(HttpsServerByDs.class);
  
	private static ExecutorService executorService= Executors.newFixedThreadPool(20);  
	
    public static void main(String args[]) throws Exception{  
    	initHomePath();
    	initLoadByStart();
    	startDsServer();  
    }
    

	public static void startDsServer() throws IOException,
			SocketException {
		AbstractShakeHandsServer shakeHandsServer = new  HttpsShakeHandsServerCard();
		String euiccPortStr = SpringPropertyPlaceholderConfigurer.getStringProperty("ds_tls_port");
		int	euiccPort = Integer.parseInt(euiccPortStr);
		ServerSocket ss= ServerSocketFactory.getDefault().createServerSocket(euiccPort);  
        ss.setReceiveBufferSize(102400);  
        ss.setReuseAddress(false);  
        while(true){  
            try {  
            	System.out.println("等待Ds客户端连接:"+euiccPortStr);
                final Socket s = ss.accept();  
                System.out.println("一个Ds客户端连接了");
                /*doHttpsShakeHands(s);  
                executorService.execute(new Runnable() {  
                    @Override  
                    public void run() {  
                    	doSocketTransportByDp(s);  
                    }  
                });  */
                STlsHankUtils sTls = shakeHandsServer.doHttpsShakeHands(s);
                shakeHandsServer.doSocketTransport(s,new DsHandler(),sTls);
  
            }catch (Exception e){  
                e.printStackTrace();  
            }  
        }
	}

	
}
