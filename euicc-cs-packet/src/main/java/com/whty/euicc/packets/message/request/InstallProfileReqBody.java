package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;
/**
 * 下载安装profile请求对象
 * @author Administrator
 *
 */
@MsgType("installProfile")
public class InstallProfileReqBody extends EuiccReqBody {
	private String isdPAid;
	private String profileFile;//请求参数不用填值,是给卡调用时的中间变量

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
