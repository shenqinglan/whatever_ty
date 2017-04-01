package com.whty.smpp.socket;

import com.whty.smpp.socket.message.DeliverSm;
/**
 * 
 * @ClassName Deliver
 * @author Administrator
 * @date 2017-3-10 下午1:44:44
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface Deliver {

	public void handler(DeliverSm requestPdu);
}
