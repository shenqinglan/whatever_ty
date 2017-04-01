package com.seleniumsoftware.SMPPSim;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

public class DeliverServiceStart implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(DeliverServiceStart.class);
    
	private Smsc smsc = Smsc.getInstance();

	boolean dsServiceStart = false;
	
	private static DeliverServiceStart dstart;
	
	private DeliverServiceStart() {
		
	}
	
	public static DeliverServiceStart getInstance() {
		if (dstart == null)
			dstart = new DeliverServiceStart();
		return dstart;
	}

	@Override
	public void run() {
		logger.info("Starting DeliverServiceStart Service....");
		try {
			while (true) {
				Properties props = new Properties();
				InputStream is = new FileInputStream("conf/deliverStart.props");
		        props.load(is);
		        boolean flag = Boolean.valueOf(props.getProperty("deliverStart")).booleanValue();
				is.close();
				if (flag) {
					runService();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Deliver Service Start threw an Exception:" + e.getMessage()
					+ ". It's game over");
		}
	}

	private void runService() throws Exception {
		
		// 开启服务
		
		smsc.getDs().moServiceRunning = true;
		smsc.getDs().startMoService();
		smsc.getIq().notifyReceiverBound();
		
		// 开置初始值
		Properties props = new Properties();
		InputStream is = new FileInputStream("conf/deliverStart.props");
        props.load(is);
        is.close();
		FileOutputStream oFile = new FileOutputStream("conf/deliverStart.props");
		props.setProperty("deliverStart", "FALSE");
		props.store(oFile, "The New properties file");
		oFile.close();
	}
}
