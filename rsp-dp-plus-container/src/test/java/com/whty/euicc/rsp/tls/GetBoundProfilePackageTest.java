package com.whty.euicc.rsp.tls;

import org.junit.Test;
import com.whty.euicc.rsp.packets.message.MessageHelper;
import com.whty.euicc.rsp.packets.message.request.GetBoundProfilePackageReq;
import com.whty.euicc.rsp.packets.parse.EuiccMsgParse;
import com.whty.tls.client.ecc.EccCardShakeHandsClient;

public class GetBoundProfilePackageTest {
	String path = "/gsma/rsp2/es9plus/getBoundProfilePackage"; 
	String type = "application/json";
	
	@Test
	public void getBoundProfilePackageTest()throws Exception{
		String data = EuiccMsgParse.prepareHttpParam(path,type,reqBodyByBean());
		String data2 = EuiccMsgParse.prepareHttpParam(path,type,reqBodyByBean2());
		String data3 = EuiccMsgParse.prepareHttpParam(path,type,"");
		String serverResult = new EccCardShakeHandsClient().clientTestByRsp(data, "127.0.0.1", 8765, data2, data3);
		System.out.println("serverResult >> \n" + serverResult);
	}
	
	private String reqBodyByBean(){
		GetBoundProfilePackageReq req = new GetBoundProfilePackageReq();
		req.setTransactionId("80081122334455667700");
		req.setEuiccSigned2("3070800811223344556677005F494104AE708AEEB4C8344781001A51F9E9E54653FA73F575D801FFAB216B9427C04B2FD7274105312E1149D316DFBEB24B8B50AEE75FCA2A57DF66A56029A01012CE170420D2FFE7A27BFAB816B67E58E7B2C5CDCB92363DC32CA7760F38D91B9D525B9EB0");
		req.setEuiccSignature2("5F37401E1934CFB16F378794D5FD59D33DD36F62CF8036749F57A31E0BAA6F5E87D5F322CF7E10663D7B9B44AC4FDA78A91FD6EF5FD629E7BE54DDB1F171BDE7D10D9F");
		return MessageHelper.gs.toJson(req);
	}
	
	private String reqBodyByBean2(){
		GetBoundProfilePackageReq req = new GetBoundProfilePackageReq();
		req.setTransactionId("80081122334455667700");
		return MessageHelper.gs.toJson(req);
	}
	
	

}
