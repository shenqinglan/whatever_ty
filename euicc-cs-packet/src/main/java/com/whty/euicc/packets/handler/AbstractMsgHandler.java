package com.whty.euicc.packets.handler;

import com.whty.euicc.common.exception.NullParameterException;
import com.whty.euicc.common.utils.Converts;
import com.whty.euicc.packets.constant.Constant;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.response.ResponseMsgBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
import com.whty.euicc.packets.thirdpartymsg.F17Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractMsgHandler implements MsgHandler {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(AbstractMsgHandler.class);

	/**
	 * 会话密钥标识
	 */
	private String encodePhoneNum;

	private boolean plaintext;

	@Override
	public EuiccMsg<ResponseMsgBody> handler(F17Msg f17Msg) {
		EuiccMsg<RequestMsgBody> reqMsg = null;
		// 解析报文
		this.encodePhoneNum = new String(f17Msg.getF3());
		String f2Str = new String(f17Msg.getF2());
		this.plaintext = (Constant.F2_PROCLAIMED_ENCODE.equals(new String(
				f17Msg.getF2())) || Constant.F2_TRADEKEY_OBTAIN.equals(f2Str)) ? true
				: false;
		plaintext = true;//todo取消暂时取消，因为redis里面没有解密初始密钥
		byte[] msgBytes = null;
		try {
			msgBytes = this.plaintext ? f17Msg.getF5() : this.decode(f17Msg
					.getF5());
		} catch (Exception e) {
			NullParameterException ex = new NullParameterException(
					e.getMessage());
			return EuiccMsg.buildEuiccMsg(null, NullParameterException.FAILURE,
					ex.getCode(), ex.getMessage());
		}
		EuiccMsgParse parse = new EuiccMsgParse();
		reqMsg = parse.toEuiccMsg(Converts.hexToBytes(new String(msgBytes)));
		// 校验报文基本信息
		try {
			checkReqMsg(reqMsg);
		} catch (NullParameterException e) {
			return EuiccMsg.buildEuiccMsg(null, NullParameterException.FAILURE,
					e.getCode(), e.getMessage());
		}
		// 处理报文
		return handler(reqMsg);
	}

	@Override
	public EuiccMsg<ResponseMsgBody> handler(byte[] msgBytes) {
		EuiccMsg<RequestMsgBody> reqMsg = null;
		EuiccMsgParse parse = new EuiccMsgParse();
		reqMsg = parse.toEuiccMsg(msgBytes);
		// 校验报文基本信息
		try {
			checkReqMsg(reqMsg);
		} catch (NullParameterException e) {
			return EuiccMsg.buildEuiccMsg(null, NullParameterException.FAILURE,
					e.getCode(), e.getMessage());
		}
		// 处理报文
		return handler(reqMsg);
	}

	/**
	 * 校验报文基本信息
	 * 
	 * @param reqMsg
	 * @throws NullParameterException
	 */
	private void checkReqMsg(EuiccMsg<RequestMsgBody> reqMsg)
			throws NullParameterException {
		if (null == reqMsg)
			throw new NullParameterException("请求报文为空");
		if (null == reqMsg.getHeader())
			throw new NullParameterException("请求报文头为空");
		if (null == reqMsg.getBody())
			throw new NullParameterException("请求报文提为空");
	}

	/**
	 * 根据报文消息，处理后生成新的报文
	 * 
	 * @param reqMsg
	 * @return
	 */
	public abstract EuiccMsg<ResponseMsgBody> handler(
			EuiccMsg<RequestMsgBody> reqMsg);

	/**
	 * 对响应数据进行加密
	 */
	public byte[] encode(EuiccMsg<ResponseMsgBody> respBody) throws Exception {
		return respBody.toBytes();
	}

	/**
	 * 对请求数据进行解密
	 * 
	 * @param ciphertext
	 * @return
	 * @throws java.security.InvalidKeyException
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws javax.crypto.NoSuchPaddingException
	 * @throws javax.crypto.IllegalBlockSizeException
	 * @throws javax.crypto.BadPaddingException
	 */
	public byte[] decode(byte[] ciphertext) throws Exception {
		return ciphertext;
	}

	public String getEncodePhoneNum() {
		return encodePhoneNum;
	}

	public void setEncodePhoneNum(String encodePhoneNum) {
		this.encodePhoneNum = encodePhoneNum;
	}

	public boolean isPlaintext() {
		return plaintext;
	}

	public void setPlaintext(boolean plaintext) {
		this.plaintext = plaintext;
	}
}
