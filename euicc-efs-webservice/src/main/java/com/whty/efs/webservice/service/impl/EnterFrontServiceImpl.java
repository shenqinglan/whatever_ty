package com.whty.efs.webservice.service.impl;


import java.io.StringReader;

import javax.jws.HandlerChain;
import javax.jws.WebService;

import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.whty.efs.packets.message.EuiccEntity;
import com.whty.efs.packets.message.request.AppPersonialBody;
import com.whty.efs.packets.message.request.AppQueryBody;
import com.whty.efs.packets.message.response.AppPersonialResp;
import com.whty.efs.packets.message.response.AppQueryResp;
import com.whty.efs.webservice.message.AppPersonialRequest;
import com.whty.efs.webservice.message.AppPersonialResponse;
import com.whty.efs.webservice.message.AppQueryRequest;
import com.whty.efs.webservice.message.AppQueryResponse;
import com.whty.efs.webservice.message.IEnterFrontService;

/**
 * webservice服务实现
 * @since 1.0.0
 * 
 */
@WebService(endpointInterface="com.whty.efs.webservice.message.IEnterFrontService",
		targetNamespace="http://www.tathing.com",
		portName="EnterFrontServicePort",
		wsdlLocation="ws/enterfront.wsdl",
		serviceName="EnterFrontService")
@HandlerChain(file="/ws/chain/handler-chain.xml")
@InInterceptors(interceptors="org.apache.cxf.interceptor.LoggingInInterceptor")
@OutInterceptors(interceptors="com.whty.efs.webservice.interceptor.ArtifactOutInterceptor")
@Component
public class EnterFrontServiceImpl implements IEnterFrontService{
	
	private static  Logger logger = LoggerFactory.getLogger(EnterFrontServiceImpl.class);
	
	protected static final Gson gson = new Gson();
	
