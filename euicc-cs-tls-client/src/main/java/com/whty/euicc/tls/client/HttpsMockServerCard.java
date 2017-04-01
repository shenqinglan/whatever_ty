package com.whty.euicc.tls.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpsMockServerCard {
    static InputStream inCard;
    static OutputStream outCard;

    public static void doHttpsShakeHands(Socket s) throws Exception {
		inCard=s.getInputStream();
		outCard=s.getOutputStream();

		//第一步 获取客户端发送clientHello做解析
		byte[] clientSupportHash=SocketUtils.readBytes(inCard);
		String clientHash=SocketUtils.hexByteToString(clientSupportHash);
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
	    
        //第九步，收到HTTP POST Request
        byte[] postRequest=SocketUtils.readBytes(inCard);
        String strpostRequest=SocketUtils.hexByteToString(postRequest);
        sTls.postRequestPaser(strpostRequest);
        
        //第十步 发送HTTP POST Response
        String httpPostResponse = sTls.httpPostResponse();
	    SocketUtils.writeBytes(outCard, SocketUtils.hexToBuffer(httpPostResponse));
	    
        //第十一步 收到响应
        byte[] postRequestFinal=SocketUtils.readBytes(inCard);
        String strpostRequestFinal = SocketUtils.hexByteToString(postRequestFinal);
        sTls.postRequestFinal(strpostRequestFinal);    
        
        //第十二步步 发送final
        String httpPostResponseFinal = sTls.httpPostResponseFinal();
	    SocketUtils.writeBytes(outCard, SocketUtils.hexToBuffer(httpPostResponseFinal));
        
        System.out.println("*************end*************");
   }

}
