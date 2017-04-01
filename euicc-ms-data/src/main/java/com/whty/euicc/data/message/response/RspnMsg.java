package com.whty.euicc.data.message.response;

import com.whty.euicc.common.utils.StringUtils;
import com.whty.euicc.data.message.response.Constant;


/**
 * 业务响应
 * @author gaofeng
 *
 */
public class RspnMsg {

	public RspnMsg(){
		// 默认业务状态为正常处理
		this.sts = Constant.SUCCESS;
		// 默认结束标识为进行中
		this.endFlag = INDEALING;
	}

	/**业务状态*/
	private String sts;
	/**业务拒绝处理码*/
	private String rjctCd;
	/**业务拒绝信息*/
	private String rjctInfo;
	/**结束标识00：进行中 01：交易结束*/
	private String endFlag;

	public static final String INDEALING = "00";// 进行中
	public static final String DEALED = "01";// 交易结束

	public RspnMsg(String sts, String rjctCd){
		this.sts = sts;
		if (StringUtils.isNotNull(rjctCd)){
			setRjctCd(rjctCd);
		}
	}

	public RspnMsg(String sts, String rjctCd, String info) {
//		this(sts, rjctCd);
//		this.rjctInfo = info;
		this(sts, rjctCd);
		int lastindex = info.lastIndexOf("::");
		String substr = info.substring(0, lastindex);
		int sedindex = substr.lastIndexOf("::");
		this.rjctInfo = info.substring(sedindex+2, lastindex);
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}
	/**
	 * 业务拒绝处理码
	 *
	 * @param rjctInfo
	 */
	public String getRjctCd() {
		return rjctCd;
	}
	/**
	 * 业务拒绝处理码
	 *
	 * @param rjctInfo
	 */
	public void setRjctCd(String rjctCd) {
		this.rjctCd = rjctCd;
		this.rjctInfo = rjctCd;
		this.endFlag = DEALED;
	}
	/**
	 * 业务信息
	 *
	 * @param rjctInfo
	 */
	public String getRjctInfo() {
		return rjctInfo;
	}
	/**
	 * 业务信息
	 *
	 * @param rjctInfo
	 */
	public void setRjctInfo(String rjctInfo) {
		this.rjctInfo = rjctInfo;
	}

	public String getEndFlag() {
		return endFlag ;
	}

	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag ;
	}

}
