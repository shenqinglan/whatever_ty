package com.whty.euicc.packets.message.response;

import com.whty.euicc.packets.message.response.attr.WithToken_MsgBody;

public class TokenSignAsSERespBody extends BaseRespBody implements
		WithToken_MsgBody {

	public TokenSignAsSERespBody() {
		super();
	}

	/** 合作机构编码 */
	private String partnerOrgCode;
	/** 卡号 */
	private String cardNO;
	/** 辅助安全域AID */
	private String ssdAID;
	/** Token签名数据 */
	private String token;

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
