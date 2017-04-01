package com.whty.euicc.pcsinterface;

public class PCSInterface5java {
	 static
	  {
	     System.loadLibrary("pscCard");
	  }

	  public static native String sendText_st(String strCommand, int cmdType);
	  
	  public static String sendText(String strCommand, int cmdType)
	 {
		System.out.println("下发指令-->" + strCommand);
		String strResp = PCSInterface5java.sendText_st(strCommand , cmdType);
		System.out.println("Card_back:  "+strResp);
		return strResp;
	 }
	
}
