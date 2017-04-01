package com.whty.smpp.queue;

import java.util.ArrayList;

import org.slf4j.LoggerFactory;

import com.whty.smpp.client.SmscMain;
import com.whty.smpp.exceptions.MessageStateNotFoundException;
import com.whty.smpp.exceptions.OutboundQueueFullException;
import com.whty.smpp.manager.LifeCycleManager;
import com.whty.smpp.pdu.SubmitSM;
import com.whty.smpp.pojo.MessageState;
import com.whty.smpp.service.Smsc;
/**
 * @ClassName OutboundQueue
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */

public class OutboundQueue implements Runnable {

	private static org.slf4j.Logger logger = LoggerFactory
			.getLogger(OutboundQueue.class);
	private Smsc smsc = Smsc.getInstance();
	private LifeCycleManager lcm = smsc.getLcm();

	ArrayList<MessageState> queue;

	public OutboundQueue(int maxsize) {
		queue = new ArrayList<MessageState>(maxsize);
	}

	public synchronized void addMessageState(MessageState message)
			throws OutboundQueueFullException {
		logger.debug("OutboundQueue: adding object to queue<"
				+ message.toString() + ">");
		if (queue.size() < smsc.getOutbound_queue_capacity()) {
			queue.add(message);
			logger.debug("Added object to OutboundQueue. Queue now contains "
					+ queue.size() + " object(s)");
			// 如果立刻通知消息，那么SMSC向ESME发送消息
			if (message.isIntermediate_notification_requested()) {
				SubmitSM p = message.getPdu();
				// delivery_receipt requested
				logger.info("Intermediate notification requested");
				smsc.prepareDeliveryReceipt(p, message.getMessage_id(),
						message.getState(), 1, 1, message.getErr());
			}
			notifyAll();
		} else
			throw new OutboundQueueFullException(
					"Request to add to OutboundQueue rejected as to do so would exceed max size of "
							+ smsc.getOutbound_queue_capacity());
	}

	public synchronized void setResponseSent(MessageState m)
			throws MessageStateNotFoundException {
		m.setResponseSent(true);
		updateMessageState(m);
	}

	public synchronized MessageState getMessageState(MessageState m)
			throws MessageStateNotFoundException {
		// logger.info("getMessageState:"+m.keyToString());
		int i = queue.indexOf(m);
		// logger.info("queue pos="+i);
		if (i > -1) {
			MessageState message = queue.get(i);
			return message;
		} else {
			throw new MessageStateNotFoundException();
		}
	}

	public synchronized void updateMessageState(MessageState newMs)
			throws MessageStateNotFoundException {
		int i = queue.indexOf(newMs);
		if (i > -1) {
			queue.set(i, newMs);
		} else {
			throw new MessageStateNotFoundException();
		}
	}

	public MessageState queryMessageState(String message_id, int ton, int npi,
			String addr) throws MessageStateNotFoundException {
		MessageState m = new MessageState();
		m.setMessage_id(message_id);
		m.setSource_addr_ton(ton);
		m.setSource_addr_npi(npi);
		m.setSource_addr(addr);
		return getMessageState(m);
	}

	public synchronized void removeMessageState(MessageState m) {
		int i = queue.indexOf(m);
		if (i > -1) {
			MessageState message = queue.remove(i);
			logger.debug("Removed object from OutboundQueue. Queue now contains , message >>> {}"
					+ queue.size() + " object(s)",message);
		} else {
			logger.error("Attempt to remove non-existent object from OutboundQueue: "
					+ m.toString());
		}
	}

	public synchronized Object[] getAllMessageStates() {
		return (Object[]) queue.toArray();
	}

	protected synchronized boolean isEmpty() {
		return queue.isEmpty();
	}

	/**
	 * @author Administrator
	 * @date 2017-1-24
	 * @Description TODO(queue QUERY_SM and REPLACE_SM messageState)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		logger.info("Starting Lifecycle Service (OutboundQueue)");
		// process queue forever
		do {
			processQueue();
		} while (true);
	}

	private void processQueue() {
		Object[] messages;
		int count;
		int oldState;
		long start;
		long finish;
		long duration;
		long sleeptime;
		synchronized (this) {
			while (isEmpty()) {
				try {
					logger.info("Lifecycle Service: OutboundQueue is Empty  - waiting");
					wait();
				} catch (InterruptedException e) {
					logger.error(
							"Exception in OutboundQueue: " + e.getMessage(), e);
				}
			}
			start = System.currentTimeMillis();
			messages = getAllMessageStates();
			// Can allow other threads to compete for lock now since working on
			// array from this point
			notifyAll();
		}
		count = messages.length;
		logger.info("Assessing state of " + count +" messages in the OutboundQueue");
		for (int i = 0; i < count; i++) {
			MessageState m = null;
			// 获取到一个消息状态，查询修改替换消息
			m = (MessageState) messages[i];
			if (!m.responseSent()) {
				// logger.debug("Response not yet sent so ignoring this MessageState object for now");
				continue;
			}
			if (lcm.messageShouldBeDiscarded(m)) {
				// logger.debug("Disarding OutboundQueue message " +
				// m.toString());
				removeMessageState(m);
			} else {
				byte currentState = m.getState();
				m = lcm.setState(m);
			}
		}
		finish = System.currentTimeMillis();
		duration = finish - start;
		sleeptime = SmscMain.getMessageStateCheckFrequency() - duration;
		if (sleeptime > 0) {
			try {
				logger.debug("Lifecycle Service sleeping for " + sleeptime
						+ " milliseconds");
				Thread.sleep(sleeptime);
			} catch (InterruptedException e) {
				logger.error("Exception whilst Lifecycle Service was sleeping "
						+ e.getMessage());
			}
		} else {
			logger.error("It's taking longer to process the OutboundQueue than MESSAGE_STATE_CHECK_FREQUENCY milliseconds. Recommend this value is increased");
		}
	}

	public int size() {
		return queue.size();
	}
}
