package com.whty.efs.euicc.http.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.whty.efs.common.constant.Constant;
import com.whty.efs.common.spring.SpringContextHolder;
import com.whty.efs.packets.interfaces.PersonialMsgHandler;
import com.whty.efs.packets.interfaces.StandardHttpMsgHandler;
import com.whty.efs.packets.message.EuiccEntity;
import com.whty.efs.packets.message.Header;
import com.whty.efs.packets.message.MsgBodyUtil;
import com.whty.efs.packets.message.request.RequestMsgBody;
import com.whty.efs.packets.message.response.BaseRespBody;
import com.whty.efs.packets.message.response.ResponseMsgBody;
import com.whty.efs.packets.message.response.RspnMsg;

@Service("tathing_StandardHttpMsgHandler")
public class TathingHttpMsgHandlerServiceImpl implements StandardHttpMsgHandler {
	private static  Logger logger = LoggerFactory.getLogger(TathingHttpMsgHandlerServiceImpl.class);
	@Override
	public EuiccEntity<ResponseMsgBody> standardHandler(
			EuiccEntity<RequestMsgBody> reqEntity) throws Exception {
		BaseRespBody respBody = new BaseRespBody();
		EuiccEntity<ResponseMsgBody> returnTsm = new EuiccEntity<ResponseMsgBody>();
		try {
			// 根据msgType处理报文
			StringBuilder beanName = new StringBuilder();
			Header header = reqEntity.getHeader();
			String type = header.getMsgType();
			if (com.whty.efs.packets.constant.Constant.TATH00800201.equals(type)){
				String appAid = MsgBodyUtil.getAppAID(reqEntity.getBody());
				beanName.append(Constant.Plugin.PERSONAL);
				beanName.append("_");
				beanName.append(appAid);
				PersonialMsgHandler msgHandler = (PersonialMsgHandler)SpringContextHolder.getBean(beanName.toString());
				returnTsm = msgHandler.appPersonial(reqEntity);
			}else if(com.whty.efs.packets.constant.Constant.TATH00900101.equals(type) || com.whty.efs.packets.constant.Constant.TATH11200201.equals(type) ){
				String appAid = MsgBodyUtil.getAppAID(reqEntity.getBody());
				beanName.append(Constant.Plugin.ACTIVATE);
				beanName.append("_");
				beanName.append(appAid);
				PersonialMsgHandler msgHandler = (PersonialMsgHandler)SpringContextHolder.getBean(beanName.toString());
				returnTsm = msgHandler.appPersonial(reqEntity);
			}
		} catch (Exception e) {
			logger.debug("异常：",e);
			returnTsm.setHeader(new Header());
			RspnMsg msg = new RspnMsg("0001", "B0001", e.getMessage());
			respBody.setRspnMsg(msg);
			returnTsm.setBody(respBody);
		}
		return returnTsm;
	}

}