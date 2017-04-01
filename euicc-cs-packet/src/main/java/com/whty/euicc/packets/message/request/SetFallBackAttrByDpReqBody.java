/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-11-7
 * Id: SetFallBackAttributeByDp.java,v 1.0 2016-11-7 上午10:17:01 Administrator
 */
package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;

/**
 * @ClassName SetFallBackAttributeByDp
 * @author Administrator
 * @date 2016-11-7 上午10:17:01
 * @Description TODO(这里用一句话描述这个类的作用)
 */
@MsgType("setFallBackAttrByDp")
public class SetFallBackAttrByDpReqBody extends EuiccReqBody{
	private String isdPAid;
	
	public String getIsdPAid() {
		return isdPAid;
	}

	public void setIsdPAid(String isdPAid) {
		this.isdPAid = isdPAid;
	}
}
