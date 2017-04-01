package com.whty.euicc.data.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.whty.euicc.base.common.Constant;
import com.whty.euicc.base.common.LogTables;
import com.whty.euicc.base.pojo.BaseUsers;
import com.whty.euicc.base.service.BaseLogsService;
import com.whty.euicc.common.base.BaseController;
import com.whty.euicc.common.base.BaseResponseDto;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.constant.ErrorCode;
import com.whty.euicc.common.constant.StaticConfig;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.https.BaseHttp;
import com.whty.euicc.data.message.EuiccMsg;
import com.whty.euicc.data.message.MsgHeader;
import com.whty.euicc.data.message.request.SrChangeSendReqBody;
import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.service.EuiccCardService;
@Controller
@RequestMapping("srChange")
public class SrChangeController extends BaseController{
	
	@Autowired
	private BaseLogsService baseLogsService;
	
	@Autowired
	EuiccCardService cardService;
	
	/**
	 * 显示主列表页面
	 * @dzmsoftgenerated 2016-08-03 11:27
	 * @return
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show() {
		return "modules/srChange/srChangeUI";
	}
	
	
	/**
	 * SM-SR Change
	 * @param request
	 * @param response
	 * @param id
	 * @throws IOException
	 */
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public void srChange(HttpServletRequest request,HttpServletResponse response, String eid) throws Exception {
		BaseUsers currentUser = (BaseUsers) SecurityUtils.getSubject()
				.getSession()
				.getAttribute(Constant.BaseUsersConstant.CURRENTUSER);
		EuiccCard card = cardService.selectByPrimaryKey(eid);
		BaseResponseDto baseResponseDto = null;
		boolean flag = true;
		String msg = "";
		try {
			smsrChangeRoot(card);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
				//msg = "连接服务异常";
			}else{
				msg = e.getMessage();
			}
		}
		baseResponseDto = flag ? new BaseResponseDto(true, "change处理中")
		: new BaseResponseDto(false, msg);
		baseLogsService.insertSelective(LogTables.getOperateLog(currentUser,
		"2", Constant.EUICC_BASE_TYPE, "EUICC_CARD",
		getObjectData(card), "", "sr-change", "change"));
		writeJSONResult(baseResponseDto, response);
	}

	public void smsrChangeRoot(EuiccCard card)throws Exception{
		MsgHeader header = new MsgHeader("srChangeSend");
		SrChangeSendReqBody requestBody = new SrChangeSendReqBody();
		requestBody.setEid(card.getEid());
		requestBody.setTargetSmsrId("0819");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(StaticConfig.getSrUrl(),json);
		RespMessage respMessage = new Gson().fromJson(new String(msgBype), RespMessage.class);
        if(!StringUtils.equals(ErrorCode.SUCCESS, respMessage.getCode())){
        	throw new EuiccBusiException(respMessage.getCode(),respMessage.getMessage());
        }
	}
	
	private String getObjectData(EuiccCard card) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("EuiccCard[");
		sBuffer.append("id=");
		sBuffer.append(card.getEid());
		sBuffer.append("]");
		return sBuffer.toString();
	}

}
