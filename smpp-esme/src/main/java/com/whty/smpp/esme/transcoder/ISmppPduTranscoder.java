/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2017-1-20
 * Id: SmppPduTranscoder.java,v 1.0 2017-1-20 上午9:18:26 Administrator
 */
package com.whty.smpp.esme.transcoder;

import org.jboss.netty.buffer.ChannelBuffer;

import com.whty.smpp.esme.message.Pdu;

/**
 * @ClassName SmppPduTranscoder
 * @author Administrator
 * @date 2017-1-20 上午9:18:26
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface ISmppPduTranscoder {

	// 对pdu消息信息进行编码
	public ChannelBuffer encode(Pdu pdu);

	// 对buffer进行解码获取pdu消息
	public Pdu decode(ChannelBuffer buffer);
}
