package sms;

import org.junit.Test;

import com.google.gson.Gson;
import com.whty.euicc.dp.http.base.BaseHttp;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.DeleteProfileByDpSmsReqBody;
import com.whty.euicc.packets.message.request.DisableProfileByDpSmsReqBody;
import com.whty.euicc.packets.message.request.EnableProfileByDpSmsReqBody;
import com.whty.euicc.packets.message.request.EnableProfileReqBody;
import com.whty.euicc.packets.message.request.MasterDeleteByDpReqBody;

/**
 * 短信方式下测试
 * 
 * @author YEX
 *
 */
public class SmsTest {

	@Test
	public void enableProfile() throws Exception {
		MsgHeader header = new MsgHeader("enableProfileByDpSms");
        EnableProfileByDpSmsReqBody requestBody = new EnableProfileByDpSmsReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPostByDp(json);
        System.out.println(new String(msgBype));
		
	}
	
	@Test
	public void disableProfile() throws Exception {
		MsgHeader header = new MsgHeader("disableProfileByDpSms");
        DisableProfileByDpSmsReqBody requestBody = new DisableProfileByDpSmsReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPostByDp(json);
        System.out.println(new String(msgBype));
	}
	@Test
	public void deleteProfile() throws Exception {
		MsgHeader header = new MsgHeader("deleteProfileByDpSms");
        DeleteProfileByDpSmsReqBody requestBody = new DeleteProfileByDpSmsReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPostByDp(json);
        System.out.println(new String(msgBype));
	}
	
	@Test
	public void MasterDelete() throws Exception {
		MsgHeader header = new MsgHeader("masterDeleteByDpSms");
        MasterDeleteByDpReqBody requestBody = new MasterDeleteByDpReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		requestBody.setIccid("00");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = BaseHttp.doPostByDp(json);
        System.out.println(new String(msgBype));
	}
}
