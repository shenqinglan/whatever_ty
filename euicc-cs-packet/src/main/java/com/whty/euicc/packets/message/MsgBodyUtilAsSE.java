package com.whty.euicc.packets.message;

import com.whty.euicc.packets.message.request.attr.WithData_MsgBody;
import com.whty.euicc.packets.message.request.attr.WithScp_MsgBody;
import com.whty.euicc.packets.message.response.attr.WithToken_MsgBody;

public class MsgBodyUtilAsSE extends MsgBodyUtil {

	public static String getData(MsgBody body) {
		if (body instanceof WithData_MsgBody) {
			return ((WithData_MsgBody) body).getData();
		}
		return null;
	}

	public static String getScp(MsgBody body) {
		if (body instanceof WithScp_MsgBody) {
			return ((WithScp_MsgBody) body).getScp();
		}
		return null;
	}

	public static void setToken(MsgBody body, String token) {
		if (body instanceof WithToken_MsgBody) {
			((WithToken_MsgBody) body).setToken(token);
		}
	}
}
