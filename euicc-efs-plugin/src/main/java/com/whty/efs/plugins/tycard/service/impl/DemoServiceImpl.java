package com.whty.efs.plugins.tycard.service.impl;

import org.springframework.stereotype.Service;

import com.whty.efs.packets.interfaces.PersonialMsgHandler;
import com.whty.efs.packets.message.Header;
import com.whty.efs.packets.message.EuiccEntity;
import com.whty.efs.packets.message.request.AppApplyBody;
import com.whty.efs.packets.message.request.RequestMsgBody;
import com.whty.efs.packets.message.response.AppApplyResp;
import com.whty.efs.packets.message.response.ResponseMsgBody;
import com.whty.efs.packets.message.response.RspnMsg;

@Service("activate_002")
public class DemoServiceImpl  implements PersonialMsgHandler{

	@Override
	public EuiccEntity<ResponseMsgBody> appPersonial(
			EuiccEntity<RequestMsgBody> reqEntity) throws Exception {
		AppApplyBody body = (AppApplyBody)reqEntity.getBody();
		Header header = reqEntity.getHeader();
		AppApplyResp respBody=run();
		EuiccEntity appApplyResp = new EuiccEntity<AppApplyResp>(header, respBody);
		return appApplyResp;
	}

	private AppApplyResp run() {
		AppApplyResp  respBody= new AppApplyResp();
		RspnMsg rspnMsg=new  RspnMsg();
		rspnMsg.setEndFlag("01");
		rspnMsg.setSts("0000");
		rspnMsg.setRjctCd("B0000");
	    rspnMsg.setRjctInfo("业务处理正常");
	    respBody.setRspnMsg(rspnMsg);
	    return respBody;
	}

}
