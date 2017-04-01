package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;
import com.whty.euicc.packets.message.request.attr.AttrAppInstalledTag;
import com.whty.euicc.packets.message.request.attr.WithCardNO_MsgBody;

/**
 * 应用查询报文入参
 * @author dengjun
 *
 */
@MsgType("tath.016.001.01")
public class AppQueryReqBody extends RequestMsgBody implements WithCardNO_MsgBody,AttrAppInstalledTag {
	public AppQueryReqBody(){}
	
	/**
	 *  ATS
	 */
	private String cardNO;
	/**
	 *  应用安装状态 00未安装01已安装
	 *  02 已经下载未个人化
	 *  03 已经个人化未激活
	 */
	private String appInstalledTag;
	
	
	public String getAppInstalledTag() {
		return appInstalledTag ;
	}
	
	public void setAppInstalledTag(String appInstalledTag) {
		this.appInstalledTag = appInstalledTag ;
	}

	
	public String getCardNO() {
		return cardNO ;
	}

	
	public void setCardNO(String cardNO) {
		this.cardNO = cardNO ;
	}

	


}
