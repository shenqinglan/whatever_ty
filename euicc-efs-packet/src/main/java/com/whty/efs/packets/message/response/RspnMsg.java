package com.whty.efs.packets.message.response;

import org.apache.commons.lang3.StringUtils;

/**
 * 业务响应
 * @author gaofeng
 *
 */
public class RspnMsg{
	/**业务状态*/
	private String sts;
	/**业务拒绝处理码*/
	private String rjctCd;
	/**业务拒绝信息*/
	private String rjctInfo;
	/**结束标识00：进行中 01：交易结束*/
	private String endFlag;
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
	public String getRjctCd() {
		return rjctCd;
	}
	public void setRjctCd(String rjctCd) {
		this.rjctCd = rjctCd;
	}
	public String getRjctInfo() {
		return rjctInfo;
	}
	public void setRjctInfo(String rjctInfo) {
		this.rjctInfo = rjctInfo;
	}
	public String getEndFlag() {
		return endFlag;
	}
	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}
	
	public RspnMsg(){
	}
	
	public RspnMsg(String sts, String rjctCd){
		this.sts = sts;
		if (StringUtils.isNotBlank(rjctCd)){
			setRjctCd(rjctCd);
		}
	}
	
	public RspnMsg(String sts, String rjctCd, String info) {
		this(sts, rjctCd);
		this.rjctInfo = info;
	}	
	
}
