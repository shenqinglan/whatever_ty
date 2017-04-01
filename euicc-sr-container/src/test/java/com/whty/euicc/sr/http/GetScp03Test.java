package com.whty.euicc.sr.http;


import org.junit.Test;
import com.google.gson.Gson;
import com.whty.euicc.common.apdu.ToTLV;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.GetScp03SequenceReqBody;
import com.whty.euicc.sr.http.base.BaseHttp;
import com.whty.security.scp03forupdate.Scp03ForUpdate;
/**
 * SM-SR的获取scp03计数器测试用例类
 * @author Administrator
 *
 */
public class GetScp03Test {
	
	@Test
	public void getScp03()throws Exception{
        MsgHeader header = new MsgHeader("getScp03Sequence");
        GetScp03SequenceReqBody requestBody = new GetScp03SequenceReqBody();
        String kerV = "00";
        String sequenceApdu = getScp03SequenceApdu(kerV);
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setSms(sequenceApdu);
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPost(json);
        System.out.println(new String(msgBype));
	}
	/**
	 * 以下方法需放到dp中，待更改
	 * @param eid
	 * @return
	 */
	private String getScp03SequenceApdu(String kerV) {
		
		String iniString = Scp03ForUpdate.initializeUpdate(kerV)+"00";
		String commandString = ToTLV.toTLV("AA", ToTLV.toTLV("22", iniString));
		return commandString;
		
	}
	
	

}
