package com.whty.smpp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.pdu.DeliverSM;
/**
 * @ClassName MoService
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */

public class MoService implements Runnable {

//	private static Logger logger = Logger.getLogger("com.seleniumsoftware.smppsim");

    private static Logger logger = LoggerFactory.getLogger(MoService.class);
    
	private Smsc smsc = Smsc.getInstance();

	private int messagesPerMin;

	boolean moServiceRunning = false;

	String deliveryFile;

	MoMessagePool messages;
	
	public boolean isMoServiceRunning() {
		return moServiceRunning;
	}

	public void setMoServiceRunning(boolean moServiceRunning) {
		this.moServiceRunning = moServiceRunning;
	}

	public MoService(String filename, int deliverMessagesPerMin) {
		deliveryFile = filename;
		messagesPerMin = deliverMessagesPerMin;
	}
	
	public void run() {
		
		//读取推送数据放到消息队列池子里面
		logger.info("Starting MO Service....");
		try {
			messages = new MoMessagePool(deliveryFile);
		} catch (Exception e) {
			logger.error("Exception creating MoMessagePool. "
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	public void startMoService() throws Exception{
		long timer = 0;
		long actualTime = 0;
		int sleepMS;
		sleepMS = (int) (60000 / messagesPerMin);
		int count = 0;
		int minCount = 0;
		DeliverSM newMessage;

		timer = System.currentTimeMillis();
		logger.info("info serviceRunning info>>>>>>"+moServiceRunning);
		
		if (moServiceRunning) {
			logger.info("deliver service Running !!! deliver service Running !!! deliver service Running !!! >>>>>>");
			Thread.sleep(3000);
		}
		
		while (moServiceRunning) {
			newMessage = messages.getMessage();
			newMessage.setSm_length(newMessage.getShort_message().length);
			newMessage.setSeq_no(smsc.getNextSequence_No());
			logger.debug("MoService: DeliverSM object:"
					+ newMessage.toString());
			smsc.getIq().addMessage(newMessage);
			count++;
			minCount++;
			
			moServiceRunning = false;
			
			if (minCount == messagesPerMin) {
				actualTime = System.currentTimeMillis() - timer;
				logger.info(count + " MO messages inserted in InboundQueue. "
						+ minCount + " per minute target, actual time "
						+ actualTime + " ms");
				logger.info("drift = " + (actualTime - 60000));
				timer = System.currentTimeMillis();
				minCount = 0;
			}
			try {
				logger.debug("MO Service is sleeping for " + sleepMS
						+ " milliseconds");
				Thread.sleep(sleepMS);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} // while loop
	}

}
