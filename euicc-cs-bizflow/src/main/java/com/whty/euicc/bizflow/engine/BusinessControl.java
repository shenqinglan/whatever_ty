// Copyright (C) 2012 WHTY
package com.whty.euicc.bizflow.engine;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.whty.euicc.bizflow.exception.BizException;
import com.whty.euicc.bizflow.template.*;
import com.whty.euicc.command.cmd.BaseCommand;
import com.whty.euicc.command.cmd.EuiccContextData;
import com.whty.euicc.command.dto.BusinessVo;
import com.whty.euicc.common.exception.Assert;
import com.whty.euicc.common.exception.InvalidParameterException;
import com.whty.euicc.common.utils.StringUtils;
import com.whty.euicc.data.pojo.BusinessObject;
import com.whty.euicc.packets.message.MsgBodyUtil;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.response.ResponseMsgBody;
import com.whty.euicc.packets.message.response.RspnMsg;
import com.whty.euicc.tasks.utils.TaskAssert;
import com.whty.flow.EasyFlow;
import com.whty.flow.container.FlowContainer;
import com.whty.flow.err.LogicViolationError;
import com.whty.flow.impl.context.FlowMapContext;
import com.whty.flow.persistence.StorageMode;
import com.whty.flow.persistence.storage.FlowStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class BusinessControl {

	private static final Logger logger = LoggerFactory
			.getLogger(BusinessControl.class);

	private static Gson GSON = new GsonBuilder().create();

	@Autowired
	private BizObjectCache cacheService;

	@Autowired
	private BizTemplateManager bizTplManager;

	@Autowired
	private BusinessExecutor executor;
	
	@Autowired
	private FlowContainer flowContainer;

	@Autowired
	private FlowStorageService flowStorageService;
	
	

	@SuppressWarnings("unchecked")
	public ResponseMsgBody doBusiness(
			BaseCommand<RequestMsgBody, ResponseMsgBody> cmd)
			throws InvalidParameterException, BizTemplateException,
			BizException {

		logger.debug("Business processing is starting...");

		/************************************************/
		/** 分发命令 **/
		/************************************************/

		/**
		 * 1.通过"命令"获取业务对象数据
		 */
		BusinessObject businessObject = buildBusinessObject(cmd);

		TaskAssert.isNotNull(businessObject, "业务对象为空");

		/**
		 * 2.加载上一环节的中间数据到cmd中
		 */
		cmd.loadBizData(po2vo(businessObject));

		// ///////////////////////////////////////////////////////////////
		String msgType = cmd.getMsgHeader().getMsgType();
		@SuppressWarnings("static-access")
		EasyFlow<FlowMapContext> flow = flowContainer.getFlow(msgType);
		TaskAssert.isNotNull(flow, "业务处理流程[" + msgType + "]为空");
		// first time new Flow
		FlowMapContext ctx = new FlowMapContext();
		if (StringUtils.isNull(cmd.getMsgHeader().getTradeRefNO())
				|| "0".equals(cmd.getMsgHeader().getTradeRefNO())
				|| flowStorageService.get(cmd.getMsgHeader().getTradeRefNO(),ctx, StorageMode.CACHE) == null) {
			ctx.addParam("cmd", cmd);
			flow.instance().trace().start(ctx);

			if (ctx.getError() != null) {
				if(msgType.equals("tath.005.001.01")){  //下载失败，记录操作日志
					//AppDownloadReqBody reqBody=	(AppDownloadReqBody) cmd.getRequestBody();
					//AppInfo appInfo=appInfoService.getAppInfo(reqBody.getAppAID());
					//insertAppSeqInfo(reqBody.getAppAID(), reqBody.getAppVersion(), reqBody.getCardNO(), appInfo );
				}
				throw new BizException(ctx.getError());
			}

			cmd = (BaseCommand<RequestMsgBody, ResponseMsgBody>) ctx
					.getParam("cmd");
			flowStorageService.save(cmd.getMsgHeader().getTradeRefNO(), ctx,StorageMode.CACHE);
		} else {
			ctx = flowStorageService.get(cmd.getMsgHeader().getTradeRefNO(),ctx, StorageMode.CACHE);
			ctx.addParam("cmd", cmd);

			try {
				ctx.getFlowInstance().toContinue(ctx);
			} catch (LogicViolationError e) {
				throw new BizException(e);
			}

			if (ctx.getError() != null) {
				if(msgType.equals("tath.005.001.01")){ //下载失败，记录操作日志
					//AppDownloadReqBody reqBody=	(AppDownloadReqBody) cmd.getRequestBody();
					//AppInfo appInfo=appInfoService.getAppInfo(reqBody.getAppAID());
					//insertAppSeqInfo(reqBody.getAppAID(), reqBody.getAppVersion(), reqBody.getCardNO(), appInfo );
				}
				throw new BizException(ctx.getError());
			}

			cmd = (BaseCommand<RequestMsgBody, ResponseMsgBody>) ctx
					.getParam("cmd");
			flowStorageService.save(cmd.getMsgHeader().getTradeRefNO(), ctx,
					StorageMode.CACHE);
		}
		if (ctx.isTerminated()) {
			MsgBodyUtil.setEndFlag(cmd.getResponseBody(), RspnMsg.DEALED);
		}
		// ///////////////////////////////////////////////////////////////

		/**
		 * 3.从业务上下文中，查找当前步骤
		 */
		// XmlStep currentStep = getBusinessStep(businessObject);
		// boolean doNext = true;
		// while (doNext) {
		//
		// /************************************************/
		// /** 分步处理业务 **/
		// /************************************************/
		// BasicTask task = executor.execute(businessObject, currentStep, cmd);
		//
		// if(task.getEvent() > 0){
		//
		// //业务数据对象，记录子流程进入点
		//
		// //执行子流程业务
		// }else{
		// XmlResult xmlResult = currentStep.getXmlResult(task.getStatus());
		//
		// Assert.isNotNull(xmlResult, "Next step is not retured by engine.");
		// TplAssert.isTrue(!"-1".equals(xmlResult.getStep()),
		// "Error happend when business processing");
		//
		// // 判断是否结束
		// if (TsmConstant.TaskStatus.ZERO.equals(xmlResult.getStep())) {
		// // 0标识流程结束,业务模板的结束标识
		// // 设置businessObject所处的当前阶段为0，标识结束
		// businessObject.setStep(TsmConstant.TaskStatus.ZERO);
		// businessObject.setIsComplete(TrueOrFalseBool.TRUE);
		// MsgBodyUtil.setEndFlag(cmd.getResponseBody(), RspnMsg.DEALED);
		// businessObject.setStatus(TrueOrFalseBool.TRUE);
		//
		// // 读模板，业务结束，退出循环
		// doNext = false;//
		// } else {
		// // 进入下一步
		// currentStep = getBusinessStep(businessObject,currentStep, xmlResult);
		//
		// TaskAssert.isNotNull(currentStep,
		// "Next step returned by engine is not mathed with template.");
		//
		// // 设置businessObject所处的当前阶段
		// businessObject.setStep(currentStep.getStepid());
		// businessObject.setIsComplete(TrueOrFalseBool.FALSE);
		// businessObject.setStatus(TrueOrFalseBool.FALSE);
		//
		// /**
		// * 如果from节点的code为#euicc#，表示该步骤是TSM自身发起的，上一步执行完成后可以直接执行本步骤
		// 如果from节点的code为#form#，意味着多次请求，退出循环
		// */
		// doNext =
		// Constant.PROUD_TSM.equals(currentStep.getXmlForm().getCode());
		// }
		// }
		// }

		/**
		 * 执行完毕后，将中间数据保存到businessObject中
		 */
		businessObject.setLastDate(new Date());

		if (cmd != null) {
			businessObject.setParam(GSON.toJson(cmd.getContext(),
					EuiccContextData.class).getBytes());
		}

		cacheService.cacheBusinessObject(businessObject);

		/**
		 * 渲染输出数据
		 */
		ResponseMsgBody responseBody = cmd.render();

		/**
		 * 执行完毕，清理过程数据
		 */
		if (null != businessObject
				&& null != responseBody
				&& null != responseBody.getRspnMsg()
				&& RspnMsg.DEALED
						.equals(responseBody.getRspnMsg().getEndFlag())) {
			// 清理掉交易缓存
			cacheService.deleteBusinessObject(businessObject);
		}

		return responseBody;
	}

	private BusinessVo po2vo(BusinessObject businessObject) {
		BusinessVo businessVo = new BusinessVo();
		businessVo.setCardNO(businessObject.getCardNO());
		businessVo.setIsComplete(businessObject.getIsComplete());
		businessVo.setLastDate(businessObject.getLastDate());
		businessVo.setMsgType(businessObject.getMsgType());
		businessVo.setParam(businessObject.getParam());
		businessVo.setSessionId(businessObject.getSessionId());
		businessVo.setStatus(businessObject.getStatus());
		businessVo.setStep(businessObject.getStep());
		return businessVo;
	}

	/**
	 * 通过cmd获取业务对象
	 * 
	 * @param cmd
	 * @return
	 * @throws InvalidParameterException
	 */
	private BusinessObject buildBusinessObject(
			BaseCommand<RequestMsgBody, ResponseMsgBody> cmd)
			throws InvalidParameterException {
		MsgHeader msgHeader = cmd.getMsgHeader();

		String cardNO = MsgBodyUtil.getCardNO(cmd.getRequestBody());

		if (StringUtils.isNull(msgHeader.getTradeRefNO())
				|| "0".equals(msgHeader.getTradeRefNO())) {
			logger.debug("首次握手，新构建 BusinessVo");

			// 构建业务模板对象
			BusinessObject businessObject = new BusinessObject(cardNO,
					msgHeader.getMsgType(), msgHeader.getTradeNO());
			// 缓存业务对象
			businessObject.setParam(new byte[0]);
			cacheService.cacheBusinessObject(businessObject);

			return businessObject;
		} else {
			// 非首次握手，采用交易参考号
			logger.debug("非首次握手，加载 BusinessVo．交易参考号：{}",
					msgHeader.getTradeRefNO());
			return cacheService.getBusinessObject(msgHeader.getTradeRefNO(),
					cardNO);
		}
	}

	@SuppressWarnings("unused")
	private XmlStep getBusinessStep(BusinessObject businessObject)
			throws InvalidParameterException, BizTemplateException {
		XmlResult xmlResult = new XmlResult();
		xmlResult.setStep(businessObject.getStep());
		xmlResult.setStatus("0");
		return getBusinessStep(businessObject, null, xmlResult);
	}

	/**
	 * find current step
	 * 
	 * @param businessObject
	 *            根模板编号
	 * @param curStep
	 *            子模板编号
	 * @param xmlResult
	 *            执行步骤编号
	 * @return
	 * @throws InvalidParameterException
	 * @throws BizTemplateException
	 * @throws Exception
	 */
	private XmlStep getBusinessStep(BusinessObject businessObject,
			XmlStep curStep, XmlResult xmlResult)
			throws InvalidParameterException, BizTemplateException {
		Assert.isNotNull(xmlResult, "步骤数为空");

		TplAssert
				.isTrue(!"0".equals(xmlResult.getStep()), "The \"step\" is 0.");

		// 获取模板对象
		BizTemplate xbt = bizTplManager.getBizTemplate(businessObject
				.getMsgType());

		// 获取步骤对象
		return BizTemplateUtil.findXmlStep(xbt, xmlResult.getStep());
	}
	
	
	
}
