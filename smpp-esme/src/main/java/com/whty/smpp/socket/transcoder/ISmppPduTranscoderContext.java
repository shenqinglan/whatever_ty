package com.whty.smpp.socket.transcoder;
/**
 * 
 * @ClassName ISmppPduTranscoderContext
 * @author Administrator
 * @date 2017-3-10 下午1:45:17
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public interface ISmppPduTranscoderContext {

	// 通过命令状态码查询结果信息
	public String lookupResultMessage(int commandStatus);
	
	// 通过tlv的tag标签查询tag的名称
	public String lookupTlvTagName(short tag);
}
