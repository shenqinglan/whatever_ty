package com.whty.euicc.rsp;

import static com.whty.euicc.rsp.init.InitLoad.initHomePath;
import static com.whty.euicc.rsp.init.InitLoad.initLoadByStart;
import static com.whty.euicc.rsp.tls.HttpsServerByDs.startDsServer;

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
    	startDsServer();  

	}

}
