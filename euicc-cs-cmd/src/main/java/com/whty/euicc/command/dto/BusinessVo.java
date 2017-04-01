package com.whty.euicc.command.dto;

import java.util.Date;

public class BusinessVo {
	
    private String msgType;

    private String step;

    /**
     * 0：未完成；1：已完成
     */
    private String isComplete;

    private Date lastDate;

    private String sessionId;

    private String cardNO;
    
    /**
     * 终端在整个业务中是否完成；0：未完成；1：已完成
     */
    private String status;
    
    /**
     * JSON格式（尽量不要存放后续步骤没有用到的参数，每个步骤以及原子任务需要存放的参数在步骤和原子任务说明处需要定义好）
     */
    private byte[] param;

	public byte[] getParam() {
		return param;
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public String getCardNO() {
		return cardNO;
	}

	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setParam(byte[] param) {
		this.param = param;
	}

}
