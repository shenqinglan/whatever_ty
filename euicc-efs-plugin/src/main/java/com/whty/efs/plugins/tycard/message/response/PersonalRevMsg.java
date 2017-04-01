package com.whty.efs.plugins.tycard.message.response;

/**
 * 个人化接收报文类
 * @author Administrator
 *
 */
public class PersonalRevMsg {
	/**
	 * 报文长度，4个字节
	 */
	private String msgLength;
	
	/**
	 * 报文类型，4个字节
	 * 00001001    个人化请求
	 * 80001002    个人化指令处理
	 * 80001003    个人化结束
	 */
	private String cmdType;
	
	/**
	 * 保留字节，16个字节
	 */
	private String remark;
	
	/**
	 * 卡号长度，4个字节
	 */
	private String cardLength;
	
	/**
	 * 卡号，LV
	 */
	private String cardNumber;
	
	/**
	 * apdu长度，4个字节
	 */
	private String apduLength;
	
	/**
	 * apdu指令，LV
	 */
	private String capdu;
	
	/**
	 * 返回个人化系统状态，0000代表正常结束，否则代表非正常结束
	 */
	private String status;

	public String getMsgLength() {
		return msgLength;
	}

	public void setMsgLength(String msgLength) {
		this.msgLength = msgLength;
	}

	public String getCmdType() {
		return cmdType;
	}

	public void setCmdType(String cmdType) {
		this.cmdType = cmdType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCardLength() {
		return cardLength;
	}

	public void setCardLength(String cardLength) {
		this.cardLength = cardLength;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getApduLength() {
		return apduLength;
	}

	public void setApduLength(String apduLength) {
		this.apduLength = apduLength;
	}

	public String getCapdu() {
		return capdu;
	}

	public void setCapdu(String capdu) {
		this.capdu = capdu;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
