package com.whty.euicc.dp.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.https.BaseHttp;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.ConnectParaUpdateByDpReqBody;
import com.whty.euicc.packets.message.request.ConnectParaUpdateByHttpsReqBody;
import com.whty.euicc.packets.message.request.GetScp03CounterByHttpsReqBody;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
/**
 * SM-DP更新连接参数的服务业务
 * @author Administrator
 *
 */
@Service("connectParaUpdateByDp")
public class ConnectParaUpdateHandler implements HttpHandler {

	@Override
	public byte[] handle(String requestStr) {
		RespMessage respMessage = new RespMessage("0000","success");
		try {
			EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
			ConnectParaUpdateByDpReqBody reqBody = (ConnectParaUpdateByDpReqBody) reqMsgObject.getBody();
			RespMessage sqCounter = getScp03SqCounter(reqBody);
			String counterString = sqCounter.getData();//+ 1
			String counter="000000"+Integer.toHexString(Integer.parseInt(counterString, 16)+1);//加密计数器增加
			counter=counter.substring((counter.length()-6), counter.length());
			System.out.println("卡片返回的counter值>>>>>>(在DP handler中)" + counterString);
			System.out.println("加1之后的counter值：：：：：：" + counter);
			return connectParaUpdate(reqBody,counter);
		} catch (Exception e) {
			e.printStackTrace();
			//返回失败信息
			if(e instanceof EuiccBusiException){
				EuiccBusiException eb = (EuiccBusiException) e;
				respMessage = new RespMessage(eb.getCode(),eb.getMessage());
			}else{
				respMessage = new RespMessage("9999",e.getMessage());
			}
			return new Gson().toJson(respMessage).getBytes();
		
		}
		
	}
	
	/**
	 * 获取scp03序列计数器
	 * @param reqBody
	 * @return
	 * @throws Exception
	 */
	public RespMessage getScp03SqCounter(ConnectParaUpdateByDpReqBody reqBody)throws Exception{
        MsgHeader header = new MsgHeader("getScp03CounterByHttps");//对应sr-handler
        GetScp03CounterByHttpsReqBody requestBody = new GetScp03CounterByHttpsReqBody();
		requestBody.setEid(reqBody.getEid());
		requestBody.setIccid(reqBody.getIccid());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        return respMessage;
	}
	
	public byte[] connectParaUpdate(ConnectParaUpdateByDpReqBody reqBody,String counterString) throws Exception{
		MsgHeader header = new MsgHeader("connectParaUpdateByHttps");//对应sr-handler
		ConnectParaUpdateByHttpsReqBody requestBody = new ConnectParaUpdateByHttpsReqBody();
		requestBody.setEid(reqBody.getEid());
		requestBody.setIccid(reqBody.getIccid());
		requestBody.setSeqCounter(counterString);
		requestBody.setSmsCenterNo(reqBody.getSmsCenterNo());
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
		if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
		return msgBype;
		

	}
	
	
	

}
