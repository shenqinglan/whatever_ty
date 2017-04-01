package com.whty.euicc;

import static com.whty.euicc.init.InitLoad.initHomePath;
import static com.whty.euicc.init.InitLoad.initLoadByStart;
import static com.whty.euicc.tls.HttpsServerBySr.startSrServer;
import static com.whty.euicc.netty.NettyServerBySr.startSrNetty;

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
    	startSrNetty();
    	startSrServer();  

	}

}
