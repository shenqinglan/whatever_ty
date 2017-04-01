package com.whty.euicc.tls.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.Key;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.utils.TlsMessageUtils;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.tls.SocketUtils;

public class HttpsShakeHandsServerCard extends AbstractShakeHandsServer{
	InputStream inCard;
    OutputStream outCard;
    String hash;
    Key key;
    ExecutorService executorService= Executors.newFixedThreadPool(20);

    final static Logger logger = LoggerFactory.getLogger(HttpsShakeHandsServerCard.class);
	public  STlsHankUtils doHttpsShakeHands(Socket s) throws Exception {
		logger.info("实网环境与卡片对接握手");
		inCard=s.getInputStream();
		outCard=s.getOutputStream();

		//第一步 获取客户端发送clientHello做解析
		byte[] clientSupportHash=SocketUtils.readBytes(inCard);
		String clientHash=SocketUtils.hexByteToString(clientSupportHash);
		logger.debug("clientHash : {}", clientHash);
		STlsHankUtils sTls = new STlsHankUtils();
		sTls.clientHelloPaser(clientHash); 
       
		//第二步 发送serverHello
		String strServerHello = sTls.serverHelloSend();
	    SocketUtils.writeBytes(outCard, SocketUtils.hexToBuffer(strServerHello));
       
	    //第三步 发送serverHelloDoneSend
	    String strServerHelloDone = sTls.serverHelloDoneSend();
	    SocketUtils.writeBytes(outCard, SocketUtils.hexToBuffer(strServerHelloDone));
	  
	    //第四步 收到clientKeyExchange
		byte[] clientKeyExchange=SocketUtils.readBytes(inCard);
		String strclientKeyExchange=SocketUtils.hexByteToString(clientKeyExchange);
		sTls.clientKeyExchangePaser(strclientKeyExchange);
       
		//第五步 收到clientChangeCipherSpec
		byte[] clientChangeCipherSpec=SocketUtils.readBytes(inCard);
		String strclientChangeCipherSpec=SocketUtils.hexByteToString(clientChangeCipherSpec);
		sTls.clientChangeCipherSpecPaser(strclientChangeCipherSpec);
    
		//第六步 收到ClientFinish
		byte[] ClientFinish=SocketUtils.readBytes(inCard);
		String strClientFinish=SocketUtils.hexByteToString(ClientFinish);
		sTls.clientFinishedPaser(strClientFinish);
       
		//第七步 发送serverChangeCipherSpec
		String serverChangeCipherSpec = sTls.serverChangeCipherSpecSend();
	    SocketUtils.writeBytes(outCard, SocketUtils.hexToBuffer(serverChangeCipherSpec));
	    
	    //第八步 发送serverChangeCipherSpec
	    String serverFinishedSend = sTls.serverFinishedSend();
	    SocketUtils.writeBytes(outCard, SocketUtils.hexToBuffer(serverFinishedSend));
	    
	    System.out.println("*************end*************");
	    return sTls; 
   }
   
   public  void doSocketTransport(Socket s,AbstractHandler handler,STlsHankUtils sTls) throws IOException{  
   	//STlsHankUtils sTls = new STlsHankUtils();
       //第九步，收到HTTP POST Request
		String request = getHttpPostRequest(s,sTls);
       
       byte[] sendMsg = handler.handle(request);
       
       //第十步 发送HTTP POST Response
       //String httpPostResponse = sTls.httpPostResponse(new String(sendMsg));
       String httpPostResponse = sTls.httpPostResponse(sendMsg);
       writeHttpPostResponse(s,SocketUtils.hexToBuffer(httpPostResponse));
	    
       //第十一步 收到响应
       String cardRespMsg = getHttpPostRequest(s,sTls);   
       
       
       boolean checkFlag  = handler.checkProcessResp(cardRespMsg);
       
       moreRequest(s,handler, sTls, cardRespMsg, checkFlag);
       
       //第十二步步 发送final
       byte[] httpFinal = handler.httpPostResponseFinalUnEnc(checkFlag);
       String httpPostResponseFinal = sTls.httpPostResponse(httpFinal);
       writeHttpPostResponse(s,SocketUtils.hexToBuffer(httpPostResponseFinal));  
   }
   
   public  void doSocketTransportForRsp(Socket s,AbstractHandler handler,STlsHankUtils sTls) throws IOException{  
	   String request = getHttpPostRequestByRsp(s,sTls);
       
       byte[] sendMsg = handler.handle(request);
       
       //第十步 发送HTTP POST Response
       byte[] httpPostResponse = sTls.httpPostResponseByRsp(sendMsg);
       writeHttpPostResponse(s,httpPostResponse);
   }


	private  boolean moreRequest(Socket s,AbstractHandler handler,
			STlsHankUtils sTls, String cardRespMsg, boolean checkFlag)
			throws IOException {
		if(!checkFlag){
			return false;
		}
		
		String eid = TlsMessageUtils.getEid(cardRespMsg);
		String smsTrigger = CacheUtil.getString(eid);//eid
		if(StringUtils.isEmpty(smsTrigger)){
			return true;
		}
       
		SmsTrigger eventTrigger = new Gson().fromJson(smsTrigger,SmsTrigger.class);
		int eventStep = eventTrigger.getStep();
		int allStep = eventTrigger.getAllStep();
		if(eventTrigger == null || eventStep == 0){
			return true;
		}
   	
   		allStep= allStep > eventStep ? allStep : eventStep;
   	
		for(int step = 0;step < allStep;step++){
			byte[] sendMsgStep = handler.handle(cardRespMsg);
			//第十步 发送HTTP POST Response
			//String httpPostResponseStep = sTls.httpPostResponse(new String(sendMsgStep));
			String httpPostResponseStep = sTls.httpPostResponse(sendMsgStep);
			writeHttpPostResponse(s,SocketUtils.hexToBuffer(httpPostResponseStep));
			//第十一步 收到响应
			String cardRespMsgStep = getHttpPostRequest(s,sTls);   
			boolean checkFlagStep  = handler.checkProcessResp(cardRespMsgStep);
			if(!checkFlagStep){
				return false;
			}
			cardRespMsg = cardRespMsgStep;
		}
		
		return true;
	}

	private  void writeHttpPostResponse(Socket s,byte[] httpPostResponse)
			throws IOException {
		outCard=s.getOutputStream();
	    SocketUtils.writeBytes(outCard, httpPostResponse);
	}


	private  String getHttpPostRequest(Socket s,STlsHankUtils sTls)
			throws IOException {
		inCard=s.getInputStream();
		byte[] postRequest=SocketUtils.readBytes(inCard);
		String strpostRequest=SocketUtils.hexByteToString(postRequest);
		logger.debug("收到卡片第一次post信息 : {}", strpostRequest);
		return sTls.postRequestPaser(strpostRequest);
	}
	
	private  String getHttpPostRequestByRsp(Socket s,STlsHankUtils sTls)
			throws IOException {
		inCard=s.getInputStream();
		byte[] postRequest=SocketUtils.readBytes(inCard);
		logger.debug("收到卡片第一次post信息 : {}", new String(postRequest));
		return sTls.postRequestPaserByRsp(postRequest);
	}

	@Override
	public void releaseStream() {
		if(inCard != null)IOUtils.closeQuietly(inCard);
		if(outCard != null)IOUtils.closeQuietly(outCard);
		
	}
}
