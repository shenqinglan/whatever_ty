/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.ga.entity;

import java.util.Date;

/**
 * 出入记录Entity
 * @author liuwsh
 * @version 2017-02-17
 */
public class GaEntranceInfo {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String doorNo;	
	private Date swipeTime;		// 刷卡时间
	private String cardNo;
	private String inOrOut;		// 出还是入
	/**
	 * @return the doorNo
	 */
	public String getDoorNo() {
		return doorNo;
	}
	/**
	 * @param doorNo the doorNo to set
	 */
	public void setDoorNo(String doorNo) {
		this.doorNo = doorNo;
	}
	/**
	 * @return the swipeTime
	 */
	public Date getSwipeTime() {
		return swipeTime;
	}
	/**
	 * @param swipeTime the swipeTime to set
	 */
	public void setSwipeTime(Date swipeTime) {
		this.swipeTime = swipeTime;
	}
	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}
	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	/**
	 * @return the inOrOut
	 */
	public String getInOrOut() {
		return inOrOut;
	}
	/**
	 * @param inOrOut the inOrOut to set
	 */
	public void setInOrOut(String inOrOut) {
		this.inOrOut = inOrOut;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
}