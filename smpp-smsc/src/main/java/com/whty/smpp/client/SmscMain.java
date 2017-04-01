package com.whty.smpp.client;

import java.io.File;
import java.util.Timer;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.whty.framework.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.smpp.manager.Counter;
import com.whty.smpp.pdu.PduConstants;
import com.whty.smpp.service.Smsc;
import com.whty.smpp.util.Utilities;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
/**
 * @ClassName SpringContextHolder
 * @author Administrator
 * @date 2017-1-12 上午10:50:38
 * @Description TODO(短信网关客户端与服务端交互)
 */
public class SmscMain {

	private static Logger logger = LoggerFactory.getLogger(SmscMain.class);
	
	private static long startAt = 0;
	private static String midPrefix = "";
	private static final long MINUTE = 1000 * 60;
	private static final long DELAY = 5000;// 5 seconds
	private static final long PERIOD = 5;

	private static int smppPort;
	private static int maxConnectionHandlers;
	private static String smscid;
	private static String[] systemids;
	private static String[] passwords;
	private static String connectionHandlerClassName;
	private static String protocolHandlerClassName;
	private static String lifeCycleManagerClassName;
	private static int deliverMessagesPerMin = 0;
	private static String deliverFile;
	
    private static boolean outbind_enabled;
    private static String esme_ip_address;
    private static int esme_port;
    private static String esme_systemid;
    private static String esme_password;
	
	private static int boundReceiverCount = 0;

	private static int delayed_iqueue_period = 60;
	private static int delayed_inbound_queue_max_attempts;
	private static int messageStateCheckFrequency;
	private static int inbound_queue_capacity;
	private static int outbound_queue_capacity;
	private static long delayReceiptsBy;
	
	private static boolean deliver_sm_includes_ussd_service_op = false;
	
	// PDU Capture
	private static boolean captureSmeBinary;
	private static String captureSmeBinaryToFile;
	private static boolean captureSmppsimBinary;
	private static String captureSmppsimBinaryToFile;
	private static boolean captureSmeDecoded;
	private static String captureSmeDecodedToFile;
	private static boolean captureSmppsimDecoded;
	private static String captureSmppsimDecodedToFile;
	
	private static boolean dlr_tlr_required;
	private static boolean dlr_opt_tlv_required;
	private static short dlr_tlv_tag;
    private static short dlr_tlv_len;
    private static byte[] dlr_tlv_value;

	//定义messageType
	private static int[] messageTypes = { PduConstants.BIND_RECEIVER,
			PduConstants.BIND_RECEIVER_RESP, PduConstants.BIND_TRANSMITTER,
			PduConstants.BIND_TRANSMITTER_RESP, PduConstants.BIND_TRANSCEIVER,
			PduConstants.BIND_TRANSCEIVER_RESP, PduConstants.OUTBIND,
			PduConstants.UNBIND, PduConstants.UNBIND_RESP,
			PduConstants.SUBMIT_SM, PduConstants.SUBMIT_SM_RESP,
			PduConstants.DELIVER_SM, PduConstants.DELIVER_SM_RESP,
			PduConstants.QUERY_SM, PduConstants.QUERY_SM_RESP,
			PduConstants.CANCEL_SM, PduConstants.CANCEL_SM_RESP,
			PduConstants.REPLACE_SM, PduConstants.REPLACE_SM_RESP,
			PduConstants.ENQUIRE_LINK, PduConstants.ENQUIRE_LINK_RESP,
			PduConstants.SUBMIT_MULTI, PduConstants.SUBMIT_MULTI_RESP,
			PduConstants.GENERIC_NAK };
	
	// 提供参数get方法
	public static int getDelayed_iqueue_period() {
		return delayed_iqueue_period;
	}

	public static int getDelayed_inbound_queue_max_attempts() {
		return delayed_inbound_queue_max_attempts;
	}

	public static long getDelayReceiptsBy() {
		return delayReceiptsBy;
	}
	
	public static String getMidPrefix() {
		return midPrefix;
	}
	
