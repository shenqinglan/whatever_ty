package com.whty.efs.packets.message.response;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用个人化响应报文体
 * @author gaofeng
 *
 */
public class AppPersonialResp extends BaseRespBody {
	/**应用AID*/
	private String appAID   ;
	/**应用版本*/
	private String appVersion   ;
	/**主账号*/
	private String pan   ;
	/**命令APDU脚本*/
	private List<Capdu> cApdu  = new ArrayList<Capdu>(0);
	
	public String getAppAID() {
		return appAID;
	}
	public void setAppAID(String appAID) {
		this.appAID = appAID;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public List<Capdu> getcApdu() {
		return cApdu;
	}
	public void setcApdu(List<Capdu> cApdu) {
		this.cApdu = cApdu;
	}
}
