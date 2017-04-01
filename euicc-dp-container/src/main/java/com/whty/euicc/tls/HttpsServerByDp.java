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

import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.euicc.handler.DpHandler;
import com.whty.euicc.tls.demo.HttpsServer;
import com.whty.euicc.tls.server.HttsShakeHandsServer;
import com.whty.euicc.tls.server.STlsHankUtils;

/**
 * dp tls启动类
 * @author Administrator
 *
 */
public class HttpsServerByDp extends HttpsServer {  
	private final static Logger logger = LoggerFactory.getLogger(HttpsServerByDp.class);
  
	private static ExecutorService executorService= Executors.newFixedThreadPool(20);  
	
    public static void main(String args[]) throws Exception{  
    	initHomePath();
    	initLoadByStart();
    	startDpServer();  
    }
    

	public static void startDpServer() throws IOException,
			SocketException {
		HttsShakeHandsServer httpHandsServer = new  HttsShakeHandsServer();
		String euiccPortStr = StringUtils.defaultIfBlank(SpringPropertyPlaceholderConfigurer.getStringProperty("tls_port"), "8091");
		int	euiccPort = Integer.parseInt(euiccPortStr);
		ServerSocket ss= ServerSocketFactory.getDefault().createServerSocket(euiccPort);  
        ss.setReceiveBufferSize(102400);  
        ss.setReuseAddress(false);  
        while(true){  
            try {  
            	System.out.println("等待Dp客户端连接");
                final Socket s = ss.accept();  
                System.out.println("一个Dp客户端连接了");
                /*doHttpsShakeHands(s);  
                executorService.execute(new Runnable() {  
                    @Override  
                    public void run() {  
                    	doSocketTransportByDp(s);  
                    }  
                });  */
                STlsHankUtils sTls = httpHandsServer.doHttpsShakeHands(s);
				httpHandsServer.doSocketTransport(s,new DpHandler(),sTls);
  
            }catch (Exception e){  
                e.printStackTrace();  
            }  
        }
	}

   /* private static void doSocketTransportByDp(Socket s){  
        try{  
            System.out.println("--------------------------------------------------------");  
            int length=in.readInt();  
            byte[] clientMsg=readBytes(length);  
            System.out.println("Dp客户端指令内容为:" + byte2hex(clientMsg));  
            System.out.println("Dp客户端指令内容为:" + new String(clientMsg));  
            byte[] respMsg = new DpHandler().handle(new String(clientMsg));
            writeBytes(new String(respMsg).getBytes());  
        }catch (Exception ex){  
            ex.printStackTrace();  
        }  
    }  */
	
}