	public static boolean isDlr_tlr_required() {
		return dlr_tlr_required;
	}

	public static short getDlr_tlv_tag() {
		return dlr_tlv_tag;
	}

	public static short getDlr_tlv_len() {
		return dlr_tlv_len;
	}

	public static byte[] getDlr_tlv_value() {
		return dlr_tlv_value;
	}

	public static long getStartAt() {
		return startAt;
	}

	public static int getSmppPort() {
		return smppPort;
	}
	
	public static String getEsme_ip_address() {
		return esme_ip_address;
	}

	public static int getEsme_port() {
		return esme_port;
	}

	public static String getEsme_systemid() {
		return esme_systemid;
	}

	public static String getEsme_password() {
		return esme_password;
	}
	
	public static int getMessageStateCheckFrequency() {
		return messageStateCheckFrequency;
	}

	public static boolean isOutbind_enabled() {
		return outbind_enabled;
	}

	public static boolean isDeliver_sm_includes_ussd_service_op() {
		return deliver_sm_includes_ussd_service_op;
	}
	
	public static boolean isDlr_opt_tlv_required() {
		return dlr_opt_tlv_required;
	}

	public static int getBoundReceiverCount()
    {
        return boundReceiverCount;
    }
	
	public static void incrementBoundReceiverCount()
    {
        boundReceiverCount++;
    }

    public static void decrementBoundReceiverCount()
    {
        boundReceiverCount--;
    }
    
    public static void showReceiverCount()
    {
        logger.info(boundReceiverCount + " receivers connected and bound");
    }

	public static int getDeliverMessagesPerMin() {
		return deliverMessagesPerMin;
	}

	public static String getDeliverFile() {
		return deliverFile;
	}

	public static int getMaxConnectionHandlers() {
		return maxConnectionHandlers;
	}

	public static String[] getPasswords() {
		return passwords;
	}

	public static String[] getSystemids() {
		return systemids;
	}

	public static String getConnectionHandlerClassName() {
		return connectionHandlerClassName;
	}

	public static String getProtocolHandlerClassName() {
		return protocolHandlerClassName;
	}

	public static String getLifeCycleManagerClassName() {
		return lifeCycleManagerClassName;
	}

	public static int getInbound_queue_capacity() {
		return inbound_queue_capacity;
	}

	public static int getOutbound_queue_capacity() {
		return outbound_queue_capacity;
	}

	public static boolean isCaptureSmeBinary() {
		return captureSmeBinary;
	}

	public static String getCaptureSmeBinaryToFile() {
		return captureSmeBinaryToFile;
	}

	public static boolean isCaptureSmppsimBinary() {
		return captureSmppsimBinary;
	}

	public static String getCaptureSmppsimBinaryToFile() {
		return captureSmppsimBinaryToFile;
	}

	public static boolean isCaptureSmeDecoded() {
		return captureSmeDecoded;
	}

	public static String getCaptureSmeDecodedToFile() {
		return captureSmeDecodedToFile;
	}

	public static boolean isCaptureSmppsimDecoded() {
		return captureSmppsimDecoded;
	}

	public static String getCaptureSmppsimDecodedToFile() {
		return captureSmppsimDecodedToFile;
	}

	private static final String SMSC_HOME = "SMSC_HOME";

	/**
	* 设置文件路径
	*/
	public static void initHomePath() {
		String path = System.getenv(SMSC_HOME);
		if (null == path) {
			path = System.getProperty(SMSC_HOME);
			if (null == path) {
				path = new File("").getAbsolutePath();
				System.setProperty(SMSC_HOME, path);
			}
		}
		logger.info("SMSC_HOME:\t{}", path);
	}

	/**
	* 获取参数
	*/
	public static void initLoadByStart() {
		String path = System.getProperty(SMSC_HOME);
		initLog(path);
		loadSpringContext(path);
	}

