package com.whty.mqtt;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName MQTTClientSubscribe
 * @author Administrator
 * @Description TODO(阻塞式订阅消息)
 */
public class BlockSubscribe {

    private static final Logger logger = LoggerFactory.getLogger(BlockSubscribe.class);
    private final static String CONNECTION_STRING = "tcp://118.178.95.186:1883";
    private final static boolean CLEAN_START = true;
    private final static short KEEP_ALIVE = 30;// 低耗网络，但是又需要及时获取数据，心跳30s
    private final static String CLIENT_ID = "subscribeClient";
    public static Topic[] topics = {
    	new Topic("87d5bd3767e841799a2de1501a634230/v1/ap/ffff0048/heartbeat", QoS.AT_LEAST_ONCE),
    };
    
    public final static long RECONNECTION_ATTEMPT_MAX = 6;
    public final static long RECONNECTION_DELAY = 2000;
    public final static int SEND_BUFFER_SIZE = 2 * 1024 * 1024;// 发送最大缓冲为2M

    public static void main(String[] args) {
    	
        // 创建MQTT对象
        MQTT mqtt = new MQTT();
        BlockingConnection connection = null;
        try {
            // 设置mqtt broker的ip和端口
            mqtt.setHost(CONNECTION_STRING);
            // 连接前清空会话信息
            mqtt.setCleanSession(CLEAN_START);
            // 设置重新连接的次数
            mqtt.setReconnectAttemptsMax(RECONNECTION_ATTEMPT_MAX);
            // 设置重连的间隔时间
            mqtt.setReconnectDelay(RECONNECTION_DELAY);
            // 设置心跳时间
            mqtt.setKeepAlive(KEEP_ALIVE);
            // 设置缓冲的大小
            mqtt.setSendBufferSize(SEND_BUFFER_SIZE);
            
            mqtt.setClientId(CLIENT_ID);
            
            mqtt.setUserName("87d5bd3767e841799a2de1501a634230");// 服务器认证用户名
			mqtt.setPassword("100a713c3a2c4690bf36545ba1a3fa61");// 服务器认证密码

            // 获取mqtt的连接对象BlockingConnection
            connection = mqtt.blockingConnection();
            // MQTT连接的创建
            connection.connect();
            
            // 订阅相关的主题信息
            byte[] qoses = connection.subscribe(topics);
            while (true) {
                // 接收订阅的消息内容
                Message message = connection.receive();
                // 获取订阅的消息内容
                byte[] payload = message.getPayload();
                logger.info("MqttClient Message Topic=" + message.getTopic() + " Content :" + new String(payload));
                // 签收消息的回执
                message.ack();
            }
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

