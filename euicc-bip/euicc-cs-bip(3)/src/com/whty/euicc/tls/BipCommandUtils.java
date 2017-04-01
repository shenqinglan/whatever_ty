package com.whty.euicc.tls;

import com.whty.euicc.tls.AnalyseUtils;
import com.whty.euicc.constant.ConstantParam;

public class BipCommandUtils {
	static String Timer_id = "";

	//下发通知事件
	public static String dataAvailable(String length, String channel_status)
			throws Exception {
		String tag = "80C20000";
		String data = "D60E990109" + ConstantParam.TERMINAL_TO_UICC + "B802" + channel_status
				+ "B701" + length;
		return AnalyseUtils.totlv(tag, data);

	}
	
	//暂未使用
	public static String comptxta(String data,String target){//待调试
		//String target = "D00C8103012701"+ConstantParam.UICC_TO_TERMINAL+"A401"+Timer_id+"9000";
		if(data.equals(target)){
			return "9000";
		}else{
			return "0000";
		}
	}
	
	//暂未使用
	public static String reccmptxt(String data,String target){//待调试
		//String target = "D0118103012700"+ConstantParam.UICC_TO_TERMINAL+"A401"+Timer_id+"A503210000";
		if(data.equals(target)){
			return "9000";
		}else{
			return "0000";
		}
	}
	
	//暂未使用
	public static String gettlv(String resp,String tag){
		int offset = resp.indexOf(tag);
		String length = resp.substring(offset+2, offset+4);
		String result = resp.substring(offset, offset+4+AnalyseUtils.atoi(length)*2);
		return result;
	}

	//暂未使用
	public static String cmptlvs08(String data,String ...s) {//待调试
		for(int i = 0;i < s.length;i++){
			String sIgnoreHigh = s[i].substring(1);
			//System.out.println(sIgnoreHigh);
			if(data.indexOf(sIgnoreHigh)==-1){
				return "9403";
			}
		}
		return "9000";
	}

	//暂未使用
	public static String getbipupdata() {//待调试
		return "9000";
	}
   
	//暂未使用
	public static String termrespfull(String ...str){
		String result = "";
		for(int i=0;i<str.length;i++){
			result +=str[i];
		}
		return result;
	}

	
	
}
