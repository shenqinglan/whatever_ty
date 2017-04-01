package com.whty.euicc;

import static com.whty.euicc.init.InitLoad.initHomePath;
import static com.whty.euicc.init.InitLoad.initLoadByStart;
import static com.whty.euicc.netty.NettyServerByDp.startDpNetty;
import static com.whty.euicc.tls.HttpsServerByDp.startDpServer;

/**
 * 程序入口
 * @author Administrator
 *
 */
public class StartServer {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		initHomePath();
    	initLoadByStart();
    	startDpNetty();
    	//startDpServer();  

	}

}
