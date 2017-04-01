package com.whty.euicc.tls.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.Key;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.whty.cache.CacheUtil;
import com.whty.euicc.common.utils.TlsMessageUtils;
import com.whty.euicc.data.pojo.SmsTrigger;
import com.whty.euicc.handler.base.AbstractHandler;
import com.whty.euicc.tls.SocketUtils;


public class HttsShakeHandsServer extends AbstractShakeHandsServer{
	 DataInputStream in;
     DataOutputStream out;
     String hash;
     Key key;
     ExecutorService executorService= Executors.newFixedThreadPool(20);

    public  STlsHankUtils doHttpsShakeHands(Socket s) throws Exception {
    	in=new DataInputStream(s.getInputStream());
    	out=new DataOutputStream(s.getOutputStream());

        //第一步 获取客户端发送clientHello做解析
        int length=in.readInt();
        in.skipBytes(4);
        byte[] clientSupportHash=SocketUtils.readBytes(in,length);
        String clientHash=new String(clientSupportHash);
        STlsHankUtils sTls = new STlsHankUtils();
        sTls.clientHelloPaser(clientHash); 
        
        //第二步 发送serverHello
        String strServerHello = sTls.serverHelloSend();
        length=strServerHello.getBytes().length;
        out.writeInt(length);
	    SocketUtils.writeBytes(out, strServerHello.getBytes(), length);
        
	    //第三步 发送serverHelloDoneSend
	    String strServerHelloDone = sTls.serverHelloDoneSend();
        length=strServerHelloDone.getBytes().length;
        out.writeInt(length);
	    SocketUtils.writeBytes(out, strServerHelloDone.getBytes(), length);
	  
	    //第四步 收到clientKeyExchange
	    length=in.readInt();
        in.skipBytes(4);
        byte[] clientKeyExchange=SocketUtils.readBytes(in,length);
        String strclientKeyExchange=new String(clientKeyExchange);
        sTls.clientKeyExchangePaser(strclientKeyExchange);
        
        //第五步 收到clientChangeCipherSpec
	    length=in.readInt();
        in.skipBytes(4);
        byte[] clientChangeCipherSpec=SocketUtils.readBytes(in,length);
        String strclientChangeCipherSpec=new String(clientChangeCipherSpec);
        sTls.clientChangeCipherSpecPaser(strclientChangeCipherSpec);
     
        //第六步 收到ClientFinish
	    length=in.readInt();
        in.skipBytes(4);
        byte[] ClientFinish=SocketUtils.readBytes(in,length);
        String strClientFinish=new String(ClientFinish);
        sTls.clientFinishedPaser(strClientFinish);
        
        //第七步 发送serverChangeCipherSpec
        String serverChangeCipherSpec = sTls.serverChangeCipherSpecSend();
        length=serverChangeCipherSpec.getBytes().length;
        out.writeInt(length);
	    SocketUtils.writeBytes(out, serverChangeCipherSpec.getBytes(), length);
	    
        //第八步 发送serverChangeCipherSpec
        String serverFinishedSend = sTls.serverFinishedSend();
        length=serverFinishedSend.getBytes().length;
        out.writeInt(length);
	    SocketUtils.writeBytes(out, serverFinishedSend.getBytes(), length);
	    
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
        writeHttpPostResponse(s,httpPostResponse);
	    
        //第十一步 收到响应
        String cardRespMsg = getHttpPostRequest(s,sTls);   
        
        
        boolean checkFlag  = handler.checkProcessResp(cardRespMsg);
        
        moreRequest(s,handler, sTls, cardRespMsg, checkFlag);
        
        //第十二步步 发送final
        byte[] httpFinal = handler.httpPostResponseFinalUnEnc(checkFlag);
        String httpPostResponseFinal = sTls.httpPostResponse(httpFinal);
        writeHttpPostResponse(s,httpPostResponseFinal);
        
        
    }
    
    public  void doSocketTransportForRsp(Socket s,AbstractHandler handler,STlsHankUtils sTls) throws IOException{  
 	    String request = getHttpPostRequestByRsp(s,sTls);
        
        byte[] sendMsg = handler.handle(request);
        
        //第十步 发送HTTP POST Response
        byte[] httpPostResponse = sTls.httpPostResponseByRsp(sendMsg);
        writeHttpPostResponseByRsp(s,httpPostResponse);
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
            writeHttpPostResponse(s,httpPostResponseStep);
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


	private  void writeHttpPostResponse(Socket s,String httpPostResponse)
			throws IOException {
		in=new DataInputStream(s.getInputStream());
		int length=httpPostResponse.getBytes().length;
        out.writeInt(length);
	    SocketUtils.writeBytes(out, httpPostResponse.getBytes(), length);
	}
	
	private  void writeHttpPostResponseByRsp(Socket s,byte[] httpPostResponse)
			throws IOException {
		in=new DataInputStream(s.getInputStream());
		int length=httpPostResponse.length;
        out.writeInt(length);
	    SocketUtils.writeBytes(out, httpPostResponse, length);
	}


	private  String getHttpPostRequest(Socket s,STlsHankUtils sTls)
			throws IOException {
		in=new DataInputStream(s.getInputStream());
		int length=in.readInt();
        in.skipBytes(4);
        byte[] postRequest=SocketUtils.readBytes(in,length);
        String strpostRequest=new String(postRequest);
        return sTls.postRequestPaser(strpostRequest);
	}
	
	private  String getHttpPostRequestByRsp(Socket s,STlsHankUtils sTls)
			throws IOException {
		in=new DataInputStream(s.getInputStream());
		int length=in.readInt();
        in.skipBytes(4);
        byte[] postRequest=SocketUtils.readBytes(in,length);
		return sTls.postRequestPaserByRsp(postRequest);
	}

	

	@Override
	public void releaseStream() {
		// TODO Auto-generated method stub
		
	}
    
    
}