	@Override
	public AppQueryResponse appQuery(AppQueryRequest appQueryRequest) {
			AppQueryResponse responese = new AppQueryResponse();
			AppQueryBody body=new AppQueryBody();
			try {
				String reqjson=gson.toJson(appQueryRequest);
				System.out.println(reqjson);
				body=gson.fromJson(reqjson, AppQueryBody.class);
				String obj = null;//WSServiceHelper.getResponse( body);
				if (null == obj){
					responese.setRspnMsg(WSServiceHelper.getMsg(""));
					responese.setAppAID("01");
					responese.setAppDesc("02");
					responese.setAppName("03");
					responese.setAppType("04");
					responese.setAppVersion("05");
					responese.setIcoUrl("06");
					responese.setLogoUrl("07");
					responese.setProviderID("08");
					
				} else {
					JsonReader reader=new JsonReader(new StringReader(obj));
					reader.setLenient(true);
					EuiccEntity<AppQueryResp> tsm1  =  gson.fromJson(reader,new TypeToken<EuiccEntity<AppQueryResp>>() {}.getType());
					String tsmJson = gson.toJson((AppQueryResp)tsm1.getBody());
					responese = gson.fromJson(tsmJson, AppQueryResponse.class);
					}
				} catch (Exception e) {
					logger.error("业务异常", e);
					responese.setRspnMsg(WSServiceHelper.getMsg(e.getMessage()));
				}
			/*	EuiccEntity<AppQueryBody> tsm  = new EuiccEntity<AppQueryBody>();
		AppQueryBody body = new AppQueryBody();
		RspnMsg msg = new RspnMsg();
		// 获取加密因子
		String secureFactor = SpringPropertyPlaceholderConfigurer.getStringProperty("secureFactor");
		try {
			// 获取报文头
			Header header = HeaderManager.getHeader();
			if(null == header){
				responese.setRspnMsg(getMsg());
				return responese;
			}
			tsm.setHeader(header);
			// 将报文体转换为我们的bean
			BeanUtils.copyProperties(body, appQueryRequest);
			tsm.setBody(body);
			// 交由service转发到tathing tsm
			byte[] returnStr = appQueryService.AppQueryRequest(tsm,2);
			byte[] enData = Encryptor.decrypt(secureFactor.getBytes(), returnStr);
			// 解密json
			JsonReader reader = new JsonReader(new StringReader(new String( enData, ENCODE)));
			reader.setLenient(true);
			// 将返回json反序列化为业务实体
			EuiccEntity<AppQueryResp> tsm1  =  gson.fromJson(reader,new TypeToken<EuiccEntity<AppQueryResp>>() {}.getType());
			// 使用json复制属性，不使用BeanUtils.copyProperties
			String tsmJson = gson.toJson((AppQueryResp)tsm1.getBody());
			responese = gson.fromJson(tsmJson, AppQueryResponse.class);
			//BeanUtils.copyProperties(responese, (AppQueryResp)tsm1.getBody());
		} catch (Exception e) {
			e.printStackTrace();
			msg.setSts("0001");
			msg.setRjctCd("B0001");
			msg.setRjctInfo("appQuery数据出错");
			msg.setEndFlag("01");
			responese.setRspnMsg(msg);
		}*/
		return responese;
	}

	

	
	@Override
	public AppPersonialResponse appPersonial(
			AppPersonialRequest appPersonialRequest) {
		AppPersonialResponse responese = new AppPersonialResponse();
		AppPersonialBody body=new AppPersonialBody();
		try {
			String reqjson=gson.toJson(appPersonialRequest);
			body=gson.fromJson(reqjson, AppPersonialBody.class);
			String obj=WSServiceHelper.getResponse( body);
			if (null==obj) {
				responese.setRspnMsg(WSServiceHelper.getMsg(""));
			}else {
				JsonReader reader = new JsonReader(new StringReader(new String( obj)));
				reader.setLenient(true);
				// 将返回json反序列化为业务实体
				EuiccEntity<AppPersonialResp> tsm1  =  gson.fromJson(reader,new TypeToken<EuiccEntity<AppPersonialResp>>() {}.getType());
				// 使用json复制属性，不使用BeanUtils.copyProperties
				String tsmJson = gson.toJson((AppPersonialResp)tsm1.getBody());
				responese = gson.fromJson(tsmJson, AppPersonialResponse.class);
			}
		} catch (Exception e) {
			logger.error("业务异常", e);
			responese.setRspnMsg(WSServiceHelper.getMsg(e.getMessage()));
		}
/*		EuiccEntity<AppPersonialBody> tsm  = new EuiccEntity<AppPersonialBody>();
		AppPersonialBody body = new AppPersonialBody();
		RspnMsg msg = new RspnMsg();
		// 获取加密因子
		String secureFactor = SpringPropertyPlaceholderConfigurer.getStringProperty("secureFactor");
		try {
			// 获取报文头
			Header header = HeaderManager.getHeader();
			if(null == header){
				responese.setRspnMsg(getMsg());
				return responese;
			}
			tsm.setHeader(header);
			// 将报文体转换为我们的bean
			String tsmJson1 = gson.toJson(appPersonialRequest);
			body = gson.fromJson(tsmJson1, AppPersonialBody.class);
			tsm.setBody(body);
			// 交由service转发到tathing tsm
			byte[] returnStr = appPersonialService.AppPersonialRequest(tsm,secureFactor,Constant.Plugin.THIRD_2_TATHING);
			byte[] enData = new DefaultEncoder().handler(returnStr, Constant.Common.DEFAULT_SECURITY_NAME, secureFactor, false, "");
			// 解密json
			JsonReader reader = new JsonReader(new StringReader(new String( enData, Constant.Common.ENCODE_UTF8)));
			reader.setLenient(true);
			// 将返回json反序列化为业务实体
			EuiccEntity<AppPersonialResp> tsm1  =  gson.fromJson(reader,new TypeToken<EuiccEntity<AppPersonialResp>>() {}.getType());
			// 使用json复制属性，不使用BeanUtils.copyProperties
			String tsmJson = gson.toJson((AppPersonialResp)tsm1.getBody());
			responese = gson.fromJson(tsmJson, AppPersonialResponse.class);
			//BeanUtils.copyProperties(responese, (AppPersonialResp)tsm1.getBody());
		} catch (Exception e) {
			e.printStackTrace();
			msg.setSts("0001");
			msg.setRjctCd("B0001");
			msg.setRjctInfo("appPersonial数据出错");
			msg.setEndFlag("01");
			responese.setRspnMsg(msg);
		}*/
		return responese;
	}









	
	
}