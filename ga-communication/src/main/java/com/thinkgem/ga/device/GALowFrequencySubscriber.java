package com.thinkgem.ga.device;
/**
 *
 * Description:
 * @author admin
 * 2017年2月10日下午17:50:15
 */

import java.util.concurrent.ScheduledExecutorService;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.thinkgem.ga.dao.GaDeviceApDao;
import com.thinkgem.ga.dao.GaDeviceMoteDao;
import com.thinkgem.ga.dao.GaDeviceMsDao;
import com.thinkgem.ga.entity.GaDeviceAp;
import com.thinkgem.ga.entity.GaDeviceMote;
import com.thinkgem.ga.entity.GaDeviceMs;

/**
 * 监听类
 * @author liuwsh
 * @version 2017-02-17
 */
public class GALowFrequencySubscriber {

    public static final String HOST = "tcp://118.178.95.186:1883";
    public static final String TOPIC2 = "87d5bd3767e841799a2de1501a634230/v1/ms/+/reportheartbeat";
    private static final String clientid = "client11";
    private MqttClient client;
    private MqttConnectOptions options;
    private String userName = "87d5bd3767e841799a2de1501a634230";
    private String passWord = "100a713c3a2c4690bf36545ba1a3fa61";

    private ScheduledExecutorService scheduler;

    public void start() {
        try {
        	
            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(HOST, clientid, new MemoryPersistence());
            // MQTT的连接设置
            options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            // 设置连接的用户名
            options.setUserName(userName);
            // 设置连接的密码
            options.setPassword(passWord.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
            // 设置回调
            client.setCallback(new GADevicePushCallback());
            client.connect(options);
            String[] s = {TOPIC2};
            int[] i = {1};
            client.subscribe(s, i);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    	GALowFrequencySubscriber g = new GALowFrequencySubscriber();
    	g.start();
    }
    
}
