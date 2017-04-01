package com.whty.euicc.command.cmd;

//import java.util.List;

import com.whty.euicc.common.exception.InvalidParameterException;
import com.whty.euicc.common.utils.StringUtils;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.AppQueryReqBody;
import com.whty.euicc.packets.message.response.AppQueryRespBody;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service("tath.016.001.01")
public class AppQueryCommand extends BaseCommand<AppQueryReqBody,AppQueryRespBody>{

	public AppQueryCommand(MsgHeader msgHeader, AppQueryReqBody requestBody) {
		super(msgHeader, requestBody);
	}

	@Override
	public boolean checkRequestMsg() throws InvalidParameterException {
		AppQueryReqBody reqBody = this.getRequestBody();
		
		reqBody.checkParameters();
		
		// ATS不能为空
		if (StringUtils.isNull(reqBody.getCardNO())){
			return false;
		}
		// 应用安装状态不能为空
		if (StringUtils.isNull(reqBody.getAppInstalledTag())){
			return false;
		}
		return true ;
	}

	@Override
	public AppQueryRespBody buildResponseBody() {
		return new AppQueryRespBody();
	}
}
