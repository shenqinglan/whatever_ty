package com.whty.efs.packets.message.request;


import com.whty.efs.packets.message.MsgType;


/**
 * 应用查询请求报文体
 * @author xiaoxiao
 *
 */
@MsgType("tath.021.002.01")
public class AppQueryBody extends RequestMsgBody {

	 public String getSeID() {
		return seID;
	}
	public void setSeID(String seID) {
		this.seID = seID;
	}
	public String getAppInstalledTag() {
		return appInstalledTag;
	}
	public void setAppInstalledTag(String appInstalledTag) {
		this.appInstalledTag = appInstalledTag;
	}
	private String seID;
	 private String appInstalledTag;

}
