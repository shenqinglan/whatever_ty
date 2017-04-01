package com.whty.efs.packets.message;

import com.whty.efs.packets.message.request.attr.WithAppAidVersion_MsgBody;



public class MsgBodyUtil {
	public static String getAppAID(Body body){
		if (body instanceof WithAppAidVersion_MsgBody) {
			return ((WithAppAidVersion_MsgBody) body).getAppAID();
		}
		return null;
	}
	
	public static String getAppVersion(Body body){
		if (body instanceof WithAppAidVersion_MsgBody) {
			return ((WithAppAidVersion_MsgBody) body).getAppVersion();
		}
		return null;
	}
}
