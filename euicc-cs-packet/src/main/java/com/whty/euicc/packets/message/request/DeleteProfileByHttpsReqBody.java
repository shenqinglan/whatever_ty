package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;
/**
 * Https下的SM-SR删除Profile请求
 * @author Administrator
 *
 */
@MsgType("deleteProfileByHttps")
public class DeleteProfileByHttpsReqBody extends PorReqBody {
	private boolean isErrorDeal;

	public boolean isErrorDeal() {
		return isErrorDeal;
	}

	public void setErrorDeal(boolean isErrorDeal) {
		this.isErrorDeal = isErrorDeal;
	}

}
