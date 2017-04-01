package com.whty;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import com.whty.efs.common.util.HttpUtil;




public class SmsSendTest {
	String url = "http://localhost:8080/euicc-efs-container/sms/sendSms";
	
	@Test
	public void sendSms()throws Exception{
		Map<String, String> argsMap = new HashMap<String, String>() {{ 
	        put( "report" , "1" ); 
	        put( "tp_pid" , "127" ); 
	        put( "tp_udhi" , "1" ); 
	        put( "msg_fmt" , "246" ); 
	        put( "src" , "10659818017705" ); 
	        put( "dest" , "2" ); 
	        put( "msg" , "2" ); 
	        put( "userid" , "2" ); 
	        put( "Platform" , "2" ); 
		}}; 
		
		
		String resp = HttpUtil.post(url, argsMap);
		System.out.println(resp);
	}

}
