/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.ga.msg.biz;

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
	private String data;
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
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	
	
	
	
}