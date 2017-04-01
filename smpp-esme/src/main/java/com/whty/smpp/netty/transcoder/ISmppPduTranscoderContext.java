package com.whty.smpp.netty.transcoder;
/**
 * @ClassName ISmppPduTranscoderContext
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface ISmppPduTranscoderContext {

	// 通过命令状态码查询结果信息
	public String lookupResultMessage(int commandStatus);
	
	// 通过tlv的tag标签查询tag的名称
	public String lookupTlvTagName(short tag);
}