	/**
	* 读取logback.xml配置文件
	*/
	private static void initLog(String path) {
		LoggerContext context = (LoggerContext) LoggerFactory
				.getILoggerFactory();
		try {
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(context);
			context.reset();
			configurator.doConfigure(path + "/conf/logback.xml");
		} catch (JoranException je) {
			je.printStackTrace();
		}
		StatusPrinter.printInCaseOfErrorsOrWarnings(context);
		logger.info("initLog complete.");
	}

	/**
	* 读取smppsim.xml配置文件
	*/
	private static ApplicationContext loadSpringContext(String path) {
		File homeDir = new File(path);
		File root = new File(homeDir, "/conf/smppsim.xml");
		return new FileSystemXmlApplicationContext("file:"
				+ root.getAbsolutePath());
	}

	/**
	* 初始化参数
	*/
	private static void initialise() throws Exception {
		maxConnectionHandlers = Integer
				.parseInt(SpringPropertyPlaceholderConfigurer
						.getStringProperty("SMPP_CONNECTION_HANDLERS"));
		smppPort = Integer.parseInt(SpringPropertyPlaceholderConfigurer
				.getStringProperty("SMPP_PORT"));
		connectionHandlerClassName = SpringPropertyPlaceholderConfigurer
				.getStringProperty("CONNECTION_HANDLER_CLASS");
		protocolHandlerClassName = SpringPropertyPlaceholderConfigurer
				.getStringProperty("PROTOCOL_HANDLER_CLASS");
		lifeCycleManagerClassName = SpringPropertyPlaceholderConfigurer
				.getStringProperty("LIFE_CYCLE_MANAGER");
		String systemidList = SpringPropertyPlaceholderConfigurer
				.getStringProperty("SYSTEM_IDS","");
		String passwordList = SpringPropertyPlaceholderConfigurer
				.getStringProperty("PASSWORDS","");
		systemids = systemidList.split(",");
		passwords = passwordList.split(",");
		
		if (systemids.length != passwords.length) {
			logger.error("Number of SYSTEM_IDS elements is not the same as the number of PASSWORDS elements");
			throw new Exception(
					"Number of SYSTEM_IDS elements is not the same as the number of PASSWORDS elements");
		}
		messageStateCheckFrequency = Integer.parseInt(SpringPropertyPlaceholderConfigurer
				.getStringProperty("messageStateCheckFrequency", "10000"));
        outbind_enabled = Boolean.valueOf(SpringPropertyPlaceholderConfigurer
				.getStringProperty("OUTBIND_ENABLED")).booleanValue();
        if(outbind_enabled)
        {
            esme_ip_address = SpringPropertyPlaceholderConfigurer
    				.getStringProperty("OUTBIND_ESME_IP_ADDRESS", "127.0.0.1");
            String ep = SpringPropertyPlaceholderConfigurer
    				.getStringProperty("OUTBIND_ESME_PORT","2776");
            esme_port = Integer.parseInt(ep);
            
        }
        esme_systemid = SpringPropertyPlaceholderConfigurer.getStringProperty("OUTBIND_ESME_SYSTEMID", "smppclient1");
        esme_password = SpringPropertyPlaceholderConfigurer.getStringProperty("OUTBIND_ESME_PASSWORD", "password");
        
        smscid = SpringPropertyPlaceholderConfigurer
				.getStringProperty("SMSCID");

		// 设置messageId
		String midStart = SpringPropertyPlaceholderConfigurer
				.getStringProperty("START_MESSAGE_ID_AT");
		if (StringUtils.isBlank(midStart)) {
			startAt = 0;

		} else if (StringUtils.equalsIgnoreCase(midStart, "random")) {
			startAt = (long) (Math.random() * 10000000);
		} else {
			startAt = Long.parseLong(midStart);
		}
		
		String strIBQcapacity = StringUtils.defaultIfBlank(
				SpringPropertyPlaceholderConfigurer
						.getStringProperty("INBOUND_QUEUE_MAX_SIZE"), "1000");
		inbound_queue_capacity = Integer.parseInt(strIBQcapacity);
		String strOBQcapacity = StringUtils.defaultIfBlank(
				SpringPropertyPlaceholderConfigurer
						.getStringProperty("OUTBOUND_QUEUE_MAX_SIZE"), "1000");
		outbound_queue_capacity = Integer.parseInt(strOBQcapacity);
		
		midPrefix = SpringPropertyPlaceholderConfigurer
				.getStringProperty("MESSAGE_ID_PREFIX","whty");
		delayed_iqueue_period = 1000 * Integer.parseInt(SpringPropertyPlaceholderConfigurer
				.getStringProperty("DELAYED_INBOUND_QUEUE_PROCESSING_PERIOD","60"));
        delayed_inbound_queue_max_attempts = Integer.parseInt(SpringPropertyPlaceholderConfigurer
				.getStringProperty("DELAYED_INBOUND_QUEUE_MAX_ATTEMPTS","10"));
		delayReceiptsBy = Long.parseLong(SpringPropertyPlaceholderConfigurer
				.getStringProperty("DELAY_DELIVERY_RECEIPTS_BY"));
		deliverMessagesPerMin = Integer
				.parseInt(SpringPropertyPlaceholderConfigurer
						.getStringProperty("DELIVERY_MESSAGES_PER_MINUTE"));
		if (deliverMessagesPerMin > 0) {
			deliverFile = SpringPropertyPlaceholderConfigurer
					.getStringProperty("DELIVER_MESSAGES_FILE");
		}
		
		String dlr_opt_tlv = SpringPropertyPlaceholderConfigurer
				.getStringProperty("DELIVERY_RECEIPT_OPTIONAL_PARAMS");
        if(dlr_opt_tlv == null || dlr_opt_tlv.equals(""))
        {
            dlr_opt_tlv_required = true;
        }
        else
        {
            dlr_opt_tlv_required = Boolean.parseBoolean(dlr_opt_tlv);
        }
		
		deliver_sm_includes_ussd_service_op = Boolean.valueOf(SpringPropertyPlaceholderConfigurer
				.getStringProperty("DELIVER_SM_INCLUDES_USSD_SERVICE_OP")).booleanValue();
	}

