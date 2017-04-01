package com.whty.euicc.test;

import com.whty.euicc.constant.CmdType;
import com.whty.euicc.pcsinterface.PCSInterface5java;
import com.whty.euicc.tls.BipUtils;

public class EnableProfileNotifyToolkit {

	
	public static void main(String[] args) throws Exception {
		LTADF();
        String terminalStr = "FFFFFFFFFFFF1FFFFF0302FFFF9FFFEFDFFF0FFF0FFFFF0FFF03003F7FFF03";//模拟手机的各项参数，出自eUICC函数定义，1336行
        BipUtils.terminalProfile(terminalStr);
        BipUtils.download_location_status();
	}
	
	/**
	 * 启用联通的profile
	 */
	public static void LTADF(){
		String LTADF = "A0000000871002FF86FFFF89FFFFFFFF";
		String len = "10";
		String head = "00a4040c".toUpperCase();
		String apdu = head + len + LTADF;
		System.out.println(apdu);
		PCSInterface5java.sendText(apdu, CmdType.SMS_0348);
	}
	
}
