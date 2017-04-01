package com.whty.efs.packets.message.request;

import com.whty.efs.packets.message.MsgBody;
import com.whty.efs.packets.message.MsgType;

@MsgType("deleteProfileByHttps")
public class ES4DeleteProfileReqBody extends EuiccReqBody implements MsgBody {
	private boolean isErrorDeal;//是否为出错处理

	public boolean isErrorDeal() {
		return isErrorDeal;
	}

	public void setErrorDeal(boolean isErrorDeal) {
		this.isErrorDeal = isErrorDeal;
	}

}
