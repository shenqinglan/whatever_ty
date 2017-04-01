package com.seleniumsoftware.SMPPSim;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

public class DeliverServiceStop implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(DeliverServiceStop.class);
    
	private Smsc smsc = Smsc.getInstance();

	boolean dsServiceStop = false;
	
	private static DeliverServiceStop ds;
	
	private DeliverServiceStop() {
		
	}
	
	public static DeliverServiceStop getInstance() {
		if (ds == null)
			ds = new DeliverServiceStop();
		return ds;
	}

	@Override
	public void run() {
		logger.info("Starting DeliverServiceStop Service....");
		try {
			while (true) {
				Properties props = new Properties();
				InputStream is = new FileInputStream("conf/deliverStop.props");
		        props.load(is);
		        boolean flag = Boolean.valueOf(props.getProperty("deliverStop")).booleanValue();
				is.close();
				if (flag) {
					runService();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Deliver Service Stop threw an Exception:" + e.getMessage()
					+ ". It's game over");
		}
	}

	private void runService() throws Exception {
		
		// 关闭服务
		smsc.getDs().moServiceRunning = false;
		
		// 关置初始值
		Properties props = new Properties();
		InputStream is = new FileInputStream("conf/deliverStop.props");
        props.load(is);
        is.close();
		FileOutputStream oFile = new FileOutputStream("conf/deliverStop.props");
		props.setProperty("deliverStop", "FALSE");
		props.store(oFile, "The New properties file");
		oFile.close();
	}
}
