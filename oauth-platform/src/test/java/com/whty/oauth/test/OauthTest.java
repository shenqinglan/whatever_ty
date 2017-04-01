package com.whty.oauth.test;

import org.junit.Test;
import com.alibaba.fastjson.JSONObject;
import com.whty.oauth.platform.util.HTTPWeb;
import com.whty.oauth.platform.util.HexUtils;

/**
 * @ClassName AddEuiccCard
 * @author Administrator
 * @date 2017-3-8 上午10:29:24
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class OauthTest {
	
	String host = "127.0.0.1";
	String port = "8090";

	@Test
	public void addCardInfo() {
		String url = "http://"+host+":"+port+"/oauth/v1/platform/test/saveEuiccCard";
		int index = 1;
		JSONObject json = new JSONObject();
		json.put("cardManufacturerId", "0"+index);
		json.put("msisdn", "18860445622"+index);
		json.put("eid", "123456"+index);
		String data = HTTPWeb.post(url, json.toJSONString());
		System.out.println("data >>> "+data);
	}
	
	@Test
	public void triggerRegister() {
		String url = "http://"+host+":"+port+"/oauth/v1/platform/down/triggerRegister";
		JSONObject json = new JSONObject();
		json.put("phoneNo", "18995621216");
		json.put("transactionId", "1231212145954712");
		String data = HTTPWeb.post(url, json.toJSONString());
		System.out.println("data >>> "+data);
	}
	
	@Test
	public void baseOauth() {		
		String url = "http://127.0.0.1:8090/oauth/v1/platform/down/baseOauth";
		JSONObject json = new JSONObject();
		json.put("phoneNo", "18995621216");
		json.put("format", "04");
		json.put("authType", "11");
		json.put("authData", "001122");
		String data = HTTPWeb.post(url, json.toJSONString());
		System.out.println("data >>> "+data);
	}
	
	@Test
	public void validCodeOauth() {
		String url = "http://"+host+":"+port+"/oauth/v1/platform/down/validCodeOauth";
		JSONObject json = new JSONObject();
		json.put("phoneNo", "18995621216");
		json.put("format", "04");
		json.put("authType", "01");
		json.put("authData", "输入英文和数字");
		String data = HTTPWeb.post(url, json.toJSONString());
		System.out.println("data >>> "+data);
	}
	
	@Test
	public void changePWD() {
		String url = "http://"+host+":"+port+"/oauth/v1/platform/down/changePwd";
		JSONObject json = new JSONObject();
		json.put("phoneNo", "18995621216");
		String data = HTTPWeb.post(url, json.toJSONString());
		System.out.println("data >>> "+data);
	}
	
	@Test
	public void resetPWD() {
		String url = "http://"+host+":"+port+"/oauth/v1/platform/down/resetPwd";
		JSONObject json = new JSONObject();
		json.put("phoneNo", "18995621216");
		String data = HTTPWeb.post(url, json.toJSONString());
		System.out.println("data >>> "+data);
	}
	
	@Test
	public void getRegisterStatus() {
		String url = "http://"+host+":"+port+"/oauth/v1/platform/down/getRegisterStatus";
		JSONObject json = new JSONObject();
		json.put("phoneNo", "18995621216");
		String data = HTTPWeb.post(url, json.toJSONString());
		System.out.println("data >>> "+data);
	}
	
	@Test
	public void clearRegister() {
		String url = "http://"+host+":"+port+"/oauth/v1/platform/down/clearRegister";
		JSONObject json = new JSONObject();
		json.put("phoneNo", "13098813754");
		String data = HTTPWeb.post(url, json.toJSONString());
		System.out.println("data >>> "+data);
	}
	
	@Test
	public void receiveSms() {
		String url = "http://"+host+":"+port+"/oauth/receiveSms";
		JSONObject json = new JSONObject();
		json.put("src", "13098813754");
		json.put("transactionID", "123456");
		json.put("msg", "02700000201506302525CA01018307CDCAADFC54EC616CB07FB75D904461CC405ED20AB1DA");
		String data = HTTPWeb.post(url, json.toJSONString());
		System.out.println("data >>> "+data);
	}

	@Test
	public void cleanStatus() {
		String url = "http://"+host+":"+port+"/oauth/v1/platform/down/clearRegister";
		JSONObject json = new JSONObject();
		json.put("phoneNo", "18995621216");
		String data = HTTPWeb.post(url, json.toJSONString());
		System.out.println("data >>> "+data);
	}
	
	/**
	 * UCS2转换测试。基于快捷认证，编码格式选择08时，通过此方法转换
	 */
	@Test
	public void testCode() {		
		System.out.println(HexUtils.convert("dghaodignz的过后为何在紧挨着吃"));
	}

	/**
	 * 对应卡测试用例，基本快捷认证_N002，基于快捷认证，编码格式选择04时，通过此方法转换
	 */
	@Test
	public void testString2Hex() {		
		System.out.println(HexUtils.getHexResult("dghaozlkda后给了那纳为去吃提前"));  		
	}

}
