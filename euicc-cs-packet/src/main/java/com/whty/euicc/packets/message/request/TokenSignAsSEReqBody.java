package com.whty.euicc.packets.message.request;

import com.whty.euicc.packets.message.MsgType;
import com.whty.euicc.packets.message.request.attr.WithCardNO_MsgBody;
import com.whty.euicc.packets.message.request.attr.WithData_MsgBody;
import com.whty.euicc.packets.message.request.attr.WithPartnerOrgCode_MsgBody;
import com.whty.euicc.packets.message.request.attr.WithScp_MsgBody;
import com.whty.euicc.packets.message.request.attr.WithSsdAid_MsgBody;

/**
 * Token签名
 * 
 * @author Ocea
 *
 */
@MsgType("tath.006.002.01")
public class TokenSignAsSEReqBody extends RequestMsgBody implements
		WithPartnerOrgCode_MsgBody, WithCardNO_MsgBody, WithSsdAid_MsgBody,
		WithScp_MsgBody, WithData_MsgBody {

	public TokenSignAsSEReqBody() {
	}

	/** 合作机构编码 */
	private String partnerOrgCode;
	/** 卡号 */
	private String cardNO;
	/** 辅助安全域AID */
	private String ssdAID;
	/** 卡片安全协议 */
	private String scp;
	/** Token签名数据 */
	private String data;

	public String getPartnerOrgCode() {
		return partnerOrgCode;
	}

	public void setPartnerOrgCode(String partnerOrgCode) {
		this.partnerOrgCode = partnerOrgCode;
	}

	public String getCardNO() {
		return cardNO;
	}

	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}

	public String getSsdAID() {
		return ssdAID;
	}

	public void setSsdAID(String ssdAID) {
		this.ssdAID = ssdAID;
	}

	public String getScp() {
		return scp;
	}

	public void setScp(String scp) {
		this.scp = scp;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
