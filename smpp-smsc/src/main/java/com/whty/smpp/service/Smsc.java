package com.whty.smpp.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.whty.smpp.client.SmscMain;
import com.whty.smpp.exceptions.InboundQueueFullException;
import com.whty.smpp.manager.DeliverServiceStart;
import com.whty.smpp.manager.DeliverServiceStop;
import com.whty.smpp.manager.LifeCycleManager;
import com.whty.smpp.pdu.DeliveryReceipt;
import com.whty.smpp.pdu.PduConstants;
import com.whty.smpp.pdu.SubmitSM;
import com.whty.smpp.queue.DelayedDrQueue;
import com.whty.smpp.queue.InboundQueue;
import com.whty.smpp.queue.OutboundQueue;
/**
 * @ClassName Smsc
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */

public class Smsc {
	
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(Smsc.class);
	private static Smsc smsc;
	private Smsc() {
	}

	public static Smsc getInstance() {
		if (smsc == null)
			smsc = new Smsc();
		return smsc;
	}
	
	private static long messageId = 0;
	
	// 标准连接handler数组
	
	private StandardConnectionHandler[] connectionHandlers;
	private ServerSocket smppServerSocket;

	private static int sequence_no = 0;

	private static byte[] smscId;

	private int receiverIndex = 0;
	
	private InboundQueue iq;
	private Thread inboundQueueService;
	
	private MoService ds;
	private Thread deliveryService;
	
	private DeliverServiceStop deliverStop ;
	private Thread deliverServiceStop;
	
	private DeliverServiceStart deliverStart ;
	private Thread deliverServiceStart;

	private LifeCycleManager lcm;
	
	private DelayedDrQueue drq;
	
	private OutboundQueue oq;
	private Thread lifecycleService;
	
	private int inbound_queue_capacity = 100;
	private int outbound_queue_capacity = 20000;

	boolean outbind_sent = false;
	
	public MoService getDs() {
		return ds;
	}

	public static byte[] getSmscId() {
		return smscId;
	}

	public static void setSmscId(byte[] smscId) {
		Smsc.smscId = smscId;
	}

	public static int getSequence_no() {
		return sequence_no;
	}


	public static void setSequence_no(int i) {
		sequence_no = i;
	}

	public InboundQueue getIq() {
		return iq;
	}

	public OutboundQueue getOq() {
		return oq;
	}

	public int getInbound_queue_capacity() {
		return inbound_queue_capacity;
	}


	public int getInbound_queue_size() {
		return iq.size();
	}

	public int getPending_queue_size() {
		return iq.pending_size();
	}
	
	
	public int getOutbound_queue_capacity() {
		return outbound_queue_capacity;
	}

	public int getOutbound_queue_size() {
		return oq.size();
	}

	public void setInbound_queue_capacity(int i) {
		inbound_queue_capacity = i;
	}

	public void setOutbound_queue_capacity(int i) {
		outbound_queue_capacity = i;
	}

	public LifeCycleManager getLcm() {
		return lcm;
	}

	public boolean isOutbind_sent() {
		return outbind_sent;
	}

	public void setOutbind_sent(boolean outbind_sent) {
		this.outbind_sent = outbind_sent;
	}

	public void start() throws Exception {
		
		// 获取messageId
		messageId = SmscMain.getStartAt();
		
		// 加载InBoundQueue服务
		iq = InboundQueue.getInstance();

		// 加载lifeCycleManagerment服务
		Class cl = Class.forName(SmscMain.getLifeCycleManagerClassName());
		lcm = (LifeCycleManager) cl.newInstance();

		// 加载OutBoundQueud服务
		oq = new OutboundQueue(outbound_queue_capacity);

		// 根据指定的连接数目加载多个handler，但是绑定的socket却只有一个
		Thread smppThread[] = new Thread[SmscMain.getMaxConnectionHandlers()];
		int threadIndex = 0;
		connectionHandlers = new StandardConnectionHandler[SmscMain.getMaxConnectionHandlers()];
		try {
			smppServerSocket = new ServerSocket(SmscMain.getSmppPort(), 10);
		} catch (Exception e) {
			throw new RuntimeException("Exception creating SMPP server: "+ e.toString());
		}
		for (int i = 0; i < SmscMain.getMaxConnectionHandlers(); i++) {
			
			// 反射机制加载连接handler类
			Class clazz = Class.forName(SmscMain.getConnectionHandlerClassName());
			StandardConnectionHandler sch = (StandardConnectionHandler) clazz.newInstance();
			sch.setSs(smppServerSocket);
			connectionHandlers[threadIndex] = sch;
			smppThread[threadIndex] = new Thread(
					connectionHandlers[threadIndex], "SCH" + threadIndex);
			smppThread[threadIndex].start();
			threadIndex++;
		}
		
		// 启动deliverMessage机制
		if (SmscMain.getDeliverMessagesPerMin() != 0) {
			ds = new MoService(SmscMain.getDeliverFile(), SmscMain.getDeliverMessagesPerMin());
		}

		// InboundQueue must always be running to allow for MO messages injected
		// via web interface
		inboundQueueService = new Thread(iq);
		inboundQueueService.start();

		// LifeCycleService (OutboundQueue) must always be running
		lifecycleService = new Thread(oq);
		lifecycleService.start();
		
		 /**
         * 设置deliver_start按钮关闭
         */
        Properties propsStart = new Properties();
		InputStream isStart = new FileInputStream("conf/deliverStart.props");
		propsStart.load(isStart);
        isStart.close();
        FileOutputStream oFileStart = new FileOutputStream("conf/deliverStart.props");
        propsStart.setProperty("deliverStart", "FALSE");
        propsStart.store(oFileStart, "The New properties file");
        oFileStart.close();
		
		 /**
         * 设置deliver_stop按钮关闭
         */
        Properties propsStop = new Properties();
		InputStream isStop = new FileInputStream("conf/deliverStop.props");
		propsStop.load(isStop);
		isStop.close();
		FileOutputStream oFileStop = new FileOutputStream("conf/deliverStop.props");
		propsStop.setProperty("deliverStop", "FALSE");
		propsStop.store(oFileStop, "The New properties file");
		oFileStop.close();
		
		/**
		 * 开启开闭开关
		 */
		deliverStart = DeliverServiceStart.getInstance();
		deliverServiceStart = new Thread(deliverStart);
		deliverServiceStart.start();
		
		deliverStop = DeliverServiceStop.getInstance();
		deliverServiceStop = new Thread(deliverStop);
		deliverServiceStop.start();
		
		// 启动延迟消息队列
		if (SmscMain.getDelayReceiptsBy() > 0) {
			logger.info("Starting delivery receipts delay service....");
			drq = DelayedDrQueue.getInstance();
			Thread t = new Thread(drq);
			t.start();
		}
	}

