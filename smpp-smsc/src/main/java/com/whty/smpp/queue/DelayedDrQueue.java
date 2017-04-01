package com.whty.smpp.queue;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.client.SmscMain;
import com.whty.smpp.exceptions.InboundQueueFullException;
import com.whty.smpp.pdu.DeliverSM;
import com.whty.smpp.pdu.DeliveryReceipt;
import com.whty.smpp.pdu.Pdu;

/**
 * @ClassName DelayedDrQueue
 * @author Administrator
 * @date 2017-1-25 上午9:20:00
 * @Description TODO(延迟推送消息队列)
 * note:添加延迟推送消息到消息队列,包括消息推送的时间，队列里面放的是推送消息报告
 * note:无限循环不停地读取延迟推送消息队列里面的消息，主要是通过时间来进行判断的
 */
public class DelayedDrQueue implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(DelayedDrQueue.class);
	
	private InboundQueue iqueue = InboundQueue.getInstance();

	ArrayList<DeliveryReceipt> dr_queue_pdus;

	private static final int period = 5000;
	
	private long delay_ms;
	
	private static DelayedDrQueue drqueue;
	
	private DelayedDrQueue() {
		dr_queue_pdus = new ArrayList<DeliveryReceipt>();
		delay_ms = SmscMain.getDelayReceiptsBy();
	}

	public static DelayedDrQueue getInstance() {
		if (drqueue == null)
			drqueue = new DelayedDrQueue();
		return drqueue;
	}

	/**
	 * @author Administrator
	 * @date 2017-1-25
	 * @param pdu
	 * @Description TODO(DeliveryReceipt，推送消息报告)
	 */
	public void delayDeliveryReceipt(DeliveryReceipt pdu) {
		synchronized (dr_queue_pdus) {
			if (!dr_queue_pdus.contains(pdu)) {
				logger.debug("DelayedDrQueue: adding object to queue<"
						+ pdu.toString() + ">");
				dr_queue_pdus.add(pdu);
			}
			logger.debug("DelayedDrQueue: now contains " + dr_queue_pdus.size()
					+ " object(s)");
		}
	}

	/**
	 * @author Administrator
	 * @date 2017-1-22
	 * @Description TODO(周期性处理延迟推送消息队列，从老消息移动到inbound Queue里面)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		logger.info("Starting DelayedDrQueue service....");
		while (true) {
			try {
				Thread.sleep(period);
			} catch (InterruptedException e) {
			}
			int dcount = dr_queue_pdus.size();
			logger.debug("Processing " + dcount	+ " messages in the delayed DR queue");

			synchronized (dr_queue_pdus) {
				for (int i = 0; i < dr_queue_pdus.size(); i++) {
					Pdu mo = dr_queue_pdus.get(i);
					try {
						DeliverSM dsm = (DeliverSM) mo;
						
						// 推送消息创建时间+延迟推送时间(5s)
						long earliest_delivery_time = (dsm.getCreated()+delay_ms);
						long now = System.currentTimeMillis();
						long diff = earliest_delivery_time - now;
						logger.debug("Considering delivery receipt: "+(diff/1000)+" seconds to go");
						
						// 如果消息推送创建时间+延迟时间，比当前时间小，从延迟推送消息队列移除消息，放到推送消息inbound队列
						if (earliest_delivery_time < now) {
							iqueue.addMessage(mo);
							dr_queue_pdus.remove(mo);
						}
					} catch (InboundQueueFullException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}