package com.thinkgem.ga.device;
/**
 *
 * Description:
 * @author admin
 * 2017年2月10日下午18:04:07
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import com.alibaba.fastjson.JSON;
import com.thinkgem.ga.dao.GaEntranceInfoDao;
import com.thinkgem.ga.dao.GaDeviceApDao;
import com.thinkgem.ga.dao.GaDeviceMoteDao;
import com.thinkgem.ga.dao.GaDeviceMsDao;
import com.thinkgem.ga.dao.IdNoInfoDao;
import com.thinkgem.ga.entity.GaEntranceInfo;
import com.thinkgem.ga.entity.GaDeviceAp;
import com.thinkgem.ga.entity.GaDeviceMote;
import com.thinkgem.ga.entity.GaDeviceMs;
import com.thinkgem.ga.entity.IdNoInfo;
import com.thinkgem.ga.msg.biz.MSReportInfo;
import com.thinkgem.ga.msg.biz.MsgParam;
import com.thinkgem.ga.msg.device.HeartBeatInfo;  

/**
 * 发布消息的回调类
 *
 * 必须实现MqttCallback的接口并实现对应的相关接口方法CallBack 类将实现 MqttCallBack。
 * 每个客户机标识都需要一个回调实例。在此示例中，构造函数传递客户机标识以另存为实例数据。
 * 在回调中，将它用来标识已经启动了该回调的哪个实例。
 * 必须在回调类中实现三个方法：
 *
 *  public void messageArrived(MqttTopic topic, MqttMessage message)接收已经预订的发布。
 *
 *  public void connectionLost(Throwable cause)在断开连接时调用。
 *
 *  public void deliveryComplete(MqttDeliveryToken token))
 *  接收到已经发布的 QoS 1 或 QoS 2 消息的传递令牌时调用。
 *  由 MqttClient.connect 激活此回调。
 *
 */
public class GADevicePushCallback implements MqttCallback {

	public void connectionLost(Throwable cause) {
		// 连接丢失后，一般在这里面进行重连
		System.out.println("连接断开，可以做重连");
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("deliveryComplete---------" + token.isComplete());
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// subscribe后得到的消息会执行到这里面
		System.out.println("接收消息主题 : " + topic);
		//System.out.println("接收消息Qos : " + message.getQos());
		System.out.println("接收消息内容 : " + new String(message.getPayload()));

		try {
			String[] arr = topic.split("/");
			String type = arr[2];
			String no = arr[3];
			if ("ap".equals(type)) {
				GaDeviceAp device = new GaDeviceAp();
				device.setDeviceId(no);
				GaDeviceApDao dao = new GaDeviceApDao();
				GaDeviceAp r = dao.select(device);
				if (r == null) {
					GaDeviceAp in = new GaDeviceAp();
					in.setId(UUID.randomUUID().toString().replaceAll("-", ""));
					in.setDeviceId(no);
					in.setStatus("1");
					dao.insert(in);
				} else {
					GaDeviceAp up = new GaDeviceAp();
					up.setId(r.getId());
					up.setStatus("1");
					dao.update(up);
				}
			} else if ("mote".equals(type)) {
				GaDeviceMote device = new GaDeviceMote();
				device.setDeviceId(no);
				GaDeviceMoteDao dao = new GaDeviceMoteDao();
				GaDeviceMote r = dao.select(device);
				if (r == null) {
					GaDeviceMote in = new GaDeviceMote();
					in.setId(UUID.randomUUID().toString().replaceAll("-", ""));
					in.setDeviceId(no);
					in.setStatus("1");
					dao.insert(in);
				} else {
					GaDeviceMote up = new GaDeviceMote();
					up.setId(r.getId());
					up.setStatus("1");
					dao.update(up);
				}
			} else if ("ms".equals(type)) {
				GaDeviceMs device = new GaDeviceMs();
				device.setDeviceId(no);
				GaDeviceMsDao dao = new GaDeviceMsDao();
				GaDeviceMs r = dao.select(device);
				if (r == null) {
					HeartBeatInfo h = JSON.parseObject(new String(message.getPayload()), HeartBeatInfo.class);
					String apNo = h.getApUid();
					GaDeviceAp aps = new GaDeviceAp();
					aps.setDeviceId(apNo);
					GaDeviceApDao apDao = new GaDeviceApDao();
					GaDeviceAp apr = apDao.select(aps);
					GaDeviceMs in = new GaDeviceMs();
					in.setId(UUID.randomUUID().toString().replaceAll("-", ""));
					in.setApId(apr.getId());
					in.setDeviceId(no);
					in.setStatus("1");
					dao.insert(in);
				} else {
					GaDeviceMs up = new GaDeviceMs();
					up.setId(r.getId());
					up.setStatus("1");
					dao.update(up);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	    public static void main(String args[]) {
	    	GADevicePushCallback p = new GADevicePushCallback();
	    	MqttMessage message = new MqttMessage();
	    	message.setPayload("{    \"msgDirect\": \"report\",    \"msgPriority\": \"normal\",    \"msgType\": \"real\",    \"msgId\": \"0\",    \"apTime\": \"1488172663\",    \"msgEncrypt\": \"none\",    \"msgCmd\": \"ms\",    \"msgUid\": \"ffff0048\",    \"apUid\": \"ffff0048\",    \"msgParam\": {        \"subCmd\": \"report\",        \"subType\": \"heartbeat\",        \"msUid\": \"4f004802\",        \"battery\": \"201\",        \"downRssi\": \"30\",        \"upRssi\": \"30\",        \"downMode\": \"sniff-off\"    }}".getBytes());
	    	try {
				p.messageArrived("87d5bd3767e841799a2de1501a634230/v1/ms/4f004802/reportheartbeat", message);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
}