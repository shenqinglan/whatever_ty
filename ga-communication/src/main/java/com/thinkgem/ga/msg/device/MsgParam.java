/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.ga.msg.device;

import java.util.Date;

/**
 * Entity
 * @author liuwsh
 * @version 2017-02-17
 */
public class MsgParam {
	
	private static final long serialVersionUID = 1L;
	
	private String subCmd;
	private String subType;
	private String msUid;
	private String battery;
	private String downRssi;
	private String upRssi;
	private String downMode;
	/**
	 * @return the subCmd
	 */
	public String getSubCmd() {
		return subCmd;
	}
	/**
	 * @param subCmd the subCmd to set
	 */
	public void setSubCmd(String subCmd) {
		this.subCmd = subCmd;
	}
	/**
	 * @return the subType
	 */
	public String getSubType() {
		return subType;
	}
	/**
	 * @param subType the subType to set
	 */
	public void setSubType(String subType) {
		this.subType = subType;
	}
	/**
	 * @return the msUid
	 */
	public String getMsUid() {
		return msUid;
	}
	/**
	 * @param msUid the msUid to set
	 */
	public void setMsUid(String msUid) {
		this.msUid = msUid;
	}
	/**
	 * @return the battery
	 */
	public String getBattery() {
		return battery;
	}
	/**
	 * @param battery the battery to set
	 */
	public void setBattery(String battery) {
		this.battery = battery;
	}
	/**
	 * @return the downRssi
	 */
	public String getDownRssi() {
		return downRssi;
	}
	/**
	 * @param downRssi the downRssi to set
	 */
	public void setDownRssi(String downRssi) {
		this.downRssi = downRssi;
	}
	/**
	 * @return the upRssi
	 */
	public String getUpRssi() {
		return upRssi;
	}
	/**
	 * @param upRssi the upRssi to set
	 */
	public void setUpRssi(String upRssi) {
		this.upRssi = upRssi;
	}
	/**
	 * @return the downMode
	 */
	public String getDownMode() {
		return downMode;
	}
	/**
	 * @param downMode the downMode to set
	 */
	public void setDownMode(String downMode) {
		this.downMode = downMode;
	}
	
	
	
}