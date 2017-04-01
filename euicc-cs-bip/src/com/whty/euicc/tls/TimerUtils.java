package com.whty.euicc.tls;
/*
 * 暂无使用，根据脚本所得
 */

import static com.whty.euicc.tls.BipUtils.*;

import com.whty.euicc.constant.CmdType;
import com.whty.euicc.constant.ConstantParam;
import com.whty.euicc.pcsinterface.PCSInterface5java;

public class TimerUtils{
	public static String Timer_id = "";
    public static String Timer_status = "false";
    static String Cardresp  = "";
    public static String sw2 = "";
    
	public static String openTimerOK() throws Exception{
		if(TimerTrigger_Exist.equals("true")){
		    resp = tkResp;
		    Timer_id = AnalyseUtils.strmidh(tkResp,13,1);
	        String returnStatus = BipCommandUtils.reccmptxt(resp,"D011" + AnalyseUtils.totlv("81","012700") + 
	    		    ConstantParam.UICC_TO_TERMINAL + "A401" + Timer_id + "A503210000");
	        if(returnStatus.equals("9000")){
	            String downData = AnalyseUtils.totlv("81","012700") + ConstantParam.TERMINAL_TO_UICC + 
	    		        ConstantParam.TERMRESP00 + "A401" + Timer_id;
	            String apdu = AnalyseUtils.totlv("81040000", downData);
	            //termrespfull %sterm
	            //PCSInterface5java.sendText(apdu);
	            tkResp = resp;
	            Timer_status="true";
	            }
		    }
		    return tkResp;
	    }	 
	    //;comptxta 9000

	public static void closeTimerOK() throws Exception{	
		if(TimerTrigger_Exist.equals("true")){
			resp = tkResp;
			String returnStatus = BipCommandUtils.comptxta(resp,"D00C" + AnalyseUtils.totlv("81","012701") + 
					ConstantParam.UICC_TO_TERMINAL + "A401" + Timer_id + "9000");
			if(returnStatus.equals("9000")){
			    String downData = AnalyseUtils.totlv("81","012700") + ConstantParam.TERMINAL_TO_UICC + 
	    		        ConstantParam.TERMRESP00 + "A401" + Timer_id + "A503000010";
			    String apdu = AnalyseUtils.totlv("81040000", downData);
			    //termrespfull %sterm
			    //PCSInterface5java.sendText(apdu);
			    tkResp = resp;
			    Timer_status="false";
			}
		}
	}

	public static void openTiemr() throws Exception {
      String apdu = "801400000C810301130002028281830100";
       Cardresp = PCSInterface5java.sendText(apdu, CmdType.TetminalResponse);
       sw2 = AnalyseUtils.LastByte(Cardresp);
       
       apdu = "80F2000C00";
       Cardresp = PCSInterface5java.sendText(apdu, CmdType.TetminalResponse);
       sw2 = AnalyseUtils.LastByte(Cardresp);
       while (!sw2.equals("")) {
			Cardresp = PCSInterface5java.sendText("80120000" + sw2,
					CmdType.FETCh);
			AnalyseUtils.bipsixthByte(Cardresp);
			Cardresp = PCSInterface5java.sendText(
					  "801400000F810301270002028281830100240101",  //终端响应，出自eUICC函数定义
					CmdType.TetminalResponse);
			sw2 = AnalyseUtils.LastByte(Cardresp);// 9000
		}
	}
	
}
