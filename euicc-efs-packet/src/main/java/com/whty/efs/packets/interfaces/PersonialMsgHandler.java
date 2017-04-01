package com.whty.efs.packets.interfaces;

import com.whty.efs.packets.message.EuiccEntity;
import com.whty.efs.packets.message.request.RequestMsgBody;
import com.whty.efs.packets.message.response.ResponseMsgBody;

public interface PersonialMsgHandler{
	public EuiccEntity<ResponseMsgBody> appPersonial(EuiccEntity<RequestMsgBody> reqEntity) throws Exception;
}