	public synchronized static String getMessageID() {
		long msgID = messageId++;
		String msgIDstr = SmscMain.getMidPrefix()+Long.toString(msgID);
		return msgIDstr;
	}

	public static int getNextSequence_No() {
		sequence_no++;
		return sequence_no;
	}

	public synchronized void receiverUnbound() {
		SmscMain.decrementBoundReceiverCount();
		SmscMain.showReceiverCount();
		if (SmscMain.getBoundReceiverCount() == 0) {
			stopMoService();
		}
	}

	public int getReceiverBoundCount() {
		return SmscMain.getBoundReceiverCount();
	}

	public StandardConnectionHandler selectReceiver(String address) {
		boolean gotReceiver = false;
		int receiversChecked = 0;
		logger.debug("Smsc: selectReceiver");
		do {
			receiverIndex = getNextReceiverIndex();
			if ((connectionHandlers[receiverIndex].isBound())
					&& (connectionHandlers[receiverIndex].isReceiver())
					&& (connectionHandlers[receiverIndex]
							.addressIsServicedByReceiver(address))) {
				gotReceiver = true;
			} else {
				receiversChecked++;
			}
		} while ((!gotReceiver)
				&& (receiversChecked <= SmscMain.getMaxConnectionHandlers()));

		logger.debug("Smsc: Using SMPPReceiver object #" + receiverIndex);
		if (gotReceiver) {
			return connectionHandlers[receiverIndex];
		} else {
			logger.error("Smsc: No receiver for message address to "+ address);
			return null;
		}
	}

	public synchronized void prepareDeliveryReceipt(SubmitSM smppmsg, String messageID, byte state, int sub, int dlvrd, int err) {
		int esm_class=4;
		if (state == PduConstants.ENROUTE) {
			esm_class = 32;
		}
		DeliveryReceipt receipt = new DeliveryReceipt(smppmsg,esm_class,messageID,state);
		Date rightNow = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		String dateAsString = df.format(rightNow);
		receipt.setMessage_id(messageID);
		String s = "000" + sub;
		int l = s.length();
		receipt.setSub(s.substring(l - 3, l));
		s = "000" + dlvrd;
		l = s.length();
		receipt.setDlvrd(s.substring(l - 3, l));
		receipt.setSubmit_date(dateAsString);
		receipt.setDone_date(dateAsString);
		String err_string = "000" + err;
		err_string = err_string.substring(err_string.length()-3,err_string.length());
		receipt.setErr(err_string);
		
		if (SmscMain.isDlr_tlr_required()) {
			receipt.addVsop(SmscMain.getDlr_tlv_tag(), SmscMain.getDlr_tlv_len(), SmscMain.getDlr_tlv_value());
		}
		
		logger.debug("sm_len=" + smppmsg.getSm_length() + ",message="
				+ smppmsg.getShort_message());
		if (smppmsg.getSm_length() > 19)
			receipt.setText(new String(smppmsg.getShort_message(),0, 20));
		else
			if (smppmsg.getSm_length() > 0)
				receipt.setText(new String(smppmsg.getShort_message(),0,
						smppmsg.getSm_length()));
		receipt.setDeliveryReceiptMessage(state);
		try {
			if (SmscMain.getDelayReceiptsBy() <= 0) {
				iq.addMessage(receipt);
			} else {
				drq.delayDeliveryReceipt(receipt);
			}
		} catch (InboundQueueFullException e) {
			//logger.error("Failed to create delivery receipt because the Inbound Queue is full");
		}
	}

	private synchronized int getNextReceiverIndex() {
		if (receiverIndex == (SmscMain.getMaxConnectionHandlers() - 1)) {
			receiverIndex = 0;
		} else {
			receiverIndex++;
		}
		return receiverIndex;
	}

	public synchronized void runningMoService() {
		SmscMain.incrementBoundReceiverCount();
		SmscMain.showReceiverCount();
		if (SmscMain.getDeliverMessagesPerMin() > 0) {
			deliveryService = new Thread(ds, "MO");
			deliveryService.start();
		}
	}
	
	public void startMoService() {
		if (ds != null) {
			if (!ds.moServiceRunning) {
				logger.info("Starting MO service");
				ds.moServiceRunning = true;
			}
		}
	}

	public void stopMoService() {
		if (ds != null) {
			if (ds.moServiceRunning) {
				logger.info("Stopping MO service");
				ds.moServiceRunning = false;
			}
		}
	}
}