package com.whty.smpp.queue;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.client.SmscMain;
import com.whty.smpp.exceptions.InboundQueueFullException;
import com.whty.smpp.pdu.Pdu;

/**
 * @ClassName DelayedInboundQueue
 * @author Administrator
 * @date 2017-1-25 上午9:33:26
 * @Description TODO(队列里面保存稍后处理的消息队列，每一个队列里面的消息都对应一个尝试次数)
 * note:每一个消息推送不成功后会执行retryLater，等待后面推送出去；推送成功后会执行deliverOk，把消息从本类对象的队列中删除；
 * note:如果最大尝试推送次数后，消息都没有通过inBoundQueue推送出去，那么就将记录消息推送次数队列的该消息删除
 * note:线程函数，无限循环，不停的把消息放到inBoundQueue里面，等待消息被成功推送出去
 */
public class DelayedInboundQueue implements Runnable {
	
	private static DelayedInboundQueue diqueue;

    private static Logger logger = LoggerFactory.getLogger(DelayedInboundQueue.class);
    
	private InboundQueue iqueue = InboundQueue.getInstance();

	ArrayList<Pdu> delayed_queue_pdus;

	ArrayList<Integer> delayed_queue_attempts;

	private int period;

	private int max_attempts;

	/**
	 * @author Administrator
	 * @date 2017-1-25
	 * @return
	 * @Description TODO(单例模式获取延迟inBoundQueue对象)
	 */
	public static DelayedInboundQueue getInstance() {
		if (diqueue == null)
			diqueue = new DelayedInboundQueue();
		return diqueue;
	}

	private DelayedInboundQueue() {
		period = SmscMain.getDelayed_iqueue_period();
		max_attempts = SmscMain.getDelayed_inbound_queue_max_attempts();
		delayed_queue_attempts = new ArrayList<Integer>();
		delayed_queue_pdus = new ArrayList<Pdu>();
	}

	/**
	 * @author Administrator
	 * @date 2017-1-25
	 * @param pdu
	 * @Description TODO(如果没有推送出去，先把pdu加入到延迟inBoundQueue里面，稍后把尝试次数+1)
	 */
	public void retryLater(Pdu pdu) {
		synchronized (delayed_queue_pdus) {
			synchronized (delayed_queue_attempts) {
				
				// 每次有一个pdu进入到队列里面，那么该pdu的延迟队列尝试次数里面添加一个0
				if (!delayed_queue_pdus.contains(pdu)) {
					logger.debug("DelayedInboundQueue: adding object to queue<"
							+ pdu.toString() + ">");
					delayed_queue_pdus.add(pdu);
					delayed_queue_attempts.add(new Integer(0));
				} else {
					
					// 如果有该pdu，那么延迟队列里面的pdu的尝试次数+1，有一个加一个，直到达到最大尝试次数中止。
					int i = delayed_queue_pdus.indexOf(pdu);
					if (i > -1) {
						int a = delayed_queue_attempts.get(i).intValue();
						a++;
						delayed_queue_attempts.set(i,a);
						logger.debug("DelayedInboundQueue: incremented retry count to "+a+" for "+"<"
								+ pdu.toString() + ">");
					}
				}
				logger.info("DelayedInboundQueue: now contains "
						+ delayed_queue_pdus.size() + " object(s)");
			}
		}
	}
	
	/**
	 * @author Administrator
	 * @date 2017-1-25
	 * @param pdu
	 * @Description TODO(推送成功后，会将pdu从DelayedInboundQueue队列里面删掉，
	 * note:该队列就是保存还没有推送出去的消息队列)
	 */
	public void deliveredOK(Pdu pdu) {
		
		// 获取pdu的序列号
		int seqno = pdu.getSeq_no();
		synchronized (delayed_queue_pdus) {
			synchronized (delayed_queue_attempts) {
				logger.debug("DelayedInboundQueue: removing object from queue<" + pdu.toString() + ">");
				int i = delayed_queue_pdus.indexOf(pdu);
				if (i > -1) {
					
					// 从延迟消息队列里面拿 出来一个pdu，如果pdu的序列号是一致的，那么就把该pdu和pdu的尝试次数从队列里面删掉
					Pdu mo = delayed_queue_pdus.get(i);
					if (mo.getSeq_no() == seqno) {
						delayed_queue_pdus.remove(i);
						delayed_queue_attempts.remove(i);
						logger.debug("Removed delayed message because it was delivered OK or with permanent error. message:{"+mo.toString()+"}");
					}
				}
				logger.info("DelayedInboundQueue: now contains "
						+ delayed_queue_pdus.size() + " object(s)");
			}
		}
		
	}

	/**
	 * @author Administrator
	 * @date 2017-1-22
	 * @Description TODO(循环遍历延迟队列，将数据从延迟队列移动到激活队列发送给ESME)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		logger.info("Starting DelayedInboundQueue service....");
		while (true) {
			try {
				Thread.sleep(period);
			} catch (InterruptedException e) {
			}
			synchronized (delayed_queue_pdus) {
				synchronized (delayed_queue_attempts) {
					for (int i = 0; i < delayed_queue_pdus.size(); i++) {
						
						// 循环遍历延迟推送消息队列里面的每一个pdu
						Pdu mo = delayed_queue_pdus.get(i);
						try {
							// 判断小于最大尝试推送次数，那么就将消息放到inBoundQueue里面，等到消息推送成功
							if (delayed_queue_attempts.get(i).intValue() < max_attempts) {
								iqueue.addMessage(mo);
								int attempts = delayed_queue_attempts.get(i).intValue() + 1;
								delayed_queue_attempts.set(i, new Integer(attempts));
								logger.debug("Requesting retry delivery of message "+mo.getSeq_no());
							} else {
								logger.info("MO message not delivered after max ("+max_attempts+") allowed attempts so deleting. message:{"+mo.toString()+"}");
								
								// 如果最大尝试推送次数后，消息都没有通过inBoundQueue推送出去，那么就将记录消息推送次数队列的该消息删除
								delayed_queue_pdus.remove(i);
							}
						} catch (InboundQueueFullException e) {
							
						}
					}
				}
			}
		}
	}

}