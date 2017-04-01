package com.whty.efs.data.pojo;

import java.io.Serializable;
import java.util.Date;


/**
 * 路由信息bean
 * @author gaofeng
 *
 */
public class AccessSender implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5797360700532151821L;
	/** 发送方名称*/
	private String senderName;
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}
	public Date getExprieTime() {
		return exprieTime;
	}
	public void setExprieTime(Date exprieTime) {
		this.exprieTime = exprieTime;
	}
	/** 是否可用*/
	private String isEnable;
	/** 过期时间*/
	private Date exprieTime;
}
