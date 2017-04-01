package com.whty.euicc.packets.parse;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.whty.euicc.packets.constant.Constant;
import com.whty.euicc.packets.message.MessageHelper;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.RequestMsgBody;

/**
 * TSM标准接口解析
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
	public EuiccMsg<RequestMsgBody> toEuiccMsg(byte[] msg) {
		logger.debug("请求报文:{}",new String(msg));
		Reader reader = toReader(msg, Constant.UTF8);
		JsonReader jsonReader = new JsonReader(reader);
		jsonReader.setLenient(true);
		Type type = new TypeToken<EuiccMsg<RequestMsgBody>>() {}.getType();
		return MessageHelper.GSONBUILDER4MSG.create().fromJson(jsonReader, type);
	}
	
	/**
	 * 把IoBuffer数据转成Reader对象
	 * 
	 * @param buffer
	 * @param cs
	 * @return
	 * @throws javax.crypto.BadPaddingException
	 * @throws javax.crypto.IllegalBlockSizeException
	 * @throws javax.crypto.NoSuchPaddingException
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws java.security.InvalidKeyException
	 * @throws Exception
	 */
	private Reader toReader(byte[] msg, Charset cs)  {
		ByteArrayInputStream bais = new ByteArrayInputStream(msg);
		return new InputStreamReader(bais, cs);
	}
}
