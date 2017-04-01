package com.whty.euicc.data.message.request;

import com.whty.euicc.data.message.MsgType;


/**
 * 短信方式设置回退属性javabean
 * @author Administrator
 *
 */
@MsgType("setFallBackAttrByDpAndSms")
public class SetFallBackByDpAndSmsReqBody extends EuiccReqBody {
	private String isdPAid;

	public String getIsdPAid() {
		return isdPAid;
	}

	public void setIsdPAid(String isdPAid) {
		this.isdPAid = isdPAid;
	}

}
