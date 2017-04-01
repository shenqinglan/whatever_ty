package com.whty.euicc.sr.handler.netty;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.whty.euicc.common.bean.RespMessage;
import com.whty.euicc.common.exception.ErrorCode;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.service.EuiccCardService;
import com.whty.euicc.handler.base.HttpHandler;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.RequestMsgBody;
import com.whty.euicc.packets.message.request.RetrieveEISReqBody;
import com.whty.euicc.packets.parse.EuiccMsgParse;
/**
 * 查询EIS
 * @author Administrator
 *
 */
@Service("retrieveEISBySr")
public class RetrieveEISHandler implements HttpHandler{
	@Autowired
	private EuiccCardService cardService;

	@Override
	public byte[] handle(String requestStr) {
		RespMessage respMessage = new RespMessage(ErrorCode.SUCCESS,"success");
		String eis = null;
		try {
		eis = retrieveEIS(requestStr);
		}catch (Exception e) {
			String msg = "";
			e.printStackTrace();
			if(e instanceof EuiccBusiException){
				EuiccBusiException busi = (EuiccBusiException) e;
				msg = busi.getMessage();
			}
			respMessage = new RespMessage(ErrorCode.FAILURE,msg);
			return new Gson().toJson(respMessage).getBytes();
			}
		return eis.getBytes();
	}
	private String retrieveEIS(String requestStr) throws Exception {
		EuiccMsg<RequestMsgBody> reqMsgObject = new EuiccMsgParse().toEuiccMsg(requestStr.getBytes());
		RetrieveEISReqBody reqBody = (RetrieveEISReqBody) reqMsgObject.getBody();
		EuiccCard card =  cardService.selectByPrimaryKey(reqBody.getEid());
		if(card == null){
			throw new EuiccBusiException("9999", "EID Unknown");
		}
		return new Gson().toJson(card);
	}

}
