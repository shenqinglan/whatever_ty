package com.whty.efs.euicc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.whty.efs.common.spring.SpringContextHolder;
import com.whty.efs.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.efs.common.util.Encryptor;
import com.whty.efs.data.pojo.Router;
import com.whty.efs.euicc.listener.RouterContainer;
import com.whty.efs.packets.interfaces.StandardHttpMsgHandler;
import com.whty.efs.packets.message.EuiccEntity;
import com.whty.efs.packets.message.Header;
import com.whty.efs.packets.message.parse.EuiccMsgParse;
import com.whty.efs.packets.message.request.RequestMsgBody;
import com.whty.efs.packets.message.response.BaseRespBody;
import com.whty.efs.packets.message.response.ResponseMsgBody;
import com.whty.efs.packets.message.response.RspnMsg;

/**
 * 总controller控制处理类
 * 
 * @author gaofeng
 */
@Controller
@RequestMapping("/")
public class HandleController {

	protected static final Gson gson = new Gson();

	private static Logger logger = LoggerFactory
			.getLogger(HandleController.class);

	private final static String ENCODE = "utf-8";
	
	@Autowired
	private RouterContainer routerContainer;

	/**
	 * 解析请求报文
	 * 
	 * @param req
	 * @return
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private EuiccEntity<RequestMsgBody> getRequestMsgBody(HttpServletRequest req)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		// 读取请求信息
		byte[] data = IOUtils.toByteArray(req.getInputStream());
		// 解密
		String secureFactor = SpringPropertyPlaceholderConfigurer
				.getStringProperty("secureFactor");
		byte[] msgBytes = new Encryptor()
				.decrypt(secureFactor.getBytes(), data);
		EuiccMsgParse parse = new EuiccMsgParse();
		return parse.toTsmMsg(msgBytes);
	}

	/**
	 * 响应报文加密
	 * 
	 * @param respEntity
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 */
	private byte[] encode(EuiccEntity<ResponseMsgBody> respEntity)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException {
		String secureFactor = SpringPropertyPlaceholderConfigurer
				.getStringProperty("secureFactor");
		logger.debug("返回的报文：{}", gson.toJson(respEntity, EuiccEntity.class));
		return new Encryptor().encrypt(secureFactor.getBytes(),
				gson.toJson(respEntity, EuiccEntity.class).getBytes(ENCODE));
	}

	@ResponseBody
	@RequestMapping("/handle")
	public byte[] handle(HttpServletRequest req) throws Exception {

		BaseRespBody respBody = new BaseRespBody();
		EuiccEntity<ResponseMsgBody> returnEuicc = new EuiccEntity<ResponseMsgBody>();
		try {
			// 解析请求报文
			EuiccEntity<RequestMsgBody> reqEntity = getRequestMsgBody(req);
			// 根据放分发业务
			Header header = reqEntity.getHeader();
			String receiver = Strings.isNullOrEmpty(header.getReceiver())?"whty":header.getReceiver();
			Router router = this.routerContainer
					.getRouter(receiver);
			StandardHttpMsgHandler msgHandler = (StandardHttpMsgHandler) SpringContextHolder
					.getBean(router.getBean_id());
			returnEuicc = msgHandler.standardHandler(reqEntity);
		} catch (Exception e) {
			logger.debug("异常：", e);
			returnEuicc.setHeader(new Header());
			RspnMsg msg = new RspnMsg("0001", "B0001", e.getMessage());
			respBody.setRspnMsg(msg);
			returnEuicc.setBody(respBody);
		}
		return encode(returnEuicc);
	}
}
