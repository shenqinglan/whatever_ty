package com.thinkgem.ga.bizdata;
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
import com.thinkgem.ga.dao.IdNoInfoDao;
import com.thinkgem.ga.entity.GaEntranceInfo;
import com.thinkgem.ga.entity.IdNoInfo;
import com.thinkgem.ga.msg.biz.MSReportInfo;
import com.thinkgem.ga.msg.biz.MsgParam;  

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
public class GADataPushCallback implements MqttCallback {

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
			MSReportInfo m = JSON.parseObject(new String(message.getPayload()), MSReportInfo.class);
			MsgParam mc = m.getMsgParam();
			String data = mc.getData();
			String[] s = Parse.parse(data);
			String gateNo = s[0];
			String cardNo = s[1];
			String swipeTime = s[2];
			String gateId = IdNoInfoDao.select(new IdNoInfo(gateNo), "gate").getId();
			String cardId = IdNoInfoDao.select(new IdNoInfo(cardNo), "card").getId();
			SimpleDateFormat former = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat after = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = former.parse(swipeTime);
			String ds = after.format(d);
			GaEntranceInfo e = new GaEntranceInfo();
			e.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			e.setDoorNo(gateId);
			e.setCardNo(cardId);
			e.setSwipeTime(after.parse(ds));
			GaEntranceInfoDao.save(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	    public static void main(String args[]) {
	    	GADataPushCallback p = new GADataPushCallback();
	    	MqttMessage message = new MqttMessage();
	    	message.setPayload("{    \"msgDirect\": \"report\",    \"msgPriority\": \"normal\",    \"msgType\": \"real\",    \"msgId\": \"0\",    \"apTime\": \"1488511649\",    \"msgEncrypt\": \"none\",    \"msgCmd\": \"ms\",    \"msgUid\": \"ffff0048\",    \"apUid\": \"ffff0048\",    \"msgParam\": {        \"subCmd\": \"report\",        \"subType\": \"uploadData\",        \"msUid\": \"4f004800\",        \"data\": \"00044f00480001056202097800020707e103030b1b0f\"    }}".getBytes());
	    	try {
				p.messageArrived("", message);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
}