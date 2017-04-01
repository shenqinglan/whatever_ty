package com.whty.efs.plugins.tycard.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.whty.efs.packets.interfaces.PersonialMsgHandler;
import com.whty.efs.packets.message.EuiccEntity;
import com.whty.efs.packets.message.request.RequestMsgBody;
import com.whty.efs.packets.message.response.ResponseMsgBody;

@Service("personal_A0000001515350")
public class DzxjPersonalServiceImpl extends PersonalServiceImpl{

	@Autowired
	@Qualifier("tycard_personal")
	private PersonialMsgHandler personialMsgHandler ;
	
	@Override
	public EuiccEntity<ResponseMsgBody> appPersonial(
			EuiccEntity<RequestMsgBody> reqEntity) throws Exception {
		return personialMsgHandler.appPersonial(reqEntity);
	}
}
