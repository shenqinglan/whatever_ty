package com.whty.efs.webservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.whty.efs.common.constant.Constant;
import com.whty.efs.common.spring.SpringContextHolder;
import com.whty.efs.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.efs.common.util.Encryptor;
import com.whty.efs.packets.interfaces.WSMsgHandler;
import com.whty.efs.packets.message.EuiccEntity;
import com.whty.efs.packets.message.Header;
import com.whty.efs.packets.message.request.RequestMsgBody;
import com.whty.efs.webservice.message.RspnMsg;
import com.whty.efs.webservice.message.parse.HeaderManager;

/**
 * web service逻辑处理帮助类
 * @author gaof
 *
 */
public class WSServiceHelper {

	private static Logger logger = LoggerFactory
			.getLogger(WSServiceHelper.class);

	protected static final Gson gson = new Gson();

	public static String  getResponse(RequestMsgBody tsmRequest) throws Exception {
		EuiccEntity<RequestMsgBody> tsm = new EuiccEntity<RequestMsgBody>();
		// 1.获取加密因子
		String secureFactor = SpringPropertyPlaceholderConfigurer
				.getStringProperty("secureFactor");
		//logger.debug("加密因子:{}", secureFactor);
		// 2.获取报文头
		Header header = HeaderManager.getHeader();
		if (null == header) {
			logger.error("报文头数据异常");
			throw new Exception("报文头数据异常");
		}
		tsm.setHeader(header);

		// 3.将报文体转换为我们的bean
		tsm.setBody(tsmRequest);

		// 4.交由service转发到tathing tsm
		byte[] returnStr = null;
		// 获取报文类型
		StringBuilder beanName = new StringBuilder(Constant.Plugin.WEB_SERVICE);
//		beanName.append("_");
		/* 可单独处理每个接口，也可以统一处理
		 *
		 * String type = header.getMsgType(); 
		 * beanName.append(type);
		 */
//		String type = header.getMsgType();
//		beanName.append(type);
		//logger.debug("业务处理类型:{}", beanName.toString());
		// 消息处理
		WSMsgHandler msgHandler = (WSMsgHandler) SpringContextHolder
				.getBean(beanName.toString());
		returnStr = msgHandler.handler(tsm);
		
		if(null == returnStr){
			logger.error("业务处理异常");
			throw new Exception("业务处理异常");
		}
//		byte[] enData = new Encryptor().encrypt(secureFactor.getBytes(), returnStr);
		// 5.解密json
//		JsonReader reader = new JsonReader(new StringReader(new String(enData,
//				Constant.Common.ENCODE_UTF8)));
		byte [] enData=new Encryptor().decrypt(secureFactor.getBytes(), returnStr);
		//logger.debug("核心返回的明文:{}",new String(enData,Constant.Common.ENCODE_UTF8));
//		JsonReader reader = new JsonReader(new StringReader(new String(enData,
//				Constant.Common.ENCODE_UTF8)));
//		reader.setLenient(true);
//		// 6.将返回json反序列化为业务实体
//		EuiccEntity<ResponseMsgBody> tsm1 = gson.fromJson(reader,
//				new TypeToken<EuiccEntity<ResponseMsgBody>>() {
//				}.getType());
//		// 7.使用json复制属性，不使用BeanUtils.copyProperties
//		String tsmJson = gson.toJson((ResponseMsgBody) tsm1.getBody());
//		wsResponse = gson.fromJson(tsmJson, wsResponse.getClass());

		// 8.返回结果
		String wsResponse=new String(enData,Constant.Common.ENCODE_UTF8);
		return wsResponse;
	}

	/**
	 * 获取错误信息
	 * @param message 错误信息
	 * @return
	 */
	public static RspnMsg getMsg(String message) {
		RspnMsg msg = new RspnMsg();
		msg.setSts("0001");
		msg.setRjctCd("B0001");
		msg.setRjctInfo(message);
		msg.setEndFlag("01");
		return msg;
	}
}
