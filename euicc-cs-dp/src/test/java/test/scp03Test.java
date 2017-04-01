package test;

import org.junit.Test;

import com.whty.euicc.common.apdu.ToTLV;
import com.whty.euicc.common.utils.ByteUtil;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.dp.handler.connectivityparameters.ParametersApdu;
import com.whty.euicc.dp.handler.connectivityparameters.TlvTokenBean;

public class scp03Test {
	private String eid = "123456";
	private String isdPAID = "A0000005591010FFFFFFFF8900001300";
	/*
	@Test
	public void externalTest() {
		InitTagsOfIsdp1Handler sdHandler = new InitTagsOfIsdp1Handler();
		String counterString = "000001";
//		String resultString = sdHandler.externalCmd(eid, isdPAID,counterString);
//		System.out.println(resultString);
		
		
	}
	*/
	@Test
	public void tlvTest() throws Exception{
		ParametersApdu para = new ParametersApdu();
		/*
		String result1 = para.tlv45IsdpApdu();
		System.out.println(result1);
		String result2 = para.tlv42IsdpApdu();
		System.out.println(result2);
		String result3 = para.tlv5F20IsdpApdu();
		System.out.println(result3);
		*/
		TlvTokenBean result4 = para.tlvToken();
		result4.setCmdString(result4.getCmdString());
		result4.setTokenVerKcv(result4.getTokenVerKcv());
		System.out.println(result4.getCmdString());
		System.out.println(result4.getTokenVerKcv());
	}
	 private String commandOrg(){
		  String smsCenter = "316540942001";
//		  String smsCenter = record.getSmscenterNo();
		  String centerNoTemp = ByteUtil.reverseSmsCenNo(smsCenter);
		  String resultCenterNo = ToTLV.toTLV("06", "91" + centerNoTemp);
		  String paramISDP = ToTLV.toTLV("A0", resultCenterNo);
		  String cmdData = "80E28800" + ToTLV.toTLV(ToTLV.toTLV("3A07", paramISDP));
		  return cmdData;
	  }
	@Test
	public void ssTest(){
		String orig = "316540942000";
		String resultString = ByteUtil.reverseSmsCenNo(orig);
		System.out.println(resultString);
	}
	@Test
	public void comTest(){
		String s = commandOrg();
		System.out.println(s);
	}

}
