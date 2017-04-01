package com.whty.smpp.queue;

import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.smpp.client.SmscMain;
import com.whty.smpp.exceptions.InboundQueueFullException;
import com.whty.smpp.pdu.DataSM;
import com.whty.smpp.pdu.DeliverSM;
import com.whty.smpp.pdu.DeliveryReceipt;
import com.whty.smpp.pdu.Outbind;
import com.whty.smpp.pdu.Pdu;
import com.whty.smpp.pdu.PduConstants;
import com.whty.smpp.service.Smsc;
import com.whty.smpp.service.StandardConnectionHandler;
import com.whty.smpp.util.LoggingUtilities;
/**
 * @ClassName InboundQueue
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class InboundQueue implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(InboundQueue.class);

	private Smsc smsc = Smsc.getInstance();

	ArrayList<Pdu> inbound_queue;

	ArrayList<Pdu> response_queue;

	private static InboundQueue iqueue;

	private static DelayedInboundQueue diqueue;

	// 如果没有接受到消息，消息会进入到待定的队列list集合
	ArrayList<Pdu> pending_queue = new ArrayList<Pdu>();

	/**
	 * @author Administrator
	 * @date 2017-1-25
	 * @return
	 * @Description TODO(单例模式获取到inBoundQueue的实例对象)
	 */
	public static InboundQueue getInstance() {
		if (iqueue == null)
			iqueue = new InboundQueue(SmscMain.getInbound_queue_capacity());
		return iqueue;
	}

	/**
	 * @param maxsize
	 * note:构造函数对inBoundQueue里面的队列和返回队列设置最大值
	 */
	private InboundQueue(int maxsize) {
		inbound_queue = new ArrayList<Pdu>(maxsize);
		response_queue = new ArrayList<Pdu>(maxsize);
	}

	/**
	 * @author Administrator
	 * @date 2017-1-25
	 * @param message
	 * @throws InboundQueueFullException
	 * @Description TODO(往inBoundQueue里面添加消息，如果容量超过最大容量，抛出队列满异常)
	 */
	public void addMessage(Pdu message) throws InboundQueueFullException {
		synchronized (inbound_queue) {
			if (inbound_queue.size() >= smsc.getInbound_queue_capacity())
				throw new InboundQueueFullException();
			logger.debug("InboundQueue: adding object to queue<"
					+ message.toString() + ">");
			inbound_queue.add(message);
			logger.debug("InboundQueue: now contains " + inbound_queue.size()
					+ " object(s)");
			inbound_queue.notifyAll();
		}
	}

	/**
	 * @author Administrator
	 * @date 2017-1-25
	 * @param seqno
	 * @param command_status
	 * @Description TODO(从response队列里面拿出来一个消息，推送出去)
	 * note:如果消息状态是队列很多，先放到延迟推送消息队列里面，如果状态正常，将消息从延迟推送队列里面删除
	 */
	public void deliveryResult(int seqno, int command_status) {
		logger.debug("MO message delivery attempted: seqno=" + seqno
				+ ",status=" + command_status + ",responses pending="
				+ response_queue.size());
		synchronized (response_queue) {
			for (int i = 0; i < response_queue.size(); i++) {
				Pdu pdu = response_queue.get(i);
				
				//根据seqno从response队列里面获取到消息 ，如果队列已经满了，那么就先放到延迟推送消息标记队列里面
				if (pdu.getSeq_no() == seqno) {
					if (command_status == PduConstants.ESME_RMSGQFUL) {
						logger.info("MO message messsage:{" + pdu.toString() + "} was rejected with queue full so putting in delayed inbound queue for retry");
						diqueue.retryLater(pdu);
					} else {
						
						// 如果队列里面的消息状态码不是队列已满，那么就从延迟推送消息标记队列里面推送消息
						diqueue.deliveredOK(pdu);
					}
					
					// 将消息从response消息队列里面删除
					response_queue.remove(i);
					break;
				}
			}
		}
		logger.debug("Awaiting responseQueue " + response_queue.size() + " responses");
	}

	// 将消息删掉
	public void removeMessage(Pdu m) {
		removeMessage(m, inbound_queue);
	}

	public void removeMessage(Pdu m, ArrayList<Pdu> queue) {
		logger.debug("Removing PDU from queue. Queue currently contains:"
				+ queue.size());
		
		// 打印inBoundQueue里面的所有的消息
		for (int i = 0; i < queue.size(); i++) {
			logger.debug(((Pdu) queue.get(i)).toString());
		}
		synchronized (queue) {
			int i = queue.indexOf(m);
			if (i > -1) {
				queue.remove(i);
			} else {
				logger.warn("Attempt to remove non-existent object from InboundQueue: "
						+ m.toString());
			}
		}
		logger.debug("Queue now contains:" + queue.size());
	}

	/**
	 * @author Administrator
	 * @date 2017-1-25
	 * @return
	 * @Description TODO(获取到inBoundQueue里面的所有消息，返回消息数组)
	 */
	private Object[] getAllMessages() {
		synchronized (inbound_queue) {
			Object[] o = inbound_queue.toArray();
			return o;
		}
	}

	/**
	 * @author Administrator
	 * @date 2017-1-25
	 * @return
	 * @Description TODO(判断inBound_queue是否为空)
	 */
	private boolean isEmpty() {
		return inbound_queue.isEmpty();
	}

	/**
	 * @author Administrator
	 * @date 2017-1-25
	 * @return
	 * @Description TODO(返回inBoundQueue的元素数目)
	 */
	public int size() {
		return inbound_queue.size();
	}

	/**
	 * @author Administrator
	 * @date 2017-1-25
	 * @return
	 * @Description TODO(获取pending_queue的元素数目)
	 */
	public int pending_size() {
		return pending_queue.size();
	}

	/**
	 * @author Administrator
	 * @date 2017-1-25
	 * @Description TODO(通知所有可以执行inBound_queue的线程苏醒)
	 */
	public void notifyReceiverBound() {
		synchronized (inbound_queue) {
			logger.debug("A receiver bound - will notify InboundQueue service");
			inbound_queue.notifyAll();
		}
	}

	/**
	 * @author Administrator
	 * @date 2017-1-22
	 * @Description TODO(等待消息处理，是mo service消息或者
	 *              loopback回调消息，均为deliver，当有一个receiver session或者
	 *              inBoundQueue消息)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		logger.info("Starting InboundQueue service....");
		
		// 开启delayedInboundQueue队列
		diqueue = DelayedInboundQueue.getInstance();
		Thread t = new Thread(diqueue);
		t.start();

		do 
		{
			// 无限循环处理inBoundQueue里面的消息
			processQueue();
			
		} while (true);
	}

	private void addPendingQueue(Pdu mo) {
		pending_queue.add(mo);
		if (SmscMain.isOutbind_enabled() && !smsc.isOutbind_sent()) {
			outbind();
		}
	}
	
	public void outbind() {
		try {
			Socket s = new Socket(SmscMain.getEsme_ip_address(),SmscMain.getEsme_port());
			OutputStream out = s.getOutputStream();
			Outbind outbind = new Outbind(SmscMain.getEsme_systemid(),SmscMain.getEsme_password());
			byte [] outbind_bytes = outbind.marshall();
			LoggingUtilities.hexDump(": OUTBIND:", outbind_bytes, outbind_bytes.length);
			LoggingUtilities.logDecodedPdu(outbind);
			out.write(outbind_bytes);
			out.flush();
			out.close();
			s.close();
			smsc.setOutbind_sent(true);
		} catch (Exception e) {
			logger.error("Attempted outbind failed. Check IP address and port are correct for outbind. Exception of type "+e.getClass().getName());
		}
	}

	private void processQueue() {
		StandardConnectionHandler receiver = null;
		synchronized (inbound_queue) {
			while (isEmpty() || (smsc.getReceiverBoundCount() == 0)) {
				try {
					if (isEmpty()) {
						
						// 如果inBoundQueue为空，那么就什么都不执行
						logger.info("InboundQueue: Empty  - waiting");
					} else {
						
						// 如果session没有收到receiver或者transreceiver消息，那么就将inBoundQueue里面的deliver消息放到pendingQueue里面
						int pc = 0;
						synchronized (pending_queue) {
							Object[] active_pdus = inbound_queue.toArray();
							for (int i = 0; i < active_pdus.length; i++) {
								Pdu mo = (Pdu) active_pdus[i];
								addPendingQueue(mo);
								removeMessage(mo);
								pc++;
							}
						}
						logger.info("Moved piece of " + pc + " MO messages to the pending queue");
					}
					inbound_queue.wait();
				} catch (InterruptedException ex) {
					logger.error(ex.getMessage());
				}
			}
		}
		
		// 如果收到了receiver消息，同时inBoundQueue的元素不为空，线程被唤醒
		if (smsc.getReceiverBoundCount() != 0) {
			Object[] pdus = getAllMessages();
			logger.debug("Attempting to deliver piece of " + pdus.length
					+ " messages from InboundQueue");
			int i = 0;
			boolean continuing = true;
			while (i < pdus.length && continuing) {
				if (pdus[i] instanceof DeliverSM) {
					continuing = processDeliverSM((DeliverSM) pdus[i],
							receiver, inbound_queue);
				} else {
					continuing = processDataSM((DataSM) pdus[i], receiver);
				}
				i++;
			}
		}
	}

	protected boolean processDeliverSM(DeliverSM pdu,
			StandardConnectionHandler receiver, ArrayList<Pdu> from_queue) {
		String pduName;
		byte[] message;
		boolean delivery_receipt = false;

		if (pdu instanceof DeliveryReceipt) {
			pduName = "DELIVER_SM (receipt):";
			delivery_receipt = true;
		} else {
			pduName = "DELIVER_SM:";
		}

		try {
			int interface_version = 0x34;
			receiver = smsc.selectReceiver(pdu.getDestination_addr());
			if (receiver != null) {
				interface_version = receiver.getHandler().getSession()
						.getInterface_version();
			}
			// adjust PDU according to client capability (interface_version) and
			// config
			if (delivery_receipt) {
				if (interface_version < 0x34
						|| !SmscMain.isDlr_opt_tlv_required()) {
					pdu.removeVsop(PduConstants.USER_MESSAGE_REFERENCE_TAG);
					pdu.removeVsop(PduConstants.RECEIPTED_MESSAGE_ID);
					pdu.removeVsop(PduConstants.MESSAGE_STATE);
					pdu.removeVsop(PduConstants.NETWORK_ERROR_CODE_TAG);
				} else {
				}
			}
			message = pdu.marshall();
			LoggingUtilities.hexDump(pduName, message, message.length);
			LoggingUtilities.logDecodedPdu(pdu);
			if (delivery_receipt) {
				if (interface_version >= 0x34
						&& SmscMain.isDlr_opt_tlv_required()) {
					// logger.info(pdu.getOptParamsAsString());
				}
			}
			if (receiver == null) {
				// logger.warn("InboundQueue: no active receiver object to deliver message. Application must issue BIND_RECEIVER with approriate address_range. Message has been moved to the pending queue");
				removeMessage(pdu, from_queue);
				addPendingQueue(pdu);
				if (smsc.getReceiverBoundCount() == 0) {
					// logger.info("No receiver sessions bound - suspending InboundQueue processing");
					return false;
				}
			} else {
				try {
					LoggingUtilities.logDecodedPdu(pdu);
					receiver.writeResponse(message);
					/**
					 * Should only remove from the queue if we didn't get a
					 * response of ESME_RMSGQFUL Right now we don't know what
					 * the response was in this code... so removal needs to be
					 * triggered asynchronously by receipt of a DELIVER_SM_RESP
					 * in the protocol handler
					 * 
					 * Sequence number matching required of course.
					 * 
					 */
					synchronized (response_queue) {
						response_queue.add(pdu);
						logger.debug("Added message " + pdu.getSeq_no()
								+ " to response queue");
					}
					removeMessage(pdu, from_queue);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		return true;
	}

	protected boolean processDataSM(DataSM pdu,
			StandardConnectionHandler receiver) {
		byte[] message;

		try {
			message = pdu.marshall();
			LoggingUtilities.hexDump("DATA_SM:", message, message.length);
			LoggingUtilities.logDecodedPdu(pdu);
			receiver = smsc.selectReceiver(pdu.getDestination_addr());
			if (receiver == null) {
				logger.info("InboundQueue: no active receiver object to deliver message. Application must issue BIND_RECEIVER with approriate address_range. Message deleted from the inbound queue.");
				removeMessage(pdu);
				if (smsc.getReceiverBoundCount() == 0) {
					logger.info("No receiver sessions bound - suspending InboundQueue processing");
					return false;
				}
			} else {
				try {
					LoggingUtilities.logDecodedPdu(pdu);
					receiver.writeResponse(message);
					removeMessage(pdu);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		return true;
	}

	/**
	 * Called when a receiver or transceiver session is established. Results in
	 * any MO messages for which a session was not available originally and
	 * which were set to one side in the pending queue, being delivered if the
	 * new session is suitable.
	 * 
	 */
	public void deliverPendingMoMessages() {
		Object[] messages = null;
		synchronized (pending_queue) {
			messages = pending_queue.toArray();
		}
		int l = messages.length;

		for (int i = 0; i < l; i++) {
			boolean ok = processDeliverSM((DeliverSM) messages[i], null,
					pending_queue);
			if (!ok)
				break;
		}

		// reset the outbind flag ready for the next time
		smsc.setOutbind_sent(false);

	}
}