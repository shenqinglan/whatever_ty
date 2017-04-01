package com.whty.euicc.dp.http;

import org.junit.Test;
import com.google.gson.Gson;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.MsgHeader;
import com.whty.euicc.packets.message.request.CreateISDPByDpReqBody;
import com.whty.euicc.packets.message.request.DeleteProfileByDpReqBody;
import com.whty.euicc.packets.message.request.DisableProfileByDpReqBody;
import com.whty.euicc.packets.message.request.EnableProfileByDpReqBody;
import com.whty.euicc.packets.message.request.InstallProfileByDpReqBody;
import com.whty.euicc.packets.message.request.PersonalAllISDPReqBody;
import com.whty.euicc.dp.http.base.BaseHttp;
/**
 * Https下SM-DP的全部业务流程测试用例类
 * @author Administrator
 *
 */
public class AllProfileTest {
	@Test
	public void AllProfile(){
		//1.创建ISD-P（DP）
		MsgHeader header = new MsgHeader("createISDPByDp");
		CreateISDPByDpReqBody requestBody = new CreateISDPByDpReqBody();
		requestBody.setEid("89001012012341234012345678901224");
		EuiccMsg euiccMsg = new EuiccMsg(header, requestBody);
		String json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		byte[] msgBype = {};
		try {
			msgBype = BaseHttp.doPostByDp(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(new String(msgBype));
        
        
        //2.个人化Profile与协商密钥（DP）
        String certDpEcdsa = "2281B180E20900AC3A01A97F2181A59301024201025F2001029501885F2404214501017303C801017f4946B041046466E042804FAAC48F839EA53E85D0B8B93F2F015028A472F07F3227AF408170ACFB39D198BA7D0DCFF3DE5032A6CC8F6ACC84EF556BFE530DEC0FF75F2AF59AF001005F3740F7BA7DE1E625D5721A5756F9B9D8D1A25D1667300801BE063AE1FED8CA9E0107B12F00500EEE49D82A5065542E0A38FDD86E276A804BF859CB5528C0457CC830";
        header = new MsgHeader("personalAllISDP");
        PersonalAllISDPReqBody requestBody2 = new PersonalAllISDPReqBody();
		requestBody2.setEid("89001012012341234012345678901224");
		requestBody2.setIccid("00");
		requestBody2.setCertDpEcdsa(certDpEcdsa);
		euiccMsg = new EuiccMsg(header, requestBody2);
		json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		try {
			msgBype = BaseHttp.doPostByDp(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(new String(msgBype));
        
        
        //3.下载Profile(DP)
        header = new MsgHeader("installProfileByDp");
        InstallProfileByDpReqBody requestBody3 = new InstallProfileByDpReqBody();
        requestBody3.setEid("89001012012341234012345678901224");
        requestBody3.setIccid("00");
        euiccMsg = new EuiccMsg(header, requestBody3);
		json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		try {
			msgBype = BaseHttp.doPostByDp(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(new String(msgBype));
        
		
		//4.启用Profile(DP)
		header = new MsgHeader("enableProfileByDp");
		EnableProfileByDpReqBody requestBody4 = new EnableProfileByDpReqBody();
		requestBody4.setEid("89001012012341234012345678901224");
		requestBody4.setIccid("00");
		euiccMsg = new EuiccMsg(header, requestBody4);
		json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		try {
			msgBype = BaseHttp.doPostByDp(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(new String(msgBype));
        //启用完后有SMS通知
        
        
        //5.禁用Profile(DP)
        header = new MsgHeader("disableProfileByDp");
        DisableProfileByDpReqBody requestBody5 = new DisableProfileByDpReqBody();
		requestBody5.setEid("89001012012341234012345678901224");
		requestBody5.setIccid("00");
		euiccMsg = new EuiccMsg(header, requestBody5);
		json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		try {
			msgBype = BaseHttp.doPostByDp(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(new String(msgBype));
        //禁用完后有SMS通知

        
        //6.删除Profile(DP)
        header = new MsgHeader("profileDeletionByDp");
        DeleteProfileByDpReqBody requestBody6 = new DeleteProfileByDpReqBody();
		requestBody6.setEid("89001012012341234012345678901224");
		requestBody6.setIccid("00");
		euiccMsg = new EuiccMsg(header, requestBody6);
		json = new Gson().toJson(euiccMsg, EuiccMsg.class);
		try {
			msgBype = BaseHttp.doPostByDp(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(new String(msgBype));
       
	}
}
