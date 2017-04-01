package gsta.com.packet.message.request;

import gsta.com.packet.message.MsgType;
import gsta.com.packet.message.request.base.RequestMsgBody;

@MsgType("/gsma/rsp2/es9plus/handleNotification")
public class HandleNotificationReq extends RequestMsgBody {
	private String profileInstallationResultData;
	private String euiccSignPIR;
	public String getProfileInstallationResultData() {
		return profileInstallationResultData;
	}
	public void setProfileInstallationResultData(
			String profileInstallationResultData) {
		this.profileInstallationResultData = profileInstallationResultData;
	}
	public String getEuiccSignPIR() {
		return euiccSignPIR;
	}
	public void setEuiccSignPIR(String euiccSignPIR) {
		this.euiccSignPIR = euiccSignPIR;
	}

}
