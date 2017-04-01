package com.whty.euicc.packets.parse;

import com.whty.euicc.common.exception.InvalidParameterException;
import com.whty.euicc.common.utils.ByteUtil;
import com.whty.euicc.common.utils.Converts;
import com.whty.euicc.packets.constant.Constant;
import com.whty.euicc.packets.thirdpartymsg.F17Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * F1~F7域数据解析
 * @author dengzm
 *
 */
public class F17MsgParse {
	
	private static final Logger logger = LoggerFactory.getLogger(F17MsgParse.class);
	
	
	/**
	 * 读取字节流，形成F17Msg对象
	 * @param in
	 * @return
	 * @throws java.io.IOException
	 * @throws InvalidParameterException 
	 * @throws javax.crypto.BadPaddingException
	 * @throws javax.crypto.IllegalBlockSizeException
	 * @throws javax.crypto.NoSuchPaddingException
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws java.security.InvalidKeyException
	 */
	public F17Msg toF17Msg(InputStream in) throws IOException, InvalidParameterException {
		// 获取F1域 
		byte[] f1 = new byte[2];
		in.read(f1);
		if (!ByteUtil.compare(Constant.MESSAGE_START, f1))
			throw new InvalidParameterException("F1域不匹配");
		// 组装F17报文对象
		F17Msg f17Msg = new F17Msg();
		f17Msg.setF1(f1);// 获取并校验开始符号
		logger.debug("入参F1:{}",new String(f17Msg.getF1()));
		// 获取F2域 
		byte[] f2 = new byte[2];// 获取会话标示
		in.read(f2);
		f17Msg.setF2(f2);
		logger.debug("入参F2:{}",new String(f17Msg.getF2()));
		// 获取F3域 
		byte[] f3 = new byte[32];// 获取MD5加密后的phoneNum
		in.read(f3);
		f17Msg.setF3(f3);
		logger.debug("入参F3:{}",new String(f17Msg.getF3()));
		// 获取F4域 
		byte[] f4 = new byte[8];// 获取数据长度
		in.read(f4);
		// 获取F5域 
		int f4Length = Integer.parseInt(new String(f4)) * 2;
		byte[] f5 = new byte[f4Length];
		int readCount = 0;
		while (readCount < f4Length){
			readCount += in.read(f5, readCount, f4Length-readCount);
		}
		f17Msg.setF5(f5);
		logger.debug("入参F4:{}",new String(f17Msg.getF4()));
		logger.debug("入参F5:{}",new String(f17Msg.getF5()));
		// 获取F6域 
		byte[] f6 = new byte[2];// 获取校验码 用户校验数据一致性
		in.read(f6);
		logger.debug("入参F6:{}",new String(f6));
		logger.debug("入参f17Msg.getF6():{}",new String(f17Msg.getF6()));
		
		if (!ByteUtil.compare(f17Msg.getF6(), f6))
			throw new InvalidParameterException("F6域不匹配");
		// 获取F7域 
		byte[] f7 = new byte[2];// 结束符号
		in.read(f7);
		f17Msg.setF7(f7);
		// 如果接口是明文则转换为明文对象
		String f2Str = new String(f17Msg.getF2());
		if (Constant.F2_PROCLAIMED_ENCODE.equals(f2Str)
				|| Constant.F2_TRADEKEY_OBTAIN.equals(f2Str)){
			EuiccMsgParse tsmMsgParse = new EuiccMsgParse();
			f17Msg.setReqMsg(tsmMsgParse.toEuiccMsg(Converts.hexToBytes(new String(f17Msg.getF5()))));
		}
		return f17Msg;
	}
}
