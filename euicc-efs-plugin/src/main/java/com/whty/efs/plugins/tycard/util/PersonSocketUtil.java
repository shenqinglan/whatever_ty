package com.whty.efs.plugins.tycard.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whty.efs.common.util.StringUtils;
import com.whty.efs.plugins.tycard.constant.Constant;
import com.whty.efs.plugins.tycard.constant.TyStaticConfig;
import com.whty.efs.plugins.tycard.message.request.PersonalSendMsg;

public class PersonSocketUtil {
	
	private static Logger logger = LoggerFactory.getLogger(PersonSocketUtil.class);

	private Socket socket = null;
    private InputStream in = null;
    private OutputStream out = null;
    
    public PersonSocketUtil() throws Exception{
		try {
			socket = new Socket(TyStaticConfig.getTyPersonalIP(), TyStaticConfig.getTyPersonalPort());
			socket.setSoTimeout(30 * 1000);
			in = socket.getInputStream();
			out = socket.getOutputStream();
		} catch (IOException e) {
//			throw new BusinessException(e);
		}
    }
    
    /**
     * 拼接个人化发送报文
     * @param psg	个人化发送报文类
     * @return
     */
    public static String convertSendMsg(PersonalSendMsg psg){
    	StringBuilder sMsg = new StringBuilder("");
    	if(StringUtils.isNotNull(psg.getCmdType())){
    		sMsg.append(psg.getCmdType());
    	}
    	if (StringUtils.isNotNull(psg.getRemark())){
    		sMsg.append(psg.getRemark());
    	}
    	if (StringUtils.isNotNull(psg.getAppTag())){
    		sMsg.append(Converts.intToHex(psg.getAppTag().length(), 8)).append(Converts.asciiToHex(psg.getAppTag()));
    	}
    	if (StringUtils.isNotNull(psg.getCardNumber())){
    		sMsg.append(Converts.intToHex(psg.getCardNumber().length(), 8)).append(Converts.asciiToHex(psg.getCardNumber()));
    	}
    	if(psg.getCmdType().equals(Constant.CmdType.ONE)){     //0x00001001个人化请求
    		if (StringUtils.isNotNull(psg.getSdAid())){
    			sMsg.append(Converts.intToHex(psg.getSdAid().length(), 8)).append(Converts.asciiToHex(psg.getSdAid()));
    		}
    		if (StringUtils.isNotNull(psg.getData())){
    			sMsg.append(Converts.intToHex(psg.getData().length(), 8)).append(Converts.asciiToHex(psg.getData()));
    		}
    		if (StringUtils.isNotNull(psg.getInstAid())){
    			sMsg.append(Converts.intToHex(psg.getInstAid().length(), 8)).append(Converts.asciiToHex(psg.getInstAid()));
    		}
    		if (StringUtils.isNotNull(psg.getTokenFlag())){
    			sMsg.append(Converts.intToHex(psg.getTokenFlag().length(), 8)).append(Converts.asciiToHex(psg.getTokenFlag()));
    		}
    		if (StringUtils.isNotNull(psg.getTokenAlg())){
    			sMsg.append(Converts.intToHex(psg.getTokenAlg().length(), 8)).append(Converts.asciiToHex(psg.getTokenAlg()));
    		} else {
    			sMsg.append("00000000");
    		}
    	} else if (psg.getCmdType().equals(Constant.CmdType.TWO)){
    		if (StringUtils.isNotNull(psg.getRapdu())){
    			String rapdu = psg.getRapdu();
    			rapdu = Converts.intToHex(rapdu.length()/2, 4)+rapdu;
    			rapdu = "000100"+rapdu;
    			sMsg.append(Converts.intToHex(rapdu.length()/2, 8)).append(rapdu);
    		}
    	}
    	if(sMsg!=null && StringUtils.isNotNull(sMsg.toString())){
    		sMsg.insert(0, Converts.intToHex(sMsg.length()/2+4, 8) );
    	}
    	return sMsg.toString();
    }
    
    /**
     * 运行方法
     * @param msg
     * @return
     * @throws IOException 
     * @throws BusinessException 
     */
    public String run(String msg)  {
		String rsp = null;
		try {
			sendMessage(msg);
			rsp = getMessage();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseUtil.quietClose(in,out);
			CloseUtil.quietClose(socket);
		}
		return rsp;
	}
    
    /**
     * 成功完成后发送报文，没有返回
     * @param msg
     * @return
     * @throws IOException
     */
    public String runSuccess(String msg) throws IOException {
		String rsp = null;
		try {
			sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseUtil.quietClose(in,out);
			CloseUtil.quietClose(socket);
		}
		return rsp;
	}
	
    /**
     * 获取报文
     * @return
     * @throws IOException
     */
	private String getMessage() throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] msgLengthByte = new byte[4];
        // 前4个字节为长度信息
        int size = in.read(msgLengthByte);

        if (size <= 0)
        {
            logger.error("Read message lenth error!");
        }
        
        int msgLen = Integer.parseInt(Converts.bytesToHex(msgLengthByte), 16)-4;

        byte[] temp = new byte[1024];
        int totalSize = 0;
        int size1 = 0;
        while (totalSize < msgLen)
        {
            size1 = in.read(temp);
            if (size1 == -1)
            {
                break;
            } else
            {
                bos.write(temp, 0, size1);
                totalSize = totalSize + size1;
            }
        }
		
//		int size = in.read(temp);
		String rMsg = Converts.bytesToHex(msgLengthByte)+Converts.bytesToHex(bos.toByteArray());
		logger.debug("个人化接收报文：" + rMsg+"，长度："+size);
		return rMsg;
		
//		byte[] rspBytes = new byte[1024];
//		int len = in.read(rspBytes);
//		
//		log.debug("返回报文长度：" + len);
//		
//		String rspMsg = Converts.bytesToHex(rspBytes);
//		
//		log.debug("返回报文：" + rspMsg.substring(0, len * 2));
//		return rspMsg.substring(0, len * 2);
	}

	/**
	 * 发送报文
	 * 
	 * @param req
	 * @throws IOException
	 */
	private void sendMessage(String msg) throws IOException {
		byte[] msgByte = Converts.hexToBytes(msg);
		out.write(msgByte);
		out.flush();
	}
	
}
