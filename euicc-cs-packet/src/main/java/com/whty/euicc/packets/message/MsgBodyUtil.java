package com.whty.euicc.packets.message;

import java.util.ArrayList;
import java.util.List;

import com.whty.euicc.packets.dto.AppQueryDto;
import com.whty.euicc.packets.message.request.Rapdu;
import com.whty.euicc.packets.message.request.attr.AttrAppInstalledTag;
import com.whty.euicc.packets.message.request.attr.AttrAppStatus;
import com.whty.euicc.packets.message.request.attr.AttrDelRes;
import com.whty.euicc.packets.message.request.attr.AttrInstRes;
import com.whty.euicc.packets.message.request.attr.WithApdu_MsgBody;
import com.whty.euicc.packets.message.request.attr.WithAppAidVersion_MsgBody;
import com.whty.euicc.packets.message.request.attr.WithCardNO_MsgBody;
import com.whty.euicc.packets.message.request.attr.WithPartnerOrgCode_MsgBody;
import com.whty.euicc.packets.message.request.attr.WithSeID_MsgBody;
import com.whty.euicc.packets.message.request.attr.WithSsdAid_MsgBody;
import com.whty.euicc.packets.message.response.BaseRespBody;
import com.whty.euicc.packets.message.response.RspnMsg;
import com.whty.euicc.packets.message.response.attr.AttrAppList;
import com.whty.euicc.packets.message.response.attr.AttrProcessId;
import com.whty.euicc.packets.message.response.attr.AttrResponseMsg;

public class MsgBodyUtil {

	public static String getSsdAID(MsgBody body) {
		if (body instanceof WithSsdAid_MsgBody) {
			return ((WithSsdAid_MsgBody) body).getSsdAID();
		}
		return null;
	}

	public static String getPartnerOrgCode(MsgBody body) {
		if (body instanceof WithPartnerOrgCode_MsgBody) {
			return ((WithPartnerOrgCode_MsgBody) body).getPartnerOrgCode();
		}
		return null;
	}

	public static String getAppAID(MsgBody body) {
		if (body instanceof WithAppAidVersion_MsgBody) {
			return ((WithAppAidVersion_MsgBody) body).getAppAID();
		}
		return null;
	}

	public static String getAppVersion(MsgBody body) {
		if (body instanceof WithAppAidVersion_MsgBody) {
			return ((WithAppAidVersion_MsgBody) body).getAppVersion();
		}
		return null;
	}

	public static String getCardNO(MsgBody body) {
		if (body instanceof WithCardNO_MsgBody) {
			return ((WithCardNO_MsgBody) body).getCardNO();
		}
		return null;
	}

	public static List<Rapdu> getRapdu(MsgBody body) {
		if (body instanceof WithApdu_MsgBody) {
			return ((WithApdu_MsgBody) body).getRapdu();
		}
		return new ArrayList<Rapdu>(0);
	}

	public static String getSeID(MsgBody body) {
		if (body instanceof WithSeID_MsgBody) {
			return ((WithSeID_MsgBody) body).getSeID();
		}
		return null;
	}

	public static String getAppInstalledTag(MsgBody body) {
		if (body instanceof AttrAppInstalledTag) {
			return ((AttrAppInstalledTag) body).getAppInstalledTag();
		}
		return null;
	}

	public static String getDelRes(MsgBody body) {
		if (body instanceof AttrDelRes) {
			return ((AttrDelRes) body).getDelRes();
		}
		return null;
	}

	public static String getInstRes(MsgBody body) {
		if (body instanceof AttrInstRes) {
			return ((AttrInstRes) body).getInstRes();
		}
		return null;
	}

	public static String getAppStatus(MsgBody body) {
		if (body instanceof AttrAppStatus) {
			return ((AttrAppStatus) body).getAppStatus();
		}
		return null;
	}

	public static void setApps(MsgBody body, List<AppQueryDto> list) {
		if (body instanceof AttrAppList) {
			((AttrAppList) body).setApps(list);
		}
	}

	/*
	 * public static void addApps(MsgBody body, AppQueryDto app) { if (body
	 * instanceof AttrAppList) { ((AttrAppList) body).addApp(app); } }
	 */
	public static void setRspnMsg(MsgBody body, RspnMsg msg) {
		if (body instanceof AttrResponseMsg) {
			((AttrResponseMsg) body).setRspnMsg(msg);
		}
	}

	public static void setProcessId(MsgBody body, String processId) {
		if (body instanceof AttrProcessId) {
			((AttrProcessId) body).setProcessId(processId);
		}

	}

	/**
	 * 转换MsgBody为指定接口实例<br>
	 * 如果转换不成功，返回null.
	 * 
	 * @param body
	 * @param clazz
	 * @return
	 * 
	 * @author baojw@whty.com.cn
	 * @date 2014年10月11日 下午4:31:35
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(MsgBody body, Class<T> clazz) {
		if (clazz.isAssignableFrom(body.getClass())) {
			return (T) body;
		} else {
			return null;
		}
	}

	/**
	 * 响应输入数据:结束标识
	 * 
	 * @param body
	 * @param endFlag
	 */
	public static void setEndFlag(MsgBody body, String endFlag) {
		if (body instanceof BaseRespBody) {
			((BaseRespBody) body).getRspnMsg().setEndFlag(endFlag);
		}
	}
}
