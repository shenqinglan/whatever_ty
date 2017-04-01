package com.whty.efs.webservice.message.parse;

import org.apache.commons.beanutils.Converter;

import com.whty.efs.packets.message.response.RspnMsg;

public class SOAPRspnMsgConvertToEntityRspnMsg implements Converter{


	/**
	 * 将SOAP消息的RspnMsg转换为entity中的RspnMsg Bean
	 */
	@SuppressWarnings("unchecked")
	@Override
	public RspnMsg convert(@SuppressWarnings("rawtypes") Class type, Object value) {
		
		if (value == null) {
			throw new RuntimeException("value is null");
		}
		if (value.getClass() == RspnMsg.class) {
			return (RspnMsg) value;
		}
		if (value.getClass() != com.whty.efs.webservice.message.RspnMsg.class) {
			throw new RuntimeException("type not match");
		}
		com.whty.efs.webservice.message.RspnMsg srcRspnMsg = (com.whty.efs.webservice.message.RspnMsg) value;
		RspnMsg distRspnMsg = new RspnMsg();
		
		distRspnMsg.setSts(srcRspnMsg.getSts());
		distRspnMsg.setRjctCd(srcRspnMsg.getRjctCd());
		distRspnMsg.setRjctInfo(srcRspnMsg.getRjctInfo());
		distRspnMsg.setEndFlag(srcRspnMsg.getEndFlag());
		
		return distRspnMsg;
	}
}
