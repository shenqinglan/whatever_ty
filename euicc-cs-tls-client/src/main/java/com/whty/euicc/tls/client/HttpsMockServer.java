package com.whty.euicc.tls.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class HttpsMockServer {
    static DataInputStream in;
    static DataOutputStream out;

    public static void doHttpsShakeHands(Socket s) throws Exception {
    	in=new DataInputStream(s.getInputStream());
    	out=new DataOutputStream(s.getOutputStream());

        //第一步 获取客户端发送clientHello做解析
    	//针对ECDHE_ECDSA算法，clientHello会增加扩展项
        int length=in.readInt();
        in.skipBytes(4);
        byte[] clientSupportHash=SocketUtils.readBytes(in,length);
        String clientHash=new String(clientSupportHash);
        System.out.println("clientHash >>" + clientHash);
        STlsHankUtils sTls = new STlsHankUtils();
        sTls.clientHelloPaser(clientHash); 
        
        //第二步 发送serverHello
        //针对ECDHE_ECDSA算法，会增加扩展项
        String strServerHello = sTls.serverHelloSend();
        length=strServerHello.getBytes().length;
        out.writeInt(length);
	    SocketUtils.writeBytes(out, strServerHello.getBytes(), length);
        
	    //第三步 发送Certificate
	    //原理上是发送证书，目前没有CI，扣取脚本里面。ECDSA公司密钥对的公钥，进行传输，私钥保留做签名
        String strCertificate = sTls.certificateSend();
        length=strCertificate.getBytes().length;
        out.writeInt(length);
	    SocketUtils.writeBytes(out, strCertificate.getBytes(), length);
	    
	    //第四步 发送server_key_exchange
	    //在使用ECDHE_ECDSA，ECDHE_RSA和ECDH_anon密钥交换算法时需要发送本消息。
	    String strServerKeyExchange = sTls.serverKeyExchangeSend();
        length=strServerKeyExchange.getBytes().length;
        out.writeInt(length);
	    SocketUtils.writeBytes(out, strServerKeyExchange.getBytes(), length);
	    
	    //第五步 发送serverHelloDoneSend
	    String strServerHelloDone = sTls.serverHelloDoneSend();
        length=strServerHelloDone.getBytes().length;
        out.writeInt(length);
	    SocketUtils.writeBytes(out, strServerHelloDone.getBytes(), length);

	    //第六步 收到clientKeyExchange
	    length=in.readInt();
        in.skipBytes(4);
        byte[] clientKeyExchange=SocketUtils.readBytes(in,length);
        String strclientKeyExchange=new String(clientKeyExchange);
        sTls.clientKeyExchangePaser(strclientKeyExchange);
        
        //第七步 收到clientChangeCipherSpec
	    length=in.readInt();
        in.skipBytes(4);
        byte[] clientChangeCipherSpec=SocketUtils.readBytes(in,length);
        String strclientChangeCipherSpec=new String(clientChangeCipherSpec);
        sTls.clientChangeCipherSpecPaser(strclientChangeCipherSpec);
     
        //第八步 收到ClientFinish
	    length=in.readInt();
        in.skipBytes(4);
        byte[] ClientFinish=SocketUtils.readBytes(in,length);
        String strClientFinish=new String(ClientFinish);
        System.out.println("strClientFinish >>" + strClientFinish);
        sTls.clientFinishedPaser(strClientFinish);
        
        //第九步 发送serverChangeCipherSpec
        String serverChangeCipherSpec = sTls.serverChangeCipherSpecSend();
        length=serverChangeCipherSpec.getBytes().length;
        out.writeInt(length);
	    SocketUtils.writeBytes(out, serverChangeCipherSpec.getBytes(), length);
	    
        //第十步 发送serverChangeCipherSpec
        String serverFinishedSend = sTls.serverFinishedSend();
        length=serverFinishedSend.getBytes().length;
        out.writeInt(length);
	    SocketUtils.writeBytes(out, serverFinishedSend.getBytes(), length);
	    
        //第十一步，收到HTTP POST Request
	    length=in.readInt();
        in.skipBytes(4);
        byte[] postRequest=SocketUtils.readBytes(in,length);
        String strpostRequest=new String(postRequest);
        sTls.postRequestPaser(strpostRequest);
        
        //第十二步 发送HTTP POST Response
        String httpPostResponse = sTls.httpPostResponse();
        length=httpPostResponse.getBytes().length;
        out.writeInt(length);
	    SocketUtils.writeBytes(out, httpPostResponse.getBytes(), length);
	    
        //第十三步 收到响应
	    length=in.readInt();
        in.skipBytes(4);
        byte[] postRequestFinal=SocketUtils.readBytes(in,length);
        String strpostRequestFinal = new String(postRequestFinal);
        sTls.postRequestFinal(strpostRequestFinal);    
        
        //第十四步步 发送final
        String httpPostResponseFinal = sTls.httpPostResponseFinal();
        length=httpPostResponseFinal.getBytes().length;
        out.writeInt(length);
	    SocketUtils.writeBytes(out, httpPostResponseFinal.getBytes(), length);
        
        System.out.println("*************end*************");
    }
}