	/**
	* main方法
	*/
	public static void main(String[] args) throws Exception {

		System.out.println("smpp-smsc is starting....");
		initHomePath();
		initLoadByStart();
		initialise();

		// 开启一个消息接收计数定时器
		Timer timer = new Timer();
		timer.schedule(new Counter(), DELAY, PERIOD * MINUTE);

		// 获取到SMSC服务器的一个实例
		Smsc smsc = Smsc.getInstance();
		smsc.setInbound_queue_capacity(inbound_queue_capacity);
		smsc.setOutbound_queue_capacity(outbound_queue_capacity);
		Smsc.setSmscId(smscid.getBytes());

        // delivery receipt
        String dlr_tlv = SpringPropertyPlaceholderConfigurer
				.getStringProperty("DELIVERY_RECEIPT_TLV");
        if(StringUtils.isBlank(dlr_tlv))
        {
            dlr_tlr_required = false;
        }
        else
        {
            String tlv_parts[] = dlr_tlv.split("/");
            if(tlv_parts.length != 3)
            {
                logger.error("DELIVERY_RECEIPT_TLV property is in invalid format");
                throw new Exception("DELIVERY_RECEIPT_TLV property is in invalid format");
            }
            else
            {
                String tag_hex = tlv_parts[0];
                String len_hex = tlv_parts[1];
                String value_hex = tlv_parts[2];
                byte[] dlr_tlv_tag_bytes = Utilities.getByteArrayFromHexString(tag_hex);
                dlr_tlv_tag = Utilities.getShortValue(dlr_tlv_tag_bytes, 0, dlr_tlv_tag_bytes.length);
                byte[] dlr_tag_len_bytes = Utilities.getByteArrayFromHexString(len_hex);
                dlr_tlv_len = Utilities.getShortValue(dlr_tag_len_bytes, 0, dlr_tag_len_bytes.length);
                dlr_tlv_value = Utilities.getByteArrayFromHexString(value_hex);
                dlr_tlr_required = true;
            }
        }

		// 开启SMSC服务器
		smsc.start();
	}

}
