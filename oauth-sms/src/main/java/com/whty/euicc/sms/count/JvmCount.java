package com.whty.euicc.sms.count;

import java.util.concurrent.atomic.AtomicInteger;

import com.whty.euicc.sms.constants.SMSConstants;
import com.whty.euicc.util.StringUtil;

/**
 * counter值递增
 * @author Administrator
 *
 */
public class JvmCount implements SMSConstants {
	private static String cntr = SMSConstants.CNTR;

	/**
	 * 计数器值递增
	 * @return
	 */
	public static String increCntr(String lastcntr) {
		cntr = lastcntr;
		int sdd = transfer(cntr);
		int nowTimes = new AtomicInteger(sdd).addAndGet(1);
		String hexTempString = StringUtil.intToHex(nowTimes);
		return  "00"+hexTempString;
	}
	
	public static int transfer(String str) {
		return Integer.valueOf(str, 16).intValue();
	}

}
