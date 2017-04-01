package com.whty.euicc.handler.base;

import com.telecom.http.tls.test.Util;

public class AbstractHandler {
	private static int cmdNum=2;
	/**
	 * 下发apdu或者其它形式命令给eUICC卡片
	 * @param requestStr
	 * @return
	 */
	public byte[] handle(String requestStr) {
		return httpPostResponseNoEnc(requestStr);
	}
	
	/**
	 * 处理eUICC卡片返回结果（校验，处理）
	 * @param requestStr
	 * @return
	 */
	public boolean checkProcessResp(String requestStr) {
		return true;
	}
	
	/**
	 * 返回
	 * @param requestStr
	 * @return
	 */
	public byte[] httpPostResponseByRsp (String type,String body){
		String data="HTTP/1.1 200 OK \r\n"+
        "X-Admin-Protocol: gsma/rsp/v2.0.0 \r\n"+
        "Content-Type: "+type+" \r\n"+
        "Content-Length: "+body.length()+" \r\n"+
        "\r\n"+body;
		return data.getBytes();	
	}
	
	
	/**
	 * 最后返回给卡片指令
	 * @param requestStr
	 * @return
	 */
	public byte[] httpPostResponseFinalUnEnc (boolean checkResult){
		String data="HTTP/1.1 204 No Content \r\n"+
        "X-Admin-Protocol: globalplatform-remote-admin/1.0 \r\n"+
        "\r\n";
		data=Util.byteArrayToHexString(Util.toByteArray(data), "");
		System.out.println("data204 " + data);
		cmdNum = 2;
		byte dataByte[] = Util.hexStringToByteArray(data);
		return dataByte;	
	}
	
	public byte[] httpPostError(){
		String data = "HTTP/1.1 500 Intenal Server Error\r\n"+
        "X-Admin-Protocol:globalplatform-remote-admin/1.0\r\n"+
        "Content-Type:application/vnd.globalplatform.card-content-mgt;version=1.0\r\n"+
        "Content-Length:4\r\n"+
		"\r\n";
		data = Util.byteArrayToHexString(Util.toByteArray(data), "");
		String dataContent = "01020304";
		data += dataContent;
		byte dataByte[] = Util.hexStringToByteArray(data);
		return dataByte;
	}
	
	protected byte[] httpPostResponseNoEnc (String apdu){  
		String data="HTTP/1.1 200 OK\r\n"+
        "X-Admin-Protocol:globalplatform-remote-admin/1.0\r\n"+
        "X-Admin-Next-URI:/server/adminagent?cmd="+(cmdNum++)+"\r\n"+
        "Content-Type:application/vnd.globalplatform.card-content-mgt;version=1.0\r\n"+
        "X-Admin-Targeted-Application: //aid/A000000559/1010FFFFFFFF8900000100\r\n"+
        //"content-length: 51 \r\n"+
        "transfer-encoding: chunked\r\n"+
        "\r\n";
		data=Util.byteArrayToHexString(Util.toByteArray(data), "");
		
		apdu = "AE80" + apdu + "0000";
		System.out.println("apdu " + apdu);
		//扩展模式下不需要这么写，根据content-length，直接data += apdu
		data += chunkedModeString(apdu);
		byte dataByte[] = Util.hexStringToByteArray(data);
		return dataByte;
	}

	protected byte[] httpPostResponseNoEnc (String apdu, String isdr){ 
		String star_app_RID = isdr.substring(0, 10);
		String star_app_PIX = isdr.substring(10);
		String star_app_lead = "/";
		String star_app = star_app_lead + "/aid/" + star_app_RID + star_app_lead + star_app_PIX + "\r\n";
		
		String data="HTTP/1.1 200 OK\r\n"+
        "X-Admin-Protocol:globalplatform-remote-admin/1.0\r\n"+
        "X-Admin-Next-URI:/server/adminagent?cmd="+(cmdNum++)+"\r\n"+
        "Content-Type:application/vnd.globalplatform.card-content-mgt;version=1.0\r\n"+
        "X-Admin-Targeted-Application:"+star_app+
        "transfer-encoding: chunked\r\n"+
        "\r\n";
		data=Util.byteArrayToHexString(Util.toByteArray(data), "");
		
		apdu = "AE80" + apdu + "0000";
		System.out.println("apdu " + apdu);
		//扩展模式下不需要这么写，根据content-length，直接data += apdu
		data += chunkedModeString(apdu);
		byte dataByte[] = Util.hexStringToByteArray(data);
		return dataByte;
	}
	
	private String toHex(String num,int lenData) {
		String hex = Integer.toHexString(Integer.valueOf(num));
		if (hex.length() % 2 != 0) {
			hex = "0" + hex;
		}
		if (lenData <= 255) {
			hex = "00" + hex;
		}
		return hex.toUpperCase();
	}
	
	private String chunkedModeString(String apdu) {
		int lenData = apdu.length()/2;
		String lenFunc = toHex(String.valueOf(lenData),lenData);
		lenFunc = lenFunc.replaceFirst("^0*", "");

		String strLen = "";
		for (int i = 0; i < lenFunc.length(); i++) {
			String tmp = lenFunc.substring(i,i+1);

			tmp = Util.byteArrayToHexString(Util.toByteArray(tmp), "");
			strLen += tmp;
		}
		//<--! %sBody_command=0D0A+%slen+0D0A+ %sBody_command
		//data里面已经存在两个0D0A,所以第一个ODOA不需要加上
		String bodySub = strLen + "0D0A" + apdu;
		
		//<--! %sbody=%sBody_sub + 0D0A300D0A
		String body = bodySub + "0D0A300D0A";
		
		return body;
	}
}
