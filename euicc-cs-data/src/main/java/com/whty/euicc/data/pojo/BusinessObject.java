// Copyright (C) 2012 WHTY
package com.whty.euicc.data.pojo;

import java.io.Serializable;
import java.util.Date;

import com.whty.euicc.data.common.constant.KeyConstant;

/**
 * @version1.1 去掉seId、xmlId、seStatus，新增xml、status、cardNO
 * @author Administrator
 *
 */
public class BusinessObject implements Serializable {
	private static final long serialVersionUID = -7390938992298246041L ;
	
	public BusinessObject() {
		super();
	}
	
	/**
	 * 业务对象构造
	 * @param cardNO 卡号|安全载体标识
	 * @param msgType 业务模型标识－数据库中对应的ID
	 * @param xmlID		模板文件名称
	 * @param sessionID	　会话标识－请求头中的tradeRefNO
	 */
	public BusinessObject(String cardNO, String msgType, /*String xml,*/ String sessionID) {
		super();
		this.setCardNO(cardNO);
		this.setMsgType(msgType);
		this.setSessionId(sessionID);

		this.setStep("1");
		this.setLastDate(new Date());
		this.setIsComplete(KeyConstant.TrueOrFalseBool.FALSE);
		this.setStatus(KeyConstant.TrueOrFalseBool.FALSE);
	}


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

    /**
     * @return 0：未完成；1：已完成
     */
    public String getIsComplete() {
        return isComplete;
    }

    /**
     * @param isComplete
     *            0：未完成；1：已完成
     */
    public void setIsComplete(String isComplete) {
        this.isComplete = isComplete;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

   

 
	
	public byte[] getParam() {
		return param ;
	}

	
	public void setParam(byte[] param) {
		this.param = param ;
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

}
