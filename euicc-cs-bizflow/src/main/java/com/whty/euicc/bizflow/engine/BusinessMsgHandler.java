package com.whty.euicc.bizflow.engine;

import com.whty.cache.CacheUtil;
import com.whty.euicc.bizflow.constant.Constant;
import com.whty.euicc.bizflow.exception.BizException;
import com.whty.euicc.bizflow.template.BizTemplateException;
import com.whty.euicc.command.cmd.BaseCommand;
import com.whty.euicc.command.common.CommandFactory;
import com.whty.euicc.common.exception.InvalidParameterException;
import com.whty.euicc.common.utils.Converts;
import com.whty.euicc.common.utils.SecurityException;
import com.whty.euicc.common.utils.SecurityUtil;
import com.whty.euicc.packets.handler.AbstractMsgHandler;
import com.whty.euicc.packets.message.MsgBodyUtil;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.Rapdu;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.response.ResponseMsgBody;
import com.whty.euicc.tasks.exception.TaskException;
import com.whty.euicc.tasks.utils.TaskAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component("msgBusiType01")
public class BusinessMsgHandler extends AbstractMsgHandler {

	private static final Logger logger = LoggerFactory
			.getLogger(BusinessMsgHandler.class);

	@SuppressWarnings("unused")
	private static final Logger apduLogger = LoggerFactory
			.getLogger("apdu_log");

	@Autowired
	private CommandFactory factory;

	@Autowired
	private BusinessControl businessControl;

	@Override
	public byte[] encode(EuiccMsg<ResponseMsgBody> respBody) throws Exception {
		if (isPlaintext())
			return super.encode(respBody);
		String transKey = CacheUtil.getString(Constant.TK_PREFIX
				+ getEncodePhoneNum());
		try {
			String json = com.whty.euicc.packets.constant.Constant.gson
					.toJson(respBody);
			logger.debug("响应报文: {}", json);
			json = SecurityUtil.padding00(Converts.bytesToHex(json.getBytes()));
			json = SecurityUtil.desecb(transKey, json, 0);
			logger.debug(
					"响应报文解密:{}",
					new String(Converts.hexToBytes(SecurityUtil.desecb(
							transKey, json, 1))));
			return json.getBytes();
		} catch (SecurityException e) {
			com.whty.euicc.common.exception.SecurityException ex = new com.whty.euicc.common.exception.SecurityException(
					e.getMessage());
			throw ex;
		}
	}

	@Override
	public byte[] decode(byte[] ciphertext) throws Exception {
		if (isPlaintext())
			return ciphertext;
		String transKey = CacheUtil.getString(Constant.TK_PREFIX
				+ getEncodePhoneNum());
		// 利用交易密钥解密
		try {
			return SecurityUtil.desecb(transKey, new String(ciphertext), 1)
					.getBytes();
		} catch (SecurityException e) {
			com.whty.euicc.common.exception.SecurityException ex = new com.whty.euicc.common.exception.SecurityException(
					e.getMessage());
			throw ex;
		}
		// return null;
		// return new Encryptor().decrypt(transKey.getBytes(), ciphertext);
	}

	/**
	 * 业务处理入口<br>
	 * <br>
	 * ***在此方法中，需要处理所有业务异常，返回到通信层***
	 */
	@Override
	public EuiccMsg<ResponseMsgBody> handler(EuiccMsg<RequestMsgBody> request) {
		logger.debug("开始处理报文消息");
		// 报文头信息
		MsgHeader header = request.getHeader();
		// 开始处理报文
		BaseCommand<RequestMsgBody, ResponseMsgBody> command = null;
		try {
			// A) 构建"命令"
			command = buildCommand(header, request.getBody());
			if (logger.isInfoEnabled()) {
				List<Rapdu> list = MsgBodyUtil.getRapdu(request.getBody());
				if (!CollectionUtils.isEmpty(list)) {
					for (Rapdu r : list) {
						logger.info("<-{}", r.getApdu());
					}
				}

			}
			// 对命令中参数的有效性进行校验

			// B) 必输项校验
			TaskAssert.isTrue(command.checkRequestMsg(), "command中参数校验失败");

			/************************************************/
			/** 业务处理 **/
			/************************************************/
			ResponseMsgBody respBody = businessControl.doBusiness(command);

			EuiccMsg<ResponseMsgBody> response = new EuiccMsg<ResponseMsgBody>(
					command.getMsgHeader(), respBody);

			return response;
		} catch (InvalidParameterException e) {
			logger.error("参数异常", e);
			return EuiccMsg.buildEuiccMsg(header,
					InvalidParameterException.FAILURE, e.getCode(), e);
		} catch (BizException e) {
			logger.error("业务处理异常", e);
			return EuiccMsg.buildEuiccMsg(header, BizException.FAILURE,
					e.getCode(), e);
		} catch (TaskException e) {
			logger.error("原子任务异常", e);
			return EuiccMsg.buildEuiccMsg(header, TaskException.FAILURE,
					e.getCode(), e);
		} catch (BizTemplateException e) {
			logger.error("业务模板解析异常", e);
			return EuiccMsg.buildEuiccMsg(header, BizTemplateException.FAILURE,
					e.getCode(), e);
		} catch(Exception e){
            logger.error("其它异常:{}",e.getMessage());
            e.printStackTrace();
            return EuiccMsg.buildEuiccMsg(header, BizTemplateException.FAILURE,
                    "9999", e);
        }
        finally {
			if (null != command) {
				// 清理数据
				command.destroy();
				// 销毁实例
				command = null;
			}
		}
	}

	/**
	 * 通过请求数据，构建"命令"对象
	 * 
	 * @param header
	 * @param body
	 * @return
	 * @throws InvalidParameterException
	 * 
	 * @author baojw@whty.com.cn
	 * @date 2014年10月14日 下午6:50:47
	 */
	private BaseCommand<RequestMsgBody, ResponseMsgBody> buildCommand(
			MsgHeader header, RequestMsgBody body)
			throws InvalidParameterException {
		// 1、通过请求头，构建"命令"实例
		BaseCommand<RequestMsgBody, ResponseMsgBody> cmd = factory.getCommand(
				header, body);

		TaskAssert.isNotNull(cmd, "构建的\"命令\"实例为空");

		// 2、初始化"命令"实例
		// 暂不考虑sd的情况
		// cmd.put(KeyConstant.cardSpaceTag, GPConstant.getCardSpaceTag);
		// cmd.setCardSpaceTag(GPConstant.getCardSpaceTag);
		return cmd;
	}

}
