package com.whty.smpp.socket.session;

import java.util.HashMap;
import java.util.Map;

import com.whty.framework.http.HttpUtil;
import com.whty.framework.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.framework.utils.StringUtil;
import com.whty.smpp.socket.Deliver;
import com.whty.smpp.socket.message.DeliverSm;
/**
 * 
 * @ClassName SMPPDeliverImpl
 * @author Administrator
 * @date 2017-3-10 下午1:45:24
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SMPPDeliverImpl implements Deliver {

	@Override
	public void handler(DeliverSm requestPdu) {
		final DeliverSm mo = (DeliverSm) requestPdu;
        System.out.println("receive deliver_sm");
        String receiveSmsUrl = SpringPropertyPlaceholderConfigurer.getStringProperty("receive_sms_url");
		Map<String, String> argsMap = new HashMap<String, String>() {{ 
		    put( "src" , mo.getSourceAddress().getAddress()); 
		    put("msg", StringUtil.bytes2HexString(mo.getShortMessage()));
		}}; 
		String resp = HttpUtil.post(receiveSmsUrl, argsMap);
		System.out.println(resp);
	}
}
