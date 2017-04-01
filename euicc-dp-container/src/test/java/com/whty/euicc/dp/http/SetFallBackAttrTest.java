/**
 * Copyright (c).
 * All rights reserved.
 * 
 * Created on 2016-11-9
 * Id: SetFallBackAttrTest.java,v 1.0 2016-11-9 下午4:42:25 Administrator
 */
package com.whty.euicc.dp.http;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.dp.http.base.BaseHttp;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.SetFallBackAttrByDpReqBody;

/**
 * @ClassName SetFallBackAttrTest
 * @author Administrator
 * @date 2016-11-9 下午4:42:25
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SetFallBackAttrTest {

	private String json = null;
	
	@Before
	public void init()throws Exception{
		MsgHeader header = new MsgHeader("setFallBackAttrByDp");
        SetFallBackAttrByDpReqBody requestBody = new SetFallBackAttrByDpReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		json = new Gson().toJson(euiccMsg, EuiccMsg.class);
	}
	
	@Test
	public void setFallBackAttr()throws Exception{
		byte[] msgBype = BaseHttp.doPostByDp(json);
        System.out.println(new String(msgBype));
	}
}
