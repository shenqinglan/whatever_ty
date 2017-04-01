package com.whty.euicc.command.cmd;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.whty.euicc.common.exception.InvalidParameterException;
import com.whty.euicc.common.utils.StringUtils;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.AppApplyReqBody;
import com.whty.euicc.packets.message.response.AppApplyRespBody;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service("tath.112.001.01")
public class AppApplyComand extends
		BaseCommand<AppApplyReqBody, AppApplyRespBody> {

	public AppApplyComand(MsgHeader msgHeader, AppApplyReqBody requestBody) {
		super(msgHeader, requestBody);
	}

	@Override
	public boolean checkRequestMsg() throws InvalidParameterException {
		AppApplyReqBody reqBody = this.getRequestBody();
		if(true)return true;
		// 卡号不能为空
		if (StringUtils.isNull(reqBody.getSeID())) {
			return false;
		}
		// 应用AID不能为空
		if (StringUtils.isNull(reqBody.getAppAID())) {
			return false;
		}
		// 应用版本不能为空
		if (StringUtils.isNull(reqBody.getAppVersion())) {
			return false;
		}
		// 身份证号不能为空
		if (StringUtils.isNull(reqBody.getIdNo())) {
			return false;
		}
		// 手机号不能为空
		if (StringUtils.isNull(reqBody.getMsisdn())) {
			return false;
		}
		// 卡号不能为空
		if (StringUtils.isNull(reqBody.getPan())) {
			return false;
		}
		// 申请人不能为空
		if (StringUtils.isNull(reqBody.getCardHolderName())) {
			return false;
		}
		// 证件类型不能为空
		if (StringUtils.isNull(reqBody.getIdType())) {
			return false;
		}

		return true;
	}

	@Override
	public AppApplyRespBody buildResponseBody() {
		return new AppApplyRespBody();
	}

}
