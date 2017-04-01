package gsta.com.packet.parse;



import gsta.com.packet.message.EuiccMsg;
import gsta.com.packet.message.MessageHelper;
import gsta.com.packet.message.MsgBody;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 标准接口解析
 * @author dengzm
 *
 */
public class EuiccMsgParse {
	private static final Logger logger = LoggerFactory.getLogger(EuiccMsgParse.class);

	
	/**
	 * 把IoBuffer数据转成Map<String,Object>数据
	 * 
	 * @param buffer
	 * @param cs
	 * @return
	 * @throws javax.crypto.BadPaddingException
	 * @throws javax.crypto.IllegalBlockSizeException
	 * @throws javax.crypto.NoSuchPaddingException
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws java.security.InvalidKeyException
	 * @throws EncryptException
	 * @throws Exception
	 */
	public EuiccMsg<MsgBody> toEuiccMsg(String msg,String path) {
		logger.debug("请求报文:{}",msg);
		msg = StringUtils.substringAfter(msg, "{");
		msg = StringUtils.substringBefore(msg, "}");
		msg = new StringBuilder().append("{").append(msg).append("}").toString();
		return MessageHelper.deserialize(msg, path);
	}
	
	
	/**
	 * 准备HTTP请求参数
	 * @param path
	 * @param type application/json or application/x-gsma-rsp-asn1
	 * @param body
	 * @return
	 */
	public static String prepareHttpParam(String path,String type,String body){
		String data = "HTTP POST "+path+" HTTP/1.1\r\n"
				+ "Host: smdp.gsma.com\r\n"
				+ "User-Agent: gsma-rsp-lpad\r\n"
				+ "X-Admin-Protocol: gsma/rsp/v2.0.0\r\n"
				+ "Content-Type: "+type+"\r\n"
				+ "Content-Length: "+body.length()+"\r\n"
				+ "\r\n"+body;
		return data;
	}
	
}
