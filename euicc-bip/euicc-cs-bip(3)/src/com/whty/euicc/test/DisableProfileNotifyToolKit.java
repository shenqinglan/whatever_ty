package com.whty.euicc.test;


import com.whty.euicc.constant.CmdType;
import com.whty.euicc.impl.SMSPP_MT;
import com.whty.euicc.pcsinterface.PCSInterface5java;
import com.whty.euicc.tls.BipUtils;

public class DisableProfileNotifyToolKit {

	
	public static void main(String[] args) throws Exception {
		DXADF();
        String terminalStr = "FFFFFFFFFFFF1FFFFF0302FFFF9FFFEFDFFF0FFF0FFFFF0FFF03003F7FFF03";//模拟手机的各项参数，出自eUICC函数定义，1336行
        BipUtils.terminalProfile(terminalStr);
        BipUtils.download_location_status();
	}
	
	
	/**
	 * 禁用联通profile
	 * 
	 */
	public static void DXADF(){
		String DXADF = "A0000003431002FF86FF0389FFFFFFFF";
		String len = "10";
		String head = "00a4040c".toUpperCase();
		String apdu = head + len + DXADF;
		PCSInterface5java.sendText(apdu, CmdType.SMS_0348);
	}
}

