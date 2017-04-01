package com.whty.efs.packets.message.request;

import com.whty.efs.packets.message.MsgBody;
import com.whty.efs.packets.message.MsgType;

/**
 * request of installation profile 
 * @author Administrator
 *
 */
@MsgType("installProfile")
public class ES3InstallProfileReqBody extends EuiccReqBody implements MsgBody {
	private String isdPAid;
	private String profileFile;//请求参数不用填值,是给卡调用时的中间变量
	private String sdAid;

	public String getSdAid() {
		return sdAid;
	}

	public void setSdAid(String sdAid) {
		this.sdAid = sdAid;
	}

	public String getIsdPAid() {
		return isdPAid;
	}

	public void setIsdPAid(String isdPAid) {
		this.isdPAid = isdPAid;
	}

	public String getProfileFile() {
		return profileFile;
	}

	public void setProfileFile(String profileFile) {
		this.profileFile = profileFile;
	}

	
}

